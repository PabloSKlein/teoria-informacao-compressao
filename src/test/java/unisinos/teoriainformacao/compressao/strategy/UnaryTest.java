package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

public class UnaryTest {
    public static final String TESTE_123_ENCODED = "000000000000000000001000000000000000000000000001010000000000000000000000000001000000000000000000000000000000100000000000000000000000000101000000000001010000000000010000000000000100";
    private final Unary unario = new Unary();

    @Test
    public void encodeSuccess() {

    }


    private Byte parseToByte(Boolean toParse) {
        return toParse ? Byte.parseByte("1") : Byte.parseByte("0");
    }
}
