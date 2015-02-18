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
import model.Board;
import model.Chess;
import model.Piece;
import model.Position;

/**
 *
 * @author newen
 */
public class Torre extends Piece {
    
    /* rook attributes */
    boolean notMovedYet;
    
    /** Creates a new instance of Pawn */
    public Torre(Color cor) {
        super("Rook",cor);
    }
    
    /** Creates a new instance of Pawn */
    public Torre(Color cor, Position pos) {
        super("Rook",cor,pos);
        this.setNotMovedYet(true);
    }
    
    public boolean isNotMovedYet() {
        return notMovedYet;
    }
    
    public void setNotMovedYet(boolean notMovedYet) {
        this.notMovedYet = notMovedYet;
    }
    
    
    /**
     *  Retorna a relac�o de posi��es(caminho) a
     * serem percorridas para alcan�ar o destino
     */
    public ArrayList <Position> getPath(Position destino){
        ArrayList <Position> path = null;
        Position posicaoAtual = this.getPosition();
        if (!destino.equals(posicaoAtual)) {
            int offset = 1;            
            /* verifica se ele nao esta querendo se mover para a mesma
             * posicao atual (no caso da verificacao de xeque-mate */
            
            /* movimento vertical da torre */
            if (destino.getY() == posicaoAtual.getY()) {
                /* cria o caminho */
                path = new ArrayList <Position> ();
                
                /* determina a direcao a ser percorrida */
                if (destino.getX() < posicaoAtual.getX()) offset = -1;
                
                /* adiciona as posicoes do caminho */
                int x;
                for (x=posicaoAtual.getX()+offset; x != destino.getX(); x+=offset) {
                    path.add(new Position(x,destino.getY()));
                }
                path.add(new Position(x,destino.getY()));
            } else if (destino.getX() == posicaoAtual.getX()) {
                /* movimento horizontal da torre */
                /* cria o caminho */
                path = new ArrayList <Position> ();
                
                /* determina a direcao a ser percorrida */
                if (destino.getY() < posicaoAtual.getY()) offset*= -1;
                
                /* adiciona as posicoes do caminho */
                int y;
                for (y=posicaoAtual.getY()+offset; y != destino.getY(); y+=offset) {
                    path.add(new Position(destino.getX(),y));
                }
                path.add(new Position(destino.getX(),y));
            }
        }
        
        if (path != null) {
            this.setNotMovedYet(false);
        }
        return path;
    }
    
    /* Torre nao utiliza este metodo */
    public ArrayList <Position> getPath(Position destino,Board board) {
        return null;
    }
    public ArrayList<Position> getPath(Position destino, Chess chess) {
        return null;
    }
    
    /* Implementar metodos das jogadas possiveis.. getters and setters etc */
    
}
