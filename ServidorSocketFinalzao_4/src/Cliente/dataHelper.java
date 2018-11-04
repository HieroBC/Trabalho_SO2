/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author diego, hiero, alexandre, vitor
 */
public class dataHelper {
    //read file
    public static String readFile (String fileName) throws FileNotFoundException{
        String line;
        Scanner fileIn = new Scanner(new File(fileName));
        line = fileIn.nextLine();
        line = line.replaceAll("\\s","");
        return line;
    }
    
    // write file
    public static void writeFile (String fileName, String conteudo) throws IOException {
        File file = new File ("/Users/diego/Documents/files/dados.txt");
        try (PrintWriter printWriter = new PrintWriter ("ordenado.txt")) {
            String[] splitted = conteudo.split(";");
            String dados = splitted[1];
            dados = dados.replaceAll(",",", ");
            printWriter.println(dados);
        }
    }
}
