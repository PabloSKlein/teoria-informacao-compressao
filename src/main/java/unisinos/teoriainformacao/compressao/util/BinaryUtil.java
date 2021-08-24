package unisinos.teoriainformacao.compressao.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class BinaryUtil {
    public static Byte[] toBinaryCharArray(int number) {
        return primitiveArrayToObjectStream(Integer.toBinaryString(number).toCharArray())
                .map(character -> Byte.parseByte(character.toString()))
                .toArray(Byte[]::new);
    }

    public static String parseToString(byte[] bytes) {
        return primitiveArrayToObjectStream(bytes)
                .map(String::valueOf)
                .collect(joining());
    }
}
