package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:context/test-applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcPhoneDaoIntTest {

    @Resource
    PhoneDao phoneDao;

    public static final Phone PHONE_1 = Phone.builder().id(1L).brand("Google").model("Pixel 7 pro").
            price(BigDecimal.valueOf(1000)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow")))).build();
    public static final Phone PHONE_2 = Phone.builder().id(2L).brand("Xiaomi").model("Xiaomi 13 Lite").
            price(BigDecimal.valueOf(650)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1004L, "Red")))).build();

    public static final Phone PHONE_3 = Phone.builder().id(3L).brand("Samsung").model("Galaxy S23").
            price(BigDecimal.valueOf(1300)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1006L, "Gray")))).build();

    public static final Phone PHONE_4 = Phone.builder().id(4L).brand("Apple").model("Iphone 12").
            price(BigDecimal.valueOf(750)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow"),
                    new Color(1005L, "Purple")))).build();

    @Test
    public void testSaveAndGetPhone() {
        phoneDao.save(PHONE_1);
        assertTrue(phoneDao.get(1L).isPresent());
        assertEquals("Pixel 7 pro", phoneDao.get(1L).get().getModel());
    }

    @Test
    public void testFindAllPhonesWithOffsetAndLimit() {
        phoneDao.save(PHONE_2);
        phoneDao.save(PHONE_3);
        assertEquals(14, phoneDao.findAll(0, 15).size());
        assertEquals(9, phoneDao.findAll(5, 9).size());
        assertEquals(0, phoneDao.findAll(0, 0).size());


    }

    @Test
    public void testSavePhoneWithColors() {
        phoneDao.save(PHONE_4);
        Phone addedPhone = phoneDao.get(PHONE_4.getId()).get();
        assertEquals(PHONE_4.getColors(), addedPhone.getColors());
    }

}