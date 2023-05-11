package com.es.core.model.order;

import com.es.core.exception.ProductNotFoundException;
import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcOrderDao implements OrderDao {

    private static final String FIND_ORDER_BY_ID = "select * from orders where secureId = ?";

    private static final String FIND_ORDER_ITEMS_BY_ORDER_ID = "select * from orders_items where orderId = ?";

    private static final String INSERT_ORDER = "insert into orders (secureId, subtotal, deliveryPrice, totalPrice, creationDay," +
            "firstName, lastName, deliveryAddress, contactPhoneNo, status, additionalInfo) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_ITEM = "insert into orders_items (orderId, phoneId, quantity) " +
            "VALUES (?, ?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private OrderResultSetExtractor orderResultSetExtractor;
    @Resource
    private OrderItemsResultSetExtractor orderItemsResultSetExtractor;

    @Override
    public Optional<Order> getBySecureId(UUID uuid) {

        Order order = jdbcTemplate.query(FIND_ORDER_BY_ID, orderResultSetExtractor, uuid);

        List<OrderItem> orderItems = jdbcTemplate.query(FIND_ORDER_ITEMS_BY_ORDER_ID,
                orderItemsResultSetExtractor, uuid);

        orderItems.forEach(orderItem -> orderItem.setPhone(phoneDao.get(orderItem.getPhone().getId())
                .orElseThrow(() -> new ProductNotFoundException("Phone with id = " + orderItem.getPhone().getId() + " not found"))));

        order.setOrderItems(orderItems);

        return Optional.of(order);
    }

    @Override
    public void save(Order order) {
        jdbcTemplate.update(INSERT_ORDER, order.getSecureId(), order.getSubtotal(), order.getDeliveryPrice(),
                order.getTotalPrice(), order.getCreationDate(), order.getFirstName(), order.getLastName(),
                order.getDeliveryAddress(), order.getContactPhoneNo(), order.getStatus().toString(), order.getAdditionalInfo()
        );

        List<OrderItem> orderItems = order.getOrderItems();

        jdbcTemplate.batchUpdate(INSERT_ORDER_ITEM,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setObject(1, order.getSecureId());
                        preparedStatement.setLong(2, orderItems.get(i).getPhone().getId());
                        preparedStatement.setLong(3, orderItems.get(i).getQuantity());

                    }

                    @Override
                    public int getBatchSize() {
                        return orderItems.size();
                    }
                }

        );
    }
}
