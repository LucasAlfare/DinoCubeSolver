package solver;

import static solver.FileUtils.loadFileToArray;
import static solver.Generator.Time.timestampOf;

public class Search {

    public static byte[] distances = new byte[19958400];
    static int[] names = {1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};

    public static void init() {
        long a = System.currentTimeMillis();
        loadFileToArray(distances, "distances.dino");
        System.out.println(timestampOf(System.currentTimeMillis() - a));
    }

    //TODO: implement IDA* to search?
    public static String search(State target) {
        State aux;
        StringBuilder solutiuon = new StringBuilder();
        while (true) {
            int currentIndex = IndexMapping.evenPermutationToIndex(target.permutation);
            if (distances[currentIndex] == 0) {
                return solutiuon.toString();
            }

            for (int i = 0; i < State.MOVES.length; i++) {
                aux = target.multiply(State.MOVES[i]);
                int nextIndex = IndexMapping.evenPermutationToIndex(aux.permutation);
                if (distances[nextIndex] < distances[currentIndex]) {
                    solutiuon.append(names[i]).append(" ");
                    target.copy(aux);
                    break;
                }
            }
        }
    }
}
