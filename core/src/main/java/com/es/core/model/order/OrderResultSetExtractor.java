package com.es.core.model.order;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class OrderResultSetExtractor implements ResultSetExtractor<Order> {
    @Override
    public Order extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Order order = null;
        if (resultSet.next()) {
            order = buildOrder(resultSet);
        }

        return order;
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        return Order.builder().
                id(resultSet.getLong("id")).
                secureId(resultSet.getObject("secureId", UUID.class)).
                subtotal(resultSet.getBigDecimal("subtotal")).
                deliveryPrice(resultSet.getBigDecimal("deliveryPrice")).
                totalPrice(resultSet.getBigDecimal("totalPrice")).
                firstName(resultSet.getString("firstName")).
                lastName(resultSet.getString("lastName")).
                deliveryAddress(resultSet.getString("deliveryAddress")).
                contactPhoneNo(resultSet.getString("contactPhoneNo")).
                status(OrderStatus.valueOf(resultSet.getString("status"))).
                build();
    }
}
