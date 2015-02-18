/*
 * Pawn.java
 *
 * Created on October 4, 2006, 8:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model.Pecas;

import java.awt.Color;
import java.util.*;
import model.Chess;
import model.Piece;
import model.Position;
import model.Board;

/**
 *
 * @author newen
 */
public class Pawn extends Piece {
    
    /* Pawn attributes */
    Position posicaoInicial; // atributo que guarda o local de criacao do peao
    // pois nesta posicao, ele pode pular 2 casas
    
    /** Creates a new instance of Pawn */
    public Pawn(Color cor) {
        super("Pawn",cor);
    }
    
    /** Creates a new instance of Pawn */
    public Pawn(Color cor, Position pos) {
        super("Pawn",cor,pos);
        this.setPosicaoInicial(pos);
    }
    
    /* Peao nao utiliza este metodo */
    public ArrayList <Position> getPath(Position destino){
        return null;
    }
    
    /**
     *  Retorna a relacão de posições(caminho) a
     * serem percorridas para alcançar o destino
     */
    public ArrayList <Position> getPath(Position destino,Board board){
        
        ArrayList path = null;
        Position posicaoAtual = this.getPosition();
        
        if (!destino.equals(posicaoAtual)) {
            int offset = 1;
            
            if (this.getColor() == Color.WHITE)
                offset *= -1; // passa a andar em direcao para cima do tabuleiro
            
        /* -> testa se a posicao destino pode ser alcancada a partir da origem
         *    - se o peao esta na posicao original (ainda nao foi movido)
         *      entao ele pode pular 2 casas
         *    - ou a posicao destino esta no msm X que a posicao origem
         *    - ou (no caso de comer uma outra peca), a posicao destino pode ser
         *    - 1 casa acima e 1 casa ao lado (desde que exista uma peca em destino)
         * -> pecas bracas andam para cima (indice menor), pretas o contrario
         */
            
            if ((posicaoAtual.getY() == destino.getY()) && board.isNullPosition(destino)) {
                /* movimento vertical, nao vai comer nenhuma peca */
                if (posicaoAtual.equals(this.getPosicaoInicial())) {
                /* ainda nao se moveu, pode entao pular 2 casas (se quiser), basta verificar se
                 * a posicao destino é valida e adicionar no caminho */
                    if (destino.getX() == posicaoAtual.getX()+(offset*2)) {
                        /* o jogador deseja pular 2 casas */
                        path = new ArrayList <Position>();
                        Position pos = new Position(posicaoAtual.getX()+offset,posicaoAtual.getY());
                        path.add(pos);
                        path.add(destino);
                    } else if (destino.getX() == posicaoAtual.getX()+offset) {
                        /* o jogador deseja pular somente 1 casa */
                        path = new ArrayList <Position>();
                        path.add(destino);
                    }
                } else {
                    /* peao ja foi movido, entao pode pular somente 1 casa */
                    if (destino.getX() == posicaoAtual.getX()+offset) {
                        /* verificando se a posicao clicada corresponde a um possivel movimento */
                        path = new ArrayList <Position>();
                        path.add(destino);
                    }
                }
            } else { /* movimento em diagonal, quer comer uma outra peca */
                /* so podera se movimentar 1 casa em X e uma casa em Y */
                if ((destino.getY() == posicaoAtual.getY()+1) || (destino.getY() == posicaoAtual.getY()-1))  {
                    /* a posicao clicada (destino) esta 1 casa de offset em X */
                    if (destino.getX() == posicaoAtual.getX()+offset) {
                        
                    /* a posicao clicada (destino) esta 1 casa de offset em Y, entao
                     * tem q verificar se existe 1 peca de outra cor na posicao destino
                     * caso contrario, n é possivel fazer o movimento, e path = null
                     */
                        Piece adv;
                        if (((adv = board.getPieceAtPosition(destino)) != null)&&(adv.getColor() != this.getColor())) {
                            path = new ArrayList <Position>();
                            path.add(destino);
                        }
                    }
                }
            }
        }
        return path;
    }
    
    /* Implementar metodos das jogadas possiveis.. getters and setters etc */
    
    public Position getPosicaoInicial() {
        return posicaoInicial;
    }
    
    public void setPosicaoInicial(Position posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }
    
    public ArrayList<Position> getPath(Position destino, Chess chess) {
        return this.getPath(destino,chess.getBoard());
    }
}
