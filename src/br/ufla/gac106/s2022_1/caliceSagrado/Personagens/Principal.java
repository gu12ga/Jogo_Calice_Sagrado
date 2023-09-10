package br.ufla.gac106.s2022_1.caliceSagrado.Personagens;

import java.util.ArrayList;

import br.ufla.gac106.s2022_1.caliceSagrado.Estado;
import br.ufla.gac106.s2022_1.caliceSagrado.Ambiente.Ambiente;
import br.ufla.gac106.s2022_1.caliceSagrado.Itens.Item;

public class Principal extends Personagem{
    private ArrayList<Item> mochila;
    private Ambiente ambiente;

    public Principal(String nome, Estado estado, int pontosDeVida, Ambiente ambiente){
        super(nome, estado, pontosDeVida);
        mochila = new ArrayList<>();
        this.ambiente = ambiente;
    }

    public void setAmbiente(Ambiente ambiente){
        this.ambiente = ambiente;
    }

    public Ambiente getAmbiente(){
        return ambiente;
    }

    public int adicionarItem(Item item){
        if(item != null){
            if(pesoMochila()+item.getPeso() > 1500){
                return 0;
            } 
            else {
                mochila.add(item);
                 return 1;
            }
        } else
            return -1;
    }

    public boolean mochilaVazia(){
        return  mochila.isEmpty();
    }

    public int pesoMochila(){
        int total = 0 ;
        for(int i = 0; i < mochila.size(); i++)
        {
            total += mochila.get(i).getPeso();
        }

        return total;
    }

    public Item removerItem(String nome){
        for(Item i : mochila){
            if(i.getNome().equals(nome)){
                mochila.remove(i);
                return i;
            }
        }
        return null;
    }

    public String inventario(){
        String s = "";
        Item item;
        for(int i = 0; i < mochila.size(); ++i){
            item = mochila.get(i);
            if(i == mochila.size() - 1){
                s += item.getNome();
                continue;
            }
            s += item.getNome() + ", ";
        }
        return s;
    }

    public boolean possuiItem(String nome){
        for(Item i : mochila){
            if(i.getNome().equals(nome)){
                return true;
            }
        }
        return false;
    }

    public Item getItem(String nome){
        Item aux = null;
        for(Item i : mochila){
            if(i.getNome().equals(nome)){
                aux = new Item(i.getNome(), i.getPeso(), i.descricao(), i.getTipo(), i.getDano(), getNome());
                return aux;
            }
        }
        return null;
    }

    // para utilizar itens do tipo misc
    public String ler(Item item){
        return item.descricao();
    }

    // consome itens do Tpo.CONSUMIVEL
    public void usar(Item item){
        setPtsVida(getPtsVida() + item.getPtsVida());
        setEstado(item.getEstado());
    }
    // equipa itens do tipo arma
    public void equipar(Item arma){
        setArma(arma);
    }

    // public List<Item> itensBatalha(){
    //     List<Item> usaveisBatalha = new ArrayList<>();
    //     for(Item i : mochila){
    //         if(i.getTipo() == TipoItem.COMESTIVEL)
    //             usaveisBatalha.add(i);
    //     }

    //     return usaveisBatalha;
    // }

    
}
