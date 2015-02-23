package principal;

import Brain.NoJogada;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;

import pecas.APeca;
import pecas.Bispo;
import pecas.Cavalo;
import pecas.Peao;
import pecas.Posicao;
import pecas.Rainha;
import pecas.Rei;
import pecas.Torre;

/**
 *
 * @author Paulo Henrique
 */
public class Movimento {
	//Pontuação calculado para o movimento a patir da função de avaliação.

    //Posição atual e nova posição do movimento.
    private APeca pecaOrigem;
    private APeca pecaDestino;
    //Peça capturada no movimento.
    private APeca pecaCapturada;
    //Indica se o movimento gera uma promoção do peão.
    private boolean promocaoPeao = false;

    private Tabuleiro tabuleiro;
    private int utilidade;
    //alpha : Melhor utilidade para Max ao longo do caminho até a raiz
    private int alpha;
    //beta : Melhor utilidade para Min ao longo do caminho até a raiz
    private int beta;
    private int profundidade;
    private static int profundidadeLimite;
    //proximosNoJogadas = todos as configurações dos tabuleiros
    //para todas jogadas possíveis        
    private ArrayList<Movimento> proximosMovimentos;
    private byte jogador;
    
    //O No Pai, que é a jogada anterior
    private Movimento movimentoAnterior;

    public Movimento(Movimento movimentoAnterior, Tabuleiro tabuleiro,
            ArrayList<Movimento> proximosMovimentos,
            int utilidade, int profundidade, int profundidadeLimite) {
        this(movimentoAnterior, tabuleiro, utilidade, profundidade);
        Movimento.profundidadeLimite = profundidadeLimite;
    }

