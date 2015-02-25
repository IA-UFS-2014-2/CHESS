package principal;

import com.sun.org.apache.regexp.internal.REProgram;

import pecas.APeca;
import pecas.Posicao;
import pecas.Rei;
import pecas.Torre;

/**
 *
 * @author Fábio Nascimento
 */
public class Tabuleiro {

    private int turno;
    private String ultima_jogada_notacao;
    private Jogada ultima_jogada;
    private APeca[][] posicoes;
    
    //Armazenando o rei do jagador e de seu oponente.
    private Rei reiOponente;
    private Rei reiProprio;
    
    //Armazenando as duas torres do jogador.
    //Instaciando inicialmente com nula, pois ela só terá algum valor 
    //se elas poderem ser utilizadas para o movimento do Roque.
    private Torre torreEsquerda = new Torre() {};
    private Torre torreDireita = new Torre() {};

	public Tabuleiro()
	{
		this.posicoes = new APeca[8][8];
	}
        
    public Tabuleiro(APeca[][] posicoes, Rei reiOponente, Rei reiProprio)
	{
		 this.posicoes = posicoes;
                 this.reiOponente = reiOponente;
                 this.reiProprio = reiProprio;
	}
	    
    public void incluirPeca(APeca peca){
        //Subtrai 1 do x e y
        Posicao posicaoAtual = peca.getPosicao_atual();
        this.posicoes[posicaoAtual.getX()-1][posicaoAtual.getY()-1] = peca;
        
        // Se a peça é o Rei, armazena-o
        if (peca instanceof Rei)
        {
        	//Se o rei for do jogador
        	if (Jogo.jogador.getNumeroJogador() == 1 && peca.getCor().equals("branca")) // numeroJogador = 1 => branca
        	{
        		reiProprio = peca;
        	}
        	else if (Jogo.jogador.getNumeroJogador() == 2 && peca.getCor().equals("preta")) // numeroJogador = 2 => preto
        	{
        		reiProprio = peca;
        	}
        	//Se o rei for do oponente
        	else
        	{
        		reiOponente = peca;
        	}
        }
        
        // Se a peça é a torre, armazena-a
        if (peca instanceof Torre)
        {
        	//Se a torre for do jogador
        	if (Jogo.jogador.getNumeroJogador() == 1 && peca.getCor().equals("branca")) // numeroJogador = 1 => branca
        	{
        		//Só armazena a torre se for possível utilizá-la para o movimento Roque
        		if (peca.getPosicao_atual().getX() == 1 && peca.getPosicao_atual().getY() == 1) torreEsquerda = peca;
        		else if (peca.getPosicao_atual().getX() == 1 && peca.getPosicao_atual().getY() == 8) torreDireita = peca;
        	}
        	else if (Jogo.jogador.getNumeroJogador() == 2 && peca.getCor().equals("preta")) // numeroJogador = 2 => preto
        	{
        		//Só armazena a torre se for possível utilizá-la para o movimento Roque
        		if (peca.getPosicao_atual().getX() == 1 && peca.getPosicao_atual().getY() == 8) torreEsquerda = peca;
        		else if (peca.getPosicao_atual().getX() == 8 && peca.getPosicao_atual().getY() == 8) torreDireita = peca;
        	}
        }
    }
    
    public APeca[][] clonePosicoes(){
        return this.posicoes.clone();
    }
    

    public APeca[][] getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(APeca[][] posicoes) {
        this.posicoes = posicoes;
    }
    

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getUltima_jogada_notacao() {
        return ultima_jogada_notacao;
    }

    public void setUltima_jogada_notacao(String ultima_jogada_notacao) {
        this.ultima_jogada_notacao = ultima_jogada_notacao;
    }

    public Jogada getUltima_jogada() {
        return ultima_jogada;
    }

    public void setUltima_jogada(Jogada ultima_jogada) {
        this.ultima_jogada = ultima_jogada;
    }
    
	public Rei getReiOponente() {
		return reiOponente;
	}

	public void setReiOponente(Rei reiOponente) {
		this.reiOponente = reiOponente;
	}
	
	public Rei getReiProprio() {
		return reiProprio;
	}

	public void setReiProprio(Rei reiProprio) {
		this.reiProprio = reiProprio;
	}
    
	public Torre getTorreEsquerda() {
		return torreEsquerda;
	}

	public void setTorreEsquerda(Torre torreEsquerda) {
		this.torreEsquerda = torreEsquerda;
	}

	public Torre getTorreDireita() {
		return torreDireita;
	}

	public void setTorreDireita(Torre torreDireita) {
		this.torreDireita = torreDireita;
	}
	
    public String toString(){
        String strTabuleiro = "";
        byte x,y;
        
        for(x = 7; x >= 0; x--){
            //Linha
            for(y = 0; y <= 7; y++){
            //Coluna
                APeca pecaCorrent = this.posicoes[x][y];
                if(pecaCorrent.isVazia()){
                    strTabuleiro+= "    X   |";
                }else{
                    if(pecaCorrent.getCor().equals("preta")){
                        strTabuleiro+= pecaCorrent.getNome()+"_"+pecaCorrent.getCor()+" |"; 
                    }else{
                        strTabuleiro+= pecaCorrent.getNome()+"_"+pecaCorrent.getCor()+"|"; 
                    }
                    
                }
               
            }
            //No final da Linha Quebra 
            strTabuleiro+="\n";
        }
        
        return strTabuleiro; 
    }
    
	//Obtendo a peça a partir da posição informada
        // O retorno desse metodo nao deveria ser uma peca do tipo APeca?
	public APeca getPecaByPosicao(Posicao posicao)
	{
		//Verificando se está dentro do tabuleiro
		if (posicao.getX() < 1 || posicao.getX() > 8 || posicao.getY() < 1 || posicao.getY() > 8)
		{
			return null;
		}
		else
		{
			return this.posicoes[posicao.getX()-1][posicao.getY()-1];
		}
	}
	
	//Obtendo a peça a partir da posição informada
	public APeca getPecaByPosicao(byte x, byte y)
	{
		return getPecaByPosicao(new Posicao(x, y));
	}
	
	//Obtendo a peça a partir da posição informada
	public APeca getPecaByPosicao(int x, int y)
	{
		return getPecaByPosicao(new Posicao((byte)x, (byte)y));
	}  
}
