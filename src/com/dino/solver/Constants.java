package com.dino.solver;

class Constants {

    static final int N_PERMUTATIONS = 19958400;
    static final int N_PIECES = 11;

    static final State M1 = new State(new byte[]{6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10});
    static final State M2 = new State(new byte[]{1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10});
    static final State M3 = new State(new byte[]{0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10});
    static final State M4 = new State(new byte[]{0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10});

    static final State M5 = new State(new byte[]{0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10});
    static final State M6 = new State(new byte[]{0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7});

    //moves order: 1 -1 2 -2 3 -3 4 -4 5 -5 6 -6 (total: 12)
    static final State[] MOVES = {
            M1, M1.multiply(M1),
            M2, M2.multiply(M2),
            M3, M3.multiply(M3),
            M4, M4.multiply(M4),
            M5, M5.multiply(M5),
            M6, M6.multiply(M6)
    };

    static final byte[] DISTANCES = new byte[N_PERMUTATIONS];
    static final byte[] NAMES = {1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};
}
