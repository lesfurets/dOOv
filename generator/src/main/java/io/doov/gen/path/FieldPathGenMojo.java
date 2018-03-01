/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.gen.path;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.*;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import io.doov.gen.utils.ClassLoaderUtils;

@Mojo(name = "generate-fields", defaultPhase = GENERATE_SOURCES,
                requiresDependencyResolution = ResolutionScope.COMPILE,
                requiresDependencyCollection = ResolutionScope.COMPILE,
                threadSafe = true)
public class FieldPathGenMojo extends AbstractMojo {

    @Parameter(required = true, defaultValue = "${basedir}/src/generated/java")
    private File outputDirectory;

    @Parameter(required = true, property = "project.build.outputDirectory")
    private File buildDirectory;

    @Parameter(required = true, defaultValue = "${basedir}/target")
    private File outputResourceDirectory;

    @Parameter(required = true)
    private String sourceClass;

    @Parameter(required = true, readonly = true, property = "project")
    private MavenProject project;

    @Parameter(required = true)
    private String packageFilter;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final URLClassLoader classLoader = ClassLoaderUtils.getUrlClassLoader(project);
        try {
            Class<?> modelClazz = Class.forName(sourceClass, true, classLoader);
            List<VisitorPath> process = process(modelClazz, packageFilter);
            for (VisitorPath path : process) {
                System.out.println(path.getFieldId().code() + " \'" + path.getReadable() + "\' " + path.getGetMethod().getGenericReturnType().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<VisitorPath> process(Class<?> projetClass, String filter) throws Exception {
        final List<VisitorPath> collected = new ArrayList<>();
        new ModelVisitor(getLog()).visitModel(projetClass, new Visitor(projetClass, collected), filter);
        return collected;
    }


}
