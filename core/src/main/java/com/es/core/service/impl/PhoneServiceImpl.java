package com.es.core.service.impl;

import com.es.core.exception.ProductNotFoundException;
import com.es.core.model.phone.*;
import com.es.core.service.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneDao phoneDao;

    @Override
    public Phone get(Long id) {
        return phoneDao.get(id).orElseThrow(() -> new ProductNotFoundException("Phone with id = " + id + " not found"));
    }

    @Override
    public void save(Phone phone) {
        phoneDao.save(phone);
    }

    @Override
    public List<Phone> findAll(String query, SortField sortField, SortOrder sortOrder,
                               boolean availability, int offset, int limit) {
        return phoneDao.findAll(query, sortField, sortOrder, availability, offset, limit);
    }

    @Override
    public Stock getStock(Long id) {
        return phoneDao.getStock(id);
    }

    @Override
    public Integer count(String query, SortField sortField, SortOrder sortOrder,
                         boolean availability, int offset, int limit) {
        return phoneDao.count(query, availability);
    }
}
