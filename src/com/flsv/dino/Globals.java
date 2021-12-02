package com.flsv.dino;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Holds global values used either by indexer as by the searcher.
 */
public class Globals {

    public static final int N_PIECES = 11;
    public static final int N_PERMUTATIONS = 19_958_400;

    private static final byte[] m1 = new byte[]{6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10};
    private static final byte[] m2 = new byte[]{1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10};
    private static final byte[] m3 = new byte[]{0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10};
    private static final byte[] m4 = new byte[]{0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10};

    private static final byte[] m5 = new byte[]{0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10};
    private static final byte[] m6 = new byte[]{0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7};

    public static final byte[][] moves = {
            m1, multiply(m1, m1),
            m2, multiply(m2, m2),
            m3, multiply(m3, m3),
            m4, multiply(m4, m4),
            m5, multiply(m5, m5),
            m6, multiply(m6, m6)
    };

    public static final byte[] names = {1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};

    public static byte[] multiply(byte[] state, byte[] move) {
        byte[] permutation = new byte[N_PIECES];
        for (int i = 0; i < N_PIECES; i++) {
            permutation[i] = state[move[i]];
        }

        return permutation;
    }

    // even permutation
    public static int evenPermutationToIndex(byte[] permutation) {
        int index = 0;
        for (int i = 0; i < permutation.length - 2; i++) {
            index *= permutation.length - i;
            for (int j = i + 1; j < permutation.length; j++) {
                if (permutation[i] > permutation[j]) {
                    index++;
                }
            }
        }

        return index;
    }

    public static byte[] indexToEvenPermutation(int index, int length) {
        int sum = 0;
        byte[] permutation = new byte[length];

        permutation[length - 1] = 1;
        permutation[length - 2] = 0;
        for (int i = length - 3; i >= 0; i--) {
            permutation[i] = (byte) (index % (length - i));
            sum += permutation[i];
            index /= length - i;
            for (int j = i + 1; j < length; j++) {
                if (permutation[j] >= permutation[i]) {
                    permutation[j]++;
                }
            }
        }

        if (sum % 2 != 0) {
            byte temp = permutation[permutation.length - 1];
            permutation[permutation.length - 1] = permutation[permutation.length - 2];
            permutation[permutation.length - 2] = temp;
        }

        return permutation;
    }

    public static void writeBinary(byte[] content, String pathname) {
        try {
            RandomAccessFile rnd = new RandomAccessFile(pathname, "rw");
            FileChannel channel = rnd.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, content.length);
            for (byte b : content) buf.put(b);
            rnd.close();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
