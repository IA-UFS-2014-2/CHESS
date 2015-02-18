
package VerificarUtilidadesDessasClasses;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import model.Piece;

/**
 *
 * @author newen
 */
public abstract class Piece {
    
    /* Nome da peca (Ex: Pawn, King, etc ) */
    String name;
    
    /* Cor da peca (preto/branco) */
    Color color;
    
    /* Position peca (posicao no tabuleiro) */
    Position position;
    
    /**
     * 
     * Creates a new instance of Piece
     */
    public Piece(String nome, Color cor) {
       this( nome, cor, new Position());            
    }
    
    /**
     * 
     * Creates a new instance of Piece
     */
    public Piece(String nome, Color cor, Position pos) {
        this.name = nome;
        this.color = cor;
        this.position = pos; 
    }
    
    /** 
     *  Retorna a relac�o de posi��es(caminho) a 
     * serem percorridas para alcan�ar o destino 
     */
    public abstract ArrayList <Position> getPath(Position destino);
    public abstract ArrayList <Position> getPath(Position destino,Board board);
    public abstract ArrayList <Position> getPath(Position destino,Chess chess);
       
    /**
     * 
     * Define a posi��o da pe�a no tabuleiro
     */ 
    public void setPosition(Position pos) {
        this.position = pos;
    }

    /**
     * 
     * Pega a posi��o da pe�a no tabuleiro
     */   
    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    

}
