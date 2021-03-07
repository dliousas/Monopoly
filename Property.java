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
public class Property {

    private String name;
    private String color;
    private int housePrice;
    private int mortgage;
    private int price;
    private boolean houses;//can have houses
    private int numHouses;
    private boolean isMortgaged;
    private boolean isOwned;
    private boolean isMonopoly;
    private int rent;
    private boolean special;
    private Player owner;
    private boolean buyable;
    private static int counter = 0;
    private int bIndex;

    private int[][] rentGuide = {
        {10, 20, 30, 30, 40, 50, 50, 60, 70, 70, 80, 90, 90, 100, 110, 110, 120, 130, 130, 150, 175, 200},
        {30, 60, 90, 90, 100, 150, 150, 180, 200, 200, 220, 250, 250, 300, 330, 330, 360, 390, 390, 450, 500, 600},
        {90, 180, 270, 270, 300, 450, 450, 500, 550, 550, 600, 700, 700, 750, 800, 800, 850, 900, 900, 1000, 1100, 1400},
        {160, 320, 400, 400, 450, 625, 625, 700, 750, 750, 800, 875, 875, 925, 975, 975, 1025, 1100, 1100, 1200, 1300, 1700},
        {250, 450, 550, 550, 600, 750, 750, 900, 950, 950, 1000, 1050, 1050, 1100, 1150, 1150, 1200, 1275, 1275, 1400, 1500, 2000}};

    public Property(String name, String color, int housePrice, int price, int rent) {
        this.name = name;
        this.color = color;
        this.housePrice = housePrice;
        this.mortgage = price / 2;
        this.price = price;
        this.rent = rent;
        this.owner = null;
        this.houses = !color.equals("RR");
        this.numHouses = 0;
        this.isMortgaged = false;
        this.isOwned = false;
        this.special = false;
        this.buyable = true;
        this.isMonopoly = false;
        if (houses) {
            bIndex = counter;
            counter++;
        }

    }

    public boolean isIsMonopoly() {
        return isMonopoly;
    }

    public void setIsMonopoly(boolean isMonopoly) {
        this.isMonopoly = isMonopoly;
    }

    public Property(String name) {
        this.name = name;
        this.isOwned = false;
        this.special = true;
        this.buyable = false;
        this.houses = false;

    }

    public void addHouse() {
        this.numHouses++;
    }

    public boolean isBuyable() {
        return this.buyable;
    }

    public void setIsMortaged(boolean isMortaged) {
        this.isMortgaged = isMortaged;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getRent() {
        if (numHouses == 0) {
            if (this.isMonopoly) {
                return rent * 2;
            }
            return rent;
        }
        int c = -1;
        //get board from oplayuer from manager
        Property[] board = owner.getManager().getBoard().getBoard();
        for (Property p : board) {
            if (!p.equals(this)) {
                c++;
            } else {
                break;
            }
        }
        return rentGuide[numHouses - 1][c];
    }

    public int getHousePrice() {
        return housePrice;
    }

    public boolean isSpecial() {
        return special;
    }

    public int getMortgage() {
        return mortgage;
    }

    public int getPrice() {
        return price;
    }

    public int getNumHouses() {
        return numHouses;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public Player getOwner() {
        return owner;
    }

    public void setIsOwned(boolean isOwned, Player p) {
        this.isOwned = isOwned;
        this.owner = p;
    }

    public boolean isMortgaged() {
        return this.isMortgaged;
    }
}
