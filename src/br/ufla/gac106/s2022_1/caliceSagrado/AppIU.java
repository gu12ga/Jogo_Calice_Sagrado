package br.ufla.gac106.s2022_1.caliceSagrado;

import br.ufla.gac106.s2022_1.baseJogo.Tela;

public class AppIU {
    private static Jogo jogo;
    public static void main(String[] args) throws Exception {
        //Jogo jogo = new Jogo(new Tela("Calice Sagrado"));	
        jogo = jogo.criarJogo(new Tela("Calice Sagrado"));	
		jogo.jogar();
    }
}