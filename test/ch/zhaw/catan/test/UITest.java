package test;

import ch.zhaw.catan.UI;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UITest {

    @Test
    void throwDices() {
        for(int index = 0; index <= 100; index++) {
            assertTrue(UI.throwDices() >=1 && UI.throwDices() <=12);
        }
    }

    @Test
    void print() {
        assertEquals("Dies ist ein Test", UI.print("Dies ist ein Test"));
    }

    @Test
    void printList() {
        List<String> testList = Arrays.asList("Dies", "ist", "ein", "Test");
        assertEquals(testList.get(0), UI.printList(testList).get(0));
        assertEquals(testList.get(1), UI.printList(testList).get(1));
        assertEquals(testList.get(2), UI.printList(testList).get(2));
        assertEquals(testList.get(3), UI.printList(testList).get(3));
    }
}