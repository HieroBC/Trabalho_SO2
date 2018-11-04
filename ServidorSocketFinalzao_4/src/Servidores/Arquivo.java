/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

/**
 *
 * @author hiero.bartholo
 */
public class Arquivo {
    private int userId;
    private List<Integer> dados;
    
    public int getUserId () {
        return this.userId;
    }
    public void setUserId (int id) {
        this.userId = id;
    }
    
    public List<Integer> getDados () {
        return this.dados;
    }
    public void setDados (List<Integer> d) {
        this.dados = d;
    }
   
    // Usa a classe Collections para ordenar a lista de numeros
    public void ordenarDados () {
        Collections.sort(this.dados);
    }
    
    // combina um array de duas listas em uma única lista já ordenada.
    public static List<Integer> combinarArquivos (List[] L) {
        List<Integer> lista1 = L[0];
        List<Integer> lista2 = L[1];
        // itera pelos dois arrays
        List<Integer> listaFinal = new ArrayList<>();
        //  int i = 0, j = 0, k = 0;
        //  se lista1[i] > lista2[j]
        while (lista1.size()>0 && lista2.size()>0) {
            if(lista1.get(0) > lista2.get(0)){
                listaFinal.add(lista2.remove(0));
            } else {
                listaFinal.add(lista1.remove(0));
            }  
        }
        if(lista1.size() > lista2.size()){
            listaFinal.addAll(lista1);
        } else {
            listaFinal.addAll(lista2);
        }
        return listaFinal;
    }
    
    // separa uma List e as retorna em um array de List`s com duas posições.
    public List<Integer>[] separarArquivo () {
        int listSize = this.dados.size();
        // cria uma List com a primeira metade e uma com a segunda metade dos dados
        List<Integer> first = new ArrayList<>(this.dados.subList(0, (listSize + 1)/2));
        List<Integer> second = new ArrayList<>(this.dados.subList((listSize + 1)/2, listSize));
        
        return new List[] {first, second};
    }
    
    public String arquivoToString () {
        //converte os dados do arquivo para String no formato:
        // "userId;1,2,3,4,5,n"
        String result = "";
        String s = Integer.toString(this.userId);
        result = result.concat(s + ";");
        int it;
        int size = this.dados.size();
        for (it = 0 ; it < size ; it++){
            result = result.concat(Integer.toString(this.dados.get(it)) + ",");
        }
        return result;
    }
    
    public static Arquivo stringToArquivo (String s) {
        //transformar string numa lista de numeros
        String[] splitted = s.split(";");
        int id = Integer.valueOf(splitted[0]);
        splitted = splitted[1].split(",");
        List<Integer> lista = new ArrayList<>();
        for (String x: splitted) {
            lista.add(Integer.valueOf(x));
            //System.out.println("Lista desordenada B:" + x);
        }
        return new Arquivo(id, lista);
        
    }
    
    Arquivo (int id, List<Integer> x) {
        this.userId = id;
        this.dados = x;
    }
}
