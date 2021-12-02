package com.flsv.dino;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import static com.flsv.dino.Globals.multiply;

@SuppressWarnings("ALL")
public class Search {

    private static boolean initied = false;
    private static byte[] distances, moving, aux;
    private static int currentIndex;
    private static final StringBuffer solution = new StringBuffer();

    public static String search(byte[] target) {
        if (!initied) init();

        moving = Arrays.copyOf(target, target.length);
        solution.delete(0, solution.length());

        while (true) {
            currentIndex = Globals.evenPermutationToIndex(moving);

            if (distances[currentIndex] == 0) return solution.toString();

            for (int i = 0; i < Globals.moves.length; i++) {
                aux = multiply(moving, Globals.moves[i]);
                int nextIndex = Globals.evenPermutationToIndex(aux);
                if (distances[nextIndex] < distances[currentIndex]) {
                    solution.append(Globals.names[i]);
                    moving = aux; //should copy instead of assign?
                    break;
                }
            }
        }
    }

    private static void init() {
        if (!initied) {
            distances = new byte[Globals.N_PERMUTATIONS];
            initied = loadFileToArray(distances, "distances.dino");
        }
    }

    private static boolean loadFileToArray(byte[] destination, String pathname) {
        try (FileInputStream stream = new FileInputStream(pathname)) {
            FileChannel channel = stream.getChannel();
            ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.get(destination);
            stream.close();
            channel.close();
            System.out.println(destination.length + " bytes carregados com sucesso no array especificado.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
