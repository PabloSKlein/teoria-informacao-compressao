package unisinos.teoriainformacao.compressao.strategy;

import org.junit.platform.commons.util.StringUtils;
import unisinos.teoriainformacao.compressao.file.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;
import static java.util.stream.Collectors.joining;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.getBinaryFormated;
import static unisinos.teoriainformacao.compressao.util.BinaryUtil.parseToString;
import static unisinos.teoriainformacao.compressao.util.Constants.BYTE_SIZE;
import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Fibonacci implements Encoder, Decoder {
    @java.lang.Override
    public String decode(Message message) {
        var binaryWord = primitiveArrayToObjectStream(getChars(message.getText()))
                .map(this::mapToBinaryString)
                .collect(joining());

        return decode(binaryWord);
    }

    private String decode(String entrada) {
        var codewordsIdentificados = new ArrayList<String>();
        var codeword = String.valueOf(entrada.charAt(0));

        for (int i = 1; i < entrada.length(); i++) {
            var atual = entrada.charAt(i);
            var anterior = entrada.charAt(i - 1);

            if (atual == '1' && anterior == '1' && codeword.length() > 0) {
                codewordsIdentificados.add(codeword);
                codeword = "";
            } else {
                codeword += atual;
            }
        }

        if (!StringUtils.isBlank(codeword) && primitiveArrayToObjectStream(codeword.toCharArray()).allMatch(c -> c != '0'))
            codewordsIdentificados.add(codeword);

        var caracteresAscii = new StringBuilder();

        for (var codeword1 : codewordsIdentificados) {
            var listaFibonacci = fibonacciAte(255);
            var soma = 0;

            for (int i = 0; i < codeword1.length(); i++) {
                if (codeword1.charAt(i) == '1')
                    soma += listaFibonacci.get(i);
            }

            caracteresAscii.append((char) soma);
        }

        return caracteresAscii.toString();
    }

    private static List<Integer> fibonacciAte(int maximo) {
        var retorno = new ArrayList<Integer>();
        int termo1 = 1, termo2 = 2, calculado = 0;
        retorno.add(termo1);
        retorno.add(termo2);

        for (int i = 2; calculado < maximo; i++) {
            calculado = termo1 + termo2;
            retorno.add(calculado);
            termo1 = termo2;
            termo2 = calculado;
        }

        return retorno;
    }

    private String mapToBinaryString(int intToDecode) {
        return parseToString(getBinaryFormated(intToDecode, BYTE_SIZE));
    }

    private char[] getChars(String text) {
        char[] chars = new char[text.length()];
        text.getChars(0, text.length(), chars, 0);
        return chars;
    }

    private String encode(String entrada) {
        var saida = new StringBuilder();
        var codeword = new StringBuilder();

        for (char letra : entrada.toCharArray()) {
            var listaFibonacci = calcularFibonacci(letra);

            var somatorio = listaFibonacci.get(0);
            codeword.append("1");

            for (var itemFibonacci : listaFibonacci.subList(1, listaFibonacci.size())) {
                if (somatorio + itemFibonacci <= letra) {
                    codeword.append("1");
                    somatorio += itemFibonacci;
                } else {
                    codeword.append("0");
                }
            }

            codeword.reverse();
            saida.append(codeword.append("1"));
            codeword = new StringBuilder();
        }

        return saida.toString();
    }

    @java.lang.Override
    public String encode(Message message) {
        return splitBytes(encode(message.getText()))
                .stream()
                .map(this::montaByte)
                .collect(joining());
    }

    private String montaByte(String bits) {
        return String.valueOf((char) Integer.parseInt(bits, 2));
    }

    @java.lang.Override
    public EncoderEnum getEncoderDecoder() {
        return null;
    }

    private static List<Integer> calcularFibonacci(int limiteMaximo) {
        List<Integer> fibonacci = new ArrayList<Integer>();
        fibonacci.add(1);
        fibonacci.add(2);
        fibonacci.add(3);

        int penultimo = fibonacci.get(fibonacci.size() - 2);
        int ultimo = fibonacci.get(fibonacci.size() - 1);

        int calculado;
        do {
            calculado = penultimo + ultimo;
            fibonacci.add(calculado);
            penultimo = ultimo;
            ultimo = calculado;
        }
        while (calculado <= limiteMaximo);

        fibonacci = fibonacci.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        return fibonacci.get(0) > limiteMaximo
                ? fibonacci.subList(1, fibonacci.size())
                : fibonacci;
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
}
