package br.ufla.gac106.s2022_1.caliceSagrado.Itens;

public class ItemMisc extends Item{
    public ItemMisc(String nome, int peso, String descricao, String entidade){
        super(nome, peso, descricao, TipoItem.MISC, -1, entidade);
    }
}
