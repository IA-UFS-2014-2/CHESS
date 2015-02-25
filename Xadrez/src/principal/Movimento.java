package principal;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;

import pecas.APeca;
import pecas.Bispo;
import pecas.Cavalo;
import pecas.Peao;
import pecas.PontoVazio;
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

    private int utilidade;

    //Posição atual e nova posição do movimento.
    private APeca pecaOrigem;
    private APeca pecaDestino;

    //Peça capturada no movimento.
    private APeca pecaCapturada;

    //Indica se o movimento gera uma promoção do peão.
    private boolean promocaoPeao = false;

    public int getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(int utilidade) {
        this.utilidade = utilidade;
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
    
    public Movimento(int utilidade) {
        this.pecaOrigem = pecaOrigem;
        this.pecaDestino = pecaDestino;
    }

    /*
     * Função que verifica se a peça destino está vazia.
     */
    public static boolean isPecaDestinoVazia(Movimento mov) {
        return mov.pecaDestino.isVazia();
    }

    /*
     * Função que verifica se a peça destino pode ser capturada.
     */
    private static boolean isPecaDestinoCapturavel(Tabuleiro tabuleiro, Movimento mov) {
        //Verificando se a peça destino está dentro do tabuleiro
        if (isPecaNoTabuleiro(mov.pecaDestino.getPosicao_atual().getX(), mov.pecaDestino.getPosicao_atual().getY())) {
            //Obtendo a peça localizada nas coordenadas da peça destino
            //mov.pecaDestino = tabuleiro.getPecaByPosicao(mov.pecaDestino.getPosicao_atual()); //TODO

            //Verificando se a peça destino está vazia, se sim não pode ser capturada.
            // Se ela nao pode ser capturada entao o retorno deve ser false
            if (mov.pecaDestino.isVazia()) {
                return false;
            } //Verificando se a cor da peça destino é diferente da peça origem 
            else if (!mov.pecaOrigem.getCor().equals(mov.pecaDestino.getCor())) {
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
    private static boolean isPecaEntreOrigemDestino(Tabuleiro tabuleiro, Movimento mov, int incrementoPorLinha, int incrementoPorColuna) {
        
        // Se linhaAtual e colunaAtual sao coordenadas de uma posicao, eles nao deveriam ser declarados como byte? 
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
            if (isPecaValida(tabuleiro, linhaAtual, colunaAtual)) {
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
    public static boolean isPecaValida(Tabuleiro tabuleiro, int x, int y) {
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
    public static boolean isValidoMovimentoPeao(Tabuleiro tabuleiro, Movimento mov) {
	// O peão pode avançar para a casa vazia, imediatamente à frente,
        // ou em seu primeiro lance ele pode avançar duas casas.
        // Desde que ambas estejam desocupadas.

        // Verificando se a peça destino está vazia.
        if (isPecaDestinoVazia(mov)) {
            //Verificando se a coluna da peça origem é a mesma da peça destino.
            if (mov.pecaOrigem.getPosicao_atual().getY() == mov.pecaDestino.getPosicao_atual().getY()) {
                if (Jogo.jogador.getNumeroJogador() == 1) //Branco 
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
                            // alterei getY() por getX()
                            if (mov.pecaDestino.getPosicao_atual().getX() == 8) //Está na última linha, ou seja pode ser promovido.
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
                            if (mov.pecaDestino.getPosicao_atual().getX() == 1) //Está na primeira linha, ou seja pode ser promovido.
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
                    || mov.pecaOrigem.getPosicao_atual().getY() + 1 == mov.pecaDestino.getPosicao_atual().getY())//Coluna direita Obs. coloquei +1 no lugar do -1
            {
                if (Jogo.jogador.getNumeroJogador() == 1) //Branco 
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
        else if (isPecaDestinoCapturavel(tabuleiro, mov)) {
            //Verificanda se a peça destino está na coluna a esquerda ou a direita da peça origem.
            if (mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY()//Coluna esquerda
                    || mov.pecaOrigem.getPosicao_atual().getY() - 1 == mov.pecaDestino.getPosicao_atual().getY())//Coluna direita
            {
                if (Jogo.jogador.getNumeroJogador() == 1) //Branco 
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
    public static boolean isValidoMovimentoCavalo(Tabuleiro tabuleiro, Movimento mov) {
		// O cavalo anda em um formato que reproduz
        // a letra L, ou seja, duas casas, na direção horizontal ou
        // vertical, e mais uma, em ângulo reto à direção anterior

        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel(tabuleiro, mov)) {
            int distanciaEntreLinhas = Math.abs(mov.pecaOrigem.getPosicao_atual().getX() - mov.pecaDestino.getPosicao_atual().getX());
            int distanciaEntreColunas = Math.abs(mov.pecaOrigem.getPosicao_atual().getY() - mov.pecaDestino.getPosicao_atual().getY());
            if ((distanciaEntreLinhas == 2 && distanciaEntreColunas == 1)
                    || (distanciaEntreLinhas == 1 && distanciaEntreColunas == 2)) {
                //Armazenando a peça capturada.
                if (isPecaDestinoCapturavel(tabuleiro, mov)) {
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
    public static boolean isValidoMovimentoBispo(Tabuleiro tabuleiro, Movimento mov) {
		// O bispo pode mover-se qualquer número de casas na diagonal,
        // mas não pode saltar sobre outras peças.

        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel(tabuleiro, mov)) {
            int distanciaEntreLinhas = mov.pecaOrigem.getPosicao_atual().getX() - mov.pecaDestino.getPosicao_atual().getX();
            int distanciaEntreColunas = mov.pecaOrigem.getPosicao_atual().getY() - mov.pecaDestino.getPosicao_atual().getY();

            if (distanciaEntreLinhas == distanciaEntreColunas && distanciaEntreColunas > 0) {
                // Movimento diagonal up-right
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, +1, +1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }

            } else if (distanciaEntreLinhas == -distanciaEntreColunas && distanciaEntreColunas > 0) {
                // Movimento diagonal down-right
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, -1, +1)) { 
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }

            } else if (distanciaEntreLinhas == distanciaEntreColunas && distanciaEntreColunas < 0) {
                // Movimento diagonal down-left
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, -1, -1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }

            } else if (distanciaEntreLinhas == -distanciaEntreColunas && distanciaEntreColunas < 0) {
                // Movimento diagonal up-left
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, +1, -1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
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
    public static boolean isValidoMovimentoTorre(Tabuleiro tabuleiro, Movimento mov) {
	// O movimento executado pelas torres é
        // sempre em paralelas (linhas ou colunas), quantas
        // casas desejar desde que haja espaço livre. 
        // E não pode saltar sobre outra peça válida.

        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel(tabuleiro, mov)) {
            int distanciaEntreLinhas = mov.pecaOrigem.getPosicao_atual().getX() - mov.pecaDestino.getPosicao_atual().getX();
            int distanciaEntreColunas = mov.pecaOrigem.getPosicao_atual().getY() - mov.pecaDestino.getPosicao_atual().getY();

            if (distanciaEntreLinhas == 0 && distanciaEntreColunas > 0) {
                // Movimento right
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, 0, +1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            } else if (distanciaEntreLinhas == 0 && distanciaEntreColunas < 0) {
                // Movimento left
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, 0, -1)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            } else if (distanciaEntreLinhas > 0 && distanciaEntreColunas == 0) {
                // Movimento up
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, +1, 0)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
                        mov.pecaCapturada = mov.pecaDestino;
                    }

                    return true;
                }
            } else if (distanciaEntreLinhas < 0 && distanciaEntreColunas == 0) {
                // Movimento down
                if (!isPecaEntreOrigemDestino(tabuleiro, mov, -1, 0)) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
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
    public static boolean isValidoMovimentoRainha(Tabuleiro tabuleiro, Movimento mov) {
		// A rainha combina o poder da torre e bispo e pode mover qualquer número
        // De casas ao longo coluna, linha ou diagonal, mas não pode saltar sobre outras peças.

        if (isValidoMovimentoBispo(tabuleiro, mov) || isValidoMovimentoTorre(tabuleiro, mov)) {
            return true;
        }

        return false;
    }

    /*
     * Função que verifica se o movimento do Réi é válido.
     */
    public static boolean isValidoMovimentoRei(Tabuleiro tabuleiro, Movimento mov) {
		// O rei somente anda uma casa por lance em todas as direções. 
        // Não pode situar-se em casa sob domínio de peça adversária, 
        // pois o rei não pode se entregar ou se colocar em situação de XEQUE jamais.
        // Esta jodada não é permitida e ambos os jogadores devem estar atentos

		// Não pode capturar peças defendidas pelo adversário, 
        // pois seria capturado no próximo lance, se colocando
        // antecipadamente em XEQUE, o que também não é permitido. 
        // Se a pecaDestino está vazia ou é capturável
        if (isPecaDestinoVazia(mov) || isPecaDestinoCapturavel(tabuleiro, mov)) {
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
                if (isReiSalvo(tabuleiro, mov, mov.pecaOrigem.getCor())) {
                    //Armazenando a peça capturada.
                    if (isPecaDestinoCapturavel(tabuleiro, mov)) {
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
                    if (!isPecaEntreOrigemDestino(tabuleiro, mov, 0, -1) //Casas da posição inical do rei até sua posição final 
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
                            if (isReiSalvo(tabuleiro, mov, mov.pecaOrigem.getCor())) {
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
                    if (!isPecaEntreOrigemDestino(tabuleiro, mov, 0, +1))//Casas da posição inical do rei até sua posição final 
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
                            if (isReiSalvo(tabuleiro, mov, mov.pecaOrigem.getCor())) {
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
    public static boolean isReiSalvo(Tabuleiro tabuleiro, Movimento mov, String cor) 
    {
        // Pegando a posição do Rei do Jogador. 
        APeca rei = tabuleiro.getReiProprio();

        System.out.println("aa "+rei);
        
        int linha = rei.getPosicao_atual().getX();
        int coluna = rei.getPosicao_atual().getY();

        // Verificando se tem algum Peão ameçando o Rei
        if (Jogo.jogador.getNumeroJogador() == 2) // Preta 
        {
            if (	(!tabuleiro.getPecaByPosicao(linha - 1, coluna - 1).getCor().equals(cor) && tabuleiro.getPecaByPosicao(linha - 1, coluna - 1) instanceof Peao) //Linha abaixo e coluna à esquerda
                 || (!tabuleiro.getPecaByPosicao(linha - 1, coluna + 1).getCor().equals(cor) && tabuleiro.getPecaByPosicao(linha - 1, coluna + 1) instanceof Peao))//Linha abaixo e coluna à direita
            {
                return false;
            }
        } 
        else // Branca
        {
            if (	(!tabuleiro.getPecaByPosicao(linha + 1, coluna - 1).getCor().equals(cor) && tabuleiro.getPecaByPosicao(linha + 1, coluna - 1) instanceof Peao) //Linha acima e coluna à esqueda
                 || (!tabuleiro.getPecaByPosicao(linha + 1, coluna + 1).getCor().equals(cor) && tabuleiro.getPecaByPosicao(linha + 1, coluna + 1) instanceof Peao))//Linha abaixo e coluna à direita
            {
                return false;
            }
        }
        
        // Movimentos adjacente inválidos
        // Rei do oponente ao redor
        for (int i = -1; i < 2; i++) //Linha abaixo, mesma linha, e acima
        {
            for (int j = -1; j < 2; j++) //Coluna esqueda, mesma coluna, e direita
            {
                if (i == 0 && j == 0) //Ignorando a própria casa
                {
                    continue;
                }
                if (tabuleiro.getPecaByPosicao(linha + i, coluna + j) instanceof Rei) 
                {
                    return false;
                }
            }
        }

        //Veridficando se tem algum Cavalo ameaçado o Rei
        if (       (!tabuleiro.getPecaByPosicao(linha + 1, coluna - 2).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha + 1, coluna - 2) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha + 1, coluna + 2).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha + 1, coluna + 2) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha - 1, coluna - 2).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha - 1, coluna - 2) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha - 1, coluna + 2).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha - 1, coluna + 2) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha + 2, coluna - 1).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha + 2, coluna - 1) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha + 2, coluna + 1).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha + 2, coluna + 1) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha - 2, coluna - 1).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha - 2, coluna - 1) instanceof Cavalo)
                || (!tabuleiro.getPecaByPosicao(linha - 2, coluna + 1).getCor().equals(cor)
                && tabuleiro.getPecaByPosicao(linha - 2, coluna + 1) instanceof Cavalo)) 
        {
            return false;
        }

        // Verificando se existem ameaças abaixo na vertical
        // Rei
        if (tabuleiro.getPecaByPosicao(linha - 1, coluna) instanceof Rei) 
        {
            return false;
        }
        // Torre e Rainha
        // Percorre as linhas abaixo
        for (int i = linha - 1; i >= 1; i--) 
        {
            APeca p = tabuleiro.getPecaByPosicao(i, coluna);
            if (!p.getCor().equals(cor) && (p instanceof Torre || p instanceof Rainha))
            {
                return false;
            } 
            else 
            {
                if (!p.isVazia()) 
                {
                    break;
                }
            }
        }

        // Verificando se existem ameaças acima na vertical
        // Rei
        // Percorre as linhas acima
        if (tabuleiro.getPecaByPosicao(linha + 1, coluna) instanceof Rei) 
        {
            return false;
        }
        //Torre e Rainha
        for (int i = linha + 1; i <= 7; i++)
        {
            APeca p = tabuleiro.getPecaByPosicao(i, coluna);
            if (!p.getCor().equals(cor) && (p instanceof Torre || p instanceof Rainha)) 
            {
                return false;
            } 
            else 
            {
                if (!p.isVazia()) 
                {
                    break;
                }
            }
        }

        // Verificando se existem ameaças esquerda horizonal
        // Rei
        if (tabuleiro.getPecaByPosicao(linha, coluna - 1) instanceof Rei) 
        {
            return false;
        }
        // Torre e Rainha
        // Percorre as colunas a esqueda
        for (int i = coluna - 1; i >= 1; i--) 
        {
            APeca p = tabuleiro.getPecaByPosicao(linha, i);
            if (!p.getCor().equals(cor) && (p instanceof Torre || p instanceof Rainha))
            {
                return false;
            } 
            else
            {
                if (!p.isVazia()) 
                {
                    break;
                }
            }
        }

		// Verificando se existem ameaças direita horizonatal        
        if (tabuleiro.getPecaByPosicao(linha, coluna + 1) instanceof Rei) 
        {
            return false;
        }
     	// Torre e Rainha
        // Percorre as colunas a esqueda
        for (int i = coluna + 1; i <= 7; i++) 
        {
            APeca p = tabuleiro.getPecaByPosicao(linha, i);
            if (!p.getCor().equals(cor) && (p instanceof Torre || p instanceof Rainha))
            {
                return false;
            }
            else 
            {
                if (!p.isVazia()) 
                {
                    break;
                }
            }
        }

        // Verificando se existem ameaças na diagonal
        for (int x = -1; x < 2; x += 2) //Linha abaixo, mesma linha, e acima
        {
            for (int y = -1; y < 2; y += 2) //Coluna esqueda, mesma coluna, e direita
            {
                if (!isReiSalvoPelaDiagonal(tabuleiro, cor, linha, coluna, x, y))
                {
                    return false;
                }
            }
        }

        return true;
    }

    // Verifica se o próprio rei não sofre nenhuma ameça diagonalmente  
    public static boolean isReiSalvoPelaDiagonal(Tabuleiro tabuleiro, String cor, int linha,int coluna, int linhaX, int colunaY) 
    {
    	//Rei na diagonal
        if (tabuleiro.getPecaByPosicao(linha + linhaX, coluna + colunaY) instanceof Rei) 
        {
            return false;
        }
        //Bispo e Rainha em todas as casas das diagonais possíveis
        for (int i = 1; i <= 8; i++) 
        {
            APeca p = tabuleiro.getPecaByPosicao(linha + i * linhaX, coluna + i * colunaY);
            if (!p.getCor().equals(cor) && (p instanceof Bispo || p instanceof Rainha)) 
            {
                return false;
            }
            else
            {
                if (!p.isVazia())
                {
                    break;
                }
            }
        }
        return true;
    }

    // Verifica se o movimento dado é válido no tabuleiro
    public static boolean isMovimentoValido(Tabuleiro tabuleiro, Movimento mov) {
        //Não realizou nenhum movimento.
        
        if(mov.pecaDestino == null){
            return false;
        }
        
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
                valido = isValidoMovimentoPeao(tabuleiro, mov);
                break;
            case 'B':
                valido = isValidoMovimentoBispo(tabuleiro, mov);
                break;
            case 'C':
                valido = isValidoMovimentoCavalo(tabuleiro, mov);
                break;
            case 'T':
                valido = isValidoMovimentoTorre(tabuleiro, mov);
                break;
            case 'D':
                valido = isValidoMovimentoRainha(tabuleiro, mov);
                break;
            case 'R':
                valido = isValidoMovimentoRei(tabuleiro, mov);
        }
        // Aqui nao deveria retornar true?
        if (!valido) {
            return false;
        }

        return true;
    }

    // Retorna o conjunto de todos os movimentos válidos da dada peca do tabuleiro
    public static ArrayList<Movimento> getTodosMovimentosPeca(Tabuleiro tabuleiro, int numeroJogador, int linha, int coluna) {
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
        
        APeca peca = tabuleiro.getPecaByPosicao(linha, coluna);
        
        if (!peca.isVazia() && ( (numeroJogador == 1 && peca.getCor().equals("branca"))
                || (numeroJogador == 2 && peca.getCor().equals("preta")) )) {
            Movimento mov = null;
            switch (peca.getNome()) {
                case 'P': //Peão
                    if (numeroJogador == 2) { // Preta
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 1, coluna));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 1, coluna + 1));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 1, coluna - 1));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                        if (linha == 7) {
                            mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - 2, coluna));
                            if (isMovimentoValido(tabuleiro, mov)) {
                                movimentos.add(mov);
                            }
                        }
                    } else // Branca
                    {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 1, coluna));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 1, coluna + 1));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 1, coluna - 1));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                        //Linha estava como 1, substituir por 2
                        if (linha == 2) {
                            mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + 2, coluna));
                            if (isMovimentoValido(tabuleiro, mov)) {
                                movimentos.add(mov);
                            }
                        }
                    }
                    break;
                case 'B':
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i, coluna + i));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - i, coluna + i));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i, coluna - i));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - i, coluna - i));
                        if (isMovimentoValido(tabuleiro, mov)) {
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
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        }
                    }
                    break;
                case 'T':
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha + i, coluna));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha - i, coluna));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna + i));
                        if (isMovimentoValido(tabuleiro, mov)) {
                            movimentos.add(mov);
                        } else {
                            break;
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna - i));
                        if (isMovimentoValido(tabuleiro, mov)) {
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
                                if (isMovimentoValido(tabuleiro, mov)) {
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
                            if (isMovimentoValido(tabuleiro, mov)) {
                                movimentos.add(mov);
                            }
                        }
                    }
                    // Roque Maior ou esquerda
                    mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna - 2));
                    if (isMovimentoValido(tabuleiro, mov)) {
                        movimentos.add(mov);
                    }

                    //Roque Menor ou direita
                    mov = new Movimento(peca, tabuleiro.getPecaByPosicao(linha, coluna + 2));
                    if (isMovimentoValido(tabuleiro, mov)) {
                        movimentos.add(mov);
                    }
                    break;
            }
        }
        return movimentos;
    }

    // Obtem todos movimentos possíveis do tabuleiro por jogador.
    public static ArrayList<Movimento> getTodosMovimentos(Tabuleiro tabuleiro, int numeroJogador) {
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();

        // Pecorre todo o tabuleiro [8][8]
        for (int linha = 1; linha <= 8; linha++) {
            for (int coluna = 1; coluna <= 8; coluna++) {
                ArrayList<Movimento> movimentosPeca = getTodosMovimentosPeca(tabuleiro, numeroJogador, linha, coluna);
                System.out.println("Movimentos PEcas: "+ movimentosPeca.size());
                
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
    
    //Usei o type cast pra resolver a exceção
    public static boolean isPecaNoTabuleiro(int x, int y) {
        return isPecaNoTabuleiro((byte)x, (byte)y);
    }

    /*
     * Função que verifica se a peça está dentro do tabuleiro
     */
    public static boolean isPecaNoTabuleiro(byte x, byte y) {
        // O tabuleiro começa nas posições x=y=1 
        if (x < 1 || x > 8 || y < 1 || y > 8) {
            return false;
        }

        return true;
    }
    
    
    public Jogada getJogada(){
     return new Jogada(this.pecaOrigem.getPosicao_atual(), this.pecaDestino.getPosicao_atual());
    }
    
    
    public static APeca[][] realizarJogadanoClonePosicoes(Jogada jogada, APeca[][] clonePosicoes){
        Posicao posicaoAtual = jogada.getPosicao_atual();
        Posicao novaPosicao  = jogada.getNova_posicao();
        
       APeca pecaPosicaoAtual = clonePosicoes[posicaoAtual.getX()][posicaoAtual.getY()];
       clonePosicoes[posicaoAtual.getX()][posicaoAtual.getY()] = new PontoVazio(posicaoAtual);
       
       pecaPosicaoAtual.setPosicao_atual(novaPosicao);
       clonePosicoes[novaPosicao.getX()][novaPosicao.getY()] = pecaPosicaoAtual;
       
       return clonePosicoes; 
    }
    
}
