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
public class JdbcOrderDaoTest {
    @Resource
    private OrderDao orderDao;
    @Resource
    private PhoneDao phoneDao;

    private Order order;

    public static final Phone PHONE = Phone.builder().id(1L).brand("Google").model("Pixel 7 pro").
            price(BigDecimal.valueOf(1000)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow")))).build();


    @Before
    public void setUp() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPhone(PHONE);
        orderItem.setQuantity(1L);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order = Order.builder().secureId(UUID.randomUUID()).subtotal(BigDecimal.TEN).
                deliveryPrice(BigDecimal.ZERO).totalPrice(BigDecimal.TEN).creationDate(LocalDateTime.now()).firstName("firstname").
                lastName("lastName").deliveryAddress("deliveryAddress").additionalInfo("additionalInfo").status(OrderStatus.NEW).
                contactPhoneNo("+3757213123").orderItems(orderItems).build();

        phoneDao.save(PHONE);
    }

    @Test
    public void testSaveAndGetOrderWithItems() {
        orderDao.save(order);
        assertTrue(orderDao.getBySecureId(order.getSecureId()).isPresent());
        assertEquals("firstname", orderDao.getBySecureId(order.getSecureId()).get().getFirstName());
        assertEquals("Google", orderDao.getBySecureId(order.getSecureId()).get().
                getOrderItems().get(0).getPhone().getBrand());
    }
}