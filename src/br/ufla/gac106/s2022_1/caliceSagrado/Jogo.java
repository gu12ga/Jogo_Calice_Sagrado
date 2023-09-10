package br.ufla.gac106.s2022_1.caliceSagrado;
import br.ufla.gac106.s2022_1.baseJogo.InterfaceUsuario;
import br.ufla.gac106.s2022_1.caliceSagrado.Ambiente.Ambiente;
import br.ufla.gac106.s2022_1.caliceSagrado.Itens.*;
import br.ufla.gac106.s2022_1.caliceSagrado.Personagens.*;
import java.util.ArrayList;
import java.util.List;

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;
    // ambiente onde se encontra o jogador
    private Ambiente ambienteAtual;
    // ambiente final
    private Ambiente purgatorio;
    // ambiente do chefão
    private Ambiente calabouco4;
    private Ambiente entradaCalabouco;
    // jogador
    private Principal jogador;
    // missões do jogo
    // private Boolean tarefa1;
    // private Boolean tarefa2;
    // Inimigos
    private List<Personagem> inimigos;
    //Batalha
    private Batalha batalha;
    // IU
    private InterfaceUsuario IU;
    // Singleton
    private static Jogo instanciaUnica;
    
    private Arquivo arquivo;
    
    private long tempoDeJogo;
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public static Jogo criarJogo(InterfaceUsuario IU){
        if(instanciaUnica == null){
            instanciaUnica = new Jogo(IU);
        }
        return instanciaUnica;
    }

    private Jogo(InterfaceUsuario IU)  {
        batalha = new Batalha(IU);
        inimigos = new ArrayList<>();
        criarInimigos();
        criarJogador();
        criarAmbientes();
        //tarefa1 = tarefa2 = false;
        analisador = new Analisador(IU);
        this.IU = IU;
        arquivo = new Arquivo();
        tempoDeJogo = System.currentTimeMillis();
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        // cria os ambientes
        Ambiente jardim, salao, oratorio, corredor, cozinha, dormitorio, calabouco1, calabouco2, calabouco3;

        salao = new Ambiente("um salão espaçoso com teto abobadado decorado com pinturas de imagens cristãs e paredes azul claro.","imagens/salaoInicial.jpg" );
        oratorio = new Ambiente("um oratório pequeno com uma estátua de cristo, um genuflexório e um livreiro com alguns livros antigos.", "imagens/oratorio.jpg");
        corredor = new Ambiente("um corredor repleto de vitrines com imagens da santíssima trindade e vários artigos de arte expostos.","imagens/corredor.jpg");
        cozinha = new Ambiente("uma cozinha antiga com uma grande janela para o jardim, uma pia de mármore, refrigerador, fogão a lenha, uma mesa grande de madeira e uns sacos de linho com mantimentos abaixo da janela.","imagens/cozinha.jpg");
        dormitorio = new Ambiente("um dormitório com uma cama simples, um armário e uma mesa com alguns livros e uma jarra com água. O armário parece ter sido arrastado e há uma marca na forma de cruz na parede.","imagens/dormitorio.png");

        entradaCalabouco = new Ambiente("um úmido e mal iluminado calabouço com um forte cheiro de mofo e teias de aranha.","imagens/entradacalabouco.jpg");
        calabouco1 = new Ambiente("um úmido e mal iluminado calabouço, há criptas nas parede com velas de chamas azuis.","imagens/calabouco.jpg");
        calabouco2 = new Ambiente("um úmido e mal iluminado calabouço, há apenas uma vela de chama verde e reluzindo a chama da vela há escritos em latim na parede.","imagens/calabouco.jpg");
        calabouco3 = new Ambiente("um úmido e mal iluminado calabouço, há algumas criptas nas paredes com velas azuis e um pote todo preto com um desenho de um feijão dourado e um pouco de terra.","imagens/calabouco.jpg");
        calabouco4 = new Ambiente("um espaço quente e com ar fresco, difícil acreditar que é subterrâneo, há um altar com um cálice dourado com um líquido cristalino.","imagens/calabouco2.jpg");
        
        jardim = new Ambiente("um jardim secreto com uma árvore antiga com frutos dourados.","imagens/jardim.jpg");
        purgatorio = new Ambiente("","imagens/purgatorio.jpg");

        // inicializa as saidas dos ambientes
        salao.ajustarSaidas("norte", corredor);
        salao.ajustarSaidas("oeste", oratorio);
        //porta da sala bloqueada
        salao.ajustarSaidaBloqueada("sul", purgatorio, "Chave do salao");

        oratorio.ajustarSaidas("leste", salao);

        corredor.ajustarSaidas("sul", salao);
        corredor.ajustarSaidas("leste", cozinha);
        corredor.ajustarSaidas("norte", dormitorio);

        cozinha.ajustarSaidas("oeste", corredor);

        dormitorio.ajustarSaidas("sul", corredor);
        //entrada calabouço bloqueada
        dormitorio.ajustarSaidaBloqueada("abaixo", entradaCalabouco, "Crucifixo");

        entradaCalabouco.ajustarSaidas("acima", dormitorio);
        entradaCalabouco.ajustarSaidas("leste", calabouco1);

        calabouco1.ajustarSaidas("oeste", entradaCalabouco);
        calabouco1.ajustarSaidas("sul", calabouco3);
        calabouco1.ajustarSaidas("leste", calabouco2);

        calabouco2.ajustarSaidas("oeste", calabouco1);
        //entrada ultimo nível bloqueada
        calabouco2.ajustarSaidaBloqueada("atraves", calabouco4, "Grimorio");
        calabouco4.ajustarSaidas("atraves", calabouco2);
        calabouco3.ajustarSaidas("norte", calabouco1);
        //entrada bloqueada
        calabouco3.ajustarSaidaBloqueada("acima", jardim, "Feijao dourado");
        jardim.ajustarSaidas("abaixo", calabouco3);
        
        //Inicia os itens de cada ambiente
        Item chave, maca, livro, livro2, livro3, livro4, livro5, Livro6, grimorio, crucifixo, feijao, caliceSagrado, caliceFalso, pocao;
        pocao = new ItemComestivel("Pocao de vida", 1, "Revigora e fortalece o corpo.", 10, "imagens/pocao.png");
        maca = new Maca("Maca", 150, "Uma maçã dourada que parece nunca apodrecer.", 10, Estado.SAGRADO, "imagens/macaDourada.png");
        grimorio = new ItemChave("Grimorio", 350, "Um grimório com uma capa preta de couro e algo escrito em latim com caracteres em ouro.", "imagens/grimorio.jpg");
        crucifixo = new ItemChave("Crucifixo", 450, "Cruxifico pequeno com desenho simples, porém, bastante pesado.", "imagens/crucifixo.jpg");
        feijao = new ItemChave("Feijao dourado", 35, "Feijão que mesmo sendo dourado parece ser orgânico.", "imagens/feijao.png");
        caliceSagrado = new Calice("Calice Sagrado", 666, "Um cálice de ouro reluzente, gélido ao toque contendo uma água cristalina", 20, false, Estado.CORROMPIDO, "imagens/calicedourado.jpg");
        caliceFalso = new Calice("Calice Prata", 666, "Um cálice de prata com água benta.", -10, false, Estado.NORMAL, "imagens/CalicePrata.jpg");
        chave = new ItemChave("Chave do salao", 38, "Uma chave velha e ornamentada. Abre a porta do salão.", "imagens/chave.png");
        
        livro = new ItemMisc("Livro da capa preta", 500, "Um manuscrito misterioso com desenhos de formas humanas nuas dançando sob uma árvore dourada.", "imagens/livroCapaPreta.png");
        livro2 = new ItemMisc("Livro com crucifixo", 500, "Um manuscrito ilustrando várias passagens ao redor de um crucifixo em forma de chave.", "imagens/livrocrucifixo.png");
        livro3 = new ItemMisc("Livro velho", 500, "Um manuscrito velho sobre cultivo de feijões com representações de plantas que levam até os céus", "imagens/livrovelho.png");
        livro4 = new ItemMisc("Livro de receitas", 500, "Receita de enebriante volátil: \n 2 - lágrimas de virgem \n 1 - cabelo branco de um recém nascido \n 1 - frasco com um grito de guerra bárbaro \n 1 - frasco com álcool de fungo de putrefação. \n O enebriante volátil é capaz de induzir alucinações ao ser inalado, perfeito para um culto de doutrinação", "imagens/livroreceita.png");
        livro5 = new ItemMisc("Livro do pecado", 500, "Um livro sobre a árvore que dá os frutos do pecado\n... e foi dito que, aquele que provar da fruta cometerá o pecado mortal e se tornará um com a criação.", "imagens/livropecado.png");
        Livro6 = new ItemMisc("Biblia",10000,"Algum livro que parece muito quente ao se aproximar, parece interessante, ou não! , tem uma força agindo sobre ela incrivel", "imagens/biblia.jpg");

        dormitorio.adicionarItem(livro2);
        dormitorio.adicionarItem(livro5);
        dormitorio.adicionarItem(Livro6);

        calabouco3.adicionarItem(livro3);

        oratorio.adicionarItem(livro);
        oratorio.adicionarItem(grimorio);

        corredor.adicionarItem(crucifixo);
        corredor.adicionarItem(caliceFalso);

        cozinha.adicionarItem(pocao);
        cozinha.adicionarItem(feijao);
        cozinha.adicionarItem(livro4);

        jardim.adicionarItem(maca);
        jardim.adicionarItem(pocao);

        calabouco4.adicionarItem(caliceSagrado);
        calabouco4.adicionarItem(chave);
        calabouco4.adicionarItem(pocao);

        // Armas
        Item chicote, faca, galho;

        chicote = new Item("Chicote", 125, "Um chicote de couro banhado a sangue usado para punir pecadores.", TipoItem.ARMA, 4,"imagens/chicote.jpg");
        faca = new Item("Faca", 49, "Faca simples bastante afiada.", TipoItem.ARMA, 2,"imagens/faca.jpg");
        galho = new Item("Galho", 2, "Galho de árvore resistente, porém, causa pouco dano", TipoItem.ARMA, 3,"imagens/galho.png");

        oratorio.adicionarItem(chicote);
        cozinha.adicionarItem(faca);
        jardim.adicionarItem(galho);



        // o jogo comeca no salao
        ambienteAtual = salao; 
    }
    /** Cria os inimigos */
    private void criarInimigos(){
        Personagem papa, padre;
        papa = new Personagem("Papa", Estado.SAGRADO, 15);
        padre = new Personagem("Padre", Estado.SAGRADO, 12);

        Item bastao = new Item("Bastão", 545, "Um bastão arcaíco de cedro bastante usado", TipoItem.ARMA, 3,"imagens/bastao.png");

        papa.setArma(bastao);
        padre.setArma(bastao);

        inimigos.add(papa);
        inimigos.add(padre);

    }

    private void criarJogador(){

        jogador = new Principal("bombadill", Estado.NORMAL, 20, new Ambiente("inicial", "imagens/bombadil.jpg"));

        Item desarmado = new Desarmado();

        jogador.setArma(desarmado);
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar()  {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e 
        // os executamos até o jogo terminar.
        IU.ambienteAtualMudou(ambienteAtual);
        boolean terminado = false;
        while (!terminado) {
            if(ambienteAtual == entradaCalabouco){
                if(inimigos.get(1).getPtsVida() > 0)
                    batalha.executar(jogador, inimigos.get(1));
            }

            if(ambienteAtual == purgatorio && jogador.getEstado() == Estado.SAGRADO){
                //System.out.println("Você restaurou o balanço universal.");
                IU.exibirMensagem("Papa: oh não, você encontrou o fruto do pecado, sua presença é esmagadora.");
                IU.exibirMensagem("\nSinto toda minha força se esvaindo....");
                IU.exibirMensagem("Você restaurou o balanço universal.");
                terminado = true;
            }
            else if(ambienteAtual==purgatorio && jogador.getEstado() == Estado.NORMAL){
                // System.out.println("Papa: Ora ora, veja quem chegou... Não me reconhece? Sou o papa, você não pode me derroar como você está agora, vou te matar com todo o prazer santo");
                IU.exibirMensagem("Papa: Ora ora, veja quem chegou... Você não pode me derrotar como você está agora, vou te matar com todo o prazer santo");
                // System.out.println("Você morreu");
                // IU.exibirMensagem("Você morreu");
                if(inimigos.get(0).getPtsVida() > 0)
                    batalha.executar(jogador, inimigos.get(0));
                if(jogador.getVivo() == false) {
                    IU.continuarMensagem("Sua alma vagará eternamente no purgatório.");
                }
                 else{
                    IU.continuarMensagem("O Papa passará a eternidade vagando no limbo.");
                }
                terminado = true;
            }
            else if(jogador.getVivo() == false){
                IU.continuarMensagem("Game over");
                terminado = true;
            }
            if(!terminado){
                Comando comando = analisador.pegarComando();
                //espaço para melhor visualização no codigo
                System.out.println();

                terminado = processarComando(comando);

                // finalizou o jogo
            }
            
        }
        arquivo.escrever(System.currentTimeMillis()-tempoDeJogo);
        IU.exibirMensagem("Tempo de jogo total: "+Integer.toString(arquivo.ler())+"s");
        IU.continuarMensagem("Obrigado por jogar. Até mais!");
        // System.out.println("Obrigado por jogar. Até mais!");
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        // System.out.println();
        // System.out.println("Bem-vindo ao Cálice Sagrado!");
        IU.continuarMensagem("Bem-vindo ao Cálice Sagrado!");
        // System.out.println("Cálice Sagrado é o novo jogo de aventura que explora um pouco da mitologia cristã.\n");
        IU.continuarMensagem("Cálice Sagrado é o novo jogo de aventura que explora um pouco da mitologia cristã.\n");
        // System.out.println("Digite 'ajuda' se você precisar de ajuda.");
        IU.continuarMensagem("Digite 'ajuda' se você precisar de ajuda.");
    
        saidas();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if(comando.ehDesconhecido()){
            // System.out.println("Eu não entendi o que você disse...");
            IU.continuarMensagem("Eu não entendi o que você disse...");
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        }
        else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        }
        else if (palavraDeComando.equals("observar")){
            observar(comando);
        }
        else if (palavraDeComando.equals("pegar")){
            pegar(comando);
        }
        else if(palavraDeComando.equals("usar")){
            usar(comando);
        }
        else if(palavraDeComando.equals("comer")){
            comer(comando);
        }
        else if(palavraDeComando.equals("largar")){
            largar(comando);
        }
        else if(palavraDeComando.equals("desequipar")){
            desequipar(comando);
        }


        return querSair;
    }
    
    //Vasculha o ambiente
    private void observar(Comando comando){
        if(comando.getSegundaPalavra() != null){
            // System.out.println("Você nao pode observar algo específico.\n");
            IU.continuarMensagem("Você nao pode observar algo específico.\n");
        }
        else {
            //          -----------------------AQ
            // System.out.println("Pontos de vida: " + jogador.getPtsVida());
            IU.continuarMensagem("Pontos de vida: " + jogador.getPtsVida());
            Estado estado = jogador.getEstado();
            switch(estado){
                case NORMAL:
                    // System.out.println("Estado: Normal");
                    IU.continuarMensagem("Estado: Normal");
                    break;
                case CORROMPIDO:
                    // System.out.println("Estado: Corrompido");
                    IU.continuarMensagem("Estado: Corrompido");
                    break;
                case SAGRADO:
                    // System.out.println("Estado: Sagrado");
                    IU.continuarMensagem("Estado: Sagrado");
                    break;
            }
            // System.out.println("Inventário: " + jogador.inventario());
            IU.continuarMensagem("Inventário: " + jogador.inventario());
            // System.out.println("Peso total: " + jogador.pesoMochila() + " / 1500 g" + "\n");
            IU.continuarMensagem("Peso total: " + jogador.pesoMochila() + " / 1500 g" + "\n");
            // System.out.println("Arma equipada: " + jogador.getArma());
            IU.continuarMensagem("Arma equipada: " + jogador.getArma());
            // System.out.println("Dano: " + jogador.danoArma());
            IU.continuarMensagem("Dano: " + jogador.danoArma() + "\n");
            // System.out.println("Itens no ambiente: ");
            IU.continuarMensagem("Itens no ambiente: ");
            // System.out.println(ambienteAtual.getDescricaoLonga());
            IU.continuarMensagem(ambienteAtual.getDescricaoLonga());
            saidas();
        }
    }

    //Coleta item no ambiente
    private void pegar(Comando comando){
        if(comando.getSegundaPalavra() == null){
            // System.out.println("Pegar o que?");
            IU.continuarMensagem("Pegar o que?");
        }
        else if(ambienteAtual.possuiItens()){
            if(ambienteAtual.temOItem(comando.getSegundaPalavra())){
                //variavel para coletar o item do ambiente e poder usa-lo
                Item item = ambienteAtual.coletarItem(comando.getSegundaPalavra());
                

                //fazer o teste do peso do item com a mochila
                int teste = jogador.adicionarItem(item);
                IU.jogadorPegouItem(item);
                
                if(teste == 1)
                    // System.out.println(comando.getSegundaPalavra() + " coletado(a)!\n");
                    IU.exibirMensagem(comando.getSegundaPalavra() + " coletado(a)!\n");
                    
                else
                    if(teste == 0){
                        ambienteAtual.adicionarItem(item);
                        // System.out.println(comando.getSegundaPalavra() + " não coletado devido ao peso."+"\n"+" Peso da mochila: " + jogador.pesoMochila()+"\n"+"Peso do item: "+item.getPeso());
                        IU.continuarMensagem(comando.getSegundaPalavra() + " não coletado devido ao peso."+"\n"+" Peso da mochila: " + jogador.pesoMochila()+"\n"+"Peso do item: "+item.getPeso());
                        IU.jogadorDescartouItem(item);
                    }
            }
            else{
                // System.out.println("Não existe tal item no ambiente.");
                IU.continuarMensagem("Não existe tal item no ambiente.");
            }
        }
        else{
            // System.out.println("O ambiente não possui itens.");
            IU.continuarMensagem("O ambiente não possui itens.");
        }
    }

    //Usa um item do inventário
    private void usar(Comando comando){
        if(comando.getSegundaPalavra() == null){
            // System.out.println("Usar o que?");
            IU.continuarMensagem("Usar o que?");
        }
        else{
            String nomeItem = comando.getSegundaPalavra();
            if(jogador.possuiItem(nomeItem)){
                Item item = jogador.getItem(nomeItem);
                if(item.getTipo() == TipoItem.CHAVE){
                    if(ambienteAtual.usarItem(item)){
                        jogador.removerItem(nomeItem);
                        IU.jogadorDescartouItem(item);
                        // System.out.println("Item usado, passagem aberta!");
                        IU.continuarMensagem("Item usado, passagem aberta!");
                    }
                    else{
                        // System.out.println("Este não é o item correto.");
                        IU.exibirMensagem("Este não é o item correto.");
                    }
                }
                else if(item.getTipo() == TipoItem.COMESTIVEL){
                    comer(comando);
                    IU.continuarMensagem(item.getNome() + " consumido!");
                }
                else if(item.getTipo() == TipoItem.ARMA){
                    jogador.equipar(item);
                    // System.out.println(item.getNome() + " equipado!");
                    IU.continuarMensagem(item.getNome() + " equipado!");
                }
                else if(item.getTipo() == TipoItem.MISC){
                    // System.out.println(jogador.ler(item));
                    IU.continuarMensagem(jogador.ler(item));
                }
            }
            else{
                // System.out.println("Você não possui esse item.");
                IU.continuarMensagem("Você não possui esse item.");
            }
        }

    }

    private void desequipar(Comando comando){
        if(comando.getSegundaPalavra() == null){
            // System.out.println("Usar o que?");
            IU.continuarMensagem("Desequipar o que?");
        }
        else{
            if(jogador.getArma() != "Desarmado"){
                String nomeArma = comando.getSegundaPalavra();
                if(jogador.getArma().equals(nomeArma)){
                    jogador.setArma(new Desarmado());
                }
                else{
                    // System.out.println("Você não está usando " + nomeArma);
                    IU.continuarMensagem("Você não está usando " + nomeArma);
                }
            }
            else{
                // System.out.println("Você já está desarmado.");
                IU.continuarMensagem("Você já está desarmado.");
            }
        }
    }

    private void comer(Comando comando){
        if(comando.getSegundaPalavra() == null){
            // System.out.println("Comer o que?");
            IU.continuarMensagem("Comer o que?");
        }
        else{
            if(jogador.possuiItem(comando.getSegundaPalavra())){
                Item item = jogador.getItem(comando.getSegundaPalavra());
                if(item.getTipo() == TipoItem.COMESTIVEL){
                    jogador.usar(item);
                    jogador.removerItem(item.getNome());
                    IU.jogadorDescartouItem(item);
                    // System.out.println(item.getNome() + " ingerido(a)!");
                    IU.continuarMensagem(item.getNome() + " ingerido(a)!");
                    // System.out.println("Status do jogador: " + jogador.getEstado());
                    IU.continuarMensagem("Status do jogador: " + jogador.getEstado());
                }
                else{
                    // System.out.println("O item não é comestível.");
                    IU.continuarMensagem("O item não é comestível.");
                }
            }
            else{
                // System.out.println("Você não possui esse item.");
                IU.continuarMensagem("Você não possui esse item.");
            }
        }
    }

    private void largar(Comando comando){
        if(comando.getSegundaPalavra() == null){
            // System.out.println("Largar o que?");
            IU.continuarMensagem("Largar o que?");
        }
        else{
            if(!jogador.mochilaVazia()){
                if(jogador.possuiItem(comando.getSegundaPalavra())){
                    Item aux = jogador.getItem(comando.getSegundaPalavra());
                    ambienteAtual.adicionarItem(aux);
                    jogador.removerItem(aux.getNome());
                    IU.jogadorDescartouItem(aux);   
                    if(aux.getTipo() == TipoItem.ARMA){
                        jogador.setArma(new Desarmado());
                    }
                    // System.out.println("Item largado.");
                    IU.continuarMensagem("Item largado.");
                }   
                else{
                    // System.out.println("Você não possui esse item.");
                    IU.continuarMensagem("Você não possui esse item.");
                }
            }
            else{
                // System.out.println("Sua mochila está vazia.");
                IU.continuarMensagem("Sua mochila está vazia.");
            }
        }
        
     }

    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de  palavras de comando
     */
    private void imprimirAjuda()  {
            //          -----------------------AQ
        // System.out.println("Você é um antropólogo interessado em artefatos antigos. Suas pesquisas e escavações, lhe levam a crer na real existência do cálice sagrado.");
        IU.continuarMensagem("Você é um antropólogo interessado em artefatos antigos. Suas pesquisas e escavações, lhe levam a crer na real existência do cálice sagrado.");
        // System.out.println("Tudo aponta que o vaticano escondeu tal artefato em uma igreja simples e remota, no interior da Itália.");
        IU.continuarMensagem("Tudo aponta que o vaticano escondeu tal artefato em uma igreja simples e remota, no interior da Itália.");
        // System.out.println("Você se encontra em frente a única igreja que apesar de ser pequena, é a única a qual o papa visita em segredo, a cada 15 dias.");
        IU.continuarMensagem("Você se encontra em frente a única igreja que apesar de ser pequena, é a única a qual o papa visita em segredo, a cada 15 dias.");
        // System.out.println("\nComandos válidos: ");
        IU.continuarMensagem("\nComandos válidos: ");
        // System.out.println(analisador.getComandos());
        IU.continuarMensagem(analisador.getComandos());
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente, 
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando)  {
        // se não há segunda palavra, não sabemos pra onde ir...
        if(!comando.temSegundaPalavra()) {            
            // System.out.println("Ir pra onde?");
            IU.continuarMensagem("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = null;
        proximoAmbiente = ambienteAtual.getAmbiente(direcao);

        if (proximoAmbiente == null) {
            // System.out.println("Nao há passagem!");
            IU.continuarMensagem("Nao há passagem!");
        }
        else if(ambienteAtual.bloqueada(direcao)){
            // System.out.println("Para passar você necessita de uma chave.");
            IU.continuarMensagem("Para passar você necessita de uma chave.");
        }
        else {
            ambienteAtual = proximoAmbiente;
            saidas();  
        }
        IU.ambienteAtualMudou(ambienteAtual);
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos 
     * realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando)  {
        if(comando.temSegundaPalavra()) {
            // System.out.println("Sair o que?");
            IU.continuarMensagem("Sair o que?");
            return false;
        }
        else {
            return true;  // sinaliza que nós realmente queremos sair
        }
    }

    // Mostra todas as saidas do ambiente atual
    private void saidas(){
        // System.out.println("Você esta em " + ambienteAtual.getDescricao());
        IU.continuarMensagem("Você esta em " + ambienteAtual.getDescricao());
        // System.out.print("Saidas: ");
        IU.continuarMensagem("Saidas: ");
        // System.out.println(ambienteAtual.getSaidas());
        IU.continuarMensagem(ambienteAtual.getSaidas());
    }
}