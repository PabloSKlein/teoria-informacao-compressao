package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Unary implements Encoder {

    @Override
    public String encode(Message message) {
        var bytes = message.getText().getBytes(StandardCharsets.US_ASCII);
        primitiveArrayToObjectStream(bytes)
                .flatMap(this::encodeByte)
                .collect(toList());
        return null;
    }

    private Stream<Boolean> encodeByte(Byte toEncode) {
        var encodedBits = new boolean[toEncode + 1];
        encodedBits[encodedBits.length - 1] = true;
        return primitiveArrayToObjectStream(encodedBits);
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return EncoderEnum.UNARY;
    }
}
