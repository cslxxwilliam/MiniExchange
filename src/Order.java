public class Order {
    private final String ordId;
    private final Side side;
    private final double px;
    private final int qty;

    public Order(String ordId, Side side, double px, int qty) {
        this.ordId = ordId;
        this.side = side;
        this.px = px;
        this.qty = qty;
    }

    public Side getSide() {
        return side;
    }

    public double getPx() {
        return px;
    }

    public int getQty() {
        return qty;
    }

    public String getOrdId() {
        return ordId;
    }
}
