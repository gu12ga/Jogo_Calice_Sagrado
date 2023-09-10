package br.ufla.gac106.s2022_1.caliceSagrado;

import java.io.BufferedReader;
import java.io.FileWriter;

import br.ufla.gac106.s2022_1.baseJogo.InterfaceUsuario;

import java.io.FileReader;


public class Arquivo {

    private FileWriter escrita;
    private BufferedReader leitura;
    public void escrever(long tempo){

    try{
        
        String aux = Integer.toString((int)((tempo/1000)+ler()));
        escrita =  new FileWriter("tempo.txt");
        escrita.write(aux);
        escrita.close();

    } catch(Exception exception){}

    }

    public int ler(){
        try{
            leitura = new BufferedReader(new FileReader("tempo.txt"));
            String aux  = leitura.readLine();
            int aux2 = Integer.parseInt(aux);
            return aux2;
        
        } catch(Exception exception){
            
            return  0;
        }
    }
    
}
