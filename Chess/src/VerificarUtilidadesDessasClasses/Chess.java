/*
 * Chess.java
 *
 * Created on October 12, 2006, 1:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VerificarUtilidadesDessasClasses;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import model.Pecas.*;
import model.utils.Time;

/**
 *
 * @author newen
 */
public class Chess {
    
    /* Board do Jogo */
    Board board;
    
    /* Pe�as mortas */
    ArrayList <Piece>deadpieces;
    
    /* Players */
    Player player1;
    
    /* Players */
    Player player2;
    
    /* Player */
    boolean firstPlayersTime;
    
    /* Game is over*/
    boolean gameover;
    
    /** Cria uma nova inst�ncia de Chess */
    public Chess() {
        
        //Inicia Jogo
        // this.startGame();
    }
    
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public boolean isGameover() {
        return gameover;
    }
    
    public boolean isFirstPlayersTime() {
        return firstPlayersTime;
    }
    
    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }
    
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    
    public ArrayList getDeadpieces() {
        return deadpieces;
    }    
    
    public void setFirstPlayersTime(boolean firstPlayersTime) {
        this.firstPlayersTime = firstPlayersTime;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    /** Inicia um novo Jogo */
    public void startGame() {
        
        // Cria tabuleiro
        this.board = new Board();
        
        // Cria jogadores
        this.createPlayers();
        
        // seta a vez do jogador 1 (pecas brancas)
        this.setFirstPlayersTime(true);
        
        this.setGameover(false);
        
        // Cria lista de pe�as mortas
        this.deadpieces = new ArrayList <Piece>();
        this.deadpieces.clear();
        
        // Limpa todas as posi��es do board
        this.board.clearBoard();
        
        // Inicializa as pe�as
        this.prepareBoard();
    }
    
    
    /**
     * Prepara o tabuleiro com a posi��o inicial das pe�as
     */
    private void prepareBoard(){
        
        //cria pe�es
        for (int i=0;i<8;i++){
            // Cria um pe�o preto e o coloca em uma posi��o, o pe�o tbm recebe a sua posi��o no board
            this.board.setPosition( new Pawn(Color.black, new Position(1,i) ) , new Position(1,i) );
            
            // Cria um pe�o brancos e o coloca em uma posi��o, o pe�o tbm recebe a sua posi��o no board
            this.board.setPosition( new Pawn(Color.white, new Position(6,i) ) , new Position(6,i) );
        }
        // Cria as torres e o coloca em uma posi��o, a torre tbm recebe a sua posi��o no board
        this.board.setPosition( new Rook(Color.black, new Position(0,0) ) , new Position(0,0) );
        this.board.setPosition( new Rook(Color.black, new Position(0,7) ) , new Position(0,7) );
        
        this.board.setPosition( new Rook(Color.white, new Position(7,0) ) , new Position(7,0) );
        this.board.setPosition( new Rook(Color.white, new Position(7,7) ) , new Position(7,7) );
        
        // Cria os cavalos e o coloca em uma posi��o, o cavalo tbm recebe a sua posi��o no board
        this.board.setPosition( new Knight(Color.black, new Position(0,1) ) , new Position(0,1) );
        this.board.setPosition( new Knight(Color.black, new Position(0,6) ) , new Position(0,6) );
        
        this.board.setPosition( new Knight(Color.white, new Position(7,1) ) , new Position(7,1) );
        this.board.setPosition( new Knight(Color.white, new Position(7,6) ) , new Position(7,6) );
        
        // Cria os bispos e o coloca em uma posi��o, o bispo tbm recebe a sua posi��o no board
        this.board.setPosition( new Bishop(Color.black, new Position(0,2) ) , new Position(0,2) );
        this.board.setPosition( new Bishop(Color.black, new Position(0,5) ) , new Position(0,5) );
        
        this.board.setPosition( new Bishop(Color.white, new Position(7,2) ) , new Position(7,2) );
        this.board.setPosition( new Bishop(Color.white, new Position(7,5) ) , new Position(7,5) );
        
        
        // Cria as Rainhas e as coloca em uma posi��o, a rainha tbm recebe a sua posi��o no board
        this.board.setPosition( new Queen(Color.black, new Position(0,3) ) , new Position(0,3) );
        this.board.setPosition( new Queen(Color.white, new Position(7,3) ) , new Position(7,3) );
        
        // Cria os Reis e os coloca em uma posi��o, o Rei tbm recebe a sua posi��o no board
        this.board.setPosition( new King(Color.black, new Position(0,4) ) , new Position(0,4) );
        this.board.setPosition( new King(Color.white, new Position(7,4) ) , new Position(7,4) );
        
    }
    
    /**
     * Valida e realiza a jogada
     */
    public boolean play(Position origem, Position destino){
        
        
        Player player = this.isFirstPlayersTime() ? this.player1 : this.player2;
        Player enemy = this.isFirstPlayersTime() ? this.player2: this.player1;
        Piece peca1 = this.board.getPieceAtPosition(origem);
        ArrayList <Position> path;
        
        if (!(peca1.getColor() == player.getColor()))  {
            return false;
        }
        
        if ( (this.board.getPieceAtPosition(origem) instanceof Pawn) ||
                (this.board.getPieceAtPosition(origem) instanceof King)) {
            /* Pawn � um caso em que deve-se passar o tabuleiro como referencia, pois
             * existe 2 tipos de movimento, 1 em vertical, outro em diagonal, sendo que,
             * para ser possivel fazer um movimento em diagonal, � obrigatorio que exista
             * outra peca (de outra cor) na posicao destino */
             /* se for rei, deve passar o chess como referencia, pois o roque � um movimento
              * diferente dos demais */
            path = this.getBoard().getPieceAtPosition(origem).getPath(destino,this);
        } else {
            /* demais pecas */
            path = this.board.getPieceAtPosition(origem).getPath(destino);
        }
        
        /* nao conseguiu fazer o movimento */
        if (path == null) {
            return false;
        }
        
        /* verifica se fez o roque */
        if (path.get(0).equals(new Position(99,99))) {
            this.setFirstPlayersTime(!this.isFirstPlayersTime());
            return true;
        }
        
        /* verifica se � possivel passar pelas casas necessarias a jogada */
        if (!this.isValidPath(path,this.board)) return false;
        
        /* verifica se a jogada faz com que o jogador da vez se deixe em xeque */
        if (!this.canMove(player.getColor(),origem,destino)){
            JOptionPane.showMessageDialog(null,"Voce esta em Xeque!","Xeque",0 );
            return false;
        } else {
            /* Verifica promocao do peao */
            if (peca1 instanceof Pawn){
                if( ((player.getColor() == Color.WHITE) && (destino.getX() == 0)) ||
                        ((player.getColor() == Color.BLACK) && (destino.getX() == 7))){
                    this.setTroca(peca1);
                }
            }
            this.moviment(origem,destino,this.board,true);
            if (this.isInCheckMate(enemy.getColor())) {
                JOptionPane.showMessageDialog(null,player.getName()+" venceu o jogo","Xeque-Mate!",0 );
                this.setGameover(true);
                return true;
            }
        }
        
        
        this.setFirstPlayersTime(!this.isFirstPlayersTime());
        return true;
    }
    
    
    public Board pseudoMoviment(Position origem, Position destino) {
        
        /* cria uma copia do tabuleiro original do jogo */
        Board cloneBoard = this.getBoard().getBoardClone();
        
        /* - faz a jogada do jogador, passando com o referencia o tabuleiro
         * copia, e nao a tabuleiro original
         */
        if (this.moviment(origem,destino,cloneBoard,false)) {}
        
        /* retorna o tabuleiro copia apos efetuada a jogada */
        return cloneBoard;
    }
    
    /* recebe um tabuleiro (o original ou a copia) e tenta fazer a jogada,
       retornando true caso seja efetuada com sucesso
     */
    public boolean moviment(Position origem, Position destino, Board board, boolean real) {
        
        Piece peca1 = board.getPieceAtPosition(origem);
        
        // Se ponto de destino est� preenchido
        if (!board.isNullPosition(destino)){
            
            Piece peca2 = board.getPieceAtPosition(destino);
            
            if(peca1.getColor() != peca2.getColor()){
                // salva a pe�a destino na lista de pe�as mortas
                if (real){
                    this.deadpieces.add(board.getPieceAtPosition(destino));
                }
                
                // move a pe�a origem para a destino (come a peca)
                board.movePiece(origem,destino);
                peca1.setPosition(destino);
                return true;
            }
        }else{
            // move a pe�a origem para a destino (come a peca)
            board.movePiece(origem,destino);
            peca1.setPosition(destino);
            return true; // movimentou
        }
        // nao movimentou
        return false;
    }
    
    public boolean isAtackedByBlack(Position pos, Board board) {
        
        ArrayList <Position> path; // aux
        Piece p; // aux
        // para cada peca do tabuleiro, verifica se existe caminho valido entre as pecas
        // adversarias e a peca informada
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                path = null;
                if ((p = board.getPieceAtPosition(new Position(i,j))) != null &&
                        (p.getColor() == Color.BLACK)) {
                    // pega o caminho entre a peca adversaria e o rei do jogador
                    if (board.getPieceAtPosition(p.getPosition()) instanceof Pawn) {
                        path = p.getPath(pos,board);
                    } else {
                        path = p.getPath(pos);
                    }
                }
                if (this.isValidPath(path,board)) {
                    // � atingivel
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isAtackedByWhite(Position pos, Board board) {
        
        ArrayList <Position> path; // aux
        Piece p; // aux
        // para cada peca do tabuleiro, verifica se existe caminho valido entre as pecas
        // adversarias e a peca informada
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                path = null;
                if ((p = board.getPieceAtPosition(new Position(i,j))) != null &&
                        (p.getColor() == Color.WHITE)) {
                    // pega o caminho entre a peca adversaria e o rei do jogador
                    if (board.getPieceAtPosition(p.getPosition()) instanceof Pawn) {
                        path = p.getPath(pos,board);
                    } else {
                        path = p.getPath(pos);
                    }
                }
                if (this.isValidPath(path,board)) {
                    // � atingivel
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isInCheckMate(Color playerColor) {
        /* retona true se o jogador informado esta na condicao de xeque-mate */
        return !this.canGetOutOfTheCheck(playerColor);
    }
    
    public boolean canGetOutOfTheCheck(Color playerColor) {
        /* retorna true e o jogador consegue ficar em uma posicao cujo
         * rei esteja fora de xeque */
        
        Piece p;
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                /* pega cada peca do jogador adversario */
                if ( ( (p = (Piece) this.board.getPieceAtPosition(new Position(i,j))) != null) &&
                        (p.getColor() == playerColor) ) {
                    /* e tenta fazer com que o adversario consiga sair da posicao de xeque */
                    if (!this.tryAllPossibleMoviments(p)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /* recebe uma peca, e tenta fazer todos os movimentos possiveis desta
     * pe�a pelo tabuleiro, verificando se consegue ficar em alguma situacao
     * em que nao esteja em xeque */
    public boolean tryAllPossibleMoviments(Piece p) {
        
        Board backupBoard = this.getBoard(); // backup do tabuleiro
        ArrayList <Position> path = null;       
        Board aux = this.getBoard().getBoardClone();
        
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                this.setBoard(aux.getBoardClone());
                if (p instanceof King || p instanceof Pawn) {
                    // faz verificacao com o tabuleiro copia
                    path = p.getPath(new Position(i,j),this);
                } else {
                    /* demais pecas */
                    path = p.getPath(new Position(i,j));
                }
                if (this.isValidPath(path,this.board)) {
                    this.moviment(p.getPosition(),path.get(path.size()-1),this.board,false);
                    if (!this.isCheck(p.getColor(),this.board)) {
                        this.setBoard(backupBoard);
                        return false;
                    }
                }
                this.setBoard(aux);
            }
        }
        this.setBoard(backupBoard);
        return true;
    }
    
    public King getKing(Color playerColor, Board board) {
        
        Piece p; // aux
        Piece king = null; // rei do jogador da Vez
        
        // percorre o tabuleiro em busca do seu rei
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if ((p = board.getPieceAtPosition(new Position(i,j))) instanceof King) {
                    if (p.getColor() == playerColor) {
                        // achou o seu rei
                        king = p;
                        break;
                    }
                }
            }
        }
        return (King) king;
    }
    
    /* verifica se em um dado tabuleiro o jogador da vez esta em xeque */
    public boolean isCheck(Color playerColor, Board board) {
        
        King myKing = this.getKing(playerColor,board);
        
        if (playerColor == Color.WHITE) {
            return this.isAtackedByBlack(myKing.getPosition(),board);
        } else {
            return this.isAtackedByWhite(myKing.getPosition(),board);
        }
    }
    
    
    public boolean canMove(Color playerColor,Position origem, Position destino) {
        
        /* faz a jogada em um tabuleiro auxiliar, e entao passa a
         verificar as condicoes com base neste */
        Board board = this.pseudoMoviment(origem,destino);
        return !this.isCheck(playerColor,board);
        
    }
    
    
    /* verifica se caminho entre a peca atual e o destino nao esta ocupado
           (por onde a peca tera de percorrer), a ultima posicao do path
           � o proprio destino, que sea verificado posteriormente, logo, verifica-se
           o caminho entre as posicoes 0 e n-1 do path
     */
    public boolean isValidPath(ArrayList <Position> path, Board board) {
        
        if (path == null) return false;
        Piece pAux;
        for (int i=0; i<path.size()-1; i++) {
            if (board.getPieceAtPosition(path.get(i))!= null) {
                return false; // n � possivel efetuar a jogada
            }
        }
        return true;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    private void createPlayers() {
        this.player1 = new Player(JOptionPane.showInputDialog(null,"Informe o nome do Jogador 1","Xadrez",JOptionPane.QUESTION_MESSAGE),Color.WHITE, new Time(0,15,0));
        this.player2 = new Player(JOptionPane.showInputDialog(null,"Informe o nome do Jogador 2","Xadrez",JOptionPane.QUESTION_MESSAGE),Color.BLACK, new Time(0,15,0));
    }
    
    public void setTroca(Piece piece) {
        
        
        Object[] possibilities = {"Rainha","Torre","Bispo","Cavalo"};
        String name = (String)JOptionPane.showInputDialog(
                null,
                "Escolha uma pe�a",
                "Promoc�o do pe�o",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "ham");
        
        if (name.equalsIgnoreCase("Rainha")){
            this.board.setPosition(new Queen(piece.getColor(), piece.getPosition()), piece.getPosition());
            
        }else if(name.equalsIgnoreCase("Torre")){
            this.board.setPosition(new Rook(piece.getColor(), piece.getPosition()),piece.getPosition());
            
        }else if(name.equalsIgnoreCase("Bispo")){
            this.board.setPosition(new Bishop(piece.getColor(), piece.getPosition()),piece.getPosition());
        }else if(name.equalsIgnoreCase("Cavalo")){
            this.board.setPosition(new Knight(piece.getColor(), piece.getPosition()),piece.getPosition());
        }
        
    }
}
