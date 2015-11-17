package sample;

import java.math.BigDecimal;

/**
 * 打印商品信息
 *
 * @author wei_jc
 * @since 1.0
 */
public class Product {
    private String name;
    private BigDecimal price;
    private int amount;

    public Product() {
    }

    public Product(String name, double price, int amount) {
        this.name = name;
        this.price = new BigDecimal(price);
        this.amount = amount;
    }

    public String getName() {
        if (name.getBytes().length > 34) {
            return UtilString.substringByByte(name, 0, 34) + "...";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
