import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Equipe: Joel Pazini Martins / Jean Carlos Ricken / Lucas Orestes Fabris

public class BuscarNome {
    public static void main(String[] args) throws IOException {
        String nome; // Vai receber o nome
        String diretorio = "C:/Users/luuu_/Desktop/dataset_g/"; // alterar caminho quando for usar
        Scanner ler = new Scanner(System.in); // Inicia o Scanner que vai armazenar o q foi digitado
        System.out.println("Escreva o nome: "); // 
        nome = ler.nextLine();
        long tempo_inicial = System.currentTimeMillis(); // inicia o calculo de tempo de execução
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(diretorio))) { // captura os arquivos dentro da pasta
		for (Path file: stream) { // enquando tiver arquivos ele vai estar no for
                    int count = 0; // contador de linhas
                    String[] nome_arr = nome.split(" ");// caso tiver nome e sobrenome ele quebra e fica so com o primeiro
                    Scanner arq_leitura = new Scanner(new FileReader(diretorio + file.getFileName())); // Leitura do arquivo inteiro
                    while (arq_leitura.hasNextLine()) { // verifica se tem proxima linha
                        String linha = arq_leitura.nextLine();// Pega tudo que tem dentro do arquivo lido linha por linha
                        String[] linha_arr = linha.split(" "); // Vai separar nome e sobrenome para poder fazer a busca apenas com o primeiro nome
                        count++; // conta linha
                        if (linha_arr[0].equals(nome) && nome_arr.length == 1) { // se o nome for do tamanho um é pesquisado apenas o primeiro nome,
                            // se for do tamanho dois nome e sobrenome 
                            // se encontar o nome digita vai printar todos os arquivos e nomes que foram localizados
                            System.out.println("[Nome do arquivo]: " + file.getFileName() + " [Número da linha]: " + count + " [Nome]: " + linha_arr[0] + " " + linha_arr[1]);
                        }else if(linha_arr[0].equals(nome_arr[0]) && linha_arr[1].equals(nome_arr[1])){
                            // caso seja digitado o nome e o sobrenome igual
                            System.out.println("[Nome do arquivo]: " + file.getFileName() + " [Número da linha]: " + count + " [Nome]: " + linha_arr[0] + " " + linha_arr[1]);
                        }
                    }
                }
            }
            catch (IOException | DirectoryIteratorException ex) {
               System.err.println(ex);
            }
            long tempo_final = System.currentTimeMillis(); // finaliza o calculo do tempo de execução 
            System.out.printf("Tempo de execução: %.3f ms%n", (tempo_final - tempo_inicial) / 1000d);
	}
}