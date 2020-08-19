package solver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Some functions to fast I/O files.
 */
public class FileUtils {

    public static void writeText(String text, String pathname) {
        try {
            RandomAccessFile rnd = new RandomAccessFile(pathname, "rw");
            FileChannel fileChannel = rnd.getChannel();
            ByteBuffer buf = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, text.length());
            buf.put(text.getBytes());
            rnd.close();
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(byte[] content, String pathname) {
        try {
            RandomAccessFile rnd = new RandomAccessFile(pathname, "rw");
            FileChannel fileChannel = rnd.getChannel();
            ByteBuffer buf = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, content.length);
            for (byte b : content) {
                buf.put(b);
            }
            rnd.close();
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFileToArray(byte[] destination, String pathname) {
        try (FileInputStream stream = new FileInputStream(pathname)) {
            FileChannel inChannel = stream.getChannel();
            ByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.get(destination);
            stream.close();
            inChannel.close();
            System.out.println(destination.length + " bytes carregados com sucesso no array especificado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
