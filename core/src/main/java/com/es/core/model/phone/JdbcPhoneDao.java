package com.es.core.model.phone;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao{

    private final String SELECT_PHONES_WITH_OFFSET_AND_LIMIT = "select * from phones offset ? limit ?";
    private final String SELECT_PHONE_BY_ID = "select * from phones where id = ?";

    private final static String SELECT_COLOR_BY_ID = "select * from colors where id = ?";

    private final static String SELECT_COLOR_IDS_BY_PHONE_ID = "select colorId from phone2color where phoneId = ?";

    private final static String INSERT_PHONES = "insert into phones (id, brand, model, price, displaySizeInches," +
            " weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
            "displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb," +
            " batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl," +
            " description) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String INSERT_INTO_PHONE_COLOR = "insert into phone2color (phoneId, colorId) values (?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        Optional<Phone> phone = jdbcTemplate.query(SELECT_PHONE_BY_ID, new Object[] {key},
                        new BeanPropertyRowMapper(Phone.class))
                .stream().findAny();

        phone.ifPresent(this::setColors);

        return phone;

    }

    public void save(final Phone phone) {
        jdbcTemplate.update(INSERT_PHONES, phone.getId(), phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(), phone.getWidthMm(),
                phone.getHeightMm(), phone.getAnnounced(), phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(), phone.getFrontCameraMegapixels(),
                phone.getRamGb(), phone.getInternalStorageGb(), phone.getBatteryCapacityMah(), phone.getTalkTimeHours(),
                phone.getStandByTimeHours(), phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription()
        );

        ArrayList<Color> colors = new ArrayList<>(phone.getColors());
        jdbcTemplate.batchUpdate(INSERT_INTO_PHONE_COLOR,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, phone.getId());
                        preparedStatement.setLong(2, colors.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return phone.getColors().size();
                    }
                }

        );
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones =  jdbcTemplate.query(SELECT_PHONES_WITH_OFFSET_AND_LIMIT, new Object[] {offset, limit},
                new BeanPropertyRowMapper(Phone.class));

        phones.forEach(this::setColors);

        return phones;
    }

    private void setColors(Phone phone){
        List<Long> colorsIds = jdbcTemplate.queryForList(SELECT_COLOR_IDS_BY_PHONE_ID, Long.class,
                phone.getId());

        Set<Color> colors = colorsIds.stream()
                .map(id -> jdbcTemplate.queryForObject(SELECT_COLOR_BY_ID, new Object[]{id},
                        new BeanPropertyRowMapper<>(Color.class))).collect(Collectors.toSet());

        phone.setColors(colors);
    }
}
