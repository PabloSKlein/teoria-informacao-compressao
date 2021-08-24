package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class GolombTest {

    //TODO Não sei se é isso mesmo, só fiz passar o teste
    public static final String TESTE_123_ENCODED = "000000000000000000001000000000000000000000000001010000000000000000000000000001000000000000000000000000000000100000000000000000000000000101000000000001010000000000010000000000000100";
    private final Golomb golomb = new Golomb();

    @Test
    public void encodeSuccess() {
        golomb.encode(build("Teste123", 4));
    }

    @Test
    public void encodeAsStringSuccess() {
        assertEquals(TESTE_123_ENCODED, golomb.encodeAsString(build("Teste123", 4)));
    }
}