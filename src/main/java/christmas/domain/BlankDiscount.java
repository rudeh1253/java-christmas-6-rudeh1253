package christmas.domain;

public class BlankDiscount extends Discount {

    private BlankDiscount() {
        super(null, null);
    }

    static Discount generateInstance() {
        return new BlankDiscount();
    }
}
