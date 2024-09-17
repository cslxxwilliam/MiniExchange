import java.util.Comparator;

/*** -1: o1 less than o2 in ordering
 * 0: o1 equals o2
 * 1: o1 larger than o2
 * **/

class BidOrdersComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPx() == o2.getPx()) {
            return 0;
        }

        return o1.getPx() > o2.getPx() ? -1 : 1;
    }
}