/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.core.dsl;

import static io.doov.core.dsl.mapping.MappingRegistry.mappings;
import static io.doov.core.dsl.meta.LeafMetadata.falseMetadata;
import static io.doov.core.dsl.meta.LeafMetadata.trueMetadata;
import static java.util.Arrays.asList;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.doov.core.dsl.field.types.NumericFieldInfo;
import io.doov.core.dsl.impl.*;
import io.doov.core.dsl.mapping.MappingRegistry;
import io.doov.core.dsl.lang.MappingRule;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.lang.StepWhen;
import io.doov.core.dsl.mapping.*;

/**
 * Entry point of the DSL.
 * <p>
 * All validation rules will start with the {@link #when(StepCondition)} method. Then you can create a step
 * condition either by referencing a {@link io.doov.core.FieldInfo} that you generated (in the example below it's
 * <code>userEmail</code>) or by using a composition method like {@link #matchAll(StepCondition...)}.
 * </p>
 * <pre><code class='java'>DOOV.when(userEmail().matches("...")).validate();</code></pre>
 */
public class DOOV {

    private DOOV() {
        // static
    }

    /**
     * Returns a step when with the given condition. This is the main entry point of the DSL.
     *
     * @param condition the condition
     * @return the step when
     */
    public static StepWhen when(StepCondition condition) {
        return new DefaultStepWhen(condition);
    }

    /**
     * Returns a condition that is always true.
     *
     * @return the step condition
     */
    public static StepCondition alwaysTrue() {
        return new DefaultStepCondition(trueMetadata(), (model, context) -> true);
    }

    /**
     * Returns a condition that is always false.
     *
     * @return the step condition
     */
    public static StepCondition alwaysFalse() {
        return new DefaultStepCondition(falseMetadata(), (model, context) -> false);
    }

    /**
     * Returns an integer condition that returns the number of given conditions that evaluates to true.
     *
     * @param steps the steps to match
     * @return the integer condition
     */
    public static IntegerCondition count(StepCondition... steps) {
        return LogicalNaryCondition.count(asList(steps));
    }

    /**
     * See {@link LogicalNaryCondition#matchAny(List)}
     *
     * @param steps the steps to match
     * @return the step condition
     * @see LogicalNaryCondition#matchAny(List)
     */
    public static StepCondition matchAny(StepCondition... steps) {
        return LogicalNaryCondition.matchAny(asList(steps));
    }

    /**
     * See {@link LogicalNaryCondition#matchAny(List)}
     *
     * @param dslFields             stream of fields
     * @param stepConditionFunction condition to apply
     * @return step condition
     */
    public static StepCondition matchAny(Stream<? extends DslField<?>> dslFields,
            Function<DefaultCondition, StepCondition> stepConditionFunction) {
        return LogicalNaryCondition.matchAny(dslFields
                .filter(Objects::nonNull)
                .map(DslField::getDefaultCondition)
                .map(stepConditionFunction)
                .collect(Collectors.toList()));
    }

    /**
     * See {@link LogicalNaryCondition#matchAll(List)}
     *
     * @param steps the steps to match
     * @return the step condition
     * @see LogicalNaryCondition#matchAll(List)
     */
    public static StepCondition matchAll(StepCondition... steps) {
        return LogicalNaryCondition.matchAll(asList(steps));
    }

    /**
     * See {@link LogicalNaryCondition#matchAll(List)}
     *
     * @param dslFields             stream of fields
     * @param stepConditionFunction condition to apply
     * @return step condition
     */
    public static StepCondition matchAll(Stream<? extends DslField<?>> dslFields,
            Function<DefaultCondition, StepCondition> stepConditionFunction) {
        return LogicalNaryCondition.matchAll(dslFields
                .filter(Objects::nonNull)
                .map(DslField::getDefaultCondition)
                .map(stepConditionFunction)
                .collect(Collectors.toList()));
    }

    /**
     * See {@link LogicalNaryCondition#matchNone(List)}
     *
     * @param steps the steps to match
     * @return the step condition
     * @see LogicalNaryCondition#matchNone(List)
     */
    public static StepCondition matchNone(StepCondition... steps) {
        return LogicalNaryCondition.matchNone(asList(steps));
    }

    /**
     * See {@link LogicalNaryCondition#matchNone(List)}
     *
     * @param dslFields             stream of fields
     * @param stepConditionFunction condition to apply
     * @return step condition
     */
    public static StepCondition matchNone(Stream<? extends DslField<?>> dslFields,
            Function<DefaultCondition, StepCondition> stepConditionFunction) {
        return LogicalNaryCondition.matchNone(dslFields
                .filter(Objects::nonNull)
                .map(DslField::getDefaultCondition)
                .map(stepConditionFunction)
                .collect(Collectors.toList()));
    }

