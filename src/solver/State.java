package solver;

import java.util.Arrays;

public class State {

    public byte[] permutation;
    public static final int N_PIECES = 11;

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

    public void copy(State state) {
        permutation = Arrays.copyOf(state.permutation, N_PIECES);
    }
}