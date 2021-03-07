/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author DemetriosLiousas
 */
public class Player implements Runnable, Payable {

    private JLabel lab;
    private static int numPlayers = 0;
    private static final Color[] color = {Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};
    private int bIndex;
    private int balance;
    private ArrayList<Property> properties;
    private Board board;
    private Bank bank;
    private GameManager manager;
    private String name;
    private int debt;
    private boolean hasGOOJF;//get out of jail free card
    private int turnsInJail;//0 = not in jail, > 0 means # turn in jail. if 3 then fiorce pay
    private String[] markers = {"P1", "P2", "P3", "P4"};
    private JTextArea log;
    private boolean actionCompleted;//if the players turn shoudl be over after trading or mortagaging, or can they buy a property

    public Player(MonopolyPiece lab, String name, JLabel brd, GameManager manager, JTextArea log, Bank bank, Board board) {
        this.board = board;
        this.bank = bank;
        this.lab = lab;
        lab.setLayout(null);
        this.name = name;
        this.manager = manager;
        lab.setSize(60, 60);
        lab.setVisible(true);

        lab.setLocation(manager.getBoard().getPoint(0).x, manager.getBoard().getPoint(0).y);
        numPlayers++;
        bIndex = 0;
        this.balance = 1500;
        properties = new ArrayList<>();
        brd.add(lab);

        hasGOOJF = false;
        this.log = log;
        this.turnsInJail = 0;
        actionCompleted = false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Player getPlayer() {
        return this;
    }

    public JLabel getLab() {
        return this.lab;
    }

    public void increment() {
        bIndex++;
        if (bIndex == 40) {
            bIndex = 0;
        }
    }

    public void decrement() {
        bIndex--;
        if (bIndex == -1) {
            bIndex = 39;
        }
    }

    public int getIndex() {
        return this.bIndex;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBalance(int i) {
        this.balance = i;
    }

    public int inJail() {
        return this.turnsInJail;
    }

    /**
     *
     * @param b b = 0, not in jail, b = 1 then 1st in jail, b = 2, second turn
     * in jail, b = 3, third i jail(after 3 must pay 50)
     */
    public void setInJail(int b) {
        this.turnsInJail = b;
    }

    public void addProperty(Property p) {
        properties.add(p);
    }

    public void removeProperty(Property p) {
        properties.remove(properties.indexOf(p));
    }

    public void buyProperty(int index) {

        boolean exceptionThrown = false;
        try {
            pay(board.getProperty(bIndex).getPrice(), bank);
            exceptionThrown = false;
        } catch (BankruptException e) {
            System.out.println("I threw an exception for not enough in buyPreopery");
            exceptionThrown = true;

        }
        actionCompleted = !exceptionThrown;
        if (!exceptionThrown) {
            properties.add(board.getProperty(index));
            board.getProperty(index).setIsOwned(true, this);
        }
        manager.makeMonopoly(this, board.getProperty(index).getColor());
        if (properties.size() < (28 / (manager.getGame().getNumPlayers() + 2))) {
            return;
        }
        Property[] p = board.available();
        if (p.length > 5) {
            return;
        }
        String o = "";
        for (int i = 0; i < p.length; i++) {
            o += p[i].getName();
            if (i != p.length - 1) {
                o += ", ";
            }
        }
        manager.printLog("Properties remaining: " + o);
    }

    public String printProperties() {
        String out = "";
        Property[] props = board.getBoard();
        for (Property p : props) {
            if (!p.isSpecial() && p.isOwned() && p.getOwner().equals(this)) {
                if (p.isMortgaged()) {
                    out += p.getName() + "(Mort), ";
                } else if (p.getNumHouses() > 0) {
                    out += p.getName() + "(" + p.getNumHouses() + "), ";
                } else {
                    out += p.getName() + ", ";
                }
            }
        }
        if (out.length() > 0) {
            out = out.substring(0, out.length() - 2);
        }
        return out;
    }

    public void land(int index, int mult) {//figure out doubles case
        Property p = board.getProperty(index);
        if (index != 10 || this.turnsInJail == 0) {
            manager.printLog(this.name + " landed on " + p.getName());
        }
        manager.getG().setTrade(true);
        if (p.isSpecial()) {
            //buy false
            manager.getG().setBuy(false);
            //speacial cases
            switch (p.getName()) {
                case "GO":
                    //bank.pay(200, this);
                    break;
                case "Income tax":
                    try {
                        this.pay(200, bank);
                    } catch (BankruptException e) {
                        e.handleBank(this, 200);
                    }
                    return;
                case "Jail":
                    if (this.turnsInJail > 0) {
                        manager.printLog("Say hi to the inmates!");
                    }
                    break;
                case "Free Parking":
                    break;
                case "Community Chest":
                    findCommC();
                    break;
                case "Chance":
                    findChance();
                    break;
                case "Luxury Tax":
                    manager.printLog(name + " landed on luxury tax. Pay $100");
                    try {
                        this.pay(100, bank);
                    } catch (BankruptException e) {
                        e.handleBank(this, 100);

                    }
                    return;
                case "Go To Jail":
                    manager.goToJail(this);
                    break;
                default:
                    break;
            }
            return;
        }

        //rent
        if (p.isOwned()) {
            if (p.getOwner() == this) {//land on yourself
                manager.getG().setEnd(true);
                manager.getG().setBuy(false);
                return;
            }
            if (!p.isMortgaged()) {
                manager.getG().setBuy(false);
                manager.printLog(this.name + " is paying rent to " + p.getOwner().getName() + " for " + p.getName());
                payRent(board.getProperty(index).getOwner(), mult);
            }
            manager.getG().setEnd(true);
            return;
        }
        if (!actionCompleted) {
            manager.printLog("Price: $" + p.getPrice());
        }
        manager.getG().setBuy(balance >= p.getPrice() && !actionCompleted);
        manager.getG().setMort(balance <= p.getPrice() && !actionCompleted);
        manager.getG().setEnd(true);
    }

    public void setActionCompleted(boolean b) {
        actionCompleted = b;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    public int calcNumRRs(ArrayList<Property> props) {
        int c = 0;
        for (Property p : props) {
            if (p.getColor().contains("RR")) {
                c++;
            }
        }
        return c;
    }

    public ArrayList<Property> getProperties() {
        return this.properties;
    }

    public int getDebtToBank() {
        return debt;
    }

    public void payRent(Player receiving, int mult) {

        int amount = 0;
        Property p = board.getProperty(bIndex);
        if (p.getColor().equals("RR")) {

            int num = calcNumRRs(receiving.getProperties());
            switch (num) {
                case 1:
                    amount = 25;
                    break;
                case 2:
                    amount = 50;
                    break;
                case 3:
                    amount = 100;
                    break;
                case 4:
                    amount = 200;
                    break;
                default:
                    break;
            }
        } else {
            amount = p.getRent();
            if (amount == -1) {//electric or water works
                amount = calcUtilityRent(bIndex);
            }
        }

        try {
            pay(amount * mult, receiving);
            receiving.checkDebt();
        } catch (BankruptException e) {
            e.handleRent(this, receiving, amount * mult);

            //remove(paying);
        }
    }

    private boolean hasBothUtilities() {
        boolean hasElec = false;
        boolean hasWater = false;
        for (Property p : properties) {
            hasElec = p.getName().equals("Electric Company");
            hasWater = p.getName().equals("Water Works");
        }
        return hasWater && hasElec;
    }

    private int calcUtilityRent(int index) {
        DiceRoller d = new DiceRoller();
        int amnt = d.getTotal();
        manager.printLog(name + d.printRoll());
        int mult;
        boolean hasBoth = board.getProperty(index).getOwner().hasBothUtilities();
        if (hasBoth) {
            mult = 10;
        } else {
            mult = 4;
        }
        return mult * amnt;
    }

    @Override
    public String toString() {
        return name + "with a balance of " + balance + " and " + properties.size() + " properties.";
    }

    @Override
    public void pay(int amount, Payable receiving) throws BankruptException {
        if (amount == 0) {
            return;
        }
        if (receiving instanceof Player) {
            manager.printLog(name + " is paying $" + amount + " to " + receiving.getName());
        } else {
            manager.printLog(name + " is paying $" + amount + " to the bank.");
        }
        manager.printLog("*******Before********");

        manager.printLog(balanceToString(this));

        if (receiving instanceof Player) {
            manager.printLog(balanceToString((Player) receiving));
        }
        if (balance < amount) {
            throw new BankruptException(name + " is bankrupt", manager);
        } else {
            receiving.setBalance(receiving.getBalance() + amount);
            this.balance -= amount;
        }
        manager.printLog("*******After********");
        manager.printLog(balanceToString(this));
        if (receiving instanceof Player) {
            manager.printLog(balanceToString((Player) receiving));

        }

    }

    @Override
    public String balanceToString(Player p) {
        return (p.getName() + "'s balance: " + p.getBalance());
    }

    public GameManager getManager() {
        return this.manager;
    }

    private void findCommC() {
        Card c = manager.getCommC();
        int value = c.getValue();
        switch (c.getMsg()) {
            case "You are assessed for street repairs–$40 per house–$115 per hotel":
                int tax = calcHouseHotel(false);
                try {
                    pay(tax, bank);

                } catch (BankruptException e) {
                    e.handleBank(this, tax);
                }
                return;
            case "Get Out of Jail Free":
                hasGOOJF = true;
                return;
            case "Advance to Go (Collect $200)":
                manager.moveTo(0, true, 1);
                return;
            case "Go to Jail–Go directly to jail–Do not pass Go–Do not collect $200":
                manager.goToJail(this);
                return;
            case "Grand Opera Night—Collect $50 from every player for opening night seats":
                for (Player p : manager.getPlayers()) {
                    if (p != this) {
                        try {
                            p.pay(50, this);
                            checkDebt();
                        } catch (BankruptException e) {
                            e.handleBank(p, 50);

                        }
                    }
                }
                return;
            default:
                break;
        }
        if (value != 0) {
            if (value < 0) {
                try {
                    pay(-value, bank);
                } catch (BankruptException e) {
                    e.handleBank(this, -value);
                }
            } else {
                bank.pay(value, this);
                checkDebt();
            }
        }
    }

    private void findChance() {
        Card c = manager.getChance();
        int value = c.getValue();
        switch (c.getMsg()) {
            case "You have been elected Chairman of the Board–Pay each player $50":
                for (Player p : manager.getPlayers()) {
                    if (p != this) {
                        try {
                            pay(50, p);
                            p.checkDebt();
                        } catch (BankruptException e) {
                            e.handleRent(this, p, 50);
                        }
                    }
                }
                return;
            case "Make general repairs on all your property–For each house pay $25–For each hotel $100":
                int amnt = calcHouseHotel(true);
                try {
                    pay(amnt, bank);
                } catch (BankruptException e) {
                    e.handleBank(this, amnt);
                }
                return;
            case "Advance token to nearest Utility. If unowned, you may buy it from the Bank. "
            + "If owned, throw dice and pay owner a total ten times the amount thrown.":
                if (bIndex > 28 || (bIndex > 0 && bIndex < 12)) {
                    manager.moveTo(12, true, 10);
                } else {
                    manager.moveTo(28, true, 10);
                }
                return;
            case "Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. "
            + "If Railroad is unowned, you may buy it from the Bank.":
                int lst = bIndex % 10;//last digit of bIndex
                int scale = 0;
                if (lst % 5 != lst % 10) {
                    scale = 5;
                }
                manager.move((5 - (lst % 5)) + scale, true, 2);
                return;
            case "Get Out of Jail Free":
                this.hasGOOJF = true;
                return;
        }

        if (value != -1 && value != -3 && value != 0) {
            if (value > 0) {

                bank.pay(value, this);
                this.checkDebt();

            } else {
                try {
                    pay(-value, bank);
                } catch (BankruptException e) {
                    e.handleBank(this, -value);

                }

            }
        }

        //handle go back 3 case
        int index = c.getIndex();
        if (index >= 0 && c.getValue() == 0) {
            if (index == 10) {
                manager.goToJail(this);
            } else {
                manager.moveTo(index, true, 1);
            }
        } else if (index == -3) {
            manager.move(3, false, 1);
        }

    }

    private int calcHouseHotel(boolean isChance) {
        int numHouses = 0;
        int numHotels = 0;

        for (Property p : properties) {
            numHouses += p.getNumHouses();
            if (numHouses == 5) {
                numHouses = 0;
                numHotels = 1;
            }
        }
        if (isChance) {
            return (numHouses * 25) + (numHotels * 100);
        }
        return (numHouses * 40) + (numHotels * 115);
    }

    public static void changeOwner(Player from, Player to, Property p) {
        to.properties.add(from.properties.remove(from.properties.indexOf(p)));
        p.setIsOwned(true, to);
    }

    public boolean hasMortgagedProperties() {
        for (Property p : properties) {
            if (p.isMortgaged()) {
                return true;
            }
        }
        return false;
    }

    public void checkDebt() {
        if (this.debt > 0) {
            try {
                int temp = this.balance;
                this.pay(temp, bank);
                this.debt -= temp;
            } catch (BankruptException w) {
                System.out.println("big problem in check debt(player)");

            }

        }
    }

    public void addDebt(int amount) {
        manager.printLog("Adding $" + amount + " of debt to " + name);
        this.debt += amount;
        manager.printLog(name + "'s current debt: " + debt);
    }

    public String[] getHouseColorList() {
        ArrayList<String> temp = new ArrayList<>();

        for (Property p : properties) {
            if (p.isIsMonopoly() && temp.indexOf(p.getColor()) == -1 && p.getHousePrice() <= this.balance) {
                temp.add(p.getColor());
            }
        }
        String[] out = new String[temp.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = temp.get(i);
        }
        return out;
    }

    public boolean hasMonopoly() {
        for (Property p : properties) {
            if (p.isIsMonopoly() && p.getNumHouses() < 5 && p.getPrice() <= this.balance) {
                return true;
            }
        }
        return false;
    }
}
