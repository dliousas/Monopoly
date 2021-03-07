package monopoly;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.BorderFactory.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author DemetriosLiousas
 */
public class GameBoard implements ActionListener {

    private static int CURR_PROCESS;
    private final JFrame frame;
    private final JLabel lab;
    private final JButton btnRoll;
    private final JButton btnBuy;
    private final JButton endTurn;
    private final JButton btnTrade;
    private final JButton btnMort;
    private final JButton btnUnMort;
    private final JButton btnBankrupt;
    private final JButton finishSelect;
    private final JButton payOutJail;
    private final JButton buyHouses;
    private final MonopolyButton house1;
    private final MonopolyButton house2;
    private final MonopolyButton house3;
    private final JToggleButton acceptTrade1;
    private final JToggleButton acceptTrade2;
    private final JFrame listFrame;
    private Player[] players;
    private GameManager manager;
    private JTextArea log;
    private JTextArea[] playerLogs;
    private JTextField currPlayer;
    private JList<String> list1;
    private JList<String> list2;
    private JTextField balance1;
    private JTextField balance2;
    private final Color back = new Color(76, 255, 227);
    private final Color fore = new Color(255, 91, 81);
    private final Font btnFont = new Font("Arial", Font.BOLD, 18);
    //                                                                  color,           thiccness, curves
    private final javax.swing.border.Border border = createLineBorder(new Color(75, 113, 255), 1, false);
    Player tradePartner;

    public void setManager(GameManager manager) {
        this.manager = manager;
    }

