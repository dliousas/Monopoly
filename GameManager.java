/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author DemetriosLiousas
 */
public class GameManager {

    private final GameBoard g;
    private static final int c = 0;
    private static int v = 6;
    private int turn = 0;
    private final Game game;
    private Board board;
    private final ChanceDeck chance;
    private final CommunityChestDeck commC;
    private final JTextArea log;
    private final Bank bank;
    private Player prevRoll;
    private int numRollsTurn = 0;
    public static final int TRADE = 3002;
    public static final int MORTGAGE = 3001;
    public static final int UNMORTGAGE = 3003;
    public static final int HOUSES_COLOR_SELECT = 3005;
    public static final int HOUSES_PURCHASE_HOUSE = 3006;

    public Board getBoard() {
        return board;
    }

    public GameBoard getG() {
        return g;
    }

    public Game getGame() {
        return game;
    }

    public Bank getBank() {
        return bank;
    }

    public GameManager(GameBoard g, Game game, JTextArea log, Bank bank, Board b) {
        this.g = g;
        this.bank = bank;
        this.game = game;
        this.log = log;
        this.board = b;
        chance = new ChanceDeck();
        commC = new CommunityChestDeck();
        prevRoll = g.getPlayers()[0];
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void printLog(String s) {
        String txt = log.getText();
        if (txt.length() > 475) {
            txt = txt.substring(txt.indexOf("\n", 40));
        }
        if (s.length() > 50) {
            s = s.substring(0, 50) + "\n" + s.substring(50);
        }
        log.setText(txt + "\n" + s + "\n");
    }

    public void toPane(String s) {
        JOptionPane.showMessageDialog(g.getFrame(), s);
    }

    public Card getCommC() {
        Card c = commC.getCard();
        toPane(c.getMsg());
        return c;
    }

    public Card getChance() {
        Card c = chance.getCard();
        toPane(c.getMsg());
        return c;
    }

    public void rollDice() {
        boolean outOfJail = false;//only true if player just rolled doubles out of jail
        DiceRoller d = new DiceRoller();
        printLog(getCurr().getName() + d.printRoll());
        boolean doubles = d.getDoubles();
        if (getCurr().inJail() == 0) {
            g.setPayOutJail(false);
        } else {
            if (doubles) {
                outOfJail = true;
                g.setPayOutJail(false);
                getCurr().setInJail(0);
            } else {
                g.setEnd(getCurr().inJail() < 3);
                getCurr().setInJail(getCurr().inJail() + 1);
                g.setPayOutJail(true);
                g.setRoll(false);
                return;
            }
        }
        int dist = d.getTotal();
        if (doubles) {
            numRollsTurn++;
            if (numRollsTurn == 3) {
                getCurr().setInJail(1);
                this.moveTo(10, false, 0);
            }
        }
        move(dist, true, 1);
        g.setEnd((!doubles || getCurr().inJail() == 0) || outOfJail);
        g.setRoll((doubles && getCurr().inJail() != 0) && !outOfJail);
    }

    /**
     *
     * @param numSpaces positive number of spaces to move
     * @param forwards true to go forward, false to go backwards
     * @param mult
     */
    public void move(int numSpaces, boolean forwards, int mult) {
        boolean startOnGo = getCurr().getIndex() == 0;//if it starts on go, it will not get paid 200, because it gets it when it lands
        if (forwards) {
            int index = (numSpaces + getCurr().getIndex()) % 40;
            while (getCurr().getIndex() != index) {
                moveHelper(board.getNext(getCurr().getIndex()));
                getCurr().increment();
                if (getCurr().getIndex() == 0 && !startOnGo) {
                    printLog(getCurr().getName() + " passed GO!");
                    bank.pay(200, getCurr());
                    getCurr().checkDebt();
                }
            }
        } else {
            int index = (getCurr().getIndex() - numSpaces);
            if (index < 0) {
                index += 40;
            }
            while (getCurr().getIndex() != index) {
                moveHelper(board.getPrev(getCurr().getIndex()));
                getCurr().decrement();
            }
        }
        getCurr().land(getCurr().getIndex(), mult);
    }

    private void moveHelper(Point f) {
        JLabel lab = getCurr().getLab();
        double dist = board.getPoint(getCurr().getIndex()).distance(f);
        if (lab.getX() < f.x || lab.getY() < f.y) {
            dist *= -1;
        }
        if (lab.getX() != f.x) {
            while (lab.getX() != f.x) {
                if (dist > 0) {
                    lab.setLocation(lab.getX() - v, lab.getY());
                } else {
                    lab.setLocation(lab.getX() + v, lab.getY());
                }
                g.getFrame().repaint();
                g.getFrame().revalidate();
                try {
                    Thread.sleep(c);
                } catch (InterruptedException e) {
                    return;
                }
            }
        } else {
            while (lab.getY() != f.y) {
                g.getFrame().repaint();
                g.getFrame().revalidate();
                if (dist > 0) {
                    lab.setLocation(lab.getX(), lab.getY() - v);
                } else {
                    lab.setLocation(lab.getX(), lab.getY() + v);
                }
                g.getFrame().repaint();
                g.getFrame().revalidate();
                try {
                    Thread.sleep(c);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void moveTo(int index, boolean forwards, int mult) {
        int numSpaces;
        if (forwards) {
            if (index < getCurr().getIndex()) {
                numSpaces = (40 - getCurr().getIndex()) + index;
            } else {
                numSpaces = index - getCurr().getIndex();
            }
        } else if (index > getCurr().getIndex()) {
            numSpaces = getCurr().getIndex() + (40 - index);
        } else {
            numSpaces = getCurr().getIndex() - index;

        }
        move(numSpaces, forwards, mult);
    }

    public Player getCurr() {
        return g.getPlayers()[turn];
    }

    public int getTurn() {
        return this.turn;
    }

    public Player[] getPlayers() {
        return g.getPlayers();
    }

    public void nextTurn(int count) {
        this.turn++;

        if (this.turn >= game.getNumPlayers()) {
            this.turn = 0;
        }
        if (g.getPlayers()[this.turn] == null) {
            if (count < game.getNumPlayers()) {
                nextTurn(count + 1);
            } else {
                toPane("The game is over. Everyone is bankrupt");

            }
        }
        g.updatePlayer();
        printLog("It is " + getCurr().getName() + "'s turn!");
        if (getCurr().inJail() > 0) {
            g.setPayOutJail(true);
        }
    }

    public void goToJail(Player p) {
        printLog(p.getName() + " is in Jail");
        p.setInJail(1);
        moveTo(10, false, 1);
        g.setEnd(true);
        g.setRoll(false);
    }

    public int incomeTax() {
        printLog(getCurr().getName() + " landed on income tax");
        return 200;
    }

    public void bankrupt() {

    }

    public Property[] getPropArr(Player player, int k) {
        ArrayList<Property> temp = new ArrayList<>();
        Property p;
        int c = 0;
        for (int i = 0; i < player.getProperties().size(); i++) {
            p = player.getProperties().get(i);
            if (k == MORTGAGE && !p.isMortgaged() && p.getNumHouses() == 0) {
                temp.add(p);
                c++;
            } else if (k == TRADE && p.getNumHouses() == 0) {
                temp.add(p);
                c++;
            } else if (k == UNMORTGAGE && p.isMortgaged()) {
                temp.add(p);
                c++;
            }
        }
        Property[] props = new Property[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            props[i] = temp.get(i);
        }

        return props;
    }

    public void populateTradeList(String s, JList a, JList b) {
        Player p1 = getCurr();
        Player p2 = game.getPlayer(s);
        makeList(a, p1, TRADE);
        makeList(b, p2, TRADE);

    }

    public boolean selectMort(JList list, int k) {
        int[] toMort = list.getSelectedIndices();
        Property temp;
        Property[] props;
        switch (k) {
            case MORTGAGE:
                props = getPropArr(getCurr(), MORTGAGE);
                break;
            case UNMORTGAGE:
                props = getPropArr(getCurr(), UNMORTGAGE);
                break;
            default:
                System.out.println("there is an error in select mort(gamemanager)");
                props = getPropArr(getCurr(), MORTGAGE);
                break;
        }
        for (int i : toMort) {
            temp = props[i];
            if (k == MORTGAGE) {
                printLog(getCurr().getName() + " is mortgaging " + temp.getName() + " for $" + temp.getMortgage());
                temp.setIsMortaged(true);
                bank.pay(temp.getMortgage(), getCurr());
                removeMonopoly(getCurr(), temp.getColor());
                getCurr().checkDebt();
            } else {
                printLog(getCurr().getName() + " is unmortgaging " + temp.getName() + " for $" + ((int) ((double) temp.getMortgage() * 1.1)));
                try {
                    getCurr().pay(((int) ((double) temp.getMortgage() * 1.1)), bank);
                    temp.setIsMortaged(false);
                    makeMonopoly(getCurr(), temp.getColor());
                } catch (BankruptException e) {
                    printLog(getCurr().getName() + " does not have enough money to unmortgage " + temp.getName() + ".");
                    printLog("Lose a turn for short funds.");
                    g.setEnd(true);
                    g.setRoll(false);
                    return true;
                }
            }

        }
        return false;
    }

    public void makeTrade(JList list1, JList list2, int bal1, int bal2, Player partner) {
        int origbal1 = getCurr().getBalance();
        int origbal2 = partner.getBalance();

        try {
            getCurr().pay(bal1, partner);
            partner.pay(bal2, getCurr());
        } catch (BankruptException e) {
            printLog("Trade unsuccessful. Make sure you have enough money to trade.");
            getCurr().setBalance(origbal1);
            partner.setBalance(origbal2);
            g.setEnd(false);
            g.setRoll(true);
            g.setAccept1(false);
            g.setAccept2(false);
            return;
        }
        getCurr().checkDebt();
        partner.checkDebt();
        int[] ind1 = list1.getSelectedIndices();
        int[] ind2 = list2.getSelectedIndices();
        Property temp;
        Property[] props1 = getPropArr(getCurr(), TRADE);
        Property[] props2 = getPropArr(partner, TRADE);
        for (int i : ind1) {
            temp = props1[i];
            printLog(getCurr().getName() + " is trading " + temp.getName() + " to " + partner.getName());
            temp.setIsOwned(true, partner);
            Player.changeOwner(getCurr(), partner, temp);
            makeMonopoly(partner, temp.getColor());
            removeMonopoly(getCurr(), temp.getColor());
        }
        for (int j : ind2) {
            temp = props2[j];
            printLog(partner.getName() + " is trading " + temp.getName() + " to " + getCurr().getName());
            temp.setIsOwned(true, getCurr());
            Player.changeOwner(partner, getCurr(), temp);
            makeMonopoly(getCurr(), temp.getColor());
            removeMonopoly(partner, temp.getColor());
        }

        printLog("Trade successful!");

        g.setListFrame(false);

        g.setEnd(false);
        g.setRoll(true);
        g.setPInfo();
    }

    public void makeList(JList list, Player player, int k) {
        Property[] props = getPropArr(player, k);
        String[] info = new String[props.length];
        for (int i = 0; i < props.length; i++) {
            if (props[i] != null) {
                switch (k) {
                    case MORTGAGE:
                        info[i] = props[i].getName() + ", " + props[i].getMortgage();
                        break;
                    case TRADE:
                        info[i] = props[i].getName() + "(Value: " + props[i].getPrice() + ")";
                        break;
                    case UNMORTGAGE:
                        info[i] = props[i].getName() + ", " + (int) ((double) props[i].getMortgage() * 1.1);
                        break;
                    default:
                        break;
                }
            }
        }
        list.setVisible(true);
        list.setListData(info);
    }

    private boolean isMonopoly(Player player, String color) {
        Property[] props = board.getBoard();
        boolean isUtitlityOrRR;
        int c = 0;
        for (Property prop : props) {

            isUtitlityOrRR = !prop.isSpecial() && (prop.getColor().equals("Utility") || prop.getColor().equals("RR"));

            if (!(prop.isSpecial() || !prop.getColor().equals(color)
                    || isUtitlityOrRR || prop.getOwner() == null || !prop.getOwner().equals(player) || prop.isMortgaged())) {
                c++;
                if (color.equals("Brown") || color.equals("Dark Blue")) {
                    if (c >= 2) {
                        printLog(player.getName() + " has a monopoly on " + color);
                        return true;
                    }
                } else {
                    if (c >= 3) {
                        printLog(player.getName() + " has a monopoly on " + color);
                        return true;
                    }
                }
            }
        }
        //player has no monolpoly if at this point
        return false;
    }

    public void removeMonopoly(Player player, String color) {//check if color is no longer a monopoly, and remove ! points
        if (!isMonopoly(player, color)) {
            board.setColorMonopoly(color, false);
        }
    }

    public void makeMonopoly(Player player, String color) {
        if (isMonopoly(player, color)) {
            board.setColorMonopoly(color, true);
        }
    }

    public void putHouses(JList list1) {
        String s = (String) list1.getSelectedValue();
        int i = 0;
        Property[] props = board.getBoard();
        Property[] colorPropArr;
        if (s.equals("Brown") || s.equals("Dark Blue")) {//two props in color
            colorPropArr = new Property[2];
        } else {//must be three props in color
            colorPropArr = new Property[3];
        }
        System.out.println("here");
        for (Property p : props) {
            if (!p.isSpecial() && p.getColor().equals(s)) {
                switch (i) {
                    case 0:
                        colorPropArr[0] = p;
                        break;
                    case 1:
                        colorPropArr[1] = p;
                        break;
                    case 2:
                        colorPropArr[2] = p;
                        break;
                    default:
                        System.out.println("here default");
                        break;
                }
                i++;

            }
        }

        g.setHouseButtons(colorPropArr);

    }
}
