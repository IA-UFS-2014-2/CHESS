package principal;

import pecas.APeca;
import pecas.Posicao;

/**
 *
 * @author Fábio Nascimento
 */
public class Tabuleiro {

    private int turno;
    private String ultima_jogada_notacao;
    private Jogada ultima_jogada;
    private final APeca[][] posicoes;

    public Tabuleiro() {
        this.posicoes = new APeca[8][8];
    }
    
    public void incluirPeca(APeca peca){
        //Subtrai 1 do x e y
        Posicao posicaoAtual = peca.getPosicao_atual();
        this.posicoes[posicaoAtual.getX()-1][posicaoAtual.getY()-1] = peca;
    }

    public APeca[][] getPosicoes() {
        return posicoes;
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
    
}
