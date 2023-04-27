package com.es.core.service;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortField;
import com.es.core.model.phone.SortOrder;
import com.es.core.model.phone.Stock;

import java.util.List;

public interface PhoneService {

    Phone get(Long id);

    void save(Phone phone);

    List<Phone> findAll(String query, SortField sortField, SortOrder sortOrder,
                        boolean availability, int offset, int limit);

    Stock getStock(Long id);

    Integer count(String query, SortField sortField, SortOrder sortOrder,
                  boolean availability, int offset, int limit);

}
