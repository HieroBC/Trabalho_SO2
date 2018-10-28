/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author hiero.bartholo
 */
public class JavaApplication2 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        int userId = 534;
       System.out.println("userId: " + userId);
        
        // importa um arquivo txt e transforma em uma lista
        // OPERACOES
        
       String listaInicial = dataHelper.readFile("/Users/hiero.bartholo/Documents/files/dados.txt");
        // Fim OPERACOES
        
        //  cria uma lista A
        //  x = [7, 3, 1, 9, 4, 16, 8, 3, 5, 2]

       System.out.println("Lista Inicial: " + listaInicial);
       
        // Cria uma string com o userId e o conteudo para poder gerar o arquivo pela string
        String arquivoString = Integer.toString(userId).concat(";").concat(listaInicial);
        
        // Transforma a lista Inicial em um arquivo
        Arquivo arquivoInicial = Arquivo.stringToArquivo(arquivoString);

        // transforma o arquivo em string, envia pro servidor, servidor A recebe e transforma em Arquivo novamente
       System.out.println("\nArquivo Inicial: " + arquivoInicial.arquivoToString());
        // OPERACOES
        
        // cria nova lista para transmitir ao servidor B
        List<Integer>[] listaDeArquivosSeparada = arquivoInicial.separarArquivo();
        
        Arquivo arquivoA = new Arquivo(arquivoInicial.getUserId() , listaDeArquivosSeparada[0]);
        Arquivo arquivoB = new Arquivo(arquivoInicial.getUserId() , listaDeArquivosSeparada[1]);
        
        // transforma o arquivoB em string, envia pro servidor B, que transforma em Arquivo novamente
      System.out.println("\nArquivo A: " + arquivoA.arquivoToString());
      System.out.println("Arquivo B: " + arquivoB.arquivoToString());
        // OPERACOES
        
        // no servidor A ocorre:
        arquivoA.ordenarDados();
        
        // no servidor B ocorre:
        arquivoB.ordenarDados();
        // transforma arquivoB em string, envia de volta ordenado para o servidor A, que transforma em Arquivo novamente
       System.out.println("\nArquivo A ordenado: " + arquivoA.arquivoToString());
       System.out.println("Arquivo B ordenado: " + arquivoB.arquivoToString());
        // OPERACOES
        
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
            // OPERACOES
            
            //funcao de converter string em txt
            dataHelper.writeFile("teste", outputFinal);
        } else {
            System.out.println("Erro: User id nao batem");
        }
    }
    
}
