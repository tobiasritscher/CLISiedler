package test;

import ch.zhaw.catan.UI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UITest {

    private UI testUI;

    @BeforeEach
    void setUp() {
        testUI = new UI();
    }

    @Test
    void printBoard() {
        assertEquals(testUI.getBoard(), testUI.printBoard(testUI.getBoard()));
    }

    @Test
    void throwDices() {
        for(int index = 0; index <= 100; index++) {
            assertTrue(testUI.throwDices() >=1 && testUI.throwDices() <=12);
        }
    }

    @Test
    void print() {
        assertEquals("Dies ist ein Test", testUI.print("Dies ist ein Test"));
    }

    @Test
    void printList() {
        List<String> testList = Arrays.asList("Dies", "ist", "ein", "Test");
        assertEquals(testList.get(0), testUI.printList(testList).get(0));
        assertEquals(testList.get(1), testUI.printList(testList).get(1));
        assertEquals(testList.get(2), testUI.printList(testList).get(2));
        assertEquals(testList.get(3), testUI.printList(testList).get(3));
    }
}