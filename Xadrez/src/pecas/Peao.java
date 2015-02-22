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
public class Peao extends APeca
{
    
    //O peao Possui valor absoluto = 1
    public Peao(String cor, Posicao posicaoAtual) {
        super(1,'P', cor, posicaoAtual);
    }
	
}
