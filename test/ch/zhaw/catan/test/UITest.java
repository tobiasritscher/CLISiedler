package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.UI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class UITest {

    private UI testUI;

    @BeforeEach
    void setUp() {
        testUI = new UI();
    }

    @Test
    void initSiedlerBoard() {
    }

    @Test
    void printBoard() {
        Assertions.assertEquals(testUI.getBoard(), testUI.printBoard(testUI.getBoard()));
    }

    @Test
    void initBoard() {
    }

    @Test
    void tradeAsk() {
    }

    @Test
    void tradeBid() {
    }

    @Test
    void resourceChoice() {
        Assertions.assertEquals(Config.Resource.GRAIN, testUI.resourceChoice("GRAIN"));
    }

    @Test
    void newLine() {
    }

    @Test
    void throwDices() {
        for(int index = 0; index >= 100; index++) {
            Assertions.assertTrue(testUI.throwDices()>=1 && testUI.throwDices()<=12);
        }
    }

    @Test
    void setBookmark() {
    }

    @Test
    void resetBookmark() {
    }

    @Test
    void print() {
        Assertions.assertEquals("Dies ist ein Test", testUI.print("Dies ist ein Test"));
    }

    @Test
    void printList() {
        List<String> testList = Arrays.asList("Dies", "ist", "ein", "Test");
        Assertions.assertEquals(testList.get(0), testUI.printList(testList).get(0));
        Assertions.assertEquals(testList.get(1), testUI.printList(testList).get(1));
        Assertions.assertEquals(testList.get(2), testUI.printList(testList).get(2));
        Assertions.assertEquals(testList.get(3), testUI.printList(testList).get(3));
    }

    @Test
    void getEnumValue() {
    }
}