package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.InitialHypothesisStream;
import com.conjuncte.hypothesis.container.Stream;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// todo: currently not synchronized, add sync later. Perhaps, as a "wrapper" (new SyncRepo(new LocalRepo)).
public class LocalRepository
        implements Repository<Hypothesis> {

    private static final int INITIAL_QUEUE_CAPACITY = 100;

    private Queue<Stream<Hypothesis>> hypothesises = new PriorityQueue<>(
            INITIAL_QUEUE_CAPACITY,
            new Comparator<Stream<Hypothesis>>() {
                @Override
                public int compare(Stream<Hypothesis> streamOne, Stream<Hypothesis> streamTwo) {
                    return -streamOne.compareTo(streamTwo);
                }
            }
    );

    private Stream<Hypothesis> currentStream;

    public LocalRepository() {
        this(new InitialHypothesisStream());
    }

    public LocalRepository(@Nonnull Stream<Hypothesis> hypothesisStream) {
        assert hypothesisStream != null; // todo: create a factory
        hypothesises.add(hypothesisStream);
    }

    @Override
    public boolean isEmpty() {
        initCurrentStream();
        return currentStream == null || !currentStream.hasNext();
    }

    @Nonnull
    @Override
    public Hypothesis get() {
        initCurrentStream();
        assert currentStream != null;
        assert currentStream.hasNext();

        Hypothesis hypothesis = currentStream.next();

        assert hypothesis != null;
        return hypothesis;
    }

    @Override
    public void add(@Nonnull Stream<Hypothesis> stream) {
        assert stream != null;
        hypothesises.add(stream);
    }

    @Override
    public int size() {
        return hypothesises.size();
    }

    private void initCurrentStream() {
        if (currentStream == null || !currentStream.hasNext()) {
            currentStream = hypothesises.poll();
        }
    }
}
