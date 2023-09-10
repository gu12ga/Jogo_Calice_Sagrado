package br.ufla.gac106.s2022_1.baseJogo;

import java.util.Scanner;

public class Terminal implements InterfaceUsuario{
    // criando um private
    private Scanner entrada;

    public Terminal() {
        entrada = new Scanner(System.in);
    }

    @Override
    public void exibirMensagem(String mensagem) {
       System.out.println(mensagem);
        
    }

    @Override
    public void continuarMensagem(String mensagem) {
        System.out.println(mensagem);
        
    }

    @Override
    public String obterComando() {
        String aux = entrada.nextLine();
        return aux;
    }

    @Override
    public String obterInformacao(String instrucao) {
        String aux = entrada.nextLine();
        return aux;
    }

    @Override
    public void ambienteAtualMudou(EntidadeGrafica ambiente) {
        // não precisa
        
    }

    @Override
    public void jogadorPegouItem(EntidadeGrafica item) {
        // não precisa
        
    }

    @Override
    public void jogadorDescartouItem(EntidadeGrafica item) {
        // não precisa
        
    }
}
