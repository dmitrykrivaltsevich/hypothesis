package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.InitialHypothesisStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocalRepositoryUnitTest {

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(new LocalRepository().isEmpty());
    }

    @Test
    public void testGet() throws Exception {
        assertNotNull(new LocalRepository().get());
    }

    @Test
    public void testAdd() throws Exception {
        LocalRepository localRepository = new LocalRepository();

        int initialSize = localRepository.size();
        localRepository.add(new InitialHypothesisStream());

        assertEquals(initialSize + 1, localRepository.size());
    }
}