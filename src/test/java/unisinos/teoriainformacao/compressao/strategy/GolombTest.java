package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class GolombTest {

    private final Golomb golomb = new Golomb();

    @Test
    public void encodeSuccess() {
        golomb.encode(build("Teste123", 4));
    }

    @Test
    public void teste() {
        int i = 1 % 2;
    }
}