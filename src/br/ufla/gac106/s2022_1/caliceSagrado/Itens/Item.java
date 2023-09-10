package br.ufla.gac106.s2022_1.caliceSagrado.Itens;
//import br.ufla.gac106.s2022_1.caliceSagrado.Personagens.Personagem;


import br.ufla.gac106.s2022_1.baseJogo.EntidadeGrafica;
import br.ufla.gac106.s2022_1.caliceSagrado.Estado;

public class Item  extends EntidadeGrafica{
    private String nome;
    private int peso;
    private int dano;
    private String descricao;
    private Estado estado;
    private TipoItem tipo;


    public Item(String nome, int peso, String descricao, TipoItem tipo, int dano, String entidade){
        super(entidade);
        this.nome =  nome;
        this.peso = peso;
        this.descricao = descricao;
        this.tipo = tipo;
        this.estado = Estado.NORMAL;
        this.dano = dano;
    }
    
    public String getDescricao(){
        String d = "";
        d += "\nNome: " + nome;
        d += "\nPeso: " + peso + "g\n";
        d += descricao;

        return d;
    }
    @Override
    public String getNome(){
        return nome;
    }


    public int getPeso(){
        return peso;
    }

    public String descricao(){
        return descricao;
    }

    public TipoItem getTipo(){
        return tipo;
    }

    // public void usar(Personagem jogador){
    //     jogador.setEstado(Estado.SAGRADO);
    // }

    // public String usar(){
    //     return null;
    // }

    public Estado getEstado(){
        return estado;
    }

    public int getDano(){
        return dano;
    }

    public int getPtsVida(){
        return 0;
    }
}
