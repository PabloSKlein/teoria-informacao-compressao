package unisinos.teoriainformacao.compressao;

import unisinos.teoriainformacao.compressao.file.FileWriter;
import unisinos.teoriainformacao.compressao.file.Message;
import unisinos.teoriainformacao.compressao.strategy.EncoderStrategy;
import unisinos.teoriainformacao.compressao.strategy.Hamming;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.file.FileReader.readFile;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.getBinaryFormated;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.Constants.BYTE_SIZE;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Program {

    public static void main(String[] args) {
        readFile().stream()
                .map(Message::buildMessage)
                .flatMap(Optional::stream)
                .map(Program::encodeByStrategy)
                .flatMap(Optional::stream)
                //.map(Program::addErrorValidation)
                .forEach(FileWriter::writeLine);
    }

    private static Optional<String> encodeByStrategy(Message message) {
        return EncoderStrategy.getEncoder(message.getEncoderKey())
                .map(encoder -> encoder.encode(message));
    }

    private static String addErrorValidation(String message) {

        var bits = primitiveArrayToObjectStream(getChars(message))
                .map(it -> parseToString(getBinaryFormated(it, BYTE_SIZE)))
                .collect(joining());

        return new Hamming().addErrorValidationBits(bits);
    }

    private static char[] getChars(String text) {
        char[] chars = new char[text.length()];
        text.getChars(0, text.length(), chars, 0);
        return chars;
    }

}
