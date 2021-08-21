package java.unisinos.teoriainformacao.compressao.file;

import java.unisinos.teoriainformacao.compressao.strategy.EncoderEnum;
import java.util.Optional;

public class Message {
    public static final int ENCODER_INDEX = 0;
    public static final int PARAM_INDEX = 1;
    public static final int TEXT_START_INDEX = 2;

    private final String text;
    private final int param;
    private final EncoderEnum encoderEnum;
    private final int encoderKey;

    public Message(String text, int param, int encoderKey) {
        this.text = text;
        this.param = param;
        this.encoderEnum = EncoderEnum.byKey(encoderKey);
        this.encoderKey = encoderKey;
    }

    public static Optional<Message> buildMessage(String text) {
        return Optional.of(new Message(text.substring(TEXT_START_INDEX),
                parseCharToInt(text, PARAM_INDEX), parseCharToInt(text, ENCODER_INDEX)));
    }

    public String getText() {
        return text;
    }

    public int getParam() {
        return param;
    }

    public EncoderEnum getEncoder() {
        return encoderEnum;
    }

    public int getEncoderKey() {
        return encoderKey;
    }

    private static int parseCharToInt(String text, int index) {
        return Character.getNumericValue(text.charAt(index));
    }


}
