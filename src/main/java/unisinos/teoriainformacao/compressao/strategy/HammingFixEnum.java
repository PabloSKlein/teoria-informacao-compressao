package unisinos.teoriainformacao.compressao.strategy;

import java.util.Arrays;

public enum HammingFixEnum {
    FIRST_IS_WRONG("1011", 0),
    SECOND_IS_WRONG("1101", 1),
    THIRD_IS_WRONG("0111", 2),
    FIRST_TOKEN_IS_WRONG("1000", 3),
    SECOND_TOKEN_IS_WRONG("0100", 4),
    THIRD_TOKEN_IS_WRONG("0010", 5),
    FOURTH_TOKEN_IS_WRONG("0001", 6);

    private final String pattern;
    private final int indexToFix;

    HammingFixEnum(String whenItIsWrong, int indexToFix) {
        this.pattern = whenItIsWrong;
        this.indexToFix = indexToFix;
    }

    public static HammingFixEnum findByPattern(String indexesToFix) {
        return Arrays.stream(HammingFixEnum.values())
                .filter(it -> it.pattern.equals(indexesToFix))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Não foi possível corrigir a mensagem!"));
    }

    public String getPattern() {
        return pattern;
    }

    public int getIndexToFix() {
        return indexToFix;
    }
}
