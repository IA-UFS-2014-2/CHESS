/*
 * Player.java
 *
 * Created on 15 de Novembro de 2006, 10:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package VerificarUtilidadesDessasClasses;

import java.awt.Color;
import model.utils.Time;

/**
 *
 * @author Marcelo Gonzaga
 */
public class Player {
    
    /* Player's name */
    public String name;
    
    /* Player's color */
    public Color color;
    
    public Time time;
    
    /** Creates a new instance of Player */
    public Player() {
        this("Player",null,new Time(0,0,0));
    }
    
    /** Creates a new instance of Player */
    public Player( String name,Color color , Time time) {
        this.setName(name);
        this.setColor(color);
        this.setTime(time);
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public Color getColor() {
        return color;
    }
    
    public void contaTempo(){
        this.time.decSecond();
    }
    
    public String getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
