package solver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static solver.FileUtils.writeBinary;

public class Generator {

    public static final int N = 19958400;
    public static int[][] table;
    public static byte[] distance;
    public static State[] moves;
    public static byte depth;
    public static int nVisited;

    public static void main(String[] args) {
        final long TOTAL_TIME_COUNTER = System.currentTimeMillis();

        State m1 = new State(new byte[]{6, 1, 2, 0, 4, 5, 3, 7, 8, 9, 10});
        State m2 = new State(new byte[]{1, 7, 2, 3, 4, 5, 6, 0, 8, 9, 10});
        State m3 = new State(new byte[]{0, 2, 4, 3, 1, 5, 6, 7, 8, 9, 10});
        State m4 = new State(new byte[]{0, 1, 3, 5, 4, 2, 6, 7, 8, 9, 10});

        State m5 = new State(new byte[]{0, 1, 2, 3, 8, 5, 6, 7, 9, 4, 10});
        State m6 = new State(new byte[]{0, 1, 2, 3, 4, 5, 6, 9, 8, 10, 7});

        moves = new State[]{
                m1, m1.multiply(m1),
                m2, m2.multiply(m2),
                m3, m3.multiply(m3),
                m4, m4.multiply(m4),
                m5, m5.multiply(m5),
                m6, m6.multiply(m6)
        };

        System.out.println("Alocando memoria para a tabela de transicao...");
        long BEGIN = System.currentTimeMillis();
        table = new int[N][moves.length];
        System.out.println(Time.timestampOf(System.currentTimeMillis() - BEGIN) + " para alocar memoria para ((11!/2)*12) itens.\n");
        State s = new State(null);
        System.out.println("Comecando a preencher a tabela de transicao com os indices...");
        BEGIN = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            s.permutation = IndexMapping.indexToEvenPermutation(i, 11);
            for (int j = 0; j < moves.length; j++) {
                table[i][j] = IndexMapping.evenPermutationToIndex(s.multiply(moves[j]).permutation);
            }
        }
        System.out.println(Time.timestampOf(System.currentTimeMillis() - BEGIN) + " para preencher a tabela.\n");

        System.out.println("Alocando memoria para o array das distancias...");
        BEGIN = System.currentTimeMillis();
        distance = new byte[N];
        System.out.println(Time.timestampOf(System.currentTimeMillis() - BEGIN) + " para alocar memoria para 11!/2 inteiros de tamanho 8-bit.\n");

        System.out.println("Inicializando distancias com valor -1...");
        BEGIN = System.currentTimeMillis();
        Arrays.fill(distance, (byte) -1);
        System.out.println(Time.timestampOf(System.currentTimeMillis() - BEGIN) + " para para inicializar 11!/2 itens.\n");

        distance[0] = 0;

        System.out.println("Comecando a mapear cada indice para seu respectivo valor de distancia...");
        BEGIN = System.currentTimeMillis();
        do {
            nVisited = 0;
            for (int i = 0; i < N; i++) {
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
        System.out.println(Time.timestampOf(System.currentTimeMillis() - BEGIN) + " para mapear 11!/2 indices.\n");

        System.out.println("Gravando distancias no arquivo distances.dino...");
        BEGIN = System.currentTimeMillis();
        writeBinary(distance, "distances.dino");
        System.out.println("Arquivo distances.dino gravado em " + Time.timestampOf(System.currentTimeMillis() - BEGIN) + ".\n");

        System.out.println("---- Operacao finalizada em " + Time.timestampOf(System.currentTimeMillis() - TOTAL_TIME_COUNTER) + " ----");
    }

    //para debugar
    public static class Time {

        public static final String TIMESTAMP_PATTERN = "ss.SSS";
        public static final String TIMESTAMP_PATTERN_MINUTES = "mm:ss.SSS";

        private static final SimpleDateFormat SDF = new SimpleDateFormat();

        public static String timestampOf(long value) {
            SDF.applyPattern(getTimestampPattern(value));
            return SDF.format(value);
        }

        private static String getTimestampPattern(long value) {
            SDF.applyPattern("mm");
            return SDF.format(value).equals("00") ? TIMESTAMP_PATTERN : TIMESTAMP_PATTERN_MINUTES;
        }
    }
}
