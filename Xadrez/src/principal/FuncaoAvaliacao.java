/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;

/**Essa classe implementa a funcao de avaliacao do tabuleiro estático, ela será utilizada
 * para calcular a heurística de cada um dos nós da árvore.
 * @author Anne
 */
public class FuncaoAvaliacao
{
    // Valor de cada tipo de peca
    int[] valorPeca = new int[6];
    
    // Quantidade de movimentos realizados pelas pecas
    int [] qtd_movimentos = new int[6];
    
    // Quantidade de pecas do adversario que um tipo de peca pode capturar em um dado momento do jogo
    int[] qtd_pecas_ataques = new int[6];
    
    // Quantidade de pecas que estão protegendo uma dada peca em um dado momento do jogo para evitar que esta 
    // peca seja capturada 
    int[] qtd_pecas_defesa = new int[7];
	
    // Progresso do peao no tabuleiro; usado para verificar até que ponto no tabuleiro o peao já andou
    int progressoPeao;
	  
   
    public FuncaoAvaliacao(int id) {}
	
    // Cria uma função de avaliação com a passagem de parâmetros e atribui os pesos aos tipos de peca
    public FuncaoAvaliacao(int progPeao, int[] qtd_mov, int[] qtd_pecas_ataq, int[] qtd_pecas_def)
    {
		
        progressoPeao = progPeao;
	qtd_movimentos = qtd_pecas_ataq;
	qtd_pecas_ataques = qtd_pecas_def;
	qtd_pecas_defesa = qtd_pecas_def;
        
        // Atribuindo pesos aos tipos de pecas
	atribuirPeso();		
    }
    
    // Gera um array inteligente e aleatório com a função de avaliaçao; n é o número de vetores que serão gerados
    public static FuncaoAvaliacao[] generate(int n)
    {
        FuncaoAvaliacao[] funcAval = new FuncaoAvaliacao[n];
	for(int i=0;i<n;i++)
        {
            funcAval[i] = new FuncaoAvaliacao();
	    funcAval[i].progressoPeao = (int)(Math.random()*5.0);
            // Cada parâmetro pode receber valores entre 0 e 5
            for(int j=0;j<6;j++)
            {
		funcAval[i].qtd_movimentos[j] = (int)(Math.random()*5.0);
		funcAval[i].qtd_pecas_ataques[j] = (int)(Math.random()*5.0);
		funcAval[i].qtd_pecas_defesa[j] = (int)(Math.random()*5.0);
	    }
	}
	return funcAval;
    }
    
    // Cria uma funcao de avaliacao padrao com valores pré-definidos para os parâmetros 
    public FuncaoAvaliacao()
    {
        for(int i=0;i<6;i++)
        {
            qtd_movimentos[i] = 0;
            qtd_pecas_ataques[i] = 0;
            qtd_pecas_defesa[i] = 0;
	}
        
        progressoPeao = 1;
	qtd_movimentos[1] = 0;
	qtd_pecas_ataques[1] = 0;
	qtd_pecas_defesa[1] = 0;
		pieceMobility[2] = 0;
		pieceThreats[2] = 1;
		pieceProtects[2] = 1;
		pieceMobility[3] = 0;
		pieceThreats[3] = 1;
		pieceProtects[3] = 1;
		pieceMobility[4] = 0;
		pieceThreats[4] = 2;
		pieceProtects[4] = 0;
		pieceMobility[5] = 1;
		pieceThreats[5] = 5;
		pieceProtects[5] = 0;
		pieceMobility[6] = 0;
		pieceThreats[6] = 4;
		pieceProtects[6] = 0;
		initOwn();
	}
	


    
    
    
    private void atribuirPeso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
    
    
}
