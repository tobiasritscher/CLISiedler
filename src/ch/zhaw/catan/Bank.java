package ch.zhaw.catan;

public class Bank {
    private final static int OFFER_AMOUNT = 4;
    private final static int RETURN_AMOUNT = 1;
    private static ResourceStock resourceStock;

    public Bank(){
        resourceStock = new ResourceStock(Config.INITIAL_RESOURCE_CARDS_BANK);
    }

    public boolean trade(Config.Resource offer, Config.Resource want){
        boolean result = resourceStock.remove(want, RETURN_AMOUNT);
        if(result){
            resourceStock.add(offer, OFFER_AMOUNT);
        }
        return result;
    }
}