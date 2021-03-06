package com.dino.solver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Alguns métodos para rápida leitura e escrita de arquivos.
 */
class FileUtils {

    static void writeText(String text, String pathname) {
        try {
            RandomAccessFile rnd = new RandomAccessFile(pathname, "rw");
            FileChannel channel = rnd.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_WRITE, 0, text.length());
            buf.put(text.getBytes());
            rnd.close();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeBinary(byte[] content, String pathname) {
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

    static boolean loadFileToArray(byte[] destination, String pathname) {
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
