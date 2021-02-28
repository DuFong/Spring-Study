package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문()
    {
        Member member = new Member();
        member.setName("Customer");
        member.setAddress(new Address("인천", "부평로", "159"));
        em.persist(member);

        Book book = new Book();
        book.setName("실전 JPA");
        book.setPrice(30000);
        book.setStockquantity(10);
        book.setAuthor("김영한");
        book.setIsbn("159");
        em.persist(book);

        int itemCount = 3;

        Long getOrderId = orderService.order(member.getId(), book.getId(), itemCount);

        Order getOrder = orderRepository.findOne(getOrderId);

        assertEquals("상품 주문 상태", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문 종류 수 확인", 1, getOrder.getOrderItems().size());
        assertEquals("주문 금액 확인", 30000 * itemCount, getOrder.geTotalPrice());
        assertEquals("재고 수량 확인", 7, book.getStockquantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 주문시수량초과(){
        Member member = new Member();
        member.setName("Greed");
        member.setAddress(new Address("서울", "강남로", "777"));
        em.persist(member);

        Book book = new Book();
        book.setName("JPA 실전 압축 2");
        book.setStockquantity(10);
        book.setPrice(20000);
        em.persist(book);

        int orderCount = 12;

        orderService.order(member.getId(), book.getId(), orderCount);

        fail("재고 수량 부족 예외가 나타나야 한다.");
    }

    @Test
    public void 주문취소()
    {
        Member member = new Member();
        member.setName("Greed");
        member.setAddress(new Address("서울", "강남로", "777"));
        em.persist(member);

        Book book = new Book();
        book.setName("JPA 실전 압축 2");
        book.setStockquantity(10);
        book.setPrice(20000);
        em.persist(book);

        int orderCount = 5;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);


        assertEquals("주문 상태 확인", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("아이템 재고 수량 확인", 10, book.getStockquantity());
    }


}