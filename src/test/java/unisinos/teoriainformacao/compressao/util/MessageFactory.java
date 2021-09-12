package unisinos.teoriainformacao.compressao.util;

import unisinos.teoriainformacao.compressao.file.Message;

import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.GOLOMB;

public class MessageFactory {
    public static Message build(String text, int param) {
        return Message.builder()
                .encoderEnum(GOLOMB)
                .encoderKey(GOLOMB.key)
                .param(param)
                .text(text)
                .build();
    }
}
