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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class SegundoServidor {
	private static PrintWriter pw;
	private static BufferedReader br;
	public static void main(String[] args) throws UnknownHostException, IOException {
		ServerSocket listener = new ServerSocket(9091);
		Socket servidor = listener.accept();
                String respostaservidor = getMessage(servidor);
                System.out.println("Lista desordenada B:" + respostaservidor);
                
                 // Transforma a lista Inicial em um arquivo
                Arquivo arquivoInicial = Arquivo.stringToArquivo(respostaservidor);

                
                //Ordenada o conteudo do arquivo
                arquivoInicial.ordenarDados();
                
                //transforma o arquivo em string
                String Respostaparaprincipal = arquivoInicial.arquivoToString();
                System.out.println("Lista ordenada B:" + arquivoInicial.arquivoToString());
                //retorna para o primeiro servidor
                Socket RetornoSegundoServer = new Socket("localhost", 9092);
		enviarMsg(Respostaparaprincipal,RetornoSegundoServer);
                

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