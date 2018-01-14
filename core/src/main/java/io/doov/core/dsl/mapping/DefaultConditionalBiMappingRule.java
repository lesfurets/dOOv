package io.doov.core.dsl.mapping;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.BiMappingRule;
import io.doov.core.dsl.lang.ConditionalBiMappingRule;
import io.doov.core.dsl.lang.MappingRegistry;
import io.doov.core.dsl.lang.ValidationRule;
import io.doov.core.dsl.meta.MetadataVisitor;

public class DefaultConditionalBiMappingRule<I, J, O, S extends FieldId & DslId, T extends FieldId & DslId>
                implements ConditionalBiMappingRule<I, J, O, S, T> {

    private final ValidationRule<S> validationRule;
    private final BiMappingRule<I, J, O, S, T> mappingRule;

    public DefaultConditionalBiMappingRule(ValidationRule<S> validationRule, BiMappingRule<I, J, O, S, T> mappingRule) {
        this.validationRule = validationRule;
        this.mappingRule = mappingRule;
    }

    @Override
    public void executeOn(DslModel<S> inModel, DslModel<T> outModel) {
        if (validationRule.executeOn(inModel).isTrue()) {
            mappingRule.executeOn(inModel, outModel);
        }
    }

    @Override
    public BiMappingRule<I, J, O, S, T> registerOn(MappingRegistry<S, T> registry) {
        registry.register(this);
        return this;
    }

    @Override
    public ValidationRule<S> validation() {
        return validationRule;
    }

    @Override
    public String readable() {
        // TODO
        return "if " + validationRule.readable() + " then " + mappingRule.readable();
    }

    @Override
    public void accept(MetadataVisitor visitor, int depth) {
        // TODO
    }
}
