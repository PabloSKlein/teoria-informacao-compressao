package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.GOLOMB;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.toBinaryCharArray;
import static unisinos.teoriainformacao.compressao.util.MathUtil.log2;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Golomb implements Encoder, Decoder {

    private static final byte BIT_STOP = 1;

    @Override
    public List<Byte> encode(Message message) {
        return primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .flatMap(it -> encode(it, message.getParam()))
                .collect(toList());
    }

    @Override
    public List<Byte> decode(Message message) {
        return null;
    }

    public String encodeAsString(Message message) {
        return primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .map(it -> encodeAsString(it, message.getParam()))
                .collect(joining());
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return GOLOMB;
    }

    private Stream<Byte> encode(Byte toEncode, int divisor) {
        var prefix = getPrefix(toEncode, divisor);
        var binaryMod = getBinaryMod(toEncode, divisor);

        return concat(primitiveArrayToObjectStream(prefix), primitiveArrayToObjectStream(binaryMod));
    }

    private String encodeAsString(Byte toEncode, int divisor) {
        var prefix = parseToString(getPrefix(toEncode, divisor));
        var binaryMod = parseToString(getBinaryMod(toEncode, divisor));

        return prefix + binaryMod;
    }

    private byte[] getBinaryMod(Byte toEncode, int divisor) {
        int modSize = log2(divisor);
        var formatedBytes = new byte[modSize];
        var binaryModBytes = toBinaryCharArray(toEncode.intValue() % divisor);

        //TODO verificar se não é melhor só ir botando zero na frente
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

}
