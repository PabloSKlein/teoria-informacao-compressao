package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.List;

public interface Encoder {
    String encodeAsString(Message message);
    List<Boolean> encodeBool(Message message);

    EncoderEnum getEncoderDecoder();
}
