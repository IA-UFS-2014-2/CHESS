/*
 * Position.java
 *
 * Created on October 12, 2006, 10:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VerificarUtilidadesDessasClasses;

public class Position {
    
    /*
     Class Atributes 
     */
    int x;
    int y;
    
     public Position() {
        this(0,0);
    }
             
    /**
     * Creates a new instance of Position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public boolean equals (Position pos) {
        return (this.getX() == pos.getX()) && (this.getY() == pos.getY());
    }
    
    public String toString() {
       return this.getX()+" - "+ this.getY();
    }
}
