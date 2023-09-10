package br.ufla.gac106.s2022_1.caliceSagrado.Itens;

public class ItemComestivel extends Item{
    private int ptsVida;

    public ItemComestivel(String nome, int peso, String descricao, int ptsVida, String entidade){
        super(nome, peso, descricao, TipoItem.COMESTIVEL, -1, entidade);
        this.ptsVida = ptsVida;
    }

    public int getPtsVida(){
        return ptsVida;
    }
}
