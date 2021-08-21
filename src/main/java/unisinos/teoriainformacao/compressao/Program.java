package java.unisinos.teoriainformacao.compressao;

import java.unisinos.teoriainformacao.compressao.file.Message;
import java.unisinos.teoriainformacao.compressao.strategy.EncoderStrategy;
import java.util.Optional;

import static java.unisinos.teoriainformacao.compressao.file.FileReader.readFile;

public class Program {
    public static void main(String[] args) {
        readFile()
                .flatMap(Message::buildMessage)
                .flatMap(Program::encodeByStrategy)
                .orElseThrow(() -> new RuntimeException("Parâmtros inválidos"));
    }

    private static Optional<byte[]> encodeByStrategy(Message message) {
        return EncoderStrategy.getEncoder(message.getEncoderKey())
                .map(encoder -> encoder.encode(message));
    }
}
