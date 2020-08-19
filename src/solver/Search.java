package solver;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import static solver.FileUtils.loadFileToArray;
import static solver.Generator.Time.timestampOf;

public class Search {

    public static byte[] distances = new byte[19958400];

    private static final State m1 = new State(new byte[]{6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10});
    private static final State m2 = new State(new byte[]{1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10});
    private static final State m3 = new State(new byte[]{0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10});
    private static final State m4 = new State(new byte[]{0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10});

    private static final State m5 = new State(new byte[]{0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10});
    private static final State m6 = new State(new byte[]{0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7});

    private static final State[] moves = new State[]{
            m1, //1
            m1.multiply(m1), //-1
            m2, //2
            m2.multiply(m2), //-2
            m3, //3
            m3.multiply(m3), //-3
            m4, //4
            m4.multiply(m4), //-4
            m5, //5
            m5.multiply(m5), //-5
            m6, //6
            m6.multiply(m6) //-6
    };

    static int[] names = {1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        loadFileToArray(distances, "distances.dino");
        //State s = new State(new byte[]{2, 6, 5, 0, 9, 8, 3, 1, 10, 7, 4});
        State s = new State(new byte[]{4, 10, 3, 8, 1, 5, 0, 9, 7, 2, 6});
        search(s);
        System.out.println(timestampOf(System.currentTimeMillis() - a));
    }

    //TODO: implement IDA* to search?
    public static void search(State target) {
        while (true) {
            int currentIndex = IndexMapping.evenPermutationToIndex(target.permutation);
            if (distances[currentIndex] == 0) {
                System.out.println("solucao encontrada!");
                return;
            }

            for (int i = 0; i < moves.length; i++) {
                State aux = target.multiply(moves[i]);
                int nextIndex = IndexMapping.evenPermutationToIndex(aux.permutation);
                if (distances[nextIndex] < distances[currentIndex]) {
                    System.out.print(names[i] + " ");
                    target.copy(aux);
                    break;
                }
            }
        }
    }


}
