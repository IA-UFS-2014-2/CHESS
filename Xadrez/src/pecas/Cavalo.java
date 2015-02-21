/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pecas;

/**
 *
 * @author Anne
 */
public class Cavalo extends APeca
{
    //public movimento
    
    //O Cavalo Possui valor absoluto = 3 * valor do Peao = 3
    public Cavalo(String cor, Posicao posicaoAtual) {
        super(3,'C', cor, posicaoAtual);
    }
    
}
