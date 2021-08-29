package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;
import unisinos.teoriainformacao.compressao.util.PrimitiveUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Unario implements Encoder {

    @Override
    public List<Byte> encode(Message message) {
        var bytes = message.getText().getBytes(StandardCharsets.US_ASCII);

        byte[] saida = {};
        byte[] temp = null;
        int n = bytes[0];
        int i;
        for (int j = 0; j < bytes.length; j++) {
            n = bytes[j];
            temp = new byte[n + 1];
            i = 0;
            while (i < n)
            {
                temp[i] = '0';
                i++;
            }
            temp[n] = '1';
        }

        return PrimitiveUtil.primitiveArrayToObjectStream(bytes).collect(Collectors.toList());
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return EncoderEnum.UNARIA;
    }
}
