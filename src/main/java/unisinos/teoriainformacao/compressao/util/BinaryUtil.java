package unisinos.teoriainformacao.compressao.util;

import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class BinaryUtil {
    public static Byte[] toBinaryCharArray(int number) {
        return primitiveArrayToObjectStream(Integer.toBinaryString(number).toCharArray())
                .map(character -> Byte.parseByte(character.toString()))
                .toArray(Byte[]::new);
    }

    public static byte[] getBinaryFormated(int intValue, int degree) {
        var formatedBytes = new byte[degree];
        var binaryBytes = toBinaryCharArray(intValue);

        for (int i = 0; i < binaryBytes.length; i++) {
            formatedBytes[formatedBytes.length - (i + 1)] = binaryBytes[binaryBytes.length - (i + 1)];
        }

        return formatedBytes;
    }

    public static String parseToString(byte[] bytes) {
        return primitiveArrayToObjectStream(bytes)
                .map(String::valueOf)
                .collect(joining());
    }
}
