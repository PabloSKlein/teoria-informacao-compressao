package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.ArrayList;

import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.GOLOMB;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.getBinaryFormated;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.MathUtil.log2;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Golomb implements Encoder, Decoder {

    private static final byte BIT_STOP = 1;
    private static final char BIT_STOP_CHAR = '1';
    private static final byte BYTE_SIZE = 8;

    @Override
    public String encode(Message message) {
        return splitBytes(primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .map(it -> encodeAsString(it, message.getParam()))
                .collect(joining())).stream()
                .map(this::montaByte)
                .collect(joining());
    }

    @Override
    public String decode(Message message) {
        var binaryWord = primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .map(this::mapToBinaryString)
                .collect(joining());

        int modSize = log2(message.getParam());
        var decodedWord = new StringBuilder();
        var numberOfZeros = 0;

        for (int i = 0; i < binaryWord.length(); i++) {
            if (binaryWord.charAt(i) == BIT_STOP_CHAR) {
                var binaryMod = binaryWord.substring(i + 1, i + modSize + 1);
                int modValue = Integer.parseInt(binaryMod, 2);

                decodedWord.append((char) ((numberOfZeros * message.getParam()) + modValue));

                numberOfZeros = 0;
                i = i + modSize;
            }

            numberOfZeros++;
        }

        return decodedWord.toString();
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return GOLOMB;
    }

    private String mapToBinaryString(int intToDecode) {
        return parseToString(getBinaryFormated(intToDecode, BYTE_SIZE));
    }


    private String montaByte(String bits) {
        //TODO aqui deu ruim
        return String.valueOf((char) Integer.parseInt(bits, 2));
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


    private String encodeAsString(Byte toEncode, int divisor) {
        var prefix = parseToString(getPrefix(toEncode, divisor));
        var binaryMod = parseToString(getBinaryMod(toEncode, divisor));
        return prefix + binaryMod;
    }

    private byte[] getBinaryMod(Byte toEncode, int divisor) {
        int modSize = log2(divisor);

        return getBinaryFormated(toEncode.intValue() % divisor, modSize);
    }

    private byte[] getPrefix(Byte toEncode, int divisor) {
        var numberOfZeros = (toEncode.intValue() / divisor);
        var bytes = numberOfZeros >= 0 ? new byte[numberOfZeros + 1] : new byte[1];

        bytes[bytes.length - 1] = BIT_STOP;

        return bytes;
    }
}
