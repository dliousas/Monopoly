/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author 17812
 */
public class MonopolyPiece extends JLabel {

    public MonopolyPiece(String fileName) {
        super();
        ImageIcon img = new ImageIcon(getClass().getResource("/" + fileName + ".jpg"));
        setIcon(img);
    }

}
