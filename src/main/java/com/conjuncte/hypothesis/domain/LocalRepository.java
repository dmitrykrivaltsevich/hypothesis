package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.InitialHypothesisStream;
import com.conjuncte.hypothesis.container.Stream;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// todo: currently not synchronized, add sync later. Perhaps, like a "wrapper" (new SyncRepo(new LocalRepo)).
public class LocalRepository
        implements Repository<Hypothesis> {

    private static final int INITIAL_QUEUE_CAPACITY = 100;

    private Queue<Stream<Hypothesis>> hypothesises = new PriorityQueue<Stream<Hypothesis>>(
            INITIAL_QUEUE_CAPACITY,
            new Comparator<Stream<Hypothesis>>() {
                @Override
                public int compare(Stream<Hypothesis> streamOne, Stream<Hypothesis> streamTwo) {
                    return -streamOne.compareTo(streamTwo);
                }
            }
    ) {{
        add(new InitialHypothesisStream());
    }};

    private Stream<Hypothesis> currentStream;

    @Override
    public boolean isEmpty() {
        if (currentStream == null || !currentStream.hasNext()) {
            currentStream = hypothesises.poll();
        }
        return currentStream != null && currentStream.hasNext();
    }

    @Nonnull
    @Override
    public Hypothesis get() {
        return null;
    }

    @Override
    public void add(@Nonnull Hypothesis data) {

    }
}
