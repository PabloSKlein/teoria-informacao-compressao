package unisinos.teoriainformacao.compressao.file;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public static List<String> readFile() {

        try {
            var encoded = Files.readAllBytes(Paths.get("./toEncodeFile.txt"));
            return List.of(new String(encoded, StandardCharsets.UTF_8));
        }catch (Exception e){
            throw new RuntimeException("Erro ao ler arquivo", e);
        }
    }
}
