package br.ufla.gac106.s2022_1.caliceSagrado.Itens;

public class ItemChave extends Item{

    public ItemChave(String nome, int peso, String descricao, String entidade){
        super(nome, peso, descricao, TipoItem.CHAVE, -1, entidade);
    }
}
