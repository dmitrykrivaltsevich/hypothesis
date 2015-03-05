package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.Stream;

import javax.annotation.Nonnull;

public class SynchronizedRepository<T>
        implements Repository<T> {

    private Repository<T> delegate;

    public SynchronizedRepository(@Nonnull Repository<T> repository) {
        assert repository != null; // todo: use factory
        this.delegate = repository;
    }

    @Override
    public synchronized boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Nonnull
    @Override
    public synchronized T get() {
        return delegate.get();
    }

    @Override
    public synchronized void add(@Nonnull Stream<T> stream) {
        assert stream != null;
        delegate.add(stream);
    }

    @Override
    public synchronized int size() {
        return delegate.size();
    }
}
