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
public class Cavalo extends Piece {
    
    /**
     * Creates a new instance of Knight
     */
    public Cavalo(Color cor) {
        super("Knight",cor);
    }
    
    /**
     * Creates a new instance of Knight
     */
    public Cavalo(Color cor, Position pos) {
        super("Knight",cor,pos);
    }
    
    /**
     *  Retorna a relac�o de posi��es(caminho) a
     * serem percorridas para alcan�ar o destino
     */
    public ArrayList <Position> getPath(Position destino){
        
        ArrayList <Position> path = null;
        Position posicaoAtual = this.getPosition();
        if (!destino.equals(posicaoAtual)) {
            
            Color c1 = this.getBoardPositionColor(posicaoAtual.getX(),posicaoAtual.getY());
            Color c2 = this.getBoardPositionColor(destino.getX(),destino.getY());
            
            /* o cavalo sempre muda de cor ao se movimentar */
            if (c1 != c2) {
                int difY = Math.abs(destino.getY()-posicaoAtual.getY());
                int difX = Math.abs(destino.getX()-posicaoAtual.getX());
                
                /* verifica se esta fazendo um L */
                if (((difX==2)&&(difY==1)) || ((difX==1) && (difY ==2))) {
                    path = new ArrayList <Position> ();
                    path.add(destino);
                }
            }
        }
        return path;
    }
    
    public Color getBoardPositionColor(int x,int y) {
        if (((x+y)%2) == 0) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    
    /* Cavalo nao utiliza este metodo */
    public ArrayList <Position> getPath(Position destino,Board board) {
        return null;
    }
    public ArrayList<Position> getPath(Position destino, Chess chess) {
        return null;
    }
    
    /* Implementar metodos das jogadas possiveis.. getters and setters etc */
    
}
