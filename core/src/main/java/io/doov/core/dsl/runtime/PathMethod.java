package io.doov.core.dsl.runtime;

public interface PathMethod<T, R> {

    R get(T link);

    void set(T link, R value);

    R create(T link);

}
