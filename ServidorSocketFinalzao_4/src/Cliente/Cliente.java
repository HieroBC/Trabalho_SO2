package Cliente;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class Cliente {
	private static PrintWriter pw;
	private BufferedReader br;
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
                //leitura do arquivo inicial
                int userId = 534;
                System.out.println("userId: " + userId);
                String listaInicial = dataHelper.readFile("/Users/hiero.bartholo/Documents/files/dados.txt");
                // Cria uma string com o userId e o conteudo para poder gerar o arquivo pela string
                String arquivoString = Integer.toString(userId).concat(";").concat(listaInicial);
                //cliente envia mensagem para o primeiro servidor
		Socket cliente = new Socket("localhost", 9090);
		enviarMsg(arquivoString,cliente);

	}
	
	private static void enviarMsg(String text, Socket skt) throws IOException {
		pw = new PrintWriter(skt.getOutputStream(), true);
		pw.println(text); 
                pw.flush();
	}
}