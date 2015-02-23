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
public class Rei extends APeca{
        //O rei nao possui valor definido em vista que nao pode ser capturado !
        // O rei considerando apenas sua capacidade de batalha, é de cerca de 4 pontos
    private final short apenasCapacidadeDeBatalha;
    public Rei(String cor, Posicao posicaoAtual) {
        super(999999,'R', cor, posicaoAtual);
        this.apenasCapacidadeDeBatalha = 4;
    }

    public short getCapacidadeDeBatalha() {
        return apenasCapacidadeDeBatalha;
    }
    
    
    
}
