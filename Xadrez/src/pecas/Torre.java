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
public class Torre extends APeca{
    
       //A torre Possui valor absoluto = 5 * valor do Peao = 5
    public Torre(String nome, String cor, Posicao posicaoAtual) {
        super(5,nome, cor, posicaoAtual);
    }
    
}
