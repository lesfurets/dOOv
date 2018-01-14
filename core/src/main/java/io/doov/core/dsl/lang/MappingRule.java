package io.doov.core.dsl.lang;

import io.doov.core.FieldId;
import io.doov.core.dsl.DslId;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.meta.SyntaxTree;

public interface MappingRule<S extends FieldId & DslId, T extends FieldId & DslId> extends Readable, SyntaxTree {

    void executeOn(DslModel<S> inModel, DslModel<T> outModel);

    MappingRule<S, T> registerOn(MappingRegistry<S, T> registry);

}
