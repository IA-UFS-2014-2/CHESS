package principal;

import pecas.APeca;
import pecas.Posicao;

/**
*
* @author Paulo Henrique
*/
public class Movimento 
{
	//Pontuação calculado para o movimento a patir da função de avaliação.
	private int pontuacao;
	
	//Posições inicial e final do movimento.
	private Posicao posicaoInicial;
	private Posicao posicaoFinal;
	
	//Peça utilizada no movimento.
	private APeca peca;

	//Peça capturada no movimento.
	private APeca pecaCapturada;
	
	/// <summary>
    /// Tipos de Movimentos.
    /// </summary>
    public enum TiposDeMovimento
    {
        /// <summary>
        /// Movimento Padrão.
        /// </summary>
        Padrao, 

        /// <summary>
        /// Roque longo.
        /// </summary>
        RoqueLongo, 

        /// <summary>
        /// Roque curto.
        /// </summary>
        RoqueCurto, 

        /// <summary>
        /// Peão promovido a Rainha.
        /// </summary>
        PeaoParaRainha, 

        /// <summary>
        /// Peão promovido a Torre.
        /// </summary>
        PeaoParaTorre, 

        /// <summary>
        /// Peão promovido a Cavalo.
        /// </summary>
        PeaoParaCavalo, 

        /// <summary>
        /// Peão promovido a Bispo.
        /// </summary>
        PeaoParaBispo, 

        /// <summary>
        /// Movimento En passent.
        /// </summary>
        EnPassent, 

        /// <summary>
        /// Movimento Nulo.
        /// </summary>
        MovimentoNulo
    }
}