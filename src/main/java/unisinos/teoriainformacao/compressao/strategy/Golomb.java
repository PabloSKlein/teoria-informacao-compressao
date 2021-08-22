package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.GOLOMB;

public class Golomb implements Encoder {

    private static final byte BIT_STOP = 1;

    @Override
    public List<Byte> encode(Message message) {
        return toObjectStream(message.getText().getBytes(US_ASCII))
                .flatMap(it -> encode(it, message.getParam()))
                .collect(toList());
    }

    private Stream<Byte> encode(Byte toEncode, int divisor) {
        var prefix = getPrefix(toEncode, divisor);
        var binaryMod = getBinaryMod(toEncode, divisor);

        return concat(toObjectStream(prefix), toObjectStream(binaryMod));
    }

    //TODO Não está funcionando, preciso conseguir um byte array com os binários que representam o valor em int que recebo por parâmetro.
    private byte[] getBinaryMod(Byte toEncode, int divisor) {
        var formatedBytes = new byte[divisor];
        var binaryModBytes = BigInteger.valueOf(toEncode.intValue() % divisor).toByteArray();

        if (formatedBytes.length != binaryModBytes.length) {
            for (int i = 0; i < binaryModBytes.length; i++) {
                formatedBytes[formatedBytes.length - (i + 1)] = binaryModBytes[i];
            }
        }

        return formatedBytes;
    }

    private byte[] getPrefix(Byte toEncode, int divisor) {
        var numberOfZeros = (toEncode.intValue() / divisor) - 1;
        var bytes = numberOfZeros >= 0 ? new byte[numberOfZeros + 1] : new byte[1];

        bytes[bytes.length - 1] = BIT_STOP;

        return bytes;
    }


    @Override
    public EncoderEnum getEncoder() {
        return GOLOMB;
    }

    private Stream<Byte> toObjectStream(byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> bytes[i]);
    }
}
