import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// Equipe: Joel Pazini Martins / Jean Carlos Ricken / Lucas Orestes Fabris

public class BuscarNomeComThread {
    static String nome;
    static String diretorio = "C:/Users/User/Desktop/Trabalho1/src/arquivos/dataset_g/"; // alterar caminho quando for usar
    public static void main(String[] args){
        File arq_path = new File(diretorio);
        File[] arq_lista = arq_path.listFiles();

        int quant_threads = 4; // informa a quantidade de threads
        Thread[] threads = new Thread[quant_threads];

        final int arq_thread = arq_lista.length/quant_threads;
        final int arq_restantes = arq_lista.length%quant_threads;
        
        Scanner ler = new Scanner(System.in);
        System.out.println("Escreva o nome: ");
        nome = ler.nextLine();
        
        for(int t=0; t<quant_threads; t++){
            final int thread = t;
            threads[t]= new Thread(){
                @Override
                public void run(){
                    exec_thread(arq_lista, quant_threads, thread, arq_thread, arq_restantes);
                }
            };
        }
        long tempo_inicial = System.currentTimeMillis();
        for(Thread t1: threads){
            t1.start();
        }
        for(Thread t2: threads){
            try{
                t2.join();
            }catch (InterruptedException e){
        
            }
        }
        long tempo_final = System.currentTimeMillis();
        System.out.printf("Tempo de execução: %.3f ms%n", (tempo_final - tempo_inicial) / 1000d);
    }
    private static void exec_thread(File[] arq_lista, int quant_threads, int thread, int arq_thread, int arq_restantes){
        for(int i=thread*arq_thread; i<(thread+1)*arq_thread; i++){
            try {
                int count = 0;
                String[] nome_arr = nome.split(" ");
                Scanner ler = new Scanner(new FileReader(diretorio + arq_lista[i].getName()));
                while (ler.hasNextLine()) {
                    String linha = ler.nextLine();
                    String[] linha_arr = linha.split(" ");
                    count++;
                    if (linha_arr[0].equals(nome) && nome_arr.length == 1) {
                        System.out.println("[Nome do arquivo]: " + arq_lista[i].getName() + " [Número da linha]: " + count + " [Nome]: " + linha_arr[0] + " " + linha_arr[1] + " [Thread]: " + thread);
                    }else if(linha_arr[0].equals(nome_arr[0]) && linha_arr[1].equals(nome_arr[1])){
                        System.out.println("[Nome do arquivo]: " + arq_lista[i].getName() + " [Número da linha]: " + count + " [Nome]: " + linha_arr[0] + " " + linha_arr[1] + " [Thread]: " + thread);
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BuscarNomeComThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(thread == quant_threads-1 && arq_restantes > 0){
            for(int j=arq_lista.length-arq_restantes; j<arq_lista.length; j++){
                try {
                    int count = 0;
                    String[] nome_arr = nome.split(" ");
                    Scanner ler = new Scanner(new FileReader(diretorio + arq_lista[j].getName()));
                    while (ler.hasNextLine()) {
                        String linha = ler.nextLine();
                        String[] linha_arr = linha.split(" ");
                        count++;
                        if (linha_arr[0].equals(nome) && nome_arr.length == 1) {
                            System.out.println("[Nome do arquivo]: " + arq_lista[j].getName() + " [Número da linha]: " + count + " [Nome]: " + linha_arr[0] + " " + linha_arr[1] + " [Thread]: " + thread);
                        }else if(linha_arr[0].equals(nome_arr[0]) && linha_arr[1].equals(nome_arr[1])){
                            System.out.println("[Nome do arquivo]: " + arq_lista[j].getName() + " [Número da linha]: " + count + " [Nome]: " + linha_arr[0] + " " + linha_arr[1] + " [Thread]: " + thread);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BuscarNomeComThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
