/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * @author Robert Pierce
 */
public interface Payable {

    /**
     *
     * @param amount
     * @param paying
     * @param receiving
     * @throws BankruptException
     */
    public void pay(int amount,Payable receiving)throws BankruptException;
    public int getBalance();
    public void setBalance(int amount);
    public String getName();

    public String balanceToString(Player player);
}
