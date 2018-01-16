/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.core.dsl.impl;

import static io.doov.core.dsl.meta.ast.AstVisitorUtils.astToString;

import java.util.Locale;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultValidationRule<F extends FieldId & DslId> implements ValidationRule<F> {

    private final StepWhen<F> stepWhen;
    private final String message;
    private final boolean shortCircuit;

    protected DefaultValidationRule(StepWhen<F> stepWhen) {
        this(stepWhen, null, true);
    }

    protected DefaultValidationRule(StepWhen<F> stepWhen, String message) {
        this(stepWhen, message, true);
    }

    public DefaultValidationRule(StepWhen<F> stepWhen, String message, boolean shortCircuit) {
        this.stepWhen = stepWhen;
        this.message = message;
        this.shortCircuit = shortCircuit;
    }
    
    protected boolean isShortCircuit() {
        return shortCircuit;
    }

    protected StepWhen getStepWhen() {
        return stepWhen;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ValidationRule<F> withMessage(String message) {
        return new DefaultValidationRule<>(stepWhen, message);
    }

    @Override
    public ValidationRule<F> withShortCircuit(boolean shortCircuit) {
        return new DefaultValidationRule<>(stepWhen, message, shortCircuit);
    }

    @Override
    public Result executeOn(DslModel<F> model) {
        final DefaultContext context = new DefaultContext(shortCircuit, stepWhen.stepCondition().getMetadata());
        boolean valid = stepWhen.stepCondition().predicate().test(model, context);
        String readable = valid ? null : (message == null ? stepWhen.stepCondition().readable() : message);
        return new DefaultResult(valid, readable, context);
    }

    @Override
    public ValidationRule<F> registerOn(RuleRegistry<F> registry) {
        registry.register(this);
        return this;
    }

    @Override
    public String readable() {
        return astToString(this, Locale.getDefault());
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        visitor.start(this, depth);
        stepWhen.accept(visitor, depth);
        visitor.visit(this, depth);
        visitor.end(this, depth);
    }

    @Override
    public Metadata getRootMetadata() {
        if (stepWhen == null)
            return null;
        if (stepWhen.stepCondition() == null)
            return null;
        return stepWhen.stepCondition().getMetadata();
    }

}