    public Movimento(Movimento movimentoAnterior, Tabuleiro tabuleiro,
       int utilidade, int profundidade) {
        this.tabuleiro = tabuleiro;
        this.utilidade = utilidade;
        this.profundidade = profundidade;
        this.getTodosMovimentos(utilidade);
        this.proximosMovimentos = proximosMovimentos;

        //Alfa e Beta Iniciam com valores nos piores casos para cada um
        this.alpha = -9999999;
        this.beta = 9999999;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public int getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(int utilidade) {
        this.utilidade = utilidade;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public static int getProfundidadeLimite() {
        return profundidadeLimite;
    }

    public static void setProfundidadeLimite(int profundidadeLimite) {
        Movimento.profundidadeLimite = profundidadeLimite;
    }

    public ArrayList<Movimento> getProximosMovimentos() {
        return proximosMovimentos;
    }

    public void setProximosMovimentos(ArrayList<Movimento> proximosMovimentos) {
        this.proximosMovimentos = proximosMovimentos;
    }

    public Movimento getMovimentoAnterior() {
        return movimentoAnterior;
    }

    public void setMovimentoAnterior(Movimento movimentoAnterior) {
        this.movimentoAnterior = movimentoAnterior;
    }

    public APeca getPecaOrigem() {
        return pecaOrigem;
    }

    public void setPecaOrigem(APeca pecaOrigem) {
        this.pecaOrigem = pecaOrigem;
    }

    public APeca getPecaDestino() {
        return pecaDestino;
    }

    public void setPecaDestino(APeca pecaDestino) {
        this.pecaDestino = pecaDestino;
    }

    public APeca getPecaCapturada() {
        return pecaCapturada;
    }

    public void setPecaCapturada(APeca pecaCapturada) {
        this.pecaCapturada = pecaCapturada;
    }

    public boolean isPromocaoPeao() {
        return promocaoPeao;
    }

    public void setPromocaoPeao(boolean promocaoPeao) {
        this.promocaoPeao = promocaoPeao;
    }

    public Movimento(APeca pecaOrigem, APeca pecaDestino) {
        this.pecaOrigem = pecaOrigem;
        this.pecaDestino = pecaDestino;
    }

    /*
     * Função que verifica se a peça destino está vazia.
     */
    public  boolean isPecaDestinoVazia(Movimento mov) {
        return mov.pecaDestino.isVazia();
    }

    /*
     * Função que verifica se a peça destino pode ser capturada.
     */
    private  boolean isPecaDestinoCapturavel( Movimento mov) {
        //Verificando se a peça destino está dentro do tabuleiro
        if (isPecaNoTabuleiro(mov.pecaDestino.getPosicao_atual().getX(), mov.pecaDestino.getPosicao_atual().getY())) {
			//Obtendo a peça localizada nas coordenadas da peça destino
            //mov.pecaDestino = tabuleiro.getPecaByPosicao(mov.pecaDestino.getPosicao_atual()); //TODO

            //Verificando se a peça destino está vazia, sem sim não pode ser capturada.
            if (mov.pecaDestino.isVazia()) {
                return true;
            } //Verificando se a cor da peça destino é diferente da peça origem 
            else if (mov.pecaOrigem.getCor() != mov.pecaDestino.getCor()) {
                return true;
            } //Verificando se a peça destino é o Rei, que não pode ser capturado
            else if (mov.pecaDestino instanceof Rei) {
                return false;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Verifica se o caminho da peca origem até a peça destino possui alguma
     * peça válida.
     *
     * @param incrementoPorLinha
     * @param incrementoPorColuna
     * @return True se existir alguma peça e False se o caminho estiver limpo.
     */
    private  boolean isPecaEntreOrigemDestino( Movimento mov, int incrementoPorLinha, int incrementoPorColuna) {

        int linhaAtual = mov.pecaOrigem.getPosicao_atual().getX() + incrementoPorLinha;
        int colunaAtual = mov.pecaOrigem.getPosicao_atual().getY() + incrementoPorColuna;
        while (true) {
            // Se chegou ao destino
            if (linhaAtual == mov.pecaDestino.getPosicao_atual().getX() && colunaAtual == mov.pecaDestino.getPosicao_atual().getY()) {
                break;
            }
            // Verificando se a linha atual e coluna atual estão dentro do tabuleiro
            if (!isPecaNoTabuleiro(linhaAtual, colunaAtual)) {
                break;
            }

            //Verifica se as coordenadas levam a uma peça válida.
            if (isPecaValida(linhaAtual, colunaAtual)) {
                return true;
            }

            linhaAtual += incrementoPorLinha;
            colunaAtual += incrementoPorColuna;
        }
        return false;
    }

    /**
     * Verifica se as cordenadas levam a uma peça válida.
     *
     * @param linha
     * @param coluna
     * @return Verdadeiro, se contém uma localiação válida.
     */
    public  boolean isPecaValida( int x, int y) {
        //Obtendo a peça de acordo com as coordenadas.
        APeca peca = tabuleiro.getPecaByPosicao(new Posicao((byte) x, (byte) y));

        // Se não estiver vazia é uma peça válida.
        if (!peca.isVazia()) {
            return true;
        }
        return false;
    }

    /*
     * Função que verifica se o movimento do Peão é válido.
     */
    public  boolean isValidoMovimentoPeao( Movimento mov) {
		// O peão pode avançar para a casa vazia, imediatamente à frente,
        // ou em seu primeiro lance ele pode avançar duas casas.
        // Desde que ambas estejam desocupadas.

        // Verificando se a peça destino está vazia.
        if (isPecaDestinoVazia(mov)) {
            //Verificando se a coluna da peça origem é a mesma da peça destino.
            if (mov.pecaOrigem.getPosicao_atual().getY() == mov.pecaDestino.getPosicao_atual().getY()) {
                if (Jogo.numeroJogador == 1) //Branco 
                {
                    //O Peão não fez nemhum movimento
                    if (mov.pecaOrigem.getQtd_movimentos() == 0) {
                        if (mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX() //Avançou um casa para cima
                                || mov.pecaOrigem.getPosicao_atual().getX() + 2 == mov.pecaDestino.getPosicao_atual().getX())//Avançou duas casas para cima
                        {
                            return true;
                        }
                    } //O Peão já se movimentou
                    else {
                        if (mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX()) //Avançou um casa para cima
                        {
                            if (mov.pecaDestino.getPosicao_atual().getY() == 8) //Está na última linha, ou seja pode ser promovido.
                            {
                                mov.promocaoPeao = true;
                            }

                            return true;
                        }
                    }
                } else // Preto
                {
                    //O Peão não fez nemhum movimento
                    if (mov.pecaOrigem.getQtd_movimentos() == 0) {
                        if (mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX() //Avançou um casa para baixo
                                || mov.pecaOrigem.getPosicao_atual().getX() - 2 == mov.pecaDestino.getPosicao_atual().getX())//Avançou duas casas para baixo
                        {
                            return true;
                        }
                    } //O Peão já se movimentou
                    else {
                        if (mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX()) //Avançou um casa para cima
                        {
                            if (mov.pecaDestino.getPosicao_atual().getY() == 1) //Está na primeira linha, ou seja pode ser promovido.
                            {
                                mov.promocaoPeao = true;
                            }

                            return true;
                        }
                    }
                }
            } // Movimento En Passant (Em passagem)
            // Verificanda se a peça destino está na coluna a esquerda ou a direita da peça origem.
            else if (mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY()//Coluna esquerda
                    || mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY())//Coluna direita
            {
                if (Jogo.numeroJogador == 1) //Branco 
                {
                    if (mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX())//Avançou um casa para cima
                    {
						// Verifica se a posicao paralea a peça origem é preenchida por um Peão
                        // e esse Peão realizou o primeiro movimento.

                        // Obtendo a peça da determinado posição no tabuleiro.
                        APeca pecaParalela = tabuleiro.getPecaByPosicao(mov.pecaDestino.getPosicao_atual());
                        if (!pecaParalela.isVazia() && pecaParalela instanceof Peao && pecaParalela.getQtd_movimentos() == 1) {
                            //Armazenando a peça capturada.
                            mov.pecaCapturada = mov.pecaDestino;

                            return true;
                        }
                    }
                } else //Preto
                {
                    if (mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX())//Avançou um casa para baixo
                    {
						// Verifica se a posicao paralea a peça origem é preenchida por um Peão
                        // e esse Peão realizou o primeiro movimento.

                        // Obtendo a peça da determinado posição no tabuleiro.
                        APeca pecaParalela = tabuleiro.getPecaByPosicao(mov.pecaDestino.getPosicao_atual());
                        if (!pecaParalela.isVazia() && pecaParalela instanceof Peao && pecaParalela.getQtd_movimentos() == 1) {
                            //Armazenando a peça capturada.
                            mov.pecaCapturada = mov.pecaDestino;

                            return true;
                        }
                    }
                }
            }
        } // Ou pode mover para uma casa ocupada por uma peça do oponente,
        // ,que esteja diagonalmente na frente dele numa coluna adjacente, 
        // capturando aquela peça.
        //Verificando se a peça destino é capturável
        else if (isPecaDestinoCapturavel(mov)) {
            //Verificanda se a peça destino está na coluna a esquerda ou a direita da peça origem.
            if (mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY()//Coluna esquerda
                    || mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY())//Coluna direita
            {
                if (Jogo.numeroJogador == 1) //Branco 
                {
                    if (mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX())//Avançou um casa para cima
                    {
                        //Armazenando a peça capturada.
                        mov.pecaCapturada = mov.pecaDestino;

                        return true;
                    }
                } else //Preto
                {
                    if (mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX())//Avançou um casa para baixo
                    {
                        //Armazenando a peça capturada.
                        mov.pecaCapturada = mov.pecaDestino;

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*
     * Função que verifica se o movimento do Cavalo é válido.
     */
    public  boolean isValidoMovimentoCavalo( Movimento mov) {
		// O cavalo anda em um formato que reproduz
        // a letra L, ou seja, duas casas, na direção horizontal ou
        // vertical, e mais uma, em ângulo reto à direção anterior

        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel(mov)) {
            int distanciaEntreLinhas = Math.abs(mov.pecaOrigem.getPosicao_atual().getX() - mov.pecaDestino.getPosicao_atual().getX());
            int distanciaEntreColunas = Math.abs(mov.pecaOrigem.getPosicao_atual().getY() - mov.pecaDestino.getPosicao_atual().getY());
            if ((distanciaEntreLinhas == 2 && distanciaEntreColunas == 1)
                    || (distanciaEntreLinhas == 1 && distanciaEntreColunas == 2)) {
                //Armazenando a peça capturada.
                if (isPecaDestinoCapturavel(mov)) {
                    mov.pecaCapturada = mov.pecaDestino;
                }

                return true;
            }
        }

        return false;
    }

    /*
     * Função que verifica se o movimento do Bispo é válido.
     */
    public  boolean isValidoMovimentoBispo( Movimento mov) {
		// O bispo pode mover-se qualquer número de casas na diagonal,
        // mas não pode saltar sobre outras peças.

        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel( mov)) {
            int distanciaEntreLinhas = Math.abs(mov.pecaOrigem.getPosicao_atual().getX() - mov.pecaDestino.getPosicao_atual().getX());
            int distanciaEntreColunas = Math.abs(mov.pecaOrigem.getPosicao_atual().getY() - mov.pecaDestino.getPosicao_atual().getY());

            if (distanciaEntreLinhas == distanciaEntreColunas && distanciaEntreColunas > 0) {
                // Movimento diagonal up-right
                if (!isPecaEntreOrigemDestino( mov, +1, +1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }

            } else if (distanciaEntreLinhas == -distanciaEntreColunas && distanciaEntreColunas > 0) {
                // Movimento diagonal down-right
                if (!isPecaEntreOrigemDestino( mov, -1, +1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }

            } else if (distanciaEntreLinhas == distanciaEntreColunas && distanciaEntreColunas < 0) {
                // Movimento diagonal down-left
                if (!isPecaEntreOrigemDestino( mov, -1, -1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }

            } else if (distanciaEntreLinhas == -distanciaEntreColunas && distanciaEntreColunas < 0) {
                // Movimento diagonal up-left
                if (!isPecaEntreOrigemDestino( mov, +1, -1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    /*
     * Função que verifica se o movimento da Torre é válido.
     */
    public  boolean isValidoMovimentoTorre( Movimento mov) {
		// O movimento executado pelas torres é
        // sempre em paralelas (linhas ou colunas), quantas
        // casas desejar desde que haja espaço livre. 
        // E não pode saltar sobre outra peça válida.

        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel( mov)) {
            int distanciaEntreLinhas = Math.abs(mov.pecaOrigem.getPosicao_atual().getX() - mov.pecaDestino.getPosicao_atual().getX());
            int distanciaEntreColunas = Math.abs(mov.pecaOrigem.getPosicao_atual().getY() - mov.pecaDestino.getPosicao_atual().getY());

            if (distanciaEntreLinhas == 0 && distanciaEntreColunas > 0) {
                // Movimento right
                if (!isPecaEntreOrigemDestino( mov, 0, +1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            } else if (distanciaEntreLinhas == 0 && distanciaEntreColunas < 0) {
                // Movimento left
                if (!isPecaEntreOrigemDestino( mov, 0, -1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            } else if (distanciaEntreLinhas > 0 && distanciaEntreColunas == 0) {
                // Movimento up
                if (!isPecaEntreOrigemDestino( mov, +1, 0)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            } else if (distanciaEntreLinhas < 0 && distanciaEntreColunas == 0) {
                // Movimento down
                if (!isPecaEntreOrigemDestino( mov, -1, 0)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    /*
     * Função que verifica se o movimento da Rainha é válido.
     */
    public  boolean isValidoMovimentoRainha( Movimento mov) {
		// A rainha combina o poder da torre e bispo e pode mover qualquer número
        // De casas ao longo coluna, linha ou diagonal, mas não pode saltar sobre outras peças.

        if (isValidoMovimentoBispo( mov) || isValidoMovimentoTorre( mov)) {
            return true;
        }

        return false;
    }

    /*
     * Função que verifica se o movimento do Réi é válido.
     */
    public  boolean isValidoMovimentoRei( Movimento mov) {
		// O rei somente anda uma casa por lance em todas as direções. 
        // Não pode situar-se em casa sob domínio de peça adversária, 
        // pois o rei não pode se entregar ou se colocar em situação de XEQUE jamais.
        // Esta jodada não é permitida e ambos os jogadores devem estar atentos

		// Não pode capturar peças defendidas pelo adversário, 
        // pois seria capturado no próximo lance, se colocando
        // antecipadamente em XEQUE, o que também não é permitido. 
        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel( mov)) {
            if (mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() == mov.pecaDestino.getPosicao_atual().getY() //up
                    || mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() + 1 == mov.pecaDestino.getPosicao_atual().getY() //up right
                    || mov.pecaOrigem.getPosicao_atual().getX() == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() + 1 == mov.pecaDestino.getPosicao_atual().getY() //right
                    || mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() + 1 == mov.pecaDestino.getPosicao_atual().getY() //down right
                    || mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() == mov.pecaDestino.getPosicao_atual().getY() //down
                    || mov.pecaOrigem.getPosicao_atual().getX() - 1 == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY() //down left
                    || mov.pecaOrigem.getPosicao_atual().getX() == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY() //left
                    || mov.pecaOrigem.getPosicao_atual().getX() + 1 == mov.pecaDestino.getPosicao_atual().getX() && mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY())//up left
            {
                //Verifica se o rei não vai entrar em XEQUE.
                if (isReiSalvo( mov, mov.pecaOrigem.getCor())) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel( mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            }

			//Movimento de Roque
            //Se o rei ainda não realizou nenhum movimento
            if (mov.pecaOrigem.getQtd_movimentos() == 0) {
				//Roque maior ou esquerdo				
                //Se a peça torreEsquerda for válida ela pode ser usada no movimento Roque
                if (!tabuleiro.getTorreEsquerda().isVazia()) //Vazio = Inválida
                {
                    //Verificando se existe alguma peça entre o rei e a torre
                    if (!isPecaEntreOrigemDestino( mov, 0, -1) //Casas da posição inical do rei até sua posição final 
                            && tabuleiro.getPecaByPosicao(mov.pecaDestino.getPosicao_atual().getX(), mov.pecaDestino.getPosicao_atual().getY() - 1).isVazia()) //Casa depois da torre
                    {
                        if (mov.pecaOrigem.getPosicao_atual().getX() == mov.pecaDestino.getPosicao_atual().getX()
                                && mov.pecaOrigem.getPosicao_atual().getY() - 2 == mov.pecaDestino.getPosicao_atual().getY())//Duas casa a esquerda					
                        {
                            //Atribuido o Rei a posição da peça destino para verificar se ficará a salvo
                            APeca Rei = tabuleiro.getReiProprio();
                            Posicao posRei = Rei.getPosicao_atual();
                            Rei.setPosicao_atual(mov.pecaDestino.getPosicao_atual());
                            tabuleiro.setReiProprio(Rei);

                            //Verifica se o rei não vai entrar em XEQUE.
                            if (isReiSalvo( mov, mov.pecaOrigem.getCor())) {
                                return true;
                            }

                            //Voltando a posição inicial do Rei
                            Rei.setPosicao_atual(posRei);
                            tabuleiro.setReiProprio(Rei);
                        }
                    }
                }

				//Roque menor ou direito				
                //Se a peça torreDireita for válida ela pode ser usada no movimento Roque
                if (!tabuleiro.getTorreDireita().isVazia()) //Vazio = Inválida
                {
                    //Verificando se existe alguma peça entre o rei e a torre
                    if (!isPecaEntreOrigemDestino( mov, 0, +1))//Casas da posição inical do rei até sua posição final 
                    {
                        if (mov.pecaOrigem.getPosicao_atual().getX() == mov.pecaDestino.getPosicao_atual().getX()
                                && mov.pecaOrigem.getPosicao_atual().getY() + 2 == mov.pecaDestino.getPosicao_atual().getY())//Duas casa a direita				
                        {
                            //Atribuido o Rei a posição da peça destino para verificar se ficará a salvo
                            APeca Rei = tabuleiro.getReiProprio();
                            Posicao posRei = Rei.getPosicao_atual();
                            Rei.setPosicao_atual(mov.pecaDestino.getPosicao_atual());
                            tabuleiro.setReiProprio(Rei);

                            //Verifica se o rei não vai entrar em XEQUE.
                            if (isReiSalvo( mov, mov.pecaOrigem.getCor())) {
                                return true;
                            }

                            //Voltando a posição inicial do Rei
                            Rei.setPosicao_atual(posRei);
                            tabuleiro.setReiProprio(Rei);
                        }
                    }
                }
            }
        }

        return false;
    }

    /*
     * Função que verifica se o rei ficará salvo após realizar o movimento.
     */
    public  boolean isReiSalvo( Movimento mov, String cor) {
        // Pegando a posição do Rei do Jogador. 
        APeca rei = Tabuleiro.getInstance().getReiProprio();

        int linha = rei.getPosicao_atual().getX();
        int coluna = rei.getPosicao_atual().getY();

        // Verificando se tem algum Peão ameçando o Rei
        if (Jogo.numeroJogador == 1) // Branca 
        {
            if ((tabuleiro.getPecaByPosicao(linha - 1, coluna - 1).getCor() != cor
                    && tabuleiro.getPecaByPosicao(linha - 1, coluna - 1) instanceof Peao)
                    || (tabuleiro.getPecaByPosicao(linha - 1, coluna + 1).getCor() != cor
                    && tabuleiro.getPecaByPosicao(linha - 1, coluna + 1) instanceof Peao)) {
                return false;
            }
        } else // Preta
        {
            if ((tabuleiro.getPecaByPosicao(linha + 1, coluna - 1).getCor() != cor
                    && tabuleiro.getPecaByPosicao(linha + 1, coluna - 1) instanceof Peao)
                    || (tabuleiro.getPecaByPosicao(linha + 1, coluna + 1).getCor() != cor
                    && tabuleiro.getPecaByPosicao(linha + 1, coluna + 1) instanceof Peao)) {
                return false;
            }
        }

        // ADJACENT KING ILLEGALITY
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (tabuleiro.getPecaByPosicao(linha + 1, coluna + 1) instanceof Rei) {
                    return false;
                }
            }
        }

        //Veridficando se tem algum Cavalo ameaçado o Rei
        if ((tabuleiro.getPecaByPosicao(linha + 1, coluna - 2).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha + 1, coluna - 2) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha + 1, coluna + 2).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha + 1, coluna + 2) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha - 1, coluna - 2).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha - 1, coluna - 2) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha - 1, coluna + 2).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha - 1, coluna + 2) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha + 2, coluna - 1).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha + 2, coluna - 1) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha + 2, coluna + 1).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha + 2, coluna + 1) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha - 2, coluna - 1).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha - 2, coluna - 1) instanceof Cavalo)
                || (tabuleiro.getPecaByPosicao(linha - 2, coluna + 1).getCor() != cor
                && tabuleiro.getPecaByPosicao(linha - 2, coluna + 1) instanceof Cavalo)) {
            return false;
        }

        // Verificando se existem ameaças a esquerda na horizontal
        if (tabuleiro.getPecaByPosicao(linha - 1, coluna) instanceof Rei) {
            return false;
        }
        for (int i = linha - 1; i >= 0; i--) {
            APeca p = tabuleiro.getPecaByPosicao(i, coluna);
            if (p.getCor() != cor && (p instanceof Torre || p instanceof Rainha)) {
                return false;
            } else {
                if (!p.isVazia()) {
                    break;
                }
            }
        }

        // Verificando se existem ameaças a direita na horizontal
        if (tabuleiro.getPecaByPosicao(linha + 1, coluna) instanceof Rei) {
            return false;
        }
        for (int i = linha + 1; i <= 7; i++) {
            APeca p = tabuleiro.getPecaByPosicao(i, coluna);
            if (p.getCor() != cor && (p instanceof Torre || p instanceof Rainha)) {
                return false;
            } else {
                if (!p.isVazia()) {
                    break;
                }
            }
        }

        // Verificando se existem ameaças acima na vertical
        if (tabuleiro.getPecaByPosicao(linha, coluna - 1) instanceof Rei) {
            return false;
        }
        for (int i = coluna - 1; i >= 0; i--) {
            APeca p = tabuleiro.getPecaByPosicao(linha, i);
            if (p.getCor() != cor && (p instanceof Torre || p instanceof Rainha)) {
                return false;
            } else {
                if (!p.isVazia()) {
                    break;
                }
            }
        }

		// Verificando se existem ameaças abaixo na vertical
        //if(tabuleiro.getPecaByPosicao(row,col-1) instanceof Rei)return false; //TODO
        if (tabuleiro.getPecaByPosicao(linha, coluna + 1) instanceof Rei) {
            return false;
        }
        for (int i = coluna + 1; i <= 7; i++) {
            APeca p = tabuleiro.getPecaByPosicao(linha, i);
            if (p.getCor() != cor && (p instanceof Torre || p instanceof Rainha)) {
                return false;
            } else {
                if (!p.isVazia()) {
                    break;
                }
            }
        }

        // Verificando se existem ameaças na diagonal
        for (int x = -1; x < 2; x += 2) {
            for (int y = -1; y < 2; y += 2) {
                if (!isReiSalvoPelaDiagonal( cor, coluna, linha, x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Verifica se o próprio rei não sofre nenhuma ameça diagonalmente  
    public  boolean isReiSalvoPelaDiagonal( String cor, int coluna, int linha, int linhaX, int colunaY) {
        if (tabuleiro.getPecaByPosicao(linha + linhaX, coluna + colunaY) instanceof Rei) {
            return false;
        }
        for (int i = 1; i <= 7; i++) {
            APeca p = tabuleiro.getPecaByPosicao(linha + i * linhaX, coluna + i * colunaY);
            if (p.getCor() != cor && (p instanceof Bispo || p instanceof Rainha)) {
                return false;
            } else {
                if (!p.isVazia()) {
                    break;
                }
            }
        }
        return true;
    }

    // Verifica se o movimento dado é válido no tabuleiro
    public  boolean isMovimentoValido( Movimento mov) {
        //Não realizou nenhum movimento.
        if (mov.pecaOrigem.getPosicao_atual().getX() == mov.pecaDestino.getPosicao_atual().getX()
                && mov.pecaOrigem.getPosicao_atual().getY() == mov.pecaDestino.getPosicao_atual().getY()) {
            return false;
        }

        //Verifica se o movimento está dentro do tabuleiro
        if (!isPecaNoTabuleiro(mov.pecaDestino.getPosicao_atual().getX(), mov.pecaDestino.getPosicao_atual().getX())) {
            return false;
        }

        boolean valido = false;
        switch (mov.pecaOrigem.getNome()) {
            case 'P':
                valido = isValidoMovimentoPeao( mov);
                break;
            case 'B':
                valido = isValidoMovimentoBispo( mov);
                break;
            case 'C':
                valido = isValidoMovimentoCavalo( mov);
                break;
            case 'T':
                valido = isValidoMovimentoTorre( mov);
                break;
            case 'D':
                valido = isValidoMovimentoRainha( mov);
                break;
            case 'R':
                valido = isValidoMovimentoRei( mov);
        }

        if (!valido) {
            return false;
        }

        return true;
    }

    // Retorna o conjunto de todos os movimentos válidos da dada peca do tabuleiro
    public  ArrayList<Movimento> getTodosMovimentosPeca( int numeroJogador, int linha, int coluna) {
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
        APeca peca = tabuleiro.getPecaByPosicao(linha, coluna);
        if ((numeroJogador == 1 && peca.getCor() == "branca")
                || (numeroJogador == 2 && peca.getCor() == "preta")) {
            Movimento mov = null;
            switch (peca.getNome()) {
                case 'P': //Peão
                    if (numeroJogador == 1) { // Branca
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 1, coluna));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 1, coluna + 1));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 1, coluna - 1));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                        if (linha == 6) {
                            mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 2, coluna));
                            if (isMovimentoValido( mov)) {
                                movimentos.add(mov);
                            }
                        }
                    } else // Preta
                    {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 1, coluna));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 1, coluna + 1));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 1, coluna - 1));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                        if (linha == 1) {
                            mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 2, coluna));
                            if (isMovimentoValido( mov)) {
                                movimentos.add(mov);
                            }
                        }
                    }
                    break;
                case 'B':
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i, coluna + i));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - i, coluna + i));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i, coluna - i));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - i, coluna - i));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    break;
                case 'C':
                    int[] linhaOffsets = {2, -2, -2, 2, -1, 1, -1, 1};
                    int[] colunaOffsets = {-1, 1, -1, 1, -2, 2, 2, -2};
                    for (int i = 0; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + linhaOffsets[i], coluna + colunaOffsets[i]));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        }
                    }
                    break;
                case 'T':
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i, coluna));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - i, coluna));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna + i));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna - i));
                        if (isMovimentoValido( mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    break;
                case 'D':
                    for (int ri = -1; ri < 2; ri++) {
                        for (int ci = -1; ci < 2; ci++) {
                            for (int i = 1; i < 8; i++) {
                                mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i * ri, coluna + i * ci));
                                if (isMovimentoValido( mov)) {
                                    movimentos.add(mov);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 'R':
                    for (int r = -1; r < 2; r++) {
                        for (int c = -1; c < 2; c++) {
                            mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + r, coluna + c));
                            if (isMovimentoValido( mov)) {
                                movimentos.add(mov);
                            }
                        }
                    }
                    // Roque Maior ou esquerda
                    mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna - 2));
                    if (isMovimentoValido( mov)) {
                        movimentos.add(mov);
                    }

                    //Roque Menor ou direita
                    mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna + 2));
                    if (isMovimentoValido( mov)) {
                        movimentos.add(mov);
                    }
                    break;
            }
        }
        return movimentos;
    }

    // Obtem todos movimentos possíveis do tabuleiro por jogador.
    public  ArrayList<Movimento> getTodosMovimentos( int numeroJogador) {
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();

        // Pecorre todo o tabuleiro [8][8]
        for (int linha = 1; linha <= 8; linha++) {
            for (int coluna = 1; coluna <= 8; coluna++) {
                ArrayList<Movimento> movimentosPeca = getTodosMovimentosPeca( numeroJogador, linha, coluna);

                for (int i = 0; i < movimentosPeca.size(); i++) {
                    movimentos.add(movimentosPeca.get(i));
                }
            }
        }

        return movimentos;
    }

    /*
     * Função que verifica se a peça está dentro do tabuleiro
     */
    public  boolean isPecaNoTabuleiro(int x, int y) {
        return isPecaNoTabuleiro(x, y);
    }

    /*
     * Função que verifica se a peça está dentro do tabuleiro
     */
    public  boolean isPecaNoTabuleiro(byte x, byte y) {
        // O tabuleiro começa nas posições x=y=1 
        if (x < 1 || x > 8 || y < 1 || y > 8) {
            return false;
        }

        return true;
    }
}
