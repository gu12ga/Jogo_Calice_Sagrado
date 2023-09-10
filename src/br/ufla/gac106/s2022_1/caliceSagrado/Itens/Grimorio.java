package br.ufla.gac106.s2022_1.caliceSagrado.Itens;

public class Grimorio extends Item {

    public Grimorio(String nome, int peso, String descricao, String entidade){
        super(nome, peso, descricao, TipoItem.MISC, -1, entidade);
    }
    // @Override
    // public String usar(){
    //     return super.descricao();
    // }

}
