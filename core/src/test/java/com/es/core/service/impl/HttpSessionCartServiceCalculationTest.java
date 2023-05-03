package com.es.core.service.impl;

import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceCalculationTest {

    private final Cart cart = new Cart();

    @Mock
    private Phone PHONE_1;
    private final Long PHONE_1_ID = 1L;
    private final Long PHONE_1_PRICE = 350L;
    private final Integer PHONE_1_STOCK = 10;
    private final Integer PHONE_1_RESERVED = 3;
    private final Long PHONE_1_ADD_QUANTITY = 3L;
    private final Long PHONE_1_UPDATE_QUANTITY = 5L;


    @Mock
    private Phone PHONE_2;
    private final Long PHONE_2_ID = 2L;
    private final Long PHONE_2_PRICE = 200L;
    private final Integer PHONE_2_STOCK = 100;
    private final Integer PHONE_2_RESERVED = 55;
    private final Long PHONE_2_ADD_QUANTITY = 1L;
    private final Long PHONE_2_UPDATE_QUANTITY = 2L;


    private PhoneServiceImpl phoneService = mock(PhoneServiceImpl.class);

    private HttpSessionCartService cartService = new HttpSessionCartService(cart, phoneService);

    @Before
    public void setup(){
        when(PHONE_1.getId()).thenReturn(PHONE_1_ID);
        when(PHONE_1.getPrice()).thenReturn(new BigDecimal(PHONE_1_PRICE));
        when(phoneService.get(PHONE_1_ID)).thenReturn(PHONE_1);
        when(phoneService.getStock(PHONE_1_ID)).thenReturn(new Stock(PHONE_1,  PHONE_1_STOCK, PHONE_1_RESERVED));

        when(PHONE_2.getId()).thenReturn(PHONE_2_ID);
        when(PHONE_2.getPrice()).thenReturn(new BigDecimal(PHONE_2_PRICE));
        when(phoneService.get(PHONE_2_ID)).thenReturn(PHONE_2);
        when(phoneService.getStock(PHONE_2_ID)).thenReturn(new Stock(PHONE_2,  PHONE_2_STOCK, PHONE_2_RESERVED));
    }


    @Test
    public void testAddToCart(){
        cartService.addPhone(PHONE_1_ID, PHONE_1_ADD_QUANTITY);
        cartService.addPhone(PHONE_2_ID, PHONE_2_ADD_QUANTITY);

        long exceptedTotalCost = PHONE_1_PRICE * PHONE_1_ADD_QUANTITY + PHONE_2_PRICE * PHONE_2_ADD_QUANTITY;
        assertEquals(exceptedTotalCost, cart.getTotalCost().longValue());

        long exceptedQuantity = PHONE_1_ADD_QUANTITY + PHONE_2_ADD_QUANTITY;
        assertEquals(exceptedQuantity, cart.getTotalQuantity());
    }

    @Test
    public void testInitialCartParameters(){
        assertEquals(0, cart.getTotalQuantity());
        assertEquals(0, cart.getTotalCost().longValue());
    }

    @Test
    public void testUpdateCart(){
        cartService.addPhone(PHONE_1_ID, PHONE_1_ADD_QUANTITY);
        cartService.addPhone(PHONE_2_ID, PHONE_2_ADD_QUANTITY);
        HashMap<Long, Long> updateItems = new HashMap<>();
        updateItems.put(PHONE_1_ID, PHONE_1_UPDATE_QUANTITY);
        updateItems.put(PHONE_2_ID, PHONE_2_UPDATE_QUANTITY);

        cartService.update(updateItems);

        long exceptedTotalCost = PHONE_1_PRICE * PHONE_1_UPDATE_QUANTITY + PHONE_2_PRICE * PHONE_2_UPDATE_QUANTITY;
        assertEquals(exceptedTotalCost, cart.getTotalCost().longValue());

        long exceptedQuantity = PHONE_1_UPDATE_QUANTITY + PHONE_2_UPDATE_QUANTITY;
        assertEquals(exceptedQuantity, cart.getTotalQuantity());
    }

    @Test
    public void testDeleteFromCart(){
        cartService.addPhone(PHONE_1_ID, PHONE_1_ADD_QUANTITY);
        cartService.addPhone(PHONE_2_ID, PHONE_2_ADD_QUANTITY);
        cartService.remove(PHONE_2_ID);

        long exceptedTotalCost = PHONE_1_PRICE * PHONE_1_ADD_QUANTITY;
        assertEquals(exceptedTotalCost, cart.getTotalCost().longValue());

        long exceptedQuantity = PHONE_1_ADD_QUANTITY ;
        assertEquals(exceptedQuantity, cart.getTotalQuantity());

    }


}