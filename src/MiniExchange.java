import java.util.*;

import static java.lang.Math.max;
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
        boolean moreToMatch = true;
        while (moreToMatch) {
            Order bidPeaked = bids.peek();
            Order askPeaked = asks.peek();

            if (bidPeaked == null || askPeaked == null) {
                break;
            }

            Order bidPolled = bids.poll();
            Order askPolled = asks.poll();

            List<Fill> currMatch = match(bidPolled, askPolled);
            matched.addAll(currMatch);

            if (currMatch.isEmpty()) {
                moreToMatch = false;
            }

            System.out.println("Fills " + currMatch);

            //create the unfilled orders for further matching
            Order pendingFillBidOrder = calculatePendingFill(bidPolled, currMatch);

            if (pendingFillBidOrder != null) {
                bids.add(pendingFillBidOrder);
            }

            //create the unfilled orders for further matching
            Order pendingFillAskOrder = calculatePendingFill(askPolled, currMatch);

            if (pendingFillAskOrder != null) {
                asks.add(pendingFillAskOrder);
            }
        }

        return matched;
    }

    private Order calculatePendingFill(Order origOrder, List<Fill> matched) {
        int pendingFillQty = calculatePendingFillQty(origOrder.getQty(), matched);

        if (pendingFillQty == 0) {
            return null;
        }
        return new Order(origOrder.getOrdId(), origOrder.getSide(), origOrder.getPx(),
                pendingFillQty);
    }

    private int calculatePendingFillQty(int qty, List<Fill> matched) {
        if (matched == null || matched.isEmpty()) {
            return qty;
        }

        System.out.printf("Original qty:%s, matched qty:%s\n", qty, matched);
        return max(qty - matched.get(0).getQty(), 0);
    }

    private List<Fill> match(Order bid, Order ask) {
        if (bid == null || ask == null) return Collections.emptyList();

        if (bid.getPx() >= ask.getPx()) {
            ArrayList<Fill> fills = new ArrayList<>();
            fills.add(new Fill(min(bid.getQty(), ask.getQty()), bid.getPx()));
            return fills;
        }
        return Collections.emptyList();
    }

}