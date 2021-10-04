package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HammingTest {

    private final Hamming hamming = new Hamming();

    @Test
    void addValidationBits000(){
        assertEquals("0000000", hamming.addErrorValidationBits("000"));
    }

    @Test
    void addValidationBits001(){
        assertEquals("0010111", hamming.addErrorValidationBits("001"));
    }

    @Test
    void addValidationBits010(){
        assertEquals("0101101", hamming.addErrorValidationBits("010"));
    }

    @Test
    void addValidationBits100(){
        assertEquals("1001011", hamming.addErrorValidationBits("100"));
    }

    @Test
    void addValidationBits101(){
        assertEquals("1011100", hamming.addErrorValidationBits("101"));
    }


    @Test
    void addValidationBits110(){
        assertEquals("1100110", hamming.addErrorValidationBits("110"));
    }

    @Test
    void addValidationBits111(){
        assertEquals("1110001", hamming.addErrorValidationBits("111"));
    }

}