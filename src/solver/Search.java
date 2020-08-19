package solver;

import static solver.FileUtils.loadFileToArray;
import static solver.Generator.Time.timestampOf;

public class Search {

    public static byte[] distances = new byte[19958400];
    static int[] names = {1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};

    static {
        long a = System.currentTimeMillis();
        loadFileToArray(distances, "distances.dino");
        State s = new State();
        s.applySequence("1 -2 4 6");
        search(s);
        System.out.println(timestampOf(System.currentTimeMillis() - a));
    }

    public static void main(String[] args) {

    }

    //TODO: implement IDA* to search?
    public static void search(State target) {
        State aux;
        while (true) {
            int currentIndex = IndexMapping.evenPermutationToIndex(target.permutation);
            if (distances[currentIndex] == 0) {
                System.out.println("solucao encontrada!");
                return;
            }

            for (int i = 0; i < State.MOVES.length; i++) {
                aux = target.multiply(State.MOVES[i]);
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
