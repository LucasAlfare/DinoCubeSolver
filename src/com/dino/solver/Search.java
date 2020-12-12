package com.dino.solver;

import com.dino.misc.Time;

import java.util.ArrayList;

import static com.dino.solver.FileUtils.loadFileToArray;

public class Search {

    public static boolean initied = false;

    public static void init() {
        if (!initied) {
            long a = System.currentTimeMillis();
            initied = loadFileToArray(Constants.DISTANCES, "src/com/dino/distances.dino");
            System.out.println("[SEARCH] " + Time.timestampFor(System.currentTimeMillis() - a) + " para carregar o arquivo.");
        }
    }

    //TODO: implement IDA* to search?
    public static ArrayList<Byte> search(State target) {
        if (!initied) {
            init();
        }

        State aux;
        ArrayList<Byte> solution = new ArrayList<>();
        long a = System.currentTimeMillis();
        while (true) {
            int currentIndex = IndexMapping.evenPermutationToIndex(target.permutation);
            if (Constants.DISTANCES[currentIndex] == 0) {
                System.out.println("[SEARCH] " + Time.timestampFor(System.currentTimeMillis() - a) + " para encontrar a solução.");
                return solution;
            }

            for (int i = 0; i < Constants.MOVES.length; i++) {
                aux = target.multiply(Constants.MOVES[i]);
                int nextIndex = IndexMapping.evenPermutationToIndex(aux.permutation);
                if (Constants.DISTANCES[nextIndex] < Constants.DISTANCES[currentIndex]) {
                    solution.add(Constants.NAMES[i]);
                    target.copy(aux);
                    break;
                }
            }
        }
    }
}
