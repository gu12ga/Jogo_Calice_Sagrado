package br.ufla.gac106.s2022_1.caliceSagrado;
import java.util.Random;

import br.ufla.gac106.s2022_1.baseJogo.InterfaceUsuario;
import br.ufla.gac106.s2022_1.caliceSagrado.Personagens.Personagem;


public class Batalha {
    private Analisador analisador;
    
    private InterfaceUsuario IU;

    public Batalha(InterfaceUsuario IU){
        this.IU = IU;
        analisador = new Analisador(IU);
    }

    // Batalha
    public boolean executar(Personagem p1, Personagem inimigo){
        // System.out.println("Um " + inimigo.getNome() + " selvagem apareceu!");
        IU.continuarMensagem("Um " + inimigo.getNome() + " selvagem apareceu!");
        boolean vezJogador = true;
        boolean escapou = false;
        do{
            status(p1, inimigo);
            menuBatalha();
            while(vezJogador){
                try{
                    Comando comando = analisador.pegarComandoBatalha();
                    if(comando != null){
                        String palavraDeComando = comando.getPalavraDeComando();
                        if (palavraDeComando.equals("atacar")) {
                            atacar(p1, inimigo);
                        }
                        else if(palavraDeComando.equals("escapar")){
                            if(escapar()){
                                inimigo.setVivo(false); // escapou
                                escapou = true;
                                break;
                            }
                            else{
                                // System.out.println("Não conseguiu escapar");
                                IU.continuarMensagem("Não conseguiu escapar");
                            }
                        }
                        vezJogador = false;
                    }
                }catch(Exception e){
                    IU.continuarMensagem(e.getMessage());
                }
            }
            // vez do inimigo
            if(!vezJogador){
                // System.out.println("Turno do " + inimigo.getNome());
                IU.continuarMensagem("Turno do " + inimigo.getNome());
                atacar(inimigo, p1);
            }
            vezJogador = true;
        }while(p1.getVivo() && inimigo.getVivo());

        if(p1.getVivo()){   // venceu ou escapou
            if(escapou){
                // System.out.println("Você saiu correndo com os rabos entre as pernas.");
                IU.continuarMensagem("Você saiu correndo com os rabos entre as pernas.");
            }
            else{
                // System.out.println("Você venceu o " + inimigo.getNome());
                IU.continuarMensagem("Você venceu o " + inimigo.getNome());
            }
            
            return true;
        }
        else{
            // System.out.println("Você foi derrotado pelo " + inimigo.getNome());
            IU.continuarMensagem("Você foi derrotado pelo " + inimigo.getNome());
            return false;
        }
    }

    private void menuBatalha(){
        // System.out.println("atacar");
        IU.continuarMensagem("atacar");
        // System.out.println("escapar");
        IU.continuarMensagem("escapar");
    }

    private void atacar(Personagem p1, Personagem p2){
        // Random rand = new Random();
        // int dano = rand.nextInt(p1.danoArma());
        int dano = p1.danoArma();
        p2.recebeDano(dano);
        if(p1.getNome().equals("bombadill")){
            // System.out.println("Dano de " + dano + " inflingido!");
            IU.continuarMensagem("Dano de " + dano + " inflingido!");
        }
        else{
            // System.out.println(p1.getNome() + " te atacou:");
            IU.continuarMensagem(p1.getNome() + " te atacou:");
            // System.out.println("Dano de " + dano + " sofrido!");
            IU.continuarMensagem("Dano de " + dano + " sofrido!");
        }
    }

    private boolean escapar(){
        Random rand = new Random();
        int chance = rand.nextInt(6);
        if(chance == 3)
            return true;
        return false;
    }

    private void status(Personagem p, Personagem p2){
        // System.out.println("Vida: " + p.getPtsVida());
        IU.continuarMensagem("Vida: " + p.getPtsVida());
        // System.out.println("Vida do inimigo: " + p2.getPtsVida());
        IU.continuarMensagem("Vida do inimigo: " + p2.getPtsVida());
    }
}