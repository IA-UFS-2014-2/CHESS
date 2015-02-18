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
public class Queen extends Piece {
    
    /** Creates a new instance of Queen */
    public Queen(Color cor) {
        super("Queen",cor);
    }
    
    /** Creates a new instance of Queen */
    public Queen(Color cor, Position pos) {
        super("Queen",cor,pos);
    }
    
    /**
     *  Retorna a relacão de posições(caminho) a
     * serem percorridas para alcançar o destino
     */
    public ArrayList <Position> getPath(Position destino){
        
        ArrayList <Position> path = null;
        Position posicaoAtual = this.getPosition();
        
        if (!destino.equals(posicaoAtual)) {
            /* verifica se utilizará o movimento de torre */
            if ((destino.getY() == posicaoAtual.getY()) ||
                    (destino.getX() == posicaoAtual.getX()) ){
                Rook r = (new Rook(Color.PINK));
                r.setPosition(this.getPosition());
                path = r.getPath(destino);
            } else {
                /* ira utilizar o movimento de bispo */
                Bishop b = (new Bishop(Color.PINK));
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
