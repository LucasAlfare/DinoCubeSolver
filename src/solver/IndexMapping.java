package solver;

/**
 * Helper methods from the Prisma Puzzle Timer, by Walter Souza.
 */
public class IndexMapping {

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
}
