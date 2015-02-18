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
public class Rainha extends Piece {
    
    /** Creates a new instance of Queen */
    public Rainha(Color cor) {
        super("Queen",cor);
    }
    
    /** Creates a new instance of Queen */
    public Rainha(Color cor, Position pos) {
        super("Queen",cor,pos);
    }
    
    /**
     *  Retorna a relac�o de posi��es(caminho) a
     * serem percorridas para alcan�ar o destino
     */
    public ArrayList <Position> getPath(Position destino){
        
        ArrayList <Position> path = null;
        Position posicaoAtual = this.getPosition();
        
        if (!destino.equals(posicaoAtual)) {
            /* verifica se utilizar� o movimento de torre */
            if ((destino.getY() == posicaoAtual.getY()) ||
                    (destino.getX() == posicaoAtual.getX()) ){
                Torre r = (new Torre(Color.PINK));
                r.setPosition(this.getPosition());
                path = r.getPath(destino);
            } else {
                /* ira utilizar o movimento de bispo */
                Bispo b = (new Bispo(Color.PINK));
                b.setPosition(this.getPosition());
                path = b.getPath(destino);
            }
        }
        
        return path;
    }
    
    /* Rainha nao utiliza este metodo */
    public ArrayList <Position> getPath(Position destino,Board board) {
        return null;
    }
    public ArrayList<Position> getPath(Position destino, Chess chess) {
        return null;
    }
    
    /* Implementar metodos das jogadas possiveis.. getters and setters etc */
    
}
