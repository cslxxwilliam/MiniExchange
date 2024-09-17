import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MiniExchangeTest {
    @Test
    public void basicInputTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 4.9, 100);
        Order order2 = new Order("B2", Side.BUY, 5.0, 100);
        miniExchange.add(order1);
        List<Fill> fills = miniExchange.add(order2);

        assertEquals(fills.size(), 0);
    }

    @Test
    public void basicFullyFilledMatchingTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 5.1, 100);
        Order order2 = new Order("S1", Side.SELL, 5.0, 100);

        List<Fill> fillsAfterB1 = miniExchange.add(order1);
        List<Fill> fillsAfterS1 = miniExchange.add(order2);

        assertEquals(fillsAfterB1.size(), 0);
        assertEquals(fillsAfterS1.size(), 1);

        Fill fill = fillsAfterS1.get(0);
        assertEquals(fill.getPx(), 5.1, 0.001);
        assertEquals(fill.getQty(), 100);
    }

    @Test
    public void basicPartialFilledMatchingTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 5.1, 50);
        Order order2 = new Order("S1", Side.SELL, 5.0, 100);

        List<Fill> fillsAfterB1 = miniExchange.add(order1);
        List<Fill> fillsAfterS1 = miniExchange.add(order2);

        assertEquals(fillsAfterB1.size(), 0);
        assertEquals(fillsAfterS1.size(), 1);

        Fill fill = fillsAfterS1.get(0);
        assertEquals(fill.getPx(), 5.1, 0.001);
        assertEquals(fill.getQty(), 50);
    }

    @Test
    public void basicMatchingWithMultipleOrdersMatchingTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 5.1, 50);
        Order order2 = new Order("S1", Side.SELL, 5.0, 100);
        Order order3 = new Order("B2", Side.BUY, 5.1, 50);

        List<Fill> fillsAfterB1 = miniExchange.add(order1);
        List<Fill> fillsAfterS1 = miniExchange.add(order2);
        List<Fill> fillsAfterB2 = miniExchange.add(order3);

        assertEquals(0, fillsAfterB1.size());
        assertEquals(1, fillsAfterS1.size());

        assertEquals(5.1, fillsAfterS1.get(0).getPx(), 0.001);
        assertEquals(50, fillsAfterS1.get(0).getQty());

        assertEquals(1, fillsAfterB2.size());
        assertEquals(5.1, fillsAfterB2.get(0).getPx(), 0.001);
        assertEquals(50, fillsAfterB2.get(0).getQty());
    }

    @Test
    public void oneSellOrdersHittingMultipleBidOrderMatchingTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 5.1, 50);
        Order order2 = new Order("S1", Side.SELL, 5.0, 100);
        Order order3 = new Order("B2", Side.BUY, 5.1, 50);

        List<Fill> fillsAfterB1 = miniExchange.add(order1);
        List<Fill> fillsAfterS1 = miniExchange.add(order2);
        List<Fill> fillsAfterB2 = miniExchange.add(order3);

        assertEquals(0, fillsAfterB1.size());
        assertEquals(1, fillsAfterS1.size());

        assertEquals(5.1, fillsAfterS1.get(0).getPx(), 0.001);
        assertEquals(50, fillsAfterS1.get(0).getQty());

        assertEquals(1, fillsAfterB2.size());
        assertEquals(5.1, fillsAfterB2.get(0).getPx(), 0.001);
        assertEquals(50, fillsAfterB2.get(0).getQty());
    }

    @Test
    public void oneBidOrdersHittingMultipleAskOrderLevelTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 5.0, 50);
        Order order2 = new Order("B2", Side.BUY, 5.1, 30);
        Order order3 = new Order("S1", Side.SELL, 5.0, 100);

        List<Fill> fillsAfterB1 = miniExchange.add(order1);
        List<Fill> fillsAfterB2 = miniExchange.add(order2);
        List<Fill> fillsAfterS1 = miniExchange.add(order3);

        assertEquals(0, fillsAfterB1.size());
        assertEquals(0, fillsAfterB2.size());

        assertEquals(2, fillsAfterS1.size());

        assertEquals(5.1, fillsAfterS1.get(0).getPx(), 0.001);
        assertEquals(30, fillsAfterS1.get(0).getQty());

        assertEquals(5.0, fillsAfterS1.get(1).getPx(), 0.001);
        assertEquals(50, fillsAfterS1.get(1).getQty());
    }

    @Test
    public void pricesAreTheSameAndMatchedBasedOnEntryTimeTest() {
        MiniExchange miniExchange = new MiniExchange();

        Order order1 = new Order("B1", Side.BUY, 5.1, 50);
        Order order2 = new Order("B2", Side.BUY, 5.1, 30);
        Order order3 = new Order("S1", Side.SELL, 5.0, 100);

        List<Fill> fillsAfterB1 = miniExchange.add(order1);
        List<Fill> fillsAfterB2 = miniExchange.add(order2);
        List<Fill> fillsAfterS1 = miniExchange.add(order3);

        assertEquals(0, fillsAfterB1.size());
        assertEquals(0, fillsAfterB2.size());

        assertEquals(2, fillsAfterS1.size());

        assertEquals(5.1, fillsAfterS1.get(0).getPx(), 0.001);
        assertEquals(50, fillsAfterS1.get(0).getQty());

        assertEquals(5.1, fillsAfterS1.get(1).getPx(), 0.001);
        assertEquals(30, fillsAfterS1.get(1).getQty());
    }

    @Test
    public void onlyTwoDecimalPlacesSupportedTest() {

    }
}
