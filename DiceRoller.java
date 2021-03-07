/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * @author 17812
 */
public class DiceRoller {
    private int d1;
    private int d2;
    private boolean doubles;
    public DiceRoller(){
        d1 = (int) (Math.random() * 6) + 1;
        d2 = (int) (Math.random() * 6) + 1;
        
        doubles = d1 == d2;
    }
    public String printRoll(){
        String o = "";
        if(doubles){
            o+=" rolled two " + d1 + "'s.";
        }else{
            o+=" rolled a " + d1 + " and a " + d2+".";
        }
        return o;
    }
    public int getd1(){
        return d1;
    }
    public int getd2(){
        return d2;
    }
    public boolean getDoubles(){
        return doubles;
    }
    public int getTotal(){
        return d1+d2;
    }
}
