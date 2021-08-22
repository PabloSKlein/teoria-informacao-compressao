package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.List;

public interface Encoder {
    List<Byte> encode(Message message);

    EncoderEnum getEncoder();
}
