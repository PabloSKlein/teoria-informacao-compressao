package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.strategy.EncoderEnum.DELTA;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.getBinaryFormated;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.Constants.BYTE_SIZE;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Delta implements Encoder, Decoder {

    private static final String DELTA_CHANGED = "1";
    private static final String DELTA_NOT_CHANGED = "0";
    private static final char DELTA_NOT_CHANGED_CHAR = '0';
    private static final String DELTA_POSITIVE = "0";
    private static final char DELTA_POSITIVE_CHAR = '0';
    private static final String DELTA_NEGATIVE = "1";
    private static final byte DELTA_SIZE = 8;
    private boolean first_byte_encode = true;
    private Byte last_byte;

    private String encoded = "";

    @Override
    public String encode(Message message) {
        return splitBytes(primitiveArrayToObjectStream(message.getText().getBytes(US_ASCII))
                .map(this::encodeAsString)
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
            var bits = String.format("%-8s", toSplit.substring(i, splitEnd)).replaceAll(" ", "0");
            bytes.add(bits);
        }

        return bytes;
    }

    @Override
    public EncoderEnum getEncoderDecoder() {
        return DELTA;
    }

    private String encodeAsString(Byte toEncode) {
        var encoded_string = "";

        //Se é o primeiro byte, precisa pegar o binário dele
        if (first_byte_encode) {
            encoded_string = String.format("%8s", Integer.toBinaryString(toEncode.intValue())).replaceAll(" ", "0");
            first_byte_encode = false;

        }
        //Senão
        else {
            //Deve-se avaliar se é igual ao último byte avaliado
            if (toEncode.equals(last_byte)) {
                // Caso seja, apenas insere bit de controle
                encoded_string = DELTA_NOT_CHANGED;
            }
            // Senão
            else {
                //Calcula a diferença do último para o atual
                int diff = (toEncode.intValue()) - (last_byte.intValue());

                //Essa diferença deve ser inserida em binário
                // Tem que ser um número fixo de posições pro decoder funcionar, foi definido 8 para poder fazer diferença de 256 (diferença máxima na tabela ASCII)
                var diff_string = String.format("%8s", Integer.toBinaryString(Math.abs(diff))).replaceAll(" ", "0");

                //Haverá um bit antes indicando se é positivo (0) ou negativo (1)
                if (diff > 0) {
                    diff_string = DELTA_POSITIVE + diff_string;
                } else {
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

        var binarioLista = primitiveArrayToObjectStream(getChars(message.getText()))
                .map(this::mapToBinaryString)
                .collect(Collectors.toList());

        var texto = new StringBuilder();

        var binararioDaUltimaLetra = binarioLista.stream().collect(joining()).substring(0, 8);
        var ultimaLetra = montaByte(binararioDaUltimaLetra);
        texto.append(ultimaLetra);

        var resto = binarioLista.stream().collect(joining()).substring(8);

        for (int i = 0; i < resto.length(); i++) {
            if (resto.charAt(i) == DELTA_NOT_CHANGED_CHAR) {
                texto.append(ultimaLetra);
            } else {
                var limiteMaximo = min(i + 10, resto.length());
                var delta = resto.substring(i + 1, limiteMaximo);
                int soma;
                if (delta.charAt(0) == DELTA_POSITIVE_CHAR) {
                    soma = Integer.parseInt(binararioDaUltimaLetra, 2) + Integer.parseInt(delta.substring(1), 2);
                } else {
                    soma = Integer.parseInt(binararioDaUltimaLetra, 2) - Integer.parseInt(delta.substring(1), 2);
                }

                binararioDaUltimaLetra = Integer.toBinaryString(soma);
                ultimaLetra = montaByte(binararioDaUltimaLetra);
                texto.append((char) soma);

                i = i + (limiteMaximo - 1);
            }
        }

        return texto.toString();

        //Minha principal dúvida aqui é como ir consumindo X bits por vez
        //Ex.: primeiro deve-se ler 8 bits, depois 1 bit para ver se manteve (delta) ou não e daí decidir se vai ler +1 ou +9 (1 sinal 8 degrau)

        //Se é o primeiro byte, precisa converter para o binário dele
        //Método de byte para ASCII?

        //Senão, deve-se avaliar se é igual ao último byte avaliado DELTA_NOT_CHANGED
        // Caso seja repete última string

        // Senão pega os 8 bits para ver qual o tamanho do degrau, faz a diferença do último ASCII com o degrau e exibe a string sendo que na diferença temos DELTA_POSITIVE/DELTA_NEGATIVE
    }

    private char[] getChars(String text) {
        char[] chars = new char[text.length()];
        text.getChars(0, text.length(), chars, 0);
        return chars;
    }

    private String mapToBinaryString(int intToDecode) {
        return parseToString(getBinaryFormated(intToDecode, BYTE_SIZE));
    }


}
