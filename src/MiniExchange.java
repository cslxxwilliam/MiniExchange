import java.util.*;

import static java.lang.Math.min;

public class MiniExchange {
    private final PriorityQueue<Order> bids = new PriorityQueue<>(new BidOrdersComparator());
    private final PriorityQueue<Order> asks = new PriorityQueue<>(new AskOrdersComparator());

    public List<Fill> add(Order order) {
        if (order.getSide().equals(Side.BUY)) {
            bids.add(order);
        } else {
            asks.add(order);
        }

        List<Fill> matched = new ArrayList<>();
        while (hasMatch()) {
            Order bidPolled = bids.poll();
            Order askPolled = asks.poll();

            Fill currMatch = match(bidPolled, askPolled);
            matched.add(currMatch);

            System.out.println("Fills " + currMatch);

            if (bidPolled != null && bidPolled.isFullyFilled()) {
                bids.add(bidPolled);
            }

            if (askPolled != null && !askPolled.isFullyFilled()) {
                asks.add(askPolled);
            }
        }

        return matched;
    }

    private boolean hasMatch() {
        Order bidPeaked = bids.peek();
        Order askPeaked = asks.peek();

        if (bidPeaked == null || askPeaked == null) {
            return false;
        }

        return bidPeaked.getPx() >= askPeaked.getPx();
    }

    private Fill match(Order bid, Order ask) {
        if (bid == null || ask == null) return null;

        if (bid.getPx() >= ask.getPx()) {
            Fill fill = new Fill(min(bid.getPendingFillQty(), ask.getPendingFillQty()), bid.getPx());
            bid.addFill(fill);
            ask.addFill(fill);
            return fill;
        }

        return null;
    }

}