package br.ufla.gac106.s2022_1.caliceSagrado.Personagens;

import br.ufla.gac106.s2022_1.caliceSagrado.Estado;
import br.ufla.gac106.s2022_1.caliceSagrado.Itens.*;

import java.util.List;

public class Personagem {
    private String nome;
    private Estado estado;
    private Boolean vivo;
    private int pontosDeVida;
    private Item armaEquipada;
    private final int MAX = 30;

    public Personagem(String nome, Estado estado, int vida){
        this.nome = nome;
        this.estado = estado;
        vivo = true;
        pontosDeVida = vida;
    }

    public String getNome(){
        return nome;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public void setVivo(Boolean b){
        if(b){
            vivo = b;
        }
        else{
            vivo = b;
            pontosDeVida = 0;
        }
    }

    public Boolean getVivo(){
        if(pontosDeVida <= 0)
            return false;
        return true;
    }

    public String getArma(){
        return armaEquipada.getNome();
    }

    public void setArma(Item arma){
        armaEquipada = arma;
    }

    public int danoArma(){
        int dano = armaEquipada.getDano();
        return dano;
    }

    public void recebeDano(int dano){
        if(pontosDeVida > 0)
            pontosDeVida -= dano;
        if(pontosDeVida <= 0)
            vivo = false;
    }

    public int getPtsVida(){
        return pontosDeVida;
    }

    public void setPtsVida(int num){
        if(pontosDeVida + num >= MAX){
            pontosDeVida = MAX;
        }
        else{
            pontosDeVida += num;
        }
    }

    public List<Item> itensBatalha(){
        return null;
    }

}
