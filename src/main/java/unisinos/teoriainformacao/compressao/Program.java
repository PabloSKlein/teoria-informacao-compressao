package unisinos.teoriainformacao.compressao;

import unisinos.teoriainformacao.compressao.file.Message;
import unisinos.teoriainformacao.compressao.strategy.EncoderStrategy;

import java.util.List;
import java.util.Optional;

import static unisinos.teoriainformacao.compressao.file.FileReader.readFile;

public class Program {
    public static void main(String[] args) {
        readFile()
                .flatMap(Message::buildMessage)
                .flatMap(Program::encodeByStrategy)
                .orElseThrow(() -> new RuntimeException("Parâmtros inválidos"));
    }

    private static Optional<List<Byte>> encodeByStrategy(Message message) {
        return EncoderStrategy.getEncoder(message.getEncoderKey())
                .map(encoder -> encoder.encode(message));
    }
}
