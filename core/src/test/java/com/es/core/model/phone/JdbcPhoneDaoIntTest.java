package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:context/test-applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcPhoneDaoIntTest {

    @Resource
    private PhoneDao phoneDao;

    public static final Phone PHONE_1 = Phone.builder().id(1L).brand("Google").model("Pixel 7 pro").
            price(BigDecimal.valueOf(1000)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow")))).build();

    public static final Phone PHONE_2 = Phone.builder().id(2L).brand("Google").model("Pixel 6").
            price(BigDecimal.valueOf(460)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow")))).build();

    public static final Phone PHONE_3 = Phone.builder().id(3L).brand("Xiaomi").model("Xiaomi 13 Lite").
            price(BigDecimal.valueOf(650)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1004L, "Red")))).build();

    public static final Phone PHONE_4 = Phone.builder().id(4L).brand("Samsung").model("Galaxy S23").
            price(BigDecimal.valueOf(1300)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1006L, "Gray")))).build();

    public static final Phone PHONE_5 = Phone.builder().id(5L).brand("Apple").model("Iphone 12").
            price(BigDecimal.valueOf(750)).colors(new HashSet<>(Arrays.asList(new Color(1000L, "Black"),
                    new Color(1001L, "White"), new Color(1002L, "Yellow"),
                    new Color(1005L, "Purple")))).build();

    @Test
    public void testSaveAndGetPhone() {
        phoneDao.save(PHONE_5);
        assertTrue(phoneDao.get(5L).isPresent());
        assertEquals("Iphone 12", phoneDao.get(5L).get().getModel());
    }

    @Test
    public void testFindPhonesWithQuery() {
        phoneDao.save(PHONE_1);
        phoneDao.save(PHONE_2);
        String query = "Google";
        List<Phone> phoneList = phoneDao.findAll(query, null, null, false, 0, 100);

        for(Phone phone : phoneList){
            assertTrue(phone.getBrand().contains(query) || phone.getModel().contains(query));
        }
    }

    @Test
    public void testSortPhonesByPrice() {
        List<Phone> allPhonesList = phoneDao.findAll("", null, null, true, 0, 100);
        List<Phone> sortedPhonesList = phoneDao.findAll("", SortField.PRICE, SortOrder.ASC, true, 0, 100);

        assertEquals(allPhonesList.stream().sorted(Comparator.comparing(Phone::getPrice)).collect(Collectors.toList()), sortedPhonesList);
    }

    @Test
    public void testSortPhonesByBrand() {
        List<Phone> allPhonesList = phoneDao.findAll("", null, null, true, 0, 100);
        List<Phone> sortedPhonesList = phoneDao.findAll("", SortField.BRAND, SortOrder.ASC, true, 0, 100);

        assertEquals(allPhonesList.stream().sorted(Comparator.comparing(Phone::getBrand)).collect(Collectors.toList()), sortedPhonesList);
    }

    @Test
    public void testSortPhonesByModel() {
        List<Phone> allPhonesList = phoneDao.findAll("", null, null, true, 0, 100);
        List<Phone> sortedPhonesList = phoneDao.findAll("", SortField.MODEL, SortOrder.ASC, true, 0, 100);

        assertEquals(allPhonesList.stream().sorted(Comparator.comparing(Phone::getModel)).collect(Collectors.toList()), sortedPhonesList);
    }

    @Test
    public void testSavePhoneWithColors() {
        phoneDao.save(PHONE_4);
        Phone addedPhone = phoneDao.get(PHONE_4.getId()).get();
        assertEquals(PHONE_4.getColors(), addedPhone.getColors());
    }
}