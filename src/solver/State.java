package solver;

import java.util.Arrays;

public class State {

    public byte[] permutation;
    public int nPieces = 11; //

    public State(byte[] permutation) {
        this.permutation = permutation;
    }

    public State multiply(State move) {
        byte[] permutation = new byte[nPieces];
        for (int i = 0; i < nPieces; i++) {
            permutation[i] = this.permutation[move.permutation[i]];
        }

        return new State(permutation);
    }

    public void copy(State state) {
        permutation = Arrays.copyOf(state.permutation, nPieces);
    }
}
