package ch.zhaw.catan;

/**
 * This class handels all transactions with the bank
 */
public class Bank {
    private final static int OFFER_AMOUNT = 4;
    private final static int RETURN_AMOUNT = 1;
    private static ResourceStock resourceStock;

    /**
     * Constructor of the class Bank, initialises a new ResourceStock object to save the ressources
     */
    public Bank() {
        resourceStock = new ResourceStock(Config.INITIAL_RESOURCE_CARDS_BANK);
    }

    public boolean checkResources(Config.Resource ask) {
        return resourceStock.getResources().get(ask) >= RETURN_AMOUNT;
    }

    /**
     * trade 4 to 1 with the bank
     *
     * @param add the resource you want to offer
     * @param ask the ressource you want to have
     */
    public void trade(Config.Resource add, Config.Resource ask) {
        resourceStock.remove(ask, RETURN_AMOUNT);
        resourceStock.add(add, OFFER_AMOUNT);
    }
}