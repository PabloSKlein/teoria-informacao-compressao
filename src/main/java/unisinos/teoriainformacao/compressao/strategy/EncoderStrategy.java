package unisinos.teoriainformacao.compressao.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EncoderStrategy {
    public static List<EncoderDecoder> getEncodersStrategy() {
        return Arrays.stream(EncoderEnum.values())
                .map(EncoderEnum::getEncoderInstance)
                .collect(Collectors.toList());
    }

    public static Optional<EncoderDecoder> getEncoder(int encoderKey) {
        return getEncodersStrategy().stream()
                .filter(encoder -> encoder.getEncoderDecoder().key == encoderKey)
                .findAny();
    }
}
