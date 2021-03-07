/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Point;

/**
 *
 * @author DemetriosLiousas
 */
public class Board {

    private Property[] board;
    private Point[] locations;

    public Board() {

        board = new Property[40];
        board[0] = new Property("GO");
        board[1] = new Property("Mediterranean Avenue", "Brown", 50, 60, 2);
        board[2] = new Property("Community Chest");
        board[3] = new Property("Baltic Avenue", "Brown", 50, 60, 4);
        board[4] = new Property("Income tax");
        board[5] = new Property("Reading Railroad", "RR", -1, 200, 25);
        board[6] = new Property("Oriental Avenue", "Light Blue", 50, 100, 6);
        board[7] = new Property("Chance");
        board[8] = new Property("Vermont Avenue", "Light Blue", 50, 100, 6);
        board[9] = new Property("Connecticut Avenue", "Light Blue", 50, 120, 8);
        board[10] = new Property("Jail");
        board[11] = new Property("St. Charles Place", "Pink", 100, 140, 10);
        board[12] = new Property("Electric Company", "Utility", -1, 150, -1);
        board[13] = new Property("States Avenue", "Pink", 100, 140, 10);
        board[14] = new Property("Virginia Avenue", "Pink", 100, 160, 12);
        board[15] = new Property("Pennsylvania Railroad", "RR", -1, 200, 25);
        board[16] = new Property("St. James Place", "Orange", 100, 180, 14);
        board[17] = new Property("Community Chest");
        board[18] = new Property("Tennessee Avenue", "Orange", 100, 180, 14);
        board[19] = new Property("New York Avenue", "Orange", 100, 200, 16);
        board[20] = new Property("Free Parking");
        board[21] = new Property("Kentucky Avenue", "Red", 150, 220, 18);
        board[22] = new Property("Chance");
        board[23] = new Property("Indiana Avenue", "Red", 150, 220, 18);
        board[24] = new Property("Illinois Avenue", "Red", 150, 240, 20);
        board[25] = new Property("B&O Railroad", "RR", -1, 200, 25);
        board[26] = new Property("Atlantic Avenue", "Yellow", 150, 260, 22);
        board[27] = new Property("Ventnor Avenue", "Yellow", 150, 260, 22);
        board[28] = new Property("Water Works", "Utility", -1, 150, -1);
        board[29] = new Property("Marvin Gardens", "Yellow", 150, 280, 24);
        board[30] = new Property("Go To Jail");
        board[31] = new Property("Pacific Avenue", "Green", 200, 300, 26);
        board[32] = new Property("North Carolina Avenue", "Green", 200, 300, 26);
        board[33] = new Property("Community Chest");
        board[34] = new Property("Pennsylvania Avenue", "Green", 200, 320, 28);
        board[35] = new Property("Short Railroad", "RR", -1, 200, 25);
        board[36] = new Property("Chance");
        board[37] = new Property("Park Place", "Dark Blue", 200, 350, 35);
        board[38] = new Property("Luxury Tax");
        board[39] = new Property("Boardwalk", "Dark Blue", 200, 400, 50);

        locations = new Point[40];
        locations[0] = new Point(744, 735);
        locations[1] = new Point(642, 735);
        locations[2] = new Point(576, 735);
        locations[3] = new Point(510, 735);
        locations[4] = new Point(444, 735);
        locations[5] = new Point(378, 735);
        locations[6] = new Point(312, 735);
        locations[7] = new Point(246, 735);
        locations[8] = new Point(180, 735);
        locations[9] = new Point(114, 735);
        locations[10] = new Point(12, 735);
        locations[11] = new Point(12, 633);
        locations[12] = new Point(12, 567);
        locations[13] = new Point(12, 501);
        locations[14] = new Point(12, 435);
        locations[15] = new Point(12, 369);
        locations[16] = new Point(12, 303);
        locations[17] = new Point(12, 237);
        locations[18] = new Point(12, 171);
        locations[19] = new Point(12, 105);
        locations[20] = new Point(12, 3);
        locations[21] = new Point(114, 3);
        locations[22] = new Point(180, 3);
        locations[23] = new Point(246, 3);
        locations[24] = new Point(312, 3);
        locations[25] = new Point(378, 3);
        locations[26] = new Point(444, 3);
        locations[27] = new Point(510, 3);
        locations[28] = new Point(576, 3);
        locations[29] = new Point(642, 3);
        locations[30] = new Point(744, 3);
        locations[31] = new Point(744, 105);
        locations[32] = new Point(744, 171);
        locations[33] = new Point(744, 237);
        locations[34] = new Point(744, 303);
        locations[35] = new Point(744, 369);
        locations[36] = new Point(744, 435);
        locations[37] = new Point(744, 501);
        locations[38] = new Point(744, 567);
        locations[39] = new Point(744, 633);
    }

    public Property getProperty(int index) {
        return board[index];
    }

    public int getPropertyIndex(Property p) {
        for (int i = 0; i < board.length; i++) {
            if (board[i].equals(p)) {
                return i;
            }
        }
        return -1;
    }

    public Property[] getBoard() {
        return board;
    }

    public Point getPoint(int index) {
        return locations[index];
    }

    public Point getNext(int i) {
        int index = i + 1;
        if (i == 39) {
            index = 0;
        }
        return locations[index];
    }

    public Point getPrev(int i) {
        int index = i - 1;
        if (index == -1) {
            index = 39;
        }
        return locations[index];
    }

    public Property[] available() {
        int num = 0;
        for (Property x : board) {
            if (!x.isOwned()) {
                num++;
            }
        }
        Property[] out = new Property[num];
        int c = 0;
        for (Property x : board) {
            if (!x.isOwned()) {
                out[c] = x;
                c++;
            }
        }
        return out;
    }

    public void setColorMonopoly(String color, boolean b) {
        for (int i = 0; i < board.length; i++) {
            Property p = board[i];
            if (b && !p.isSpecial()) {
                if (p.getColor().equals(color)) {
                    board[i].setIsMonopoly(true);
                }
            }
        }
    }
}
