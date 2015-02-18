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
public class King extends Piece {
    
    /* king attributes */
    boolean notMovedYet;
    
    /** Creates a new instance of King */
    public King(Color cor) {
        super("King",cor);
    }
    
    /** Creates a new instance of King */
    public King(Color cor, Position pos) {
        super("King",cor,pos);
        this.setNotMovedYet(true);
    }
    
    public boolean isNotMovedYet() {
        return notMovedYet;
    }
    
    public void setNotMovedYet(boolean notMovedYet) {
        this.notMovedYet = notMovedYet;
    }
    
    /**
     *  Retorna a relacão de posições(caminho) a
     * serem percorridas para alcançar o destino
     */
    public ArrayList <Position> getPath(Position destino,Chess chess){
        ArrayList <Position> path = null;
        Position posicaoAtual = this.getPosition();
        
        if (!destino.equals(posicaoAtual)) {
            Board board = chess.getBoard();
            Position roque2;
            Position roque1;
            Rook torre1 = null;
            Rook torre2 = null;
            Color corRei = this.getColor();
            int offset;
            Piece pi;
            
            /* verifica os pontos de roque, para pecas pretas e brancas */
            if (corRei == Color.WHITE) {
                /* rei branco */
                if ( (pi = board.getPieceAtPosition(new Position(7,7))) != null && pi instanceof Rook) {
                    torre1 = (Rook) board.getPieceAtPosition(new Position(7,7));
                }
                roque1 = new Position(7,6);
                if ( (pi = board.getPieceAtPosition(new Position(7,0))) != null && pi instanceof Rook) {
                    torre2 = (Rook) pi;
                }
                roque2 = new Position(7,2);
            } else { /* rei preto  */
                if ( (pi = board.getPieceAtPosition(new Position(0,7))) != null && pi instanceof Rook) {
                    torre1 = (Rook) pi;
                }
                roque1 = new Position(0,6);
                if ( (pi = board.getPieceAtPosition(new Position(0,0))) != null && pi instanceof Rook) {
                    torre2 = (Rook) pi;
                }
                roque2 = new Position(0,2);
            }
            
            
            /* verifica se deseja-se fazer um roque */
            /* verifica se o rei ainda n foi movido */
            if (this.isNotMovedYet() && (destino.equals(roque1) ||
                    destino.equals(roque2)) ) {
                
                Board cloneBoard = board.getBoardClone();
                
                if ((destino.equals(roque1) && corRei == Color.WHITE)) {
                    offset = 1; // vai mover p/ direita, pois é roque 1
                    /* rei branco que fazer roque1 */
                    /* verifica se a torre1 n foi movida ainda */
                    if (torre1 != null && torre1.isNotMovedYet()) {
                        /* torre1 n foi movida ainda, pode-se fazer o roque1 */
                        /* verifica se as casas ao redor nao estao ocupadas (1 regra do roque) */
                        if (board.isNullPosition(new Position(7,5)) && board.isNullPosition(new Position(7,6))) {
                        /* - as casas 7,5 e 7,6 ja estao livres, porem nao podem estar sendo atacadas por uma
                         *   peca adversaria (2a regra do roque)
                         */
                            if (!((chess.isAtackedByBlack(new Position(7,5),board)) ||
                                    (chess.isAtackedByBlack(new Position(7,6),board)))) {
                                /* posicoes nao estao sobe ataque, entao faz-se o roque */
                                board.setPosition(this,new Position(7,6));
                                this.setPosition(new Position(7,6));
                                board.setPosition(torre1,new Position(7,5));
                                torre1.setPosition(new Position(7,5));
                                board.setPosition(null,posicaoAtual);
                                board.setPosition(null,new Position(7,7));
                                path = new ArrayList <Position>();
                                path.add(new Position(99,99)); // posicao especial de roque
                                return path;
                            }
                        }
                    }
                } else if ((destino.equals(roque1) && corRei == Color.BLACK)) {
                    offset = 1;
                    /* rei preto que fazer roque1 */
                    if (torre1 != null && torre1.isNotMovedYet()) {
                        /* torre1 n foi movida ainda, pode-se fazer o roque1 */
                        /* torre1 n foi movida ainda, pode-se fazer o roque1 */
                        /* verifica se as casas ao redor nao estao ocupadas */
                        if (board.isNullPosition(new Position(0,5)) && board.isNullPosition(new Position(0,6))) {
                            if (!((chess.isAtackedByWhite(new Position(0,5),board)) ||
                                    (chess.isAtackedByWhite(new Position(0,6),board)))) {
                                /* pode fazer o roque */
                                board.setPosition(this,new Position(0,6));
                                this.setPosition(new Position(0,6));
                                board.setPosition(torre1,new Position(0,5));
                                torre1.setPosition(new Position(0,5));
                                board.setPosition(null,posicaoAtual);
                                board.setPosition(null,new Position(0,7));
                                path = new ArrayList <Position>();
                                path.add(new Position(99,99)); // posicao especial de roque
                                return path;
                            }
                        }
                    }
                } else if ((destino.equals(roque2) && corRei == Color.BLACK)) {
                    offset = -1;
                    /* rei preto que fazer roque2 */
                    if (torre2 != null && torre2.isNotMovedYet()) {
                        /* torre1 n foi movida ainda, pode-se fazer o roque2 */
                        /* verifica se as casas ao redor nao estao ocupadas */
                        if (board.isNullPosition(new Position(0,2)) && board.isNullPosition(new Position(0,3)) &&
                                board.isNullPosition(new Position(0,1)) ) {
                            if (!( (chess.isAtackedByWhite(new Position(0,1),board)) ||
                                    (chess.isAtackedByWhite(new Position(0,2),board)) ||
                                    (chess.isAtackedByWhite(new Position(0,3),board)) )) {
                                
                                /* pode fazer o roque */
                                board.setPosition(this,new Position(0,2));
                                this.setPosition(new Position(0,2));
                                board.setPosition(torre2,new Position(0,3));
                                torre2.setPosition(new Position(0,3));
                                board.setPosition(null,new Position(0,0));
                                board.setPosition(null,posicaoAtual);
                                path = new ArrayList <Position>();
                                path.add(new Position(99,99)); // posicao especial de roque
                                return path;
                            }
                        }
                    }
                } else if ((destino.equals(roque2) && corRei == Color.WHITE)) {
                    offset = -1;
                    /* rei branco que fazer roque2 */
                    if (torre1 != null && torre2.isNotMovedYet()) {
                        /* torre1 n foi movida ainda, pode-se fazer o roque2 */
                        /* torre1 n foi movida ainda, pode-se fazer o roque1 */
                        /* verifica se as casas ao redor nao estao ocupadas */
                        if (board.isNullPosition(new Position(7,1)) && board.isNullPosition(new Position(7,2)) &&
                                board.isNullPosition(new Position(7,3)) ) {
                            if (!( (chess.isAtackedByBlack(new Position(7,1),board)) ||
                                    (chess.isAtackedByBlack(new Position(7,2),board)) ||
                                    (chess.isAtackedByBlack(new Position(7,3),board)) )) {
                                /* pode fazer o roque */
                                board.setPosition(this,new Position(7,2));
                                this.setPosition(new Position(7,2));
                                board.setPosition(torre2,new Position(7,3));
                                torre2.setPosition(new Position(7,3));
                                board.setPosition(null,posicaoAtual);
                                board.setPosition(null,new Position(7,0));
                                path = new ArrayList <Position>();
                                path.add(new Position(99,99)); // posicao especial de roque
                                return path;
                            }
                        }
                    }
                }
                
            } else { /* rei movido.. n faz-se roque */
                int difX = Math.abs(destino.getX()-posicaoAtual.getX());
                int difY = Math.abs(destino.getY()-posicaoAtual.getY());
                
                /* verifica se esta se movendo ao redor da casa atual */
                if ( (((difX==1)&&(difY==0)) || ((difY==1)&&(difX==0))) || ((difX==1)&&(difY==1))) {
                    path = new ArrayList <Position> ();
                    path.add(destino);
                }
            }
            if (path != null) {
                this.setNotMovedYet(false);
            }
        }
        return path;
    }
    
    
    /* Rei nao utiliza este metodo */
    public ArrayList <Position> getPath(Position destino) {
        return null;
    }
    public ArrayList <Position> getPath(Position destino,Board board) {
        return null;
    }
    
    /* Implementar metodos das jogadas possiveis.. getters and setters etc */
    
}
