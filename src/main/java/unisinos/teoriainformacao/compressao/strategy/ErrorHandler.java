package unisinos.teoriainformacao.compressao.strategy;

public interface ErrorHandler {
    String addErrorValidationBits(String bitsToEncode);
    String decode(String bitsToValid);
}
