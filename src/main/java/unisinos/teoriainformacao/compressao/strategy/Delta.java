package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.DELTA;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Delta implements Encoder, Decoder {

    private static final byte BYTE_SIZE = 8;
    private static final String DELTA_CHANGED = "1";
    private static final String DELTA_NOT_CHANGED = "0";
    private static final String DELTA_POSITIVE = "0";
    private static final String DELTA_NEGATIVE = "1";
    private static final byte DELTA_SIZE = 8;
    private boolean first_byte = true;
    private Byte last_byte;

    private String encoded = "";

    @Override
    public String encodeAsString(Message message) {
        return splitBytes(primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .map(it -> encodeAsString(it))
                .collect(joining())).stream()
                .map(this::montaByte)
                .collect(joining());
    }

    private String montaByte(String bits) {
        int charCode = Integer.parseInt(bits, 2);
        return Character.toString((char) charCode);
    }

    private ArrayList<String> splitBytes(String toSplit) {
        var bytes = new ArrayList<String>();
        for (int i = 0; i < toSplit.length(); i = i + BYTE_SIZE) {
            var splitEnd = min(i + BYTE_SIZE, toSplit.length());
            var bits = toSplit.substring(i, splitEnd);
            bytes.add(bits);
        }

        return bytes;
    }

    @Override
    public List<Boolean> encodeBool(Message message) {
        return null;
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return DELTA;
    }

    private String encodeAsString(Byte toEncode) {
        var encoded_string = "";

        //Se é o primeiro byte, precisa pegar o binário dele
        if(first_byte){
            encoded_string = Integer.toBinaryString(toEncode.intValue());
            first_byte = false;
        }
        //Senão
        else{
            //Deve-se avaliar se é igual ao último byte avaliado
            if(toEncode.equals(last_byte)){
                // Caso seja, apenas insere bit de controle
                encoded_string = DELTA_NOT_CHANGED;
            }
            // Senão
            else{
                //Calcula a diferença do último para o atual
                int diff = (toEncode.intValue()) - (last_byte.intValue());

                //Essa diferença deve ser inserida em binário
                // Tem que ser um número fixo de posições pro decoder funcionar, foi definido 8 para poder fazer diferença de 256 (diferença máxima na tabela ASCII)
                var diff_string = String.format("%8s", Integer.toBinaryString(Math.abs(diff))).replaceAll(" ", "0");

                //Haverá um bit antes indicando se é positivo (0) ou negativo (1)
                if(diff > 0){
                    diff_string = DELTA_POSITIVE + diff_string;
                }else{
                    diff_string = DELTA_NEGATIVE + diff_string;
                }

                encoded_string = DELTA_CHANGED + diff_string;
            }


        }

        last_byte = toEncode;

        //TEST
        encoded = encoded + encoded_string;

        return encoded_string;
    }

    @Override
    public String decode(Message message) {
        return null;
    }
}
