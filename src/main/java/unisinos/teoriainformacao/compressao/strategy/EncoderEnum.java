package unisinos.teoriainformacao.compressao.strategy;

import java.util.Arrays;

public enum EncoderEnum {
    GOLOMB(1) {
        @Override
        public Encoder getEncoderInstance() {
            return new Golomb();
        }
    },
    ELIAS_GAMMA(2) {
        @Override
        public Encoder getEncoderInstance() {
            return new Golomb();
        }
    },
    FIBONACCI(3) {
        @Override
        public Encoder getEncoderInstance() {
            return new Golomb();
        }
    },
    UNARY(4) {
        @Override
        public Encoder getEncoderInstance() {
            return new Unary();
        }
    },
    DELTA(5) {
        @Override
        public Encoder getEncoderInstance() {
            return new Golomb();
        }
    };

    public final int key;

    EncoderEnum(int key) {
        this.key = key;
    }

    public static EncoderEnum byKey(int encoderKey) {
        return Arrays.stream(EncoderEnum.values())
                .filter(encoderEnum -> encoderEnum.key == encoderKey)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Chave do encoder inválida"));
    }

    public abstract Encoder getEncoderInstance();

}
