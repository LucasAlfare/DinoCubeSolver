package com.dino.solver;

import java.util.Arrays;

import static com.dino.solver.Constants.MOVES;
import static com.dino.solver.Constants.N_PIECES;

public class State {

    public byte[] permutation;

    public State() {
        permutation = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    public State(byte[] permutation) {
        this.permutation = permutation;
    }

    public State multiply(State move) {
        byte[] permutation = new byte[N_PIECES];
        for (int i = 0; i < N_PIECES; i++) {
            permutation[i] = this.permutation[move.permutation[i]];
        }

        return new State(permutation);
    }

    public void applySequence(String seq) {
        String[] names = seq.split(" ");
        for (String n : names) {
            applyMoves(Byte.parseByte(n));
        }
    }

    public void applyMoves(int... names) {
        State aux = new State(permutation);
        for (int n : names) {
            if (n == 1) {
                aux = aux.multiply(MOVES[0]);
            } else if (n == -1) {
                aux = aux.multiply(MOVES[1]);
            } else if (n == 2) {
                aux = aux.multiply(MOVES[2]);
            } else if (n == -2) {
                aux = aux.multiply(MOVES[3]);
            } else if (n == 3) {
                aux = aux.multiply(MOVES[4]);
            } else if (n == -3) {
                aux = aux.multiply(MOVES[5]);
            } else if (n == 4) {
                aux = aux.multiply(MOVES[6]);
            } else if (n == -4) {
                aux = aux.multiply(MOVES[7]);
            } else if (n == 5) {
                aux = aux.multiply(MOVES[8]);
            } else if (n == -5) {
                aux = aux.multiply(MOVES[9]);
            } else if (n == 6) {
                aux = aux.multiply(MOVES[10]);
            } else if (n == -6) {
                aux = aux.multiply(MOVES[11]);
            } else {
                System.err.println("Can't apply the move of label " + n + ". Only the numbers -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6 are allowed.");
                break;
            }
        }

        this.copy(aux);
    }

    public void copy(State state) {
        permutation = Arrays.copyOf(state.permutation, N_PIECES);
    }
}
