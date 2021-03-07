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
public class Bank implements Payable {

    private int money;
    private final Game game;

    public Bank(int money, Game g) {
        this.money = money;
        this.game = g;
    }

    @Override
    public void pay(int amount, Payable receiving) {
        game.getManager().printLog("*******Before********");
        game.getManager().printLog(balanceToString((Player) receiving));
        receiving.setBalance(receiving.getBalance() + amount);
        money -= amount;
        game.getManager().printLog(receiving.getName() + " received $" + amount + " from the bank.");
        game.getManager().printLog("*******After********");
        game.getManager().printLog(balanceToString((Player) receiving));
    }

    @Override
    public int getBalance() {
        return this.money;
    }

    @Override
    public void setBalance(int amount) {
        this.money = amount;
    }

    @Override
    public String getName() {
        return "The bank";
    }

    /**
     *
     * @param p
     * @return
     */
    @Override
    public String balanceToString(Player p) {
        return (p.getName() + "'s balance: " + p.getBalance());
    }
}
