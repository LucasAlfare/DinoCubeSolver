package com.flsv.dino;


import java.util.Arrays;

import static com.flsv.dino.Globals.*;

@SuppressWarnings("ALL")
public class Indexer {

    /**
     * Indexes all 6-colour Dino Cube states distances in a binary file.
     */
    public static void main(String[] args) {
        final long TOTAL_TIME_COUNTER = System.currentTimeMillis();

        byte[] auxState;

        long BEGIN = System.currentTimeMillis();
        int[][] table = new int[N_PERMUTATIONS][moves.length];
        System.out.println(Time.timestampFor(System.currentTimeMillis() - BEGIN) + " para alocar memoria para ((11!/2)*12) itens.\n");
        System.out.println("Comecando a preencher a tabela de transicao com os indices...");
        BEGIN = System.currentTimeMillis();

        for (int i = 0; i < N_PERMUTATIONS; i++) {
            auxState = indexToEvenPermutation(i, 11);
            for (int j = 0; j < moves.length; j++) {
                table[i][j] = Globals.evenPermutationToIndex(multiply(auxState, moves[j]));
            }
        }
        System.out.println(Time.timestampFor(System.currentTimeMillis() - BEGIN) + " para preencher a tabela.\n");
        System.out.println("Alocando memoria para o array das distancias...");
        BEGIN = System.currentTimeMillis();
        byte[] distance = new byte[N_PERMUTATIONS];
        System.out.println(Time.timestampFor(System.currentTimeMillis() - BEGIN) + " para alocar memoria para 11!/2 inteiros de tamanho 8-bit.\n");

        System.out.println("Inicializando todas distancias com valor -1...");
        BEGIN = System.currentTimeMillis();
        Arrays.fill(distance, (byte) -1);
        System.out.println(Time.timestampFor(System.currentTimeMillis() - BEGIN) + " para para inicializar 11!/2 itens.\n");

        byte depth = 0;
        distance[0] = depth;

        System.out.println("Comecando a mapear cada indice para seu respectivo valor de distancia...");
        BEGIN = System.currentTimeMillis();
        int nVisited;
        do {
            nVisited = 0;
            for (int i = 0; i < N_PERMUTATIONS; i++) {
                if (distance[i] == depth) {
                    for (int j = 0; j < moves.length; j++) {
                        if (distance[table[i][j]] == -1) {
                            distance[table[i][j]] = (byte) (depth + 1);
                            nVisited++;
                        }
                    }
                }
            }

            depth++;
            System.out.println(nVisited + " novas posicoes na profundidade " + depth);
        } while (nVisited > 0);
        System.out.println(Time.timestampFor(System.currentTimeMillis() - BEGIN) + " para mapear 11!/2 indices.\n");

        System.out.println("Gravando distancias no arquivo distances.dino...");
        BEGIN = System.currentTimeMillis();
        writeBinary(distance, "distances2.dino");
        System.out.println("Arquivo distances.dino gravado em " + Time.timestampFor(System.currentTimeMillis() - BEGIN) + ".\n");

        System.out.println("---- Operacao finalizada em " + Time.timestampFor(System.currentTimeMillis() - TOTAL_TIME_COUNTER) + " ----");
    }
}