    public GameBoard(Player[] players) {
        this.players = players;

        this.frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(1300, 900);
        frame.setName("Monopoly");

        this.listFrame = new JFrame();
        listFrame.setLayout(null);
        listFrame.setSize(425, 500);
        listFrame.setName("Select Properties");
        listFrame.setVisible(false);
        listFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        currPlayer = new JTextField();
        frame.add(currPlayer);
        currPlayer.setLayout(null);
        currPlayer.setVisible(true);
        currPlayer.setEditable(false);
        currPlayer.setLocation(915, 0);
        currPlayer.setSize(300, 30);
        currPlayer.setBackground(back);
        currPlayer.setFont(new Font("Arial", Font.PLAIN, 24));
        currPlayer.setForeground(fore);
        currPlayer.setBorder(border);
        currPlayer.setText("Begin game to display!");
        currPlayer.setHorizontalAlignment(JTextField.CENTER);

        log = new JTextArea();
        frame.add(log);
        log.setVisible(true);
        log.setEditable(false);
        log.setLocation(915, 30);
        log.setLayout(null);
        log.setSize(300, 770);
        log.setBackground(Color.WHITE);
        log.setBorder(border);

        int x = 1215;
        int y = 0;
        playerLogs = new JTextArea[players.length];
        int height = 300;

        for (int i = 0; i < playerLogs.length; i++) {
            playerLogs[i] = new JTextArea();
            frame.add(playerLogs[i]);
            playerLogs[i].setVisible(true);
            playerLogs[i].setEditable(false);
            playerLogs[i].setLocation(x, y);
            playerLogs[i].setLayout(null);
            playerLogs[i].setSize(200, height);
            playerLogs[i].setBackground(Color.WHITE);
            playerLogs[i].setBorder(border);
            //playerLogs[i].setText(players[i].getName());
            switch (i) {
                case 0:
                    x += 200;
                    break;
                case 1:
                    x -= 200;
                    y += height;
                case 2:
                    x += 200;
            }
        }
        lab = new JLabel();
        lab.setLayout(null);
        lab.setVisible(true);
        lab.setLayout(null);
        lab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/realboardd.jpg")));
        lab.setLocation(0, 0);
        lab.setSize(800, 800);
        frame.add(lab);

        list1 = new JList<>();
        list1.setBorder(border);
        list1.setSize(200, 400);
        listFrame.add(list1);
        list1.setVisible(false);
        list1.setLocation(0, 50);

        list2 = new JList<>();
        list2.setBorder(border);
        list2.setSize(200, 400);
        listFrame.add(list2);
        list2.setVisible(false);
        list2.setLocation(200, 50);

        balance1 = new JTextField("Money being traded");
        balance1.setBorder(border);
        balance1.setSize(200, 25);
        listFrame.add(balance1);
        balance1.setVisible(false);
        balance1.setLocation(0, 25);
        balance2 = new JTextField("Money being traded");
        balance2.setBorder(border);
        balance2.setSize(200, 25);
        listFrame.add(balance2);
        balance2.setVisible(false);
        balance2.setLocation(200, 25);

        this.btnRoll = new JButton();
        this.btnBuy = new JButton();
        this.endTurn = new JButton();
        this.btnMort = new JButton();
        this.btnUnMort = new JButton();
        this.btnBankrupt = new JButton();
        this.finishSelect = new JButton();
        this.btnTrade = new JButton();
        this.payOutJail = new JButton();
        this.house1 = new MonopolyButton();
        this.house2 = new MonopolyButton();
        this.house3 = new MonopolyButton();
        this.buyHouses = new JButton();
        this.acceptTrade1 = new JToggleButton();
        this.acceptTrade2 = new JToggleButton();

        btnRoll.setLayout(null);
        btnRoll.setVisible(true);
        btnRoll.setBorder(border);
        btnRoll.setText("<html><center>Roll<br>Dice</center></html>");
        btnRoll.setFont(btnFont);
        btnRoll.setForeground(fore);
        btnRoll.setBackground(back);
        btnRoll.setLocation(800, 0);
        btnRoll.setSize(115, 115);

        buyHouses.setLayout(null);
        buyHouses.setVisible(false);
        buyHouses.setBorder(border);
        buyHouses.setText("<html><center>Buy<br>Houses</center></html>");
        buyHouses.setFont(btnFont);
        buyHouses.setForeground(fore);
        buyHouses.setBackground(back);
        buyHouses.setLocation(800, 690);
        buyHouses.setSize(115, 110);

        btnBuy.setLayout(null);
        btnBuy.setVisible(false);
        btnBuy.setBorder(border);
        btnBuy.setText("<html><center>Buy<br>Property</center></html>");
        btnBuy.setFont(btnFont);
        btnBuy.setForeground(fore);
        btnBuy.setBackground(back);
        btnBuy.setLocation(800, 115);
        btnBuy.setSize(115, 115);

        payOutJail.setLayout(null);
        payOutJail.setVisible(false);
        payOutJail.setBorder(border);
        payOutJail.setText("<html><center>Pay $50 To<br>Be Freed</center></html>");
        payOutJail.setFont(btnFont);
        payOutJail.setForeground(fore);
        payOutJail.setBackground(back);
        payOutJail.setLocation(800, 115);
        payOutJail.setSize(115, 115);

        endTurn.setLayout(null);
        endTurn.setVisible(false);
        endTurn.setFont(btnFont);
        endTurn.setBorder(border);
        endTurn.setText("<html><center>Finish<br>Turn</center></html>");
        endTurn.setForeground(fore);
        endTurn.setBackground(back);
        endTurn.setLocation(800, 230);
        endTurn.setSize(115, 115);

        btnTrade.setLayout(null);
        btnTrade.setVisible(false);
        btnTrade.setFont(btnFont);
        btnTrade.setBorder(border);
        btnTrade.setText("<html><center>Trade</center></html>");
        btnTrade.setForeground(fore);
        btnTrade.setBackground(back);
        btnTrade.setLocation(800, 345);
        btnTrade.setSize(115, 115);

        btnMort.setLayout(null);
        btnMort.setVisible(false);
        btnMort.setBorder(border);
        btnMort.setText("<html><center>Mortgage<br>Properties</center></html>");
        btnMort.setFont(btnFont);
        btnMort.setForeground(fore);
        btnMort.setBackground(back);
        btnMort.setLocation(800, 460);
        btnMort.setSize(115, 115);

        btnUnMort.setLayout(null);
        btnUnMort.setVisible(false);
        btnUnMort.setBorder(border);
        btnUnMort.setText("<html><center>Unmortgage<br>Properties</center></html>");
        btnUnMort.setFont(btnFont);
        btnUnMort.setForeground(fore);
        btnUnMort.setBackground(back);
        btnUnMort.setLocation(800, 575);
        btnUnMort.setSize(115, 115);

        btnBankrupt.setLayout(null);
        btnBankrupt.setVisible(false);
        btnBankrupt.setBorder(border);
        btnBankrupt.setText("<html><center>Declare<br>Bankruptcy</center></html>");
        btnBankrupt.setFont(btnFont);
        btnBankrupt.setForeground(fore);
        btnBankrupt.setBackground(back);
        btnBankrupt.setLocation(800, 575);
        btnBankrupt.setSize(115, 115);

        finishSelect.setLayout(null);
        finishSelect.setVisible(false);
        finishSelect.setBorder(border);
        finishSelect.setText("<html><center>Finish<br>Selecting</center></html>");
        finishSelect.setFont(btnFont);
        finishSelect.setForeground(fore);
        finishSelect.setBackground(back);
        finishSelect.setLocation(listFrame.getWidth() / 2 - 100, 50);
        finishSelect.setSize(200, 50);

        acceptTrade1.setLayout(null);
        acceptTrade1.setVisible(false);
        acceptTrade1.setBorder(border);
        acceptTrade1.setText("<html><center>Accept Trade</center></html>");
        acceptTrade1.setFont(btnFont);
        acceptTrade1.setForeground(fore);
        acceptTrade1.setBackground(back);
        acceptTrade1.setLocation(0, 0);
        acceptTrade1.setSize(200, 25);

        acceptTrade2.setLayout(null);
        acceptTrade2.setVisible(false);
        acceptTrade2.setBorder(border);
        acceptTrade2.setText("<html><center>Accept Trade</center></html>");
        acceptTrade2.setFont(btnFont);
        acceptTrade2.setForeground(fore);
        acceptTrade2.setBackground(back);
        acceptTrade2.setLocation(200, 0);
        acceptTrade2.setSize(200, 25);

        house1.setLayout(null);
        house1.setVisible(false);
        house1.setBorder(border);
        house1.setFont(btnFont);
        house1.setForeground(fore);
        house1.setBackground(back);
        house1.setLocation(25, 100);
        house1.setSize(125, 125);

        house2.setLayout(null);
        house2.setVisible(false);
        house2.setBorder(border);
        house2.setFont(btnFont);
        house2.setForeground(fore);
        house2.setBackground(back);
        house2.setLocation(150, 100);
        house2.setSize(125, 125);

        house3.setLayout(null);
        house3.setVisible(false);
        house3.setBorder(border);
        house3.setFont(btnFont);
        house3.setForeground(fore);
        house3.setBackground(back);
        house3.setLocation(275, 100);
        house3.setSize(125, 125);

        frame.add(endTurn);
        frame.add(btnRoll);
        frame.add(btnBuy);
        frame.add(btnMort);
        frame.add(btnUnMort);
        frame.add(btnBankrupt);
        listFrame.add(finishSelect);
        frame.add(btnTrade);
        frame.add(payOutJail);
        frame.add(buyHouses);
        listFrame.add(acceptTrade1);
        listFrame.add(acceptTrade2);
        listFrame.add(house1);
        listFrame.add(house2);
        listFrame.add(house3);

        btnRoll.addActionListener(this);
        buyHouses.addActionListener(this);
        btnBuy.addActionListener(this);
        endTurn.addActionListener(this);
        btnMort.addActionListener(this);
        btnTrade.addActionListener(this);
        btnBankrupt.addActionListener(this);
        finishSelect.addActionListener(this);
        btnUnMort.addActionListener(this);
        payOutJail.addActionListener(this);
        acceptTrade1.addActionListener(this);
        acceptTrade2.addActionListener(this);
        house1.addActionListener(this);
        house2.addActionListener(this);
        house3.addActionListener(this);
        //btnAuction.addActionListener(this);
        //listFrame.setVisible(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *
     * @return
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     *
     * @return
     */
    public JLabel getLab() {
        return this.lab;
    }

    /**
     *
     * @return
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     *
     */
    public void updatePlayer() {
        this.currPlayer.setText(manager.getCurr().getName() + " 's turn");
    }

    private String lineBreaks(String out, String left) {
        if (left.length() < 33) {
            return out + left;
        }
        for (int i = 31; i >= 0; i--) {
            if (left.substring(i, i + 1).equals(" ")) {
                return lineBreaks(out + left.substring(0, i) + "\n", left.substring(i));
            }
        }
        return lineBreaks(out + left.substring(0, 33), left.substring(33));
    }

    /**
     *
     * @param p
     */
    public void setPInfo() {
        for (Player player : players) {
            String str = player.printProperties();
            playerLogs[manager.getGame().getPlayerIndex(player)].setText(player.getName() + "- Balance:" + player.getBalance() + "\n" + lineBreaks("", str));
        }
//        String str = p.printProperties();
//        playerLogs[manager.getGame().getPlayerIndex(p)].setText(p.getName() + "- Balance:" + p.getBalance() + "\n" + lineBreaks("", str));
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        setPInfo();
        if (e.getSource() == btnRoll) {
            this.setAllButtonsVisible(false);
            manager.rollDice();
            btnTrade.setVisible(false);
            //endTurn.setVisible(true);
        }
        if (e.getSource() == endTurn) {

            manager.getCurr().setActionCompleted(false);
            manager.nextTurn(0);
            btnRoll.setVisible(true);
            endTurn.setVisible(false);
            btnBuy.setVisible(false);
            btnTrade.setVisible(true);
            payOutJail.setVisible(false);
            btnMort.setVisible(false);
            if (manager.getCurr().hasMortgagedProperties()) {
                btnUnMort.setVisible(true);
            }
            if (manager.getCurr().hasMonopoly()) {
                buyHouses.setVisible(true);
            }
        }

        if (e.getSource() == buyHouses) {
            setAllButtonsVisible(false);
            CURR_PROCESS = GameManager.HOUSES_COLOR_SELECT;
            list1.setListData(manager.getCurr().getHouseColorList());

            houseConfig();

        }

        if (acceptTrade2.isSelected() && acceptTrade1.isSelected()) {
            boolean notInt1 = true;
            boolean notInt2 = true;
            int bal1 = 0;
            int bal2 = 0;
            while (notInt1) {
                try {
                    bal1 = Integer.parseInt(balance1.getText());
                    notInt1 = false;
                } catch (NumberFormatException a) {
                    balance1.setText(JOptionPane.showInputDialog("Re-enter money being traded from left-side player"));
                }
            }
            while (notInt2) {
                try {
                    bal2 = Integer.parseInt(balance2.getText());
                    notInt2 = false;
                } catch (NumberFormatException b) {
                    balance2.setText(JOptionPane.showInputDialog("Re-enter money being traded from right-side player"));
                }
            }
            manager.makeTrade(list1, list2, bal1, bal2, tradePartner);
            balance1.setText("Money being traded");
            balance2.setText("Money being traded");
            acceptTrade1.setSelected(false);
            acceptTrade2.setSelected(false);
        }

        if (e.getSource() == btnBuy) {
            btnBuy.setVisible(false);
            manager.getCurr().buyProperty(manager.getCurr().getIndex());

        }
        if (e.getSource() == btnTrade) {
            //select which Player to trade with
            boolean isName = false;
            String s = null;
            while (!isName) {
                s = JOptionPane.showInputDialog("What is the name of the player you would like to trade with?");
                isName = this.manager.getGame().isPlayer(s, manager.getCurr());
            }
            manager.populateTradeList(s, list1, list2);
            tradePartner = manager.getGame().getPlayer(s);
            tradeConfig();
            endTurn.setVisible(true);
            btnTrade.setVisible(false);

            //display properties into lists and money
        }
        if (e.getSource() == btnBankrupt) {
            manager.bankrupt();
        }
        if (e.getSource() == btnMort) {
            mortConfig("mortgage.");
            btnRoll.setVisible(false);
            endTurn.setVisible(false);
            btnBuy.setVisible(false);
            btnTrade.setVisible(false);
            payOutJail.setVisible(false);
            btnMort.setVisible(false);
            btnUnMort.setVisible(false);
            CURR_PROCESS = GameManager.MORTGAGE;
            manager.makeList(list1, manager.getCurr(), GameManager.MORTGAGE);
            //mortSelect.setVisible(true);

        }

        if (e.getSource() == btnUnMort) {
            mortConfig("unmortgage.");
            btnRoll.setVisible(false);
            endTurn.setVisible(false);
            btnBuy.setVisible(false);
            btnTrade.setVisible(false);
            payOutJail.setVisible(false);
            btnUnMort.setVisible(false);
            btnMort.setVisible(false);
            CURR_PROCESS = GameManager.UNMORTGAGE;
            manager.makeList(list1, manager.getCurr(), GameManager.UNMORTGAGE);
        }
        boolean paid = true;
        if (e.getSource() == finishSelect) {
            finishSelect.setVisible(false);
            switch (CURR_PROCESS) {
                case GameManager.MORTGAGE:
                    manager.selectMort(list1, GameManager.MORTGAGE);
                    if (manager.getCurr().getBalance() >= manager.getBoard().getProperty(manager.getCurr().getIndex()).getPrice()) {
                        btnBuy.setVisible(true);
                    }
                    btnTrade.setVisible(false);
                    endTurn.setVisible(true);
                    break;
                case GameManager.UNMORTGAGE:
                    paid = !manager.selectMort(list1, GameManager.UNMORTGAGE);
                    btnRoll.setVisible(paid);
                    endTurn.setVisible(!paid);
                    break;
                case GameManager.HOUSES_COLOR_SELECT:
                    manager.putHouses(list1);
                    CURR_PROCESS = GameManager.HOUSES_PURCHASE_HOUSE;
                    listFrame.setVisible(true);
                    btnRoll.setVisible(true);
                    finishSelect.setVisible(true);
                    break;
                case GameManager.HOUSES_PURCHASE_HOUSE:
                    listFrame.setVisible(false);
                    this.setAllButtonsVisible(false);
                    house1.setVisible(false);
                    house2.setVisible(false);
                    house3.setVisible(false);
                    house1.removeProperty();
                    house2.removeProperty();
                    house3.removeProperty();
                    btnRoll.setVisible(true);
                    break;
                default:
                    System.out.println("there is an error in mortSelect get source method(gameboard)");
                    break;
            }
            btnMort.setVisible(false);
            btnUnMort.setVisible(false);
        }
        if (e.getSource() == payOutJail) {//fix
            try {
                manager.getCurr().pay(50, manager.getBank());
                manager.getCurr().setInJail(0);
            } catch (BankruptException bnkrpt) {
                bnkrpt.handleBank(manager.getCurr(), 50);
            }
            payOutJail.setVisible(false);
            endTurn.setVisible(true);
        }

        if (e.getSource() == house1 || e.getSource() == house2 || e.getSource() == house3) {

            MonopolyButton butt = (MonopolyButton) e.getSource();
            butt.setVisible(false);
            try {
                manager.getCurr().pay(butt.getProperty().getHousePrice(), manager.getBank());
                butt.getProperty().addHouse();
                checkHouseBalance();
            } catch (BankruptException w) {
                System.out.println("I shouldnt get to this statemetnt\nErrior in e.getSource() == house1");
            }
            int p1 = house1.getProperty().getNumHouses();
            int p2 = house2.getProperty().getNumHouses();

            if (house3.getProperty() == null) {
                System.out.println(p1 + ", " + p2);
                house1.setVisible(p1 <= p2 && manager.getCurr().getBalance() >= house1.getProperty().getPrice() && p1 < 5);
                house2.setVisible(p2 <= p1 && manager.getCurr().getBalance() >= house2.getProperty().getPrice() && p2 < 5);
            } else {

                int p3 = house3.getProperty().getNumHouses();
                house1.setVisible(p1 <= p2 && p1 <= p3 && manager.getCurr().getBalance() >= house1.getProperty().getPrice() && p1 < 5);
                house2.setVisible(p2 <= p1 && p2 <= p3 && manager.getCurr().getBalance() >= house2.getProperty().getPrice() && p2 < 5);
                house3.setVisible(p3 <= p1 && p3 <= p2 && manager.getCurr().getBalance() >= house3.getProperty().getPrice() && p3 < 5);
            }
        }

    }

    public JList<String> getList1() {
        return list1;
    }

    private void checkHouseBalance() {
        if (manager.getCurr().getBalance() < house1.getProperty().getHousePrice()) {
            house1.setVisible(false);
            house2.setVisible(false);
            house3.setVisible(false);
        }
    }

    public void setList1(JList<String> list1) {
        this.list1 = list1;
    }

    public JList<String> getList2() {
        return list2;
    }

    public void setList2(JList<String> list2) {
        this.list2 = list2;
    }

    public JTextField getBalance1() {
        return balance1;
    }

    public void setBalance1(JTextField balance1) {
        this.balance1 = balance1;
    }

    public JTextField getBalance2() {
        return balance2;
    }

    public void setBalance2(JTextField balance2) {
        this.balance2 = balance2;
    }

    public void setBuy(boolean b) {
        btnBuy.setVisible(b);
    }

    public void setListFrame(boolean b) {
        listFrame.setVisible(b);
        if (!b) {
            list1.removeAll();
            list2.removeAll();
            list1.revalidate();
            list2.revalidate();
        }
    }

    public void setPayOutJail(boolean b) {
        payOutJail.setVisible(b);
    }

    public void setAccept1(boolean a) {
        acceptTrade1.setSelected(a);
    }

    public void setAccept2(boolean a) {
        acceptTrade2.setSelected(a);
    }

    public void setTrade(boolean b) {
        btnTrade.setVisible(b);
    }

    public void setEnd(boolean b) {
        endTurn.setVisible(b);
    }

    public void setRoll(boolean b) {
        btnRoll.setVisible(b);
    }

    public void setMort(boolean b) {
        btnMort.setVisible(b);
    }

    public void setBankrupt(boolean b) {
        btnBankrupt.setVisible(b);
    }

    public JTextArea getLog() {
        return this.log;
    }

    //confgure list frame for trade configuration
    private void tradeConfig() {
        JOptionPane.showMessageDialog(listFrame, "Select properties being traded and input the money being traded.");
        list1.setVisible(true);
        list2.setVisible(true);
        balance1.setVisible(true);
        balance2.setVisible(true);
        acceptTrade1.setVisible(true);
        acceptTrade2.setVisible(true);
        finishSelect.setVisible(false);
        list1.setLocation(0, 50);
        listFrame.repaint();
        listFrame.setVisible(true);
    }

    private void mortConfig(String process) {
        JOptionPane.showMessageDialog(listFrame, "Select properties to " + process);//process = mortgage or unmortgage
        list1.setVisible(true);
        list2.setVisible(false);
        balance1.setVisible(false);
        balance2.setVisible(false);
        acceptTrade1.setVisible(false);
        acceptTrade2.setVisible(false);
        finishSelect.setVisible(true);
        list1.setLocation(listFrame.getWidth() / 2 - 100, 100);
        listFrame.repaint();
        listFrame.setVisible(true);
    }

    //num = number of properties in color(dark blue = 2)
    private void houseConfig() {
        JOptionPane.showMessageDialog(listFrame, "Select one color to place houses.");
        list1.setVisible(true);

        list2.setVisible(false);
        balance1.setVisible(false);
        balance2.setVisible(false);
        acceptTrade1.setVisible(false);
        acceptTrade2.setVisible(false);
        finishSelect.setVisible(true);
        list1.setLocation(listFrame.getWidth() / 2 - 100, 100);
        listFrame.repaint();
        listFrame.setVisible(true);
    }

    public void setAllButtonsVisible(boolean b) {
        btnRoll.setVisible(b);
        btnBuy.setVisible(b);
        endTurn.setVisible(b);
        btnTrade.setVisible(b);
        btnMort.setVisible(b);
        btnUnMort.setVisible(b);
        btnBankrupt.setVisible(b);
        payOutJail.setVisible(b);
        buyHouses.setVisible(b);
    }

    public void setHouseButtons(Property[] colorProps) {
        list1.setVisible(false);
        list1.removeAll();
        listFrame.setVisible(true);
        house1.setVisible(colorProps[0].getNumHouses() <= colorProps[1].getNumHouses()
                && (colorProps.length == 2 || colorProps[0].getNumHouses() <= colorProps[2].getNumHouses()));
        house1.setProperty(colorProps[0]);
        house2.setVisible(colorProps[1].getNumHouses() <= colorProps[0].getNumHouses()
                && (colorProps.length == 2 || colorProps[1].getNumHouses() <= colorProps[2].getNumHouses()));
        house2.setProperty(colorProps[1]);
        if (colorProps.length == 3) {
            house3.setVisible(colorProps[2].getNumHouses() <= colorProps[1].getNumHouses()
                    && colorProps[2].getNumHouses() <= colorProps[0].getNumHouses());
            house3.setProperty(colorProps[2]);
        }
    }

}
