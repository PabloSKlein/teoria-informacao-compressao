package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class UnaryTest {
    public static final String TESTE_123_ENCODED = "000000000000000000001000000000000000000000000001010000000000000000000000000001000000000000000000000000000000100000000000000000000000000101000000000001010000000000010000000000000100";
    private final Unary unario = new Unary();

    @Test
    public void encodeSuccess() {
        var encoded = unario.encodeBool(build("0123", 4));
        var trues = encoded.stream()
                .filter(it -> it).count();
        var falses = encoded.stream()
                .filter(it -> !it).count();
        assertEquals(4, trues);
        assertEquals(198, falses);

        var encodedText = encoded.stream()
                .map(this::parseToByte)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }


    private Byte parseToByte(Boolean toParse){
        return toParse ? Byte.parseByte("1") : Byte.parseByte("0");
    }
}
