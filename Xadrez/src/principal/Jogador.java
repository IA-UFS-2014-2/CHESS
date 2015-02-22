/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;


/**
 * Essa classe implementa um jogador com as suas respectivas características.
 * @author Anne
 */
public class Jogador 
{    
    // Funcao de avaliacao usada pelo jogador
    FuncaoAvaliacao funcAval;
    
    // Profundidade da arvore
    int ply;
	
    // A profundidade que será utilizada pesquisa Quiescente
    int deepPly;
	
    // Pontuacao da pesquisa quiescence 
    double deepScore;
	
    // Numero de posições não estáveis que devem ser expandidas até um nó estável
    // ser atingido
    int deepLimit;
    
    // Cria uma nova instância do jogador com os dados parametros
    public Jogador(FuncaoAvaliacao s, int p, int dp, double ds, int dl) {
	funcAval = s;
	ply = p;
	deepPly = dp;
	deepScore = ds;
	deepLimit = dl;
    }
	
    
    
    
    
    
}
