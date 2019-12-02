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
    public Bank(){
        resourceStock = new ResourceStock(Config.INITIAL_RESOURCE_CARDS_BANK);
    }

    /**
     * trade 4 to 1 with the bank
     * @param offer the resource you want to offer
     * @param want the ressource you want to have
     * @return true if the trade was all right and if it is done
     */
    public boolean trade(Config.Resource offer, Config.Resource want){
        boolean result = resourceStock.remove(want, RETURN_AMOUNT);
        if(result){
            resourceStock.add(offer, OFFER_AMOUNT);
        }
        return result;
    }
}