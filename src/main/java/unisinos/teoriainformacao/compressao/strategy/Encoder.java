package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.List;

public interface Encoder {
    String encode(Message message);

    EncoderEnum getEncoderDecoder();
}
