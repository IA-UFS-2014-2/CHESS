/*
 * Board.java
 *
 * Created on October 3, 2006, 8:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VerificarUtilidadesDessasClasses;

import model.Pecas.*;

/**
 *
 * @author newen
 */
public class Board {
    
    /* Matriz de pecas do board */
    Piece board[][];
    
    /* 
     Contrutor da classe board
     */
    public Board() {
        this.board = new Piece[8][8];
        this.clearBoard();
    }
    
    /* 
     Inicializa board
     */
    public void clearBoard () {
        
        for (int i=0; i<8; i++) 
            for (int j=0; j<8; j++)
                this.board[i][j] = null;
    }
    
    /* 
       Coloca a pe�a em uma posicao do board
     */
    public void setPosition(Piece peca, Position pos){
        this.board[pos.getX()][pos.getY()] = peca;
    }
    
    /* 
       Remove pe�a em uma posicao do board
     */
    public void setNullPosition(Position pos){
        this.board[pos.getX()][pos.getY()] = null;
    }
    
    
    /* 
       Verifica se posi��o � vazia
     */
    public boolean isNullPosition(Position pos){
        return this.board[pos.getX()][pos.getY()] == null;
    }
    
    /*
        Move a peca localizada em origem para o destino
        - sobreescreve a peca localizada em destino (se existir)
        - limpa a posicao origem
     */
    public void movePiece (Position origem, Position destino) {
        
        //Pega a pe�a da posi��o
        Piece peca = this.getPieceAtPosition(origem);
        
        // Move a pe�a de origem para o destino
        this.setPosition(peca, destino);     
        
        // Atualiza posi��o da pe�a movida
        peca.setPosition(destino);
        
        // Seta origem como vazia
        this.setNullPosition(origem);
    }
    
    public Board getBoardClone () {
        
        Board board = new Board();
        
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (this.getPieceAtPosition(new Position(i,j)) != null) {
                    Piece p = this.getPieceAtPosition(new Position(i,j));
                    if (p instanceof Bishop) {
                        Bishop b = new Bishop(p.getColor(),p.getPosition());
                        board.setPosition(b,b.getPosition());                        
                    } else if (p instanceof King) {
                        King k = new King(p.getColor(),p.getPosition());
                        board.setPosition(k,k.getPosition());
                    } else if (p instanceof Knight) {
                        Knight n = new Knight(p.getColor(),p.getPosition());
                        board.setPosition(n,n.getPosition());
                    } else if (p instanceof Pawn) {
                        Pawn pa = new Pawn(p.getColor(),p.getPosition());
                        board.setPosition(pa,pa.getPosition());
                    } else if (p instanceof Queen) {
                        Queen q = new Queen(p.getColor(),p.getPosition());
                        board.setPosition(q,q.getPosition());
                    } else if (p instanceof Rook) {                        
                        Rook r = new Rook(p.getColor(),p.getPosition());
                        board.setPosition(r,r.getPosition());
                    }
                } else {
                    board.setPosition(null,new Position(i,j));
                }
            }
        }
        return board; 
    }
    
    /* 
       Retorna a pe�a em uma determinada posicao
     */
    public Piece getPieceAtPosition(Position pos){
        return this.board[pos.getX()][pos.getY()];
    }
}
