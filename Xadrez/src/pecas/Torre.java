
package pecas;

/**
 *
 * @author Anne
 */
public class Torre extends APeca{
    
       //A torre Possui valor absoluto = 5 * valor do Peao = 5
    public Torre(String cor, Posicao posicaoAtual) {
        super(5,'T', cor, posicaoAtual);
    }
    
}
