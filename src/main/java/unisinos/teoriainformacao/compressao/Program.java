package unisinos.teoriainformacao.compressao;

import unisinos.teoriainformacao.compressao.file.FileWriter;
import unisinos.teoriainformacao.compressao.file.Message;
import unisinos.teoriainformacao.compressao.strategy.EncoderStrategy;
import unisinos.teoriainformacao.compressao.strategy.Hamming;

import java.util.Optional;

import static unisinos.teoriainformacao.compressao.file.FileReader.readFile;

public class Program {

    public static void main(String[] args) {
        readFile().stream()
                .map(Message::buildMessage)
                .flatMap(Optional::stream)
                .map(Program::encodeByStrategy)
                .flatMap(Optional::stream)
                .map(Program::addErrorValidation)
                .forEach(FileWriter::writeLine);
    }

    private static Optional<String> encodeByStrategy(Message message) {
        return EncoderStrategy.getEncoder(message.getEncoderKey())
                .map(encoder -> encoder.encode(message));
    }

    private static String addErrorValidation(String message) {
        return new Hamming().addErrorValidationBits(message);
    }
}
