package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.getBinaryFormated;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.Constants.BYTE_SIZE;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Unary implements Encoder, Decoder {

    @Override
    public String encode(Message message) {
        var bytes = message.getText().getBytes(StandardCharsets.US_ASCII);

        var byteStream = primitiveArrayToObjectStream(bytes).collect(toList());

        return splitBytes(encodeAsString(byteStream)).stream()
                .map(this::montaByte)
                .collect(joining());
    }

    @Override
    public String decode(Message message) {
        var binaryWord = primitiveArrayToObjectStream(getChars(message.getText()))
                .map(this::mapToBinaryString)
                .collect(joining());

        var decodedWord = new StringBuilder();
        var numberOfZeros = 0;

        var charAnterior = binaryWord.charAt(0);

        for (int i = 0; i < binaryWord.length(); i++) {
            var charAtual = binaryWord.charAt(i);

            if (charAtual != charAnterior) {
                decodedWord.append((char) ((numberOfZeros)));

                numberOfZeros = 0;
                charAnterior = charAtual;
            }

            numberOfZeros++;
        }

        return decodedWord.toString();
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return EncoderEnum.UNARY;
    }

    private char[] getChars(String text) {
        char[] chars = new char[text.length()];
        text.getChars(0, text.length(), chars, 0);
        return chars;
    }


    private String mapToBinaryString(int intToDecode) {
        return parseToString(getBinaryFormated(intToDecode, BYTE_SIZE));
    }

    private String montaByte(String bits) {
        var lastChar = bits.charAt(bits.length() - 1);
        while (bits.length() != 8) {
            bits = bits.concat(lastChar == '0' ? "1" : "0");
        }
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

    private String encodeAsString(List<Byte> toEncode) {
        var builder = new StringBuilder();


        for (int i = 0; i < toEncode.size(); i++) {
            var bits = new int[toEncode.get(i)];

            if (i % 2 == 0) Arrays.fill(bits, 1);

            builder.append(Arrays.stream(bits).mapToObj(String::valueOf).collect(joining()));
        }

        return builder.toString();
    }
}
