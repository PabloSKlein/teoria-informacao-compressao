package unisinos.teoriainformacao.compressao.strategy;

import org.junit.jupiter.api.Test;
import unisinos.teoriainformacao.compressao.file.Message;
import static unisinos.teoriainformacao.compressao.util.MessageFactory.build;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTest {
    private Fibonacci fibonacciEncoder = new Fibonacci();

    @Test
    public void testarEncode()
    {
        var resultadoEsperado = "0000101011101010000110001001001110010010011101010000110010101100101000011101010000110010101110101000011000000100110100100001110000010011001010000110010010001100000010011100001000110000\n" +
                "0000101011101010000110001001001110010010011101010000110010101100101000011101010000110010101110101000011000000100110100100001110000010011001010000110010010001100000010011100001000110000\n" +
                "00001010111010100001100010010011100100100111010100001100101011001010000111010100001100101011101010000110000001001101001000011100000100110010100001100100100011000000100111000010001";
        var message = build("Teste de encoding", 1);
        var stringBits = fibonacciEncoder.encode(message);

        assertEquals(resultadoEsperado, stringBits);
    }

    public void testarDecode()
    {
        var resultado = fibonacciEncoder.decode(build("Teste de encoding", 0));

    }
}