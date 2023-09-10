package br.ufla.gac106.s2022_1.caliceSagrado.Itens;

import br.ufla.gac106.s2022_1.caliceSagrado.Estado;
//import br.ufla.gac106.s2022_1.caliceSagrado.Personagens.Personagem;

public class Maca extends ItemComestivel{
    private Estado estado;

    public Maca(String nome, int peso, String descricao, int ptsVida, Estado estado, String entidade){
        super(nome, peso, descricao, ptsVida, entidade);
        this.estado = estado;

    }

    public Estado getEstado(){
        return estado;
    }
    
    // @Override
    // public void usar(Personagem p){
    //     p.setEstado(estado);
    // }

}
