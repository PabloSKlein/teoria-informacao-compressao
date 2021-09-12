package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Unary implements Encoder {

    @Override
    public String encodeAsString(Message message) {
        return null;
    }

    @Override
    public List<Boolean> encodeBool(Message message) {
        var bytes = message.getText().getBytes(StandardCharsets.US_ASCII);
        return primitiveArrayToObjectStream(bytes)
                .flatMap(this::encodeByte)
                .collect(toList());
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
