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


@Component
public class JdbcPhoneDao implements PhoneDao {

    private final String SELECT_PHONES_WITH_OFFSET_AND_LIMIT = "select * from phones offset ? limit ?";

    private final String SELECT_ALL_PHONES = "select * from phones ";

    private final String AVAILABLE_ONLY_QUERY = "join stocks on phones.id = stocks.phoneId " +
            "where (stocks.stock > stocks.reserved " +
            "and phones.price is not null) ";
    private final String SEARCH_QUERY = "concat(phones.brand, phones.model) ilike '%%%s%%' ";
    private final String SORT_QUERY = "order by ? ";
    private final String OFFSET_AND_LIMIT_QUERY = "offset ? limit ? ";

    private final String NESTED_QUERY_STR = "NESTED_QUERY";

    private final String SELECT_PHONES_WITH_CLR =
            "select phones.*, colors.id as colorId, colors.code as colorCode from " +
                    "( " + NESTED_QUERY_STR + " ) as phones left join phone2color " +
                    "on phones.id = phone2color.phoneId" +
                    " left join colors on phone2color.colorId = colors.id";

    private final String SELECT_PHONE_BY_ID = "select * from phones where id = ?";

    private final String SELECT_PHONE_WITH_CLR_BY_ID = "select phone.*, colors.id as colorId, colors.code as colorCode from " +
            "(" + SELECT_PHONE_BY_ID + ") as phone left join phone2color " +
            "on phone.id = phone2color.phoneId" +
            " left join colors on phone2color.colorId = colors.id";

    private final static String INSERT_PHONES = "insert into phones (id, brand, model, price, displaySizeInches," +
            " weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, " +
            "displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb," +
            " batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl," +
            " description) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String INSERT_INTO_PHONE_COLOR = "insert into phone2color (phoneId, colorId) values (?, ?)";

    private final String SELECT_STOCK_BY_PHONE_ID = "select * from stocks where phoneId = ?";

    private final String UPDATE_STOCK = "update stocks set stock = ?, reserved = ? " +
            "where stocks.phoneId = ?";

    private final String COUNT_QUERY = "select count(1) from  ( " + NESTED_QUERY_STR + " )";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        return jdbcTemplate.query(SELECT_PHONE_WITH_CLR_BY_ID, new Object[]{key},
                        new PhoneResultSetExtractor())
                .stream().findAny();

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

    @Override
    public List<Phone> findAll(String searchQuery, SortField sortField, SortOrder sortOrder,
                               boolean availability, int offset, int limit) {

        String query = buildQuery(searchQuery, sortField, sortOrder, availability, offset, limit);

        return jdbcTemplate.query(query,  new Object[]{offset, limit},
                new PhoneResultSetExtractor());
    }

    @Override
    public Stock getStock(Long id) {
        return jdbcTemplate.queryForObject(SELECT_STOCK_BY_PHONE_ID,  new Object[]{id},
                new BeanPropertyRowMapper<>(Stock.class));
    }

    @Override
    public void updateStock(Stock stock) {
        jdbcTemplate.update(UPDATE_STOCK, new Object[]{stock.getStock(), stock.getReserved(), stock.getPhone().getId()});
    }

    @Override
    public Integer count(String searchQuery, boolean availability) {

        String query = buildQueryForCount(searchQuery, availability, -1, -1);

        return jdbcTemplate.queryForObject( query,
                Integer.class);
    }

    private String buildQuery(String query, SortField sortField, SortOrder sortOrder,
                              boolean availability, int offset, int limit) {
        StringBuilder nestedQuery = new StringBuilder(SELECT_ALL_PHONES);

        if (availability) {
            nestedQuery.append(AVAILABLE_ONLY_QUERY).append("and ");
        } else {
            nestedQuery.append("where ");
        }

        nestedQuery.append(SEARCH_QUERY);

        if (sortField != null && sortOrder != null) {
            if (sortField == SortField.BRAND || sortField == SortField.MODEL){
                nestedQuery.append(SORT_QUERY.replace("?", "lower( " + sortField.name() + ") "));
            } else {
                nestedQuery.append(SORT_QUERY.replace("?", sortField.name()));
            }
            nestedQuery.append(sortOrder.name() + " ");
        }

        if (offset >= 0 && limit >= 0) nestedQuery.append(OFFSET_AND_LIMIT_QUERY);

        if (query == null) query = "";

        return String.format(SELECT_PHONES_WITH_CLR.replace(NESTED_QUERY_STR, nestedQuery.toString()),
                query.trim().toLowerCase());
    }

    private String buildQueryForCount(String query,
                              boolean availability, int offset, int limit) {
        StringBuilder nestedQuery = new StringBuilder(SELECT_ALL_PHONES);

        if (availability) {
            nestedQuery.append(AVAILABLE_ONLY_QUERY).append("and ");
        } else {
            nestedQuery.append("where ");
        }

        nestedQuery.append(SEARCH_QUERY);

        if (offset >= 0 && limit >= 0) nestedQuery.append(OFFSET_AND_LIMIT_QUERY);

        if (query == null) query = "";

        return String.format(COUNT_QUERY.replace(NESTED_QUERY_STR, nestedQuery.toString()),
                query.trim().toLowerCase());
    }

}
