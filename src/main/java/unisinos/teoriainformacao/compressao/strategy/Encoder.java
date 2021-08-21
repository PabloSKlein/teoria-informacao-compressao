package java.unisinos.teoriainformacao.compressao.strategy;

import java.unisinos.teoriainformacao.compressao.file.Message;

public interface Encoder {
    byte[] encode(Message message);

    EncoderEnum getEncoder();
}
