/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * @author DemetriosLiousas
 */
public class Card {
    private String msg;
    private int value;
    private int bIndex;
    public Card(String msg, int value) {
        this.msg = msg;
        this.value = value;
    }
    public Card(String msg, int value, int bIndex){
        this.msg = msg;
        this.value = value;
        this.bIndex = bIndex;
    }
    public String getMsg() {
        return msg;
    }

    public int getValue() {
        return value;
    }
    public int getIndex(){
        return this.bIndex;
    }
    @Override
    public String toString(){
        return "Message: " + msg + ", Value:  " + value;
    }
    
}
