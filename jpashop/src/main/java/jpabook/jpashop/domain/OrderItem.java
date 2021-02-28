package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    protected OrderItem() {}  // *** -> 밑의 createOrderItem 메소드로만 객체 생성가능하게 하려고 !!

    //== 생성 로직 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    //== 비지니스 로직 ==//
    public void cancel() {
        getItem().addStock(count);
    }

    //== 조회 로직 ==//
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
