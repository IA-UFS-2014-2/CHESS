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
    private APeca reiOponente;
    private APeca reiProprio;
    
    //Armazenando as duas torres do jogador.
    //Instaciando inicialmente com nula, pois ela só terá algum valor 
    //se elas poderem ser utilizadas para o movimento do Roque.
    private APeca torreEsquerda = new APeca() {};
    private APeca torreDireita = new APeca() {};

	private static Tabuleiro instance = null;
	
	protected Tabuleiro()
	{
		this.posicoes = new APeca[8][8];
	}
        
        protected Tabuleiro(APeca[][] posicoes)
	{
		 this.posicoes = posicoes;
	}
	
	public static Tabuleiro getInstance() 
	{
		if(instance == null) 
		{
			instance = new Tabuleiro();
		}
		
		return instance;
	}
    
    public void incluirPeca(APeca peca){
        //Subtrai 1 do x e y
        Posicao posicaoAtual = peca.getPosicao_atual();
        this.posicoes[posicaoAtual.getX()-1][posicaoAtual.getY()-1] = peca;
        
        // Se a peça é o Rei, armazena-o
        if (peca instanceof Rei)
        {
        	//Se o rei for do jogador
        	if (Jogo.jogador.getNumeroJogador() == 1 && peca.getCor() == "branca") // numeroJogador = 1 => branca
        	{
        		reiProprio = peca;
        	}
        	else if (Jogo.jogador.getNumeroJogador() == 2 && peca.getCor() == "preta") // numeroJogador = 2 => preto
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
        	if (Jogo.jogador.getNumeroJogador() == 1 && peca.getCor() == "branca") // numeroJogador = 1 => branca
        	{
        		//Só armazena a torre se for possível utilizá-la para o movimento Roque
        		if (peca.getPosicao_atual().getX() == 1 && peca.getPosicao_atual().getY() == 1) torreEsquerda = peca;
        		else if (peca.getPosicao_atual().getX() == 1 && peca.getPosicao_atual().getY() == 8) torreDireita = peca;
        	}
        	else if (Jogo.jogador.getNumeroJogador() == 2 && peca.getCor() == "preta") // numeroJogador = 2 => preto
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
    
	public APeca getReiOponente() {
		return reiOponente;
	}

	public void setReiOponente(APeca reiOponente) {
		this.reiOponente = reiOponente;
	}
	
	public APeca getReiProprio() {
		return reiProprio;
	}

	public void setReiProprio(APeca reiProprio) {
		this.reiProprio = reiProprio;
	}
    
	public APeca getTorreEsquerda() {
		return torreEsquerda;
	}

	public void setTorreEsquerda(APeca torreEsquerda) {
		this.torreEsquerda = torreEsquerda;
	}

	public APeca getTorreDireita() {
		return torreDireita;
	}

	public void setTorreDireita(APeca torreDireita) {
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
	public APeca getPecaByPosicao(Posicao posicao)
	{
		//Verificando se está dentro do tabuleiro
		if (posicao.getX() < 1 || posicao.getX() > 8 || posicao.getY() < 1 || posicao.getY() > 8)
		{
			// Devolvendo uma peça nula
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
