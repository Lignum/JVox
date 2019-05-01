package me.lignum.jvox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

final class StreamUtils {
    public static int readIntLE(InputStream stream) throws IOException {
        byte[] bytes = new byte[4];
        if (stream.read(bytes) != 4) {
            throw new IOException("Not enough bytes to read an int");
        }

        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static float readFloatLE(InputStream stream) throws IOException {
        byte[] bytes = new byte[4];
        if (stream.read(bytes) != 4) {
            throw new IOException("Not enough bytes to read a float");
        }

        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static Vector3<Integer> readVector3i(InputStream stream) throws IOException {
        return new Vector3<>(readIntLE(stream), readIntLE(stream), readIntLE(stream));
    }

    public static Vector3<Byte> readVector3b(InputStream stream) throws IOException {
        int x = stream.read();
        int y = stream.read();
        int z = stream.read();

        if (x == -1 || y == -1 || z == -1) {
            throw new IOException("Not enough bytes to read a vector3b");
        }

        return new Vector3<>((byte)x, (byte)y, (byte)z);
    }

    public static String readString(InputStream stream) throws IOException {
        int n = readIntLE(stream);
        if (n < 0) {
            throw new IOException("String is too large to read");
        }

        byte[] bytes = new byte[n];
        if (stream.read(bytes) != n) {
            throw new IOException("Not enough bytes to read a string of size " + n);
        }

        return new String(bytes);
    }

    public static HashMap<String, String> readDictionary(InputStream stream) throws IOException {
        int n = StreamUtils.readIntLE(stream);
        if (n < 0) {
            throw new InvalidVoxException("Dictionary too large");
        }

        HashMap<String, String> dict = new HashMap<>(n);

        for (int i = 0; i < n; i++) {
            String key = StreamUtils.readString(stream);
            String value = StreamUtils.readString(stream);
            dict.put(key, value);
        }

        return dict;
    }
}
