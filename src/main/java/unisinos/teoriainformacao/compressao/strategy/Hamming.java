package unisinos.teoriainformacao.compressao.strategy;

import java.util.stream.Collectors;

import static unisinos.teoriainformacao.compressao.util.PrimitiveUtil.primitiveArrayToObjectStream;

public class Hamming implements ErrorHandler {


    public static final int NUMBER_OF_BITS_TO_ENCODE = 3;
    public static final int NUMBER_OF_BITS_AFTER_ENCODE = 7;

    @Override
    public String addErrorValidationBits(String bitsToEncode) {
        var encodedStringBuilder = new StringBuilder();
        for (int i = 0; i < bitsToEncode.length(); i = i + NUMBER_OF_BITS_TO_ENCODE) {
            var indiceFinal = i + NUMBER_OF_BITS_TO_ENCODE;
            if (indiceFinal > bitsToEncode.length()) {
                encodedStringBuilder.append(bitsToEncode.substring(i));
            } else {
                encodedStringBuilder.append(defineNewBits(bitsToEncode.substring(i, i + NUMBER_OF_BITS_TO_ENCODE)));
            }
        }
        return encodedStringBuilder.toString();
    }

    @Override
    public String decode(String bitsToValid) {
        var encodedStringBuilder = new StringBuilder();
        for (int i = 0; i < bitsToValid.length(); i = i + NUMBER_OF_BITS_AFTER_ENCODE) {
            var originalBits = bitsToValid.substring(i, i + NUMBER_OF_BITS_TO_ENCODE);
            var recivedHammingBits = bitsToValid.substring(i + NUMBER_OF_BITS_TO_ENCODE, i + NUMBER_OF_BITS_AFTER_ENCODE);
            var calculatedHammingBits = defineNewBits(originalBits).substring(i + NUMBER_OF_BITS_TO_ENCODE);

            if (!recivedHammingBits.equals(calculatedHammingBits)) {
                return tryToFixMessage(originalBits, recivedHammingBits, calculatedHammingBits);
            }

            encodedStringBuilder.append(originalBits);
        }
        return encodedStringBuilder.toString();
    }

    private String tryToFixMessage(String originalBits, String recivedHammingBits, String calculatedHammingBits) {
        var iondexesToFix = new StringBuilder();
        for (int i = 0; i < recivedHammingBits.length(); i++) {
            iondexesToFix.append(recivedHammingBits.charAt(i) != calculatedHammingBits.charAt(i) ? "1" : "0");
        }

        int indexToFix = HammingFixEnum.findByPattern(iondexesToFix.toString()).getIndexToFix();

        var chars = originalBits.toCharArray();
        if (indexToFix < 3) {
            chars[indexToFix] = flipValue(chars[indexToFix]);
        }

        return new String(chars);
    }

    private char flipValue(char c) {
        return c == '1' ? '0' : '1';
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
