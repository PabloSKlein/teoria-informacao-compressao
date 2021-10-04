package unisinos.teoriainformacao.compressao.strategy;

import java.util.stream.Collectors;

import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Hamming implements ErrorHandler {


    public static final int NUMBER_OF_BITS_TO_ENCODE = 3;

    @Override
    public String addErrorValidationBits(String bitsToEncode) {
        var encodedStringBuilder = new StringBuilder();
        for (int i = 0; i < bitsToEncode.length(); i = i + NUMBER_OF_BITS_TO_ENCODE) {
            encodedStringBuilder.append(defineNewBits(bitsToEncode.substring(i, i + NUMBER_OF_BITS_TO_ENCODE)));
        }
        return encodedStringBuilder.toString();
    }

    @Override
    public String decode(String bitsToValid) {
        return null;
    }

    private String defineNewBits(String bits) {
        var intList = primitiveArrayToObjectStream(bits.toCharArray())
                .map(Integer::valueOf)
                .collect(Collectors.toList());


        var result1e2 = getResultBit(intList.get(0) + intList.get(1));
        var result2e3 = getResultBit(intList.get(1) + intList.get(2));
        var result3e1 = getResultBit(intList.get(2) + intList.get(0));
        var result1e2e3 = getResultBit(intList.get(0) + intList.get(1) + intList.get(2));

        return bits.concat(result1e2).concat(result2e3).concat(result3e1).concat(result1e2e3);
    }

    private String getResultBit(Integer sum) {
        return sum % 2 == 0 ? "0" : "1";
    }
}
