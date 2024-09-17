import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String ordId;
    private final Side side;
    private final double px;
    private final int qty;
    private final List<Object> fills = new ArrayList<>();
    private double filledQty=0;
    private double pendingFillQty;

    public Order(String ordId, Side side, double px, int qty) {
        this.ordId = ordId;
        this.side = side;
        this.px = px;
        this.qty = qty;
        this.pendingFillQty = qty;
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

    public boolean isFullyFilled() {
        return filledQty==qty;
    }

    public void addFill(Fill fill) {
        this.fills.add(fill);
        filledQty = filledQty + fill.getQty();
        pendingFillQty = pendingFillQty -fill.getQty();
    }

    public double getPendingFillQty() {
        return pendingFillQty;
    }
}
