package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {
    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        List<Phone> phones = new ArrayList<>();
        Phone phone;

        if (resultSet.next()){

            while(!resultSet.isAfterLast()){
                phone = buildPhone(resultSet);
                phone.setColors(getColors(resultSet, resultSet.getLong("id")));
                phones.add(phone);
            }

        }

        return phones;
    }


    private Phone buildPhone(ResultSet resultSet) throws SQLException {
        return Phone.builder().
                id(resultSet.getLong("id")).
                brand(resultSet.getString("brand")).
                model(resultSet.getString("model")).
                price(resultSet.getBigDecimal("price")).
                displaySizeInches(resultSet.getBigDecimal("displaySizeInches")).
                weightGr(resultSet.getInt("weightGr")).
                lengthMm(resultSet.getBigDecimal("lengthMm")).
                widthMm(resultSet.getBigDecimal("widthMm")).
                heightMm(resultSet.getBigDecimal("heightMm")).
                announced(resultSet.getDate("announced")).
                deviceType(resultSet.getString("deviceType")).
                os(resultSet.getString("os")).
                displayResolution(resultSet.getString("displayResolution")).
                pixelDensity(resultSet.getInt("pixelDensity")).
                displayTechnology(resultSet.getString("displayTechnology")).
                backCameraMegapixels(resultSet.getBigDecimal("backCameraMegapixels")).
                frontCameraMegapixels(resultSet.getBigDecimal("frontCameraMegapixels")).
                ramGb(resultSet.getBigDecimal("ramGb")).
                internalStorageGb(resultSet.getBigDecimal("internalStorageGb")).
                batteryCapacityMah(resultSet.getInt("batteryCapacityMah")).
                talkTimeHours(resultSet.getBigDecimal("talkTimeHours")).
                standByTimeHours(resultSet.getBigDecimal("standByTimeHours")).
                bluetooth(resultSet.getString("bluetooth")).
                positioning(resultSet.getString("positioning")).
                imageUrl(resultSet.getString("imageUrl")).
                description(resultSet.getString("description")).
                build();
    }

    private Set<Color> getColors(ResultSet resultSet, Long phoneId) throws SQLException {

        Set<Color> colors = new HashSet<>();

        while(!resultSet.isAfterLast() && phoneId == resultSet.getLong("id")){
            colors.add(new Color(resultSet.getLong("colorId"), resultSet.getString("colorCode")));

            resultSet.next();
        }

        return colors;
    }
}
