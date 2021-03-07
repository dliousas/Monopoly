/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import javax.swing.JButton;

/**
 *
 * @author DemetriosLiousas
 */
public class MonopolyButton extends JButton{
    private Property property;
    public MonopolyButton(){
        super();
        this.property = null;
    }
    public void setProperty(Property p){
        this.property = p;
        String txt = p.getName();
        if(txt.contains(" ")){
            txt = "<html><center>"+txt.substring(0, txt.indexOf(" ")) + "<br>" + txt.substring(txt.indexOf(" ") + 1)+"</center></html>";
        }
        this.setText(txt);
    }
    public Property getProperty(){
        return property;
    }
    public void removeProperty(){
        this.property = null;
        this.setText("");
    }
}
