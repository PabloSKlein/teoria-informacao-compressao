package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class UnaryTest {
    private final Unary unary = new Unary();

    @Test
    public void encodeSuccess() {
        assertEquals("      @    \u0004     \u0001", unary.encode(build("1+0", 4)));
    }

    @Test
    public void decodeAsStringSuccess() {
        assertEquals("+-*", unary.decode(build(unary.encode(build("+-*", 4)), 4)));
    }

}
