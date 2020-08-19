package solver;

import java.util.Arrays;

public class State {

    public byte[] permutation;
    public static final int N_PIECES = 11;

    private static final State M1 = new State(new byte[]{6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10});
    private static final State M2 = new State(new byte[]{1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10});
    private static final State M3 = new State(new byte[]{0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10});
    private static final State M4 = new State(new byte[]{0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10});

    private static final State M5 = new State(new byte[]{0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10});
    private static final State M6 = new State(new byte[]{0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7});

    //moves order: 1 -1 2 -2 3 -3 4 -4 5 -5 6 -6 (total: 12)
    public static final State[] MOVES = {
            M1, M1.multiply(M1),
            M2, M2.multiply(M2),
            M3, M3.multiply(M3),
            M4, M4.multiply(M4),
            M5, M5.multiply(M5),
            M6, M6.multiply(M6)
    };

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
            applyMoves(Integer.parseInt(n));
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
            }
        }

        this.copy(aux);
    }

    public void copy(State state) {
        permutation = Arrays.copyOf(state.permutation, N_PIECES);
    }
}
