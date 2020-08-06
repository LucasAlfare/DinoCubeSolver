package solver;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Search {

    public static final int N_DISTANCES_IN_FILE = 19958400;
    public static final int[] distance;

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

    static {
        distance = loadDepthFile("dino.depth");
    }

    public static ArrayList<Integer> search(State testing) {
        ArrayList<Integer> movesIndexes = new ArrayList<>();

        while (true) {
            int currentIndex = IndexMapping.evenPermutationToIndex(testing.permutation);
            if (distance[currentIndex] == 0) {
                break;
            }

            for (int i = 0; i < moves.length; i++) {
                State next = testing.multiply(moves[i]);
                int nextIndex = IndexMapping.evenPermutationToIndex(next.permutation);
                if (distance[nextIndex] == distance[currentIndex] - 1) {
                    testing.copy(next);
                    movesIndexes.add(i);
                    break;
                }
            }
        }

        return movesIndexes;
    }

    @SuppressWarnings("SameParameterValue")
    private static int[] loadDepthFile(String pathname) {
        try (FileInputStream stream = new FileInputStream(pathname)) {
            FileChannel inChannel = stream.getChannel();
            ByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            int[] res = new int[N_DISTANCES_IN_FILE];
            buffer.order(ByteOrder.BIG_ENDIAN);
            IntBuffer intBuffer = buffer.asIntBuffer();
            intBuffer.get(res);
            stream.close();
            inChannel.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new int[0]; //deve nunca acontecer...
    }
}
