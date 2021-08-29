package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.List;

public interface EncoderDecoder {
    List<Byte> encode(Message message);

    List<Byte> decode(Message message);

    EncoderEnum getEncoderDecoder();
}
