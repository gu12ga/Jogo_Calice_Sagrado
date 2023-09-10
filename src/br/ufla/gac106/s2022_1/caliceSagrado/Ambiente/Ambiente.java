package br.ufla.gac106.s2022_1.caliceSagrado.Ambiente;
import java.util.ArrayList;
import java.util.HashMap;
import br.ufla.gac106.s2022_1.caliceSagrado.Personagens.Principal;
import br.ufla.gac106.s2022_1.baseJogo.EntidadeGrafica;
import br.ufla.gac106.s2022_1.caliceSagrado.Itens.Item;


/**
 * Classe Ambiente - um ambiente em um jogo adventure. 
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado aos 
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul, leste 
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente vizinho, 
 * ou null se não há saída naquela direção.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */
public class Ambiente extends EntidadeGrafica{
    // descrição do ambiente
    private String descricao;
    //String representa a direção e Ambiente o próprio valor
    private HashMap<String, Ambiente> saidas;
    private ArrayList<Item> itens;
    //private Item item;
    private String bloqueada;
    private String chave;
    String nome;

    public Ambiente(String nome, String descricao, String bloqueada, String chave, String entidade){
        super(entidade);
        this.descricao = descricao;
        this.bloqueada = bloqueada;
        this.chave = chave;
        itens = new ArrayList<>();
        saidas = new HashMap<String, Ambiente>();
        this.nome = nome;

    }
    @Override
    public String getNome(){
        return nome;
    }



    public Ambiente(String descricao, String entidade){
        this(null, descricao, null, null, entidade);
    }

    public void setBloqueada(String x){
        bloqueada = x;
    }

    public boolean bloqueada(String direcao){
        if(direcao.equals(bloqueada))
            return true;
        return false;
    }

    public String getChave(){
        return chave;
    }

    //Adiciona as saídas do ambiente.
    public void ajustarSaidas(String direcao, Ambiente ambiente){
        saidas.put(direcao, ambiente);
    }

    public String getDescricao() {
        return descricao;
    }

    public Ambiente getAmbiente(String direcao){
        return saidas.get(direcao);
    }

    //Todas as direções de saidas existentes
    public String getSaidas(){
        String texto = "";
        for(String direcao : saidas.keySet()){
            if(direcao != bloqueada)
                texto = texto + direcao + " ";
        }
        return texto;
    }

    //Descricao de todos itens no ambiente
    public String getDescricaoLonga(){
        String texto = "";

        if(!itens.isEmpty()){
            for(Item i : itens)
                
                texto += i.getDescricao() + "\n";
        }
        else
            texto += "Não há nada aqui.\n";
        return texto;
    }

    //verifica se possui item no ambiente
    public boolean possuiItens(){
        if(!itens.isEmpty()){
            return true;
        }
        return false;
    }

    //coleta um item especifico no ambiente
    public Item coletarItem(String nome){
        if(!itens.isEmpty()){
            if(possuiItens()){
                for(Item i: itens){
                    if(i.getNome().equals(nome)){
                        Item itemAux = i;
                        itens.remove(i);
                        return itemAux;
                    }
                }
            }
        }
        return null;
    }

    //verifica se possui um certo item no ambiente
    public Boolean temOItem(String nome){
        if(possuiItens()){
            for(Item i : itens){
                if(i.getNome().equals(nome)){
                    return true;
                }
            }
        }
        return false;
    }

    //abre passagens bloqueadas do ambiente
    public void ajustarSaidaBloqueada(String direcao, Ambiente local, String nomeItem){
        ajustarSaidas(direcao, local);
        bloqueada = direcao;
        chave = nomeItem;
    }



    //usar item no ambiente
    public boolean usarItem(Item item){
        if(item.getNome().equals(chave)){
            bloqueada = null;
            return true;
        }
        return false;
    }

    public boolean usarItem(Item item, Principal jogador){
        if(item.getNome().equals(chave)){

            Item aux = jogador.removerItem(item.getNome());

            jogador.adicionarItem(coletarItem(getChave()));

            adicionarItem(aux);

            setBloqueada(null);
            return true;
        }
        
        return false;
    }

    //adicionar item no ambiente
    public void adicionarItem(Item item){
        itens.add(item);
    }

}
