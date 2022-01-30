package hello.itemservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data // 실제 Domain에 사용하기에는 위험 요소가 존재
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
