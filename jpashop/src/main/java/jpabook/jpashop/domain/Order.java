package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // -> casecade 때문에 Order만 persist해도 나머지 OrderItem도 persist!!
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // -> casecade 때문에 Order만 persist해도 나머지 delivery도 persist!!
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // hibernate가 자동 지원

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    // createOrder() 메소드로만 객체 생성 허용기능
    protected Order() {}


    //=== 연관관계 편의 메소드===//  양방향관계일 때 한번에 양쪽에 객체를 넣어주기 위함! 편하자고~
    public void setMember(Member member)
    {
        member.getOrders().add(this);
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem)
    {
        orderItem.setOrder(this);
        this.orderItems.add(orderItem);
    }

    public void setDelivery(Delivery delivery)
    {
        delivery.setOrder(this);
        this.delivery = delivery;
    }


    //== 생성 메소드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems)
    {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        for (OrderItem orderItem: orderItems)
        {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    //== 비지니스 로직 ==//
    public void cancel()
    {
        if(delivery.getStatus() == DeliveryStatus.COMP)
        {
            throw new IllegalStateException("이미 배송완료 되어 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : this.orderItems)
        {
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==//
    public int geTotalPrice()
    {
        int totalPrice = 0;
        for(OrderItem orderItem : this.orderItems)
        {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
