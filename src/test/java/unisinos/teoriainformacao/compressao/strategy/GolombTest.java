package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class GolombTest {

    private final Golomb golomb = new Golomb();

    @Test
    public void encodeAsStringSuccess() {
        assertEquals("", golomb.encodeAsString(build("+-*", 4)));
    }
}