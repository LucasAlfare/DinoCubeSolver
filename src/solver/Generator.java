package solver;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class Generator {

    /* 11!/2 = 19958400 */
    public static final int N_PERMS = (11 * 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2) / 2;
    public static final int N_PIECES = 11;
    public static final int N_MOVES = 6 * 2;
    static State[] moves;
    static int[][] table;
    static int[] distance;
    static int depth = 0;
    static int nVisited;

    /*
    ----ÍNDICES DAS PEÇAS----
     0: WB
     1: WR
     2: WG
     3: WO

     4: RG
     5: GO
     6: OB
     7: BR

     * x2 *

     8: YG
     9: YR
    10: YB

    peça ignorada (11): YO

    ----NOTAÇÃO DOS MOVIMENTOS----
    M1: ULB
    M2: UBR
    M3: URF
    M4: UFL

    M5: DFR
    M6: DRB

    OBS.: DLF e DBL não são utilizados pois afetam a peça de
    índice 11, que deve ser ignorada.
     */

    static {
        //auxiliar para checar tempo decorrido
        long a = System.currentTimeMillis();

        System.out.println("initalizing moves...");
        State m1 = new State(new byte[]{6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10});
        State m2 = new State(new byte[]{1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10});
        State m3 = new State(new byte[]{0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10});
        State m4 = new State(new byte[]{0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10});

        State m5 = new State(new byte[]{0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10});
        State m6 = new State(new byte[]{0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7});

        moves = new State[]{
                m1, m1.multiply(m1),
                m2, m2.multiply(m2),
                m3, m3.multiply(m3),
                m4, m4.multiply(m4),
                m5, m5.multiply(m5),
                m6, m6.multiply(m6)
        };

        System.out.println("moves initialized in " + (System.currentTimeMillis() - a) + "ms.");

        System.out.println("allocating memory to the table...");
        a = System.currentTimeMillis();
        table = new int[N_PERMS][N_MOVES];
        System.out.println("memory allocated in " + (System.currentTimeMillis() - a) + "ms.");

        State s = new State(null);
        System.out.println("beggining index mappings...");
        a = System.currentTimeMillis();
        for (int i = 0; i < N_PERMS; i++) {
            s.permutation = IndexMapping.indexToEvenPermutation(i, N_PIECES);
            for (int j = 0; j < moves.length; j++) {
                table[i][j] = IndexMapping.evenPermutationToIndex(s.multiply(moves[j]).permutation);
            }
        }
        System.out.println("index mapping finished in " + (System.currentTimeMillis() - a) + "ms.");

        distance = new int[N_PERMS];
        //TODO: conseiderar 0 como o valor "não visitado" e evitar preencher c/ -1?
        Arrays.fill(distance, -1);
        distance[0] = 0;

        System.out.println("starting mapping distances...");
        System.out.println("----------------------------------------");
        a = System.currentTimeMillis();
        do {
            nVisited = 0;
            for (int i = 0; i < N_PERMS; i++) {
                if (distance[i] == depth) {
                    for (int j = 0; j < N_MOVES; j++) {
                        if (distance[table[i][j]] == -1) {
                            distance[table[i][j]] = depth + 1;
                            nVisited++;
                        }
                    }
                }
            }

            depth++;
            System.out.println(nVisited + " positions at distance/depth " + depth);
        } while (nVisited > 0);
        System.out.println("finished distance mapping in " + (System.currentTimeMillis() - a) + "ms.");
        System.out.println("----------------------------------------");

        System.out.println("writing distances to a file...");
        a = System.currentTimeMillis();
        writeArrayToFile(distance, "dino.depth");
        System.out.println("depth file wrote in " + (System.currentTimeMillis() - a) + "ms.");
    }

    static void writeArrayToFile(int[] ints, String filepath) {
        try {
            RandomAccessFile rnd = new RandomAccessFile(filepath, "rw");
            FileChannel file = rnd.getChannel();
            ByteBuffer buf = file.map(FileChannel.MapMode.READ_WRITE, 0, 4 * ints.length);
            for (int i : ints) {
                buf.putInt(i);
            }
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) { /* pass */ }

    /*
        ----------------------------------------
        12 positions at depth 1
        88 positions at depth 2
        576 positions at depth 3
        3576 positions at depth 4
        20288 positions at depth 5
        101255 positions at depth 6
        431446 positions at depth 7
        1534012 positions at depth 8
        4057104 positions at depth 9
        6460024 positions at depth 10
        5009052 positions at depth 11
        1877618 positions at depth 12
        411142 positions at depth 13
        49800 positions at depth 14
        2350 positions at depth 15
        56 positions at depth 16
        0 positions at depth 17
        finished depths in 2953ms.
        ----------------------------------------
    */
}
