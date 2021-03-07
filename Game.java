/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import javax.swing.*;

/**
 *
 * @author DemetriosLiousas
 */
public class Game {

    private final GameBoard gameboard;
    private final Player[] players;
    private final GameManager manager;
    private final Bank bank;
    private final Board board;

    public static void main(String[] args) {
        Game g = new Game();
    }

    public Game() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int num = 0;

        while (num > 4 || num < 2) {
            num = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many players will be playing. There is a maximum of 4 players and a minimum of 2 players."));
        }
        JOptionPane.showMessageDialog(frame, "<html><body><p>Welcome to Monopoly!<br>"
                + "Click \"OK\" to get started</p></body></html>");
        JOptionPane.showMessageDialog(frame, "<html><body><p>Each person will be prompted to select their piece.<br>"
                + "The available pieces are: Boot, Car, Dog, Hat, Ship, Wheelbarrow, Iron, Thimble<br>"
                + "When prompted, you must input the name of the piece you would like <em><u>exactly</u></em> how it is shown above<br>"
                + "If you fail to do so, or you select a piece that has already been taken, you will simply be prompted to select again.</p></body></html>");

        MonopolyPiece[] labs = new MonopolyPiece[num];

        String[] names = new String[num];
        String pieceName = "";
        for (int i = 0; i < num; i++) {

            names[i] = JOptionPane.showInputDialog(frame, "Player " + (i + 1) + ", What's your name?");
            pieceName = "";
            while (true) {
                pieceName = JOptionPane.showInputDialog(frame, names[i] + ", what piece would you like to use?\n"
                        + "Possible options: boot, car, dog, hat, ship, wheelbarrow, iron, thimble");

                pieceName = pieceName.toLowerCase();
                if (isValid(pieceName)) {
                    break;
                }
            }

            MonopolyPiece p = new MonopolyPiece(pieceName);
            labs[i] = p;

        }

        frame.dispose();

        players = new Player[num];
        gameboard = new GameBoard(players);
        bank = new Bank(15140 * (this.players.length * 4), this);
        board = new Board();
        manager = new GameManager(gameboard, this, gameboard.getLog(), bank, board);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(labs[i], names[i], gameboard.getLab(), manager, gameboard.getLog(), bank, board);
        }
        gameboard.setManager(manager);
        manager.getG().updatePlayer();
    }

    private boolean isValid(String s) {
        String[] pieces = {"boot", "car", "dog", "hat", "ship", "wheelbarrow", "iron", "thimble"};
        for (String x : pieces) {
            if (x.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public int getNumPlayers() {
        return players.length;
    }

    public boolean isPlayer(String s, Player curr) {
        for (Player p : players) {
            if (p.getName().equals(s) && p != curr) {
                return true;
            }
        }
        return false;
    }

    public GameManager getManager() {
        return this.manager;
    }

    public Player getPlayer(String s) {
        for (Player p : players) {
            if (p.getName().equals(s)) {
                return p;
            }
        }
        return null;
    }

    public int getPlayerIndex(Player p) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(p)) {
                return i;
            }
        }
        return -1;
    }

}
