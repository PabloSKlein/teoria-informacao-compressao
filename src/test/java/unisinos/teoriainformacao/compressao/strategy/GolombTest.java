package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class GolombTest {

    private final Golomb golomb = new Golomb();

    @Test
    public void encodeAsStringSuccess() {
        assertEquals(" 8  \u0005", golomb.encode(build("1+0", 4)));
    }

    @Test
    public void decodeAsStringSuccess() {
        assertEquals("+-*", golomb.decode(build(golomb.encode(build("+-*", 4)), 4)));
    }
}