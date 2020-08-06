package solver;

import java.util.ArrayList;
import java.util.Random;

public class SearchTest {

    public static final int[] MOVES_NAMES = {
            1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6
    };

    public static final Random RND = new Random();

    public static void main(String[] args) {
        int counter = 0;
        long a = System.currentTimeMillis();
        while (System.currentTimeMillis() - a <= 1000L) {
            randomStateTest();
            counter++;
        }
        System.out.println(counter + " random states in around 1 second");

//        System.out.println(randomStateScramble());
//        System.out.println(solution(new State(new byte[]{8,4,0,1,5,7,10,6,3,2,9})));
    }

    public static String solution(State target) {
        StringBuilder solution = new StringBuilder();
        for (int index : Search.search(target)) {
            solution.append(MOVES_NAMES[index]).append(" ");
        }
        return solution.toString();
    }

    public static String randomStateScramble() {
        int index;

        do {
            index = RND.nextInt(Search.N_DISTANCES_IN_FILE);
        } while (Search.distance[index] < 10);

        ArrayList<Integer> solution = Search.search(new State(IndexMapping.indexToEvenPermutation(index, 11)));
        StringBuilder scramble = new StringBuilder();
        for (int i = solution.size() - 1; i >= 0; i--) {
            scramble.append(MOVES_NAMES[solution.get(i)] * -1).append(" ");
        }

        return scramble.toString();
    }

    private static void randomStateTest() {
        int index;

        do {
            index = RND.nextInt(Search.N_DISTANCES_IN_FILE);
        } while (Search.distance[index] < 10);

        Search.search(new State(IndexMapping.indexToEvenPermutation(index, 11)));
    }
}
