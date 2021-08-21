package java.unisinos.teoriainformacao.compressao.strategy;

import java.unisinos.teoriainformacao.compressao.file.Message;

import static java.unisinos.teoriainformacao.compressao.strategy.EncoderEnum.GOLOMB;

public class Golomb implements Encoder {
    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }

    @Override
    public EncoderEnum getEncoder() {
        return GOLOMB;
    }
}
