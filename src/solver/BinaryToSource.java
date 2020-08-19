package solver;

public class BinaryToSource {

    public static void main(String[] args) {
        byte[] distances = new byte[19958400];
        FileUtils.loadFileToArray(distances, "distances.dino");

        StringBuilder src = new StringBuilder("package solver;public class Distances{public static final byte[]DISTANCES={");

        int len = distances.length;
        for (int i = 0; i < len - 1; i++) {
            src.append(distances[i]).append(",");
        }

        src.append(distances[len - 1]).append("};");
        src.append("}");

        FileUtils.writeText(src.toString(), "Distances.java");
    }
}
