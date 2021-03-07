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
public class BankruptException extends Exception {

    private GameManager manager;

    public BankruptException(String msg, GameManager manager) {
        super(msg);
        this.manager = manager;
        manager.toPane("You can't do that! You do not have the funds.\nYou mortgage properties or declare bankruptcy");
        manager.getG().setMort(true);
        manager.getG().setEnd(false);
    }

    public void handleBank(Player p, int amountOwed) {
        int difference = amountOwed - p.getBalance();
        boolean canContinue = totalMortgage(p, difference);
        if (!canContinue) {
            return;
        }
        payRemainingBalance(p, manager.getBank());
        p.addDebt(difference);
    }

    public void handleRent(Player paying, Player receiving, int amountOwed) {
        int difference = amountOwed - paying.getBalance();
        boolean canContinue = totalMortgage(paying, difference);
        if (!canContinue) {
            return;
        }
        payRemainingBalance(paying, receiving);
        paying.addDebt(difference);
        manager.printLog("Paying " + receiving.getName() + " for their uncollected rent.");
        manager.getBank().pay(difference, receiving);
        receiving.checkDebt();

    }

    private boolean totalMortgage(Player p, int diff) {
        int tot = 0;
        Property[] properties = manager.getPropArr(p, GameManager.MORTGAGE);
        for (Property prop : properties) {
            tot += prop.getMortgage();
        }
        if (tot < diff) {
            manager.printLog(p.getName() + " is bankrupt.");
            manager.getG().setAllButtonsVisible(false);
            manager.getG().setBankrupt(true);
            return false;
        }
        return true;
    }

    private void payRemainingBalance(Player p, Payable receiving) {
        manager.printLog("Paying " + p.getName() + "'s remaining balance to " + receiving.getName());
        try {
            p.pay(p.getBalance(), receiving);
            if (receiving instanceof Player) {
                ((Player) receiving).checkDebt();
            }
        } catch (BankruptException a) {
            System.out.println("big problem in bankrupt exception(pay remaining balance");
        }
    }
}
