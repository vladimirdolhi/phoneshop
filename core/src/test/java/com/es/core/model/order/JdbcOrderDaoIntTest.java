package com.es.core.model.order;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:context/test-applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcOrderDaoIntTest {
    @Resource
    private OrderDao orderDao;
    @Resource
    private PhoneDao phoneDao;

    private Order order;

    public static final Phone PHONE_1 = Phone.builder().id(1L).brand("Google").model("Pixel 7 pro").
            price(BigDecimal.valueOf(1000)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow")))).build();
    public static final Phone PHONE_2 = Phone.builder().id(2L).brand("Google").model("Pixel 6").
            price(BigDecimal.valueOf(460)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow")))).build();


    @Before
    public void setUp() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setPhone(PHONE_1);
        orderItem1.setQuantity(1L);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setPhone(PHONE_2);
        orderItem2.setQuantity(1L);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        order = Order.builder().secureId(UUID.randomUUID()).subtotal(BigDecimal.TEN).
                deliveryPrice(BigDecimal.ZERO).totalPrice(BigDecimal.TEN).creationDate(LocalDateTime.now()).firstName("firstname").
                lastName("lastName").deliveryAddress("deliveryAddress").additionalInfo("additionalInfo").status(OrderStatus.NEW).
                contactPhoneNo("+3757213123").orderItems(orderItems).build();

        phoneDao.save(PHONE_1);
        phoneDao.save(PHONE_2);
    }

    @Test
    public void testSaveAndGetOrderWithItems() {
        orderDao.save(order);

        assertTrue(orderDao.getBySecureId(order.getSecureId()).isPresent());

        Order recievedOrder = orderDao.getBySecureId(order.getSecureId()).get();

        assertEquals("firstname", recievedOrder.getFirstName());

        assertEquals("Pixel 7 pro", recievedOrder.getOrderItems().get(0).getPhone().getModel());

        assertEquals("Pixel 6", recievedOrder.getOrderItems().get(1).getPhone().getModel());

        assertEquals("additionalInfo", recievedOrder.getAdditionalInfo());
    }
}