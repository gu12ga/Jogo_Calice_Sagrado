package br.ufla.gac106.s2022_1.caliceSagrado.Itens;

import br.ufla.gac106.s2022_1.caliceSagrado.Estado;

public class Calice extends ItemComestivel{
    //atributo com o estado do objeto
    private Estado estado;

    //atributo para dizer se o calice é falso ou verdadeiro
    private boolean ehVerdadeiro;

    public Calice(String nome, int peso, String descricao, int ptsVida, boolean ehVerdadeiro, Estado estado, String entidade){
        super(nome, peso, descricao, ptsVida, entidade);
        this.estado = estado;
        this.ehVerdadeiro = ehVerdadeiro;
    }

    public Estado getEstado(){
        return estado;
    }
    
    public boolean caliceEhVerdadeiro() {
        return ehVerdadeiro;
    }
}