    /**
     * Start defining a mapping
     *
     * @param inFieldInfo in field
     * @param <I>         in type
     * @return map step
     */
    public static <I> SimpleStepMap<I> map(DslField<I> inFieldInfo) {
        return new SimpleStepMap<>(inFieldInfo);
    }

    /**
     * Start defining a bi mapping
     *
     * @param inFieldInfo  in field
     * @param in2FieldInfo in field 2
     * @param <I>          in type
     * @param <J>          in type 2
     * @return bi map step
     */
    public static <I, J> BiStepMap<I, J> map(DslField<I> inFieldInfo, DslField<J> in2FieldInfo) {
        return new BiStepMap<>(inFieldInfo, in2FieldInfo);
    }

    /**
     * Start defining a nary mapping
     *
     * @param inFieldInfos list of in fields
     * @return nary map step
     */
    public static NaryStepMap map(DslField<?>... inFieldInfos) {
        return new NaryStepMap(Arrays.asList(inFieldInfos));
    }

    /**
     * Start defining a nary mapping
     *
     * @param inFieldInfos list of in fields
     * @return nary map step
     */
    public static NaryStepMap map(Stream<? extends DslField<?>> inFieldInfos) {
        return new NaryStepMap(inFieldInfos.collect(Collectors.toList()));
    }

    /**
     * Start defining a value mapping
     *
     * @param valueSupplier value supplier
     * @param <I>           value type
     * @return value map step
     */
    public static <I> StaticStepMap<I> map(Supplier<I> valueSupplier) {
        return new StaticStepMap<>(valueSupplier);
    }

    /**
     * Start defining a value mapping
     *
     * @param value value
     * @param <I>   value type
     * @return value step map
     */
    public static <I> StaticStepMap<I> map(I value) {
        return new StaticStepMap<>(() -> value);
    }

    /**
     * Create an array of mapping rules from a range of index
     *
     * @param startInclusive      inclusive start index, inclusive
     * @param endExclusive        exclusive end index
     * @param mappingRuleFunction index to mapping rule function
     * @return mapping registry containing rules
     */
    public static MappingRegistry mapRange(int startInclusive, int endExclusive,
            Function<Integer, MappingRule> mappingRuleFunction) {
        return mappings(IntStream.range(startInclusive, endExclusive)
                .mapToObj(mappingRuleFunction::apply)
                .toArray(MappingRule[]::new));
    }

    /**
     * Create an array of mapping rules from a stream of fields
     *
     * @param fieldStream         field stream
     * @param mappingRuleFunction field to mapping rule function
     * @return mapping registry containing rules
     */
    public static MappingRegistry mapFor(Stream<? extends DslField<?>> fieldStream,
            Function<DslField<?>, MappingRule> mappingRuleFunction) {
        return mappings(fieldStream
                .filter(Objects::nonNull)
                .map(mappingRuleFunction::apply)
                .toArray(MappingRule[]::new));
    }

    /**
     * Returns the field in the position
     *
     * @param fieldStream field stream
     * @param index       field list position
     * @param <T>         field info type
     * @return field info
     */
    public static <T extends DslField<?>> T fieldInPosition(Stream<T> fieldStream, int index) {
        return fieldStream
                .filter(Objects::nonNull)
                .filter(f -> f.id().position() == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Field with position " + index + " not found."));
    }

    /**
     * See {@link NumericCondition#min(List)}
     *
     * @param <N>    the type of the field infos
     * @param fields the fields to minimize
     * @return the numeric condition
     * @see NumericCondition#min(List)
     */
    @SafeVarargs
    public static <N extends Number> NumericCondition<N> min(NumericFieldInfo<N>... fields) {
        return Arrays.stream(fields)
                .filter(Objects::nonNull)
                .findFirst()
                .map(NumericFieldInfo::getNumericCondition)
                .map(c -> c.min(Arrays.asList(fields)))
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * See {@link NumericCondition#sum(List)}
     *
     * @param <N>    the type of the field infos
     * @param fields the fields to sum
     * @return the numeric condition
     * @see NumericCondition#sum(List)
     */
    @SafeVarargs
    public static <N extends Number> NumericCondition<N> sum(NumericFieldInfo<N>... fields) {
        return Arrays.stream(fields)
                .filter(Objects::nonNull)
                .findFirst()
                .map(NumericFieldInfo::getNumericCondition)
                .map(c -> c.sum(Arrays.asList(fields)))
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * See {@link NumericCondition#sum(List)}
     *
     * @param <N>        the type of the field infos
     * @param conditions the conditions to sum
     * @return the numeric condition
     * @see NumericCondition#sum(List)
     */
    @SafeVarargs
    public static <N extends Number> NumericCondition<N> sum(NumericCondition<N>... conditions) {
        return Arrays.stream(conditions)
                .filter(Objects::nonNull)
                .findFirst()
                .map(c -> c.sumConditions(Arrays.asList(conditions)))
                .orElseThrow(IllegalArgumentException::new);
    }

}
