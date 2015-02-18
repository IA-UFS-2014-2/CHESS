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
public class Bishop extends Piece {
    
    /** Creates a new instance of Pawn */
    public Bishop(Color cor) {
        super("Bishop",cor);
    }
    
    /** Creates a new instance of Pawn */
    public Bishop(Color cor, Position pos) {
        super("Bishop",cor,pos);
    }
    
    /* Bispo nao utiliza este metodo */
    public ArrayList <Position> getPath(Position destino,Board board){
        return null;
    }
    
    public Color getBoardPositionColor(int x,int y) {
        if (((x+y)%2) == 0) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    
    
    /**
     *  Retorna a relacão de posições(caminho) a
     * serem percorridas para alcançar o destino
     */
    public ArrayList <Position> getPath(Position destino) {
        
        ArrayList <Position> path = null;
        Position posicaoAtual = this.getPosition();
        
        if (!destino.equals(posicaoAtual)) {
            
            /* determina a cor das casas origem e destino */
            Color c1 = this.getBoardPositionColor(posicaoAtual.getX(),posicaoAtual.getY());
            Color c2 = this.getBoardPositionColor(destino.getX(),destino.getY());
            
            /* verifica se as casas tem a mesma cor */
            if (c1 == c2) {
                int l = Math.abs(destino.getX()-posicaoAtual.getX());
                int m = Math.abs(destino.getY()-posicaoAtual.getY());
                /* verifica se é um deslocamento em diagonal */
                if (l == m) {
                    /* - a cada casa q o bispo anda em X, deve andar uma  em Y, seguindo um offset */
                    int offsetX = 1;
                    int offsetY = 1;
                    /* determina a direcao do bispo "cima-baixo" "esquerda-direita" */
                    if (destino.getX() < posicaoAtual.getX()) offsetX *= -1;
                    if (destino.getY() < posicaoAtual.getY()) offsetY *= -1;
                    
                    path = new ArrayList <Position> ();
                    int x, y;
                    for (x = posicaoAtual.getX()+offsetX, y = posicaoAtual.getY()+offsetY;
                    x != destino.getX(); x+=offsetX, y+=offsetY) {
                        path.add(new Position(x,y));
                    }
                    path.add(new Position(x,y));
                }
            }
        }
        return path;
    }
    
    /* Implementar metodos das jogadas possiveis.. getters and setters etc */
    
    public ArrayList<Position> getPath(Position destino, Chess chess) {
        return null;
    }
    
}
