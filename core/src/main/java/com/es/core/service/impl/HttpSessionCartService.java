package com.es.core.service.impl;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpSessionCartService implements CartService {

    private final Cart cart;
    private final PhoneService phoneService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long requestedQuantity) {

        Phone phone = phoneService.get(phoneId);
        Optional<CartItem> cartItemOptional = getCartItem(phone.getId());

        long cartQuantity = cartItemOptional.map(CartItem::getQuantity).orElse(0L);
        long newCartQuantity = cartQuantity + requestedQuantity;

        Stock stock = phoneService.getStock(phone.getId());

        if (newCartQuantity > stock.getStock() - stock.getReserved()){
            throw new OutOfStockException("Requested quantity: " + requestedQuantity +
                    ", available: " + (stock.getStock() - stock.getReserved()));
        }

        if (cartItemOptional.isPresent()){
            cartItemOptional.get().setQuantity(cartQuantity + requestedQuantity);
        } else {
            cart.getItems().add(new CartItem(phone, requestedQuantity));
        }

        recalculateCart();
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private Optional<CartItem> getCartItem(Long id){
        return cart.getItems().stream().
                filter(cartItem -> cartItem.getPhone().getId().equals(id)).findAny();
    }

    private void recalculateCart(){
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity).mapToLong(q -> q).sum());
        cart.setTotalCost(BigDecimal.valueOf(cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPhone().getPrice().doubleValue())
                .sum()));
    }

}
