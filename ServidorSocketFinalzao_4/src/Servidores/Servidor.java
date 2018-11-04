package Servidores;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diego, hiero, alexandre, vitor
 */


import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;



public class Servidor {
	private static PrintWriter pw;
	private static BufferedReader br;
	public static void main(String[] args) throws UnknownHostException, IOException {
        
            //primeiro servidor recebe
            ServerSocket listener = new ServerSocket(9090);
            Socket servidor = listener.accept();
                
            // Transforma a lista Inicial em um arquivo
            String listaarquivoinicial = getMessage(servidor);
            System.out.println("Lista Desordenada: " + listaarquivoinicial);
            Arquivo arquivoInicial = Arquivo.stringToArquivo(listaarquivoinicial);
             //System.out.println("Lista desordenada total:" + getMessage(servidor));
            
            // cria nova lista para transmitir ao servidor B
            List<Integer>[] listaDeArquivosSeparada = arquivoInicial.separarArquivo();
            
            // inicializa ambos os arquivos
            Arquivo arquivoA = new Arquivo(arquivoInicial.getUserId() , listaDeArquivosSeparada[0]);
            Arquivo arquivoB = new Arquivo(arquivoInicial.getUserId() , listaDeArquivosSeparada[1]);
            System.out.println("\nArquivo A: " + arquivoA.arquivoToString());
            // transforma o arquivoB em string, envia pro servidor B, que transforma em Arquivo novamente
            String escritoB = arquivoB.arquivoToString();
            System.out.println("Arquivo B: " + escritoB);
            //envia para o segundo servidor
            Socket servidor2 = new Socket("localhost",9091);
            enviarMsg(escritoB,servidor2);
                
            //Ordena a Primeira Lista no servidor A, aonde ocorre:
            arquivoA.ordenarDados();
            System.out.println("Lista ordenada A:" + arquivoA.arquivoToString());
            //recebe do segundo servidor
            ServerSocket servidorRetorno = new ServerSocket(9092);
            Socket respostaB = servidorRetorno.accept();
            String RespostaVetB = getMessage(respostaB);
            arquivoB = Arquivo.stringToArquivo(RespostaVetB);
            System.out.println("Resposta do segundo servidor ordenado: " + RespostaVetB);
           
            
             // junta os dois arquivos do mesmo userId em um só
            Arquivo arquivoFinal = null;
            if (arquivoA.getUserId() == arquivoB.getUserId()) {
                listaDeArquivosSeparada[0] = arquivoA.getDados();
                listaDeArquivosSeparada[1] = arquivoB.getDados();
                List<Integer> listaCombinada = Arquivo.combinarArquivos(listaDeArquivosSeparada);
                arquivoFinal = new Arquivo(arquivoA.getUserId(), listaCombinada);
                // transforma o arquivo final em string, envia para o cliente, que transforma em um Arquivo e salva em txt. tambem mostra a saida no campo de logs.
                String outputFinal = arquivoFinal.arquivoToString();
                System.out.println("\nArquivo Final: " + outputFinal);

                //funcao de converter string em txt
                dataHelper.writeFile("resultadoOrdenacao.txt", outputFinal);
            } else {
                System.out.println("Erro: User id nao batem");
            }
            
            //retorna
	}
	
	private static void enviarMsg(String text, Socket skt) throws IOException {
		pw = new PrintWriter(skt.getOutputStream(), true);
		pw.println(text); 
                pw.flush();
	}

	private static String getMessage(Socket skt) throws IOException {
		br = new BufferedReader(new InputStreamReader(skt.getInputStream())); 
		return br.readLine();
	}
        
        private static void escrever(String nomeArquivo) throws IOException {
            String data = "";
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(nomeArquivo)));
            while ((data = br.readLine()) != null && data.length() > 0) {
                bw.write(data);
                bw.newLine();
                bw.flush();
            }
            bw.close();
        }
}