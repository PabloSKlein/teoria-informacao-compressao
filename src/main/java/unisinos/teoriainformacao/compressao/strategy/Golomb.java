package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.GOLOMB;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.toBinaryCharArray;
import static unisinos.teoriainformacao.compressao.util.MathUtil.log2;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Golomb implements Encoder, Decoder {

    private static final byte BIT_STOP = 1;
    private static final byte BYTE_SIZE = 8;

    @Override
    public String encodeAsString(Message message) {
        return splitBytes(primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .map(it -> encodeAsString(it, message.getParam()))
                .collect(joining())).stream()
                .map(this::montaByte)
                .collect(joining());
    }

    private String montaByte(String bits) {
        int charCode = Integer.parseInt(bits, 2);
        return Character.toString((char) charCode);
    }

    private ArrayList<String> splitBytes(String toSplit) {
        var bytes = new ArrayList<String>();
        for (int i = 0; i < toSplit.length(); i = i + BYTE_SIZE) {
            var splitEnd = min(i + BYTE_SIZE, toSplit.length());
            var bits = toSplit.substring(i, splitEnd);
            bytes.add(bits);
        }

        return bytes;
    }

    @Override
    public List<Boolean> encodeBool(Message message) {
        return null;
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return GOLOMB;
    }

    private String encodeAsString(Byte toEncode, int divisor) {
        var prefix = parseToString(getPrefix(toEncode, divisor));
        var binaryMod = parseToString(getBinaryMod(toEncode, divisor));
        return prefix + binaryMod;
    }

    private byte[] getBinaryMod(Byte toEncode, int divisor) {
        int modSize = log2(divisor);//Aqui ta errado
        var formatedBytes = new byte[modSize];
        var binaryModBytes = toBinaryCharArray(toEncode.intValue() % divisor);

        //TODO verificar se não é melhor só ir botando zero na frente

        for (int i = 0; i < binaryModBytes.length; i++) {
            formatedBytes[formatedBytes.length - (i + 1)] = binaryModBytes[i];
        }

        return formatedBytes;
    }

    private byte[] getPrefix(Byte toEncode, int divisor) {
        var numberOfZeros = (toEncode.intValue() / divisor);
        var bytes = numberOfZeros >= 0 ? new byte[numberOfZeros + 1] : new byte[1];

        bytes[bytes.length - 1] = BIT_STOP;

        return bytes;
    }

    @Override
    public String decode(Message message) {
        return null;
    }
}
