package br.ufla.gac106.s2022_1.caliceSagrado;

import br.ufla.gac106.s2022_1.baseJogo.Terminal;

public class App {
    private static Jogo jogo;
    public static void main(String[] args) throws Exception {
        //Jogo jogo = new Jogo(new Terminal());
        jogo = jogo.criarJogo(new Terminal());		
		jogo.jogar();
    }
}