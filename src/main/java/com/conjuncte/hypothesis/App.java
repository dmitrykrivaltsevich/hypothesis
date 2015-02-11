package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Hello world!
 */
public class App {

    // todo: add initial hypothesises
    private Queue<Hypothesis> hypothesises = new LinkedList<Hypothesis>();


    public static void main(String[] args) {
        new App().checkAllHypothesises(FreeRadixRegister.of(49));
    }

    private void checkAllHypothesises() {
    }
}
