package ch.zhaw.catan;

public class Settlement {
    private int winPoints = 1;
    private boolean isCity = false;

    public void setToCity(){
        winPoints = 2;
        isCity = true;
    }
}
