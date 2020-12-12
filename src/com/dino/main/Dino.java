package com.dino.main;

import com.dino.solver.Search;
import com.dino.solver.State;

import java.util.ArrayList;
import java.util.Arrays;

public class Dino {

    private final State state;

    public Dino() {
        state = new State();
    }

    public Dino(byte[] statePermutation) {
        state = new State(statePermutation);
    }

    public void applySequence(String seq) {
        state.applySequence(seq);
    }

    public boolean isSolved() {
        for (int i = 0; i < 11; i++) {
            if (state.permutation[i] != i) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Byte> getSolution() {
        return Search.search(state);
    }

    public byte[] getStatePermutation() {
        return state.permutation;
    }

    public void setStatePermutation(byte[] statePermutation) {
        state.permutation = statePermutation;
    }

    @Override
    public String toString() {
        return "Dino{" +
                "state=" + (Arrays.toString(state.permutation)) +
                '}';
    }
}
