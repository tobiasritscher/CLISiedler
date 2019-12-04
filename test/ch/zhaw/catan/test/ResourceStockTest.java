package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.ResourceStock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ResourceStockTest {

    private ResourceStock testResourceStock;
    private Map<Config.Resource, Integer> testResourceMap;

    @BeforeEach
    void setUp() {
        testResourceStock = new ResourceStock();
        testResourceMap = new HashMap<>();
        testResourceMap.put(Config.Resource.GRAIN, 1);
        testResourceMap.put(Config.Resource.WOOD, 1);
        testResourceMap.put(Config.Resource.WOOL, 1);
        testResourceMap.put(Config.Resource.STONE, 1);
        testResourceMap.put(Config.Resource.CLAY, 1);
    }

    @Test
    void getResources() {
        testResourceStock.add(Config.Resource.GRAIN, 1);
        testResourceStock.add(Config.Resource.WOOD, 1);
        testResourceStock.add(Config.Resource.WOOL, 1);
        testResourceStock.add(Config.Resource.STONE, 1);
        testResourceStock.add(Config.Resource.CLAY, 1);

        Assertions.assertEquals(testResourceStock.getResources(), testResourceMap);
    }

    @Test
    void available() {
        testResourceStock.add(Config.Resource.GRAIN, 1);
        // Positive Test
        Assertions.assertTrue(testResourceStock.available(Config.Resource.GRAIN, 1));
        // Negative Test
        Assertions.assertFalse(testResourceStock.available(Config.Resource.WOOD, 1));
    }

    @Test
    void add() {
        testResourceStock.add(Config.Resource.GRAIN, 1);
        Assertions.assertEquals("{GR=1}", testResourceStock.getResources().toString());
    }

    @Test
    void remove() {
        // Positive test
        testResourceStock.add(Config.Resource.GRAIN, 2);
        testResourceStock.remove(Config.Resource.GRAIN, 1);
        Assertions.assertEquals("{GR=1}", testResourceStock.getResources().toString());

        // Negative test, remove method isn't expected to do anything if amount to remove exceeds existing amount
        testResourceStock.remove(Config.Resource.GRAIN, 2);
        Assertions.assertEquals("{GR=1}", testResourceStock.getResources().toString());
    }
}