import java.util.Comparator;

class AskOrdersComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPx() == o2.getPx()) {
            return 0;
        }

        return o1.getPx() < o2.getPx() ? -1 : 1;
    }
}