/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.sample.benchmark.ui;

import static org.jfree.chart.ChartFactory.createTimeSeriesChart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.validation.Validation;
import javax.validation.Validator;

import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import io.doov.core.FieldModel;
import io.doov.sample.benchmark.BenchmarkRule;
import io.doov.sample.model.SampleModel;
import io.doov.sample.model.SampleModels;
import io.doov.sample.validation.RulesOld;

public class BenchmarkUI {

    public static void main(String[] args) {
        final TimeSeriesCollection dataSet = new TimeSeriesCollection();
        final TimeSeries series_doov = new TimeSeries("dOOv");
        final TimeSeries series_bareMetal = new TimeSeries("Bare Metal");
        final TimeSeries series_beanValidation = new TimeSeries("Bean Validation");
        dataSet.addSeries(series_doov);
        dataSet.addSeries(series_bareMetal);
        dataSet.addSeries(series_beanValidation);

        final JFrame f = new JFrame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(new Dimension(1280, 1024));
        f.getContentPane().add(new ChartPanel(createTimeSeriesChart("dOOv benchmark", "time", "hits", dataSet)), BorderLayout.CENTER);
        f.setVisible(true);

        final BareMetalRunnable bareRunnable = new BareMetalRunnable();
        final DoovRunnable doovRunnable = new DoovRunnable();
        final BeanValidationRunnable beanValidationRunnable = new BeanValidationRunnable();
        new Thread(beanValidationRunnable).start();
        new Thread(bareRunnable).start();
        new Thread(doovRunnable).start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    final Second second = new Second();
                    series_doov.addOrUpdate(second, doovRunnable.hits.get());
                    series_bareMetal.addOrUpdate(second, bareRunnable.hits.get());
                    series_beanValidation.addOrUpdate(second, beanValidationRunnable.hits.get());
                });
            }
        }, 0, 200);
    }

    static class BareMetalRunnable implements Runnable {
        final AtomicInteger hits = new AtomicInteger();

        @Override
        public void run() {
            for (;;) {
                final SampleModel model = SampleModels.sample();
                RulesOld.validateAccount(model.getUser(), model.getAccount(), model.getConfiguration());
                hits.incrementAndGet();
            }
        }
    }

    static class DoovRunnable implements Runnable {
        final AtomicInteger hits = new AtomicInteger();

        @Override
        public void run() {
            for (;;) {
                final FieldModel model = SampleModels.wrapper();
                BenchmarkRule.COUNTRY.executeOn(model).isTrue();
                hits.incrementAndGet();
            }
        }
    }

    static class BeanValidationRunnable implements Runnable {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final AtomicInteger hits = new AtomicInteger();

        @Override
        public void run() {
            for (;;) {
                final SampleModel model = SampleModels.sample();
                validator.validate(model);
                hits.incrementAndGet();
            }
        }

    }

}
