# Encoder - Decoder (Teoria da informação 2021/2)

## Implementação

As interfaces `Encoder` e `Decoder` definem o contrato básico para a implementação de cada algoritmo.

Algortimos de encoder/decoder implementados:

1. Delta
1. Fibonacci
1. Golomb
1. Unário

O algortimo Hamming foi implementado em cima do contrato desta interface, portanto funciona é aplicado em todos os casos.

O CRC não foi implementado.

Tecnologia: Java 16 (com Gradle).

## Execução

Não foi implementada a CLI para execução, portanto é necessário executar via ferramentas de debug (sugestão é usar o IntelliJ) ou recompilar e executar sempre que precisar alterar o arquivo a ser lido.

O arquivo a ser lido é configurado no arquivo `FileReader.java` (o padrão é `toEncodeFile.txt` e a saída será um arquivo chamado `encodedFile.txt`).

Os algoritmos para _encoding_/_decoding_ têm testes unitários para facilitar os testes/debug.