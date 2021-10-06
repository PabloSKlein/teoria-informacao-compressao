package unisinos.teoriainformacao.compressao.strategy;

import unisinos.teoriainformacao.compressao.file.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Fibonacci implements Encoder, Decoder {
    @java.lang.Override
    public String decode(Message message) {
        return null;
    }

    public String encode(String entrada)
    {
        var saida = "";
        var codeword = "";

        for (char letra: entrada.toCharArray())
        {
           var listaFibonacci = calcularFibonacci(letra);

           var somatorio = listaFibonacci.get(0);
           codeword += '1';

           for(var itemFibonacci: listaFibonacci.subList(1, listaFibonacci.size()))
           {
               if (somatorio + itemFibonacci <= letra)
               {
                   codeword += '1';
                   somatorio += itemFibonacci;
               }
               else
               {
                    codeword += '0';
               }
           }

           codeword = new StringBuilder(codeword).reverse().toString();
           saida += codeword + "1";
           codeword = "";
        }

        return saida;
    }

    @java.lang.Override
    public String encode(Message message) {
        return encode(message.getText());
    }

    @java.lang.Override
    public EncoderEnum getEncoderDecoder() {
        return null;
    }

    private static List<Integer> calcularFibonacci(int limiteMaximo)
    {
        List<Integer> fibonacci = new ArrayList<Integer>();
        fibonacci.add(1);
        fibonacci.add(2);
        fibonacci.add(3);

        int penultimo = fibonacci.get(fibonacci.size() - 1);
        int ultimo = fibonacci.get(fibonacci.size() - 1);

        int calculado;
        do
        {
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
}
