package com.es.core.model.order;

import com.es.core.exception.ProductNotFoundException;
import com.es.core.model.phone.*;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    private static final String FIND_ORDER_BY_SECURE_ID = "select * from orders where secureId = ?";
    private static final String FIND_ORDER_BY_ID = "select * from orders where id = ?";
    private static final String FIND_ORDER_ITEMS_BY_ORDER_SECURE_ID = "select * from orders_items where orderId = ?";
    private static final String FIND_ORDER_ITEMS_BY_ORDER_ID = "select * from orders_items where id = ?";

    private static final String INSERT_ORDER = "insert into orders (secureId, subtotal, deliveryPrice, totalPrice, creationDate," +
            "firstName, lastName, deliveryAddress, contactPhoneNo, status, additionalInfo) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL_ORDERS = "select * from orders order by creationDate desc";

    private static final String INSERT_ORDER_ITEM = "insert into orders_items (orderId, phoneId, quantity) " +
            "VALUES (?, ?, ?)";

    private static final String UPDATE_ORDER_STATUS = "update orders set status = ? where id = ?";


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private OrderResultSetExtractor orderResultSetExtractor;
    @Resource
    private OrderItemsResultSetExtractor orderItemsResultSetExtractor;

    @Override
    public Optional<Order> getById(Long id) {
        Order order = jdbcTemplate.query(FIND_ORDER_BY_ID, orderResultSetExtractor, id);

        if (order != null && order.getSecureId() != null){
            List<OrderItem> orderItems = jdbcTemplate.query(FIND_ORDER_ITEMS_BY_ORDER_SECURE_ID,
                    orderItemsResultSetExtractor, order.getSecureId());

            orderItems.forEach(orderItem -> orderItem.setPhone(phoneDao.get(orderItem.getPhone().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Phone with id = " + orderItem.getPhone().getId() + " not found"))));

            order.setOrderItems(orderItems);
        }

        return Optional.ofNullable(order);
    }

    @Override
    public Optional<Order> getBySecureId(UUID uuid) {

        Order order = jdbcTemplate.query(FIND_ORDER_BY_SECURE_ID, orderResultSetExtractor, uuid);

        if (order != null && order.getSecureId() != null){
            List<OrderItem> orderItems = jdbcTemplate.query(FIND_ORDER_ITEMS_BY_ORDER_SECURE_ID,
                    orderItemsResultSetExtractor, uuid);

            orderItems.forEach(orderItem -> orderItem.setPhone(phoneDao.get(orderItem.getPhone().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Phone with id = " + orderItem.getPhone().getId() + " not found"))));

            order.setOrderItems(orderItems);
        }

        return Optional.ofNullable(order);
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

    @Override
    public void updateStatus(Long id, OrderStatus orderStatus) {
        jdbcTemplate.update(UPDATE_ORDER_STATUS, orderStatus.name(), id);
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(FIND_ALL_ORDERS, new BeanPropertyRowMapper<>(Order.class));
    }

}