package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

public class DeltaTest {

    private final Delta delta = new Delta();

    @Test
    public void encodeAsStringSuccess() {
        assertEquals("", delta.encode(build("ADDA", 0)));
    }

    @Test
    public void decode() {
        assertEquals("ADDA", delta.decode(build(delta.encode(build("ADDA", 4)), 4)));
    }

}