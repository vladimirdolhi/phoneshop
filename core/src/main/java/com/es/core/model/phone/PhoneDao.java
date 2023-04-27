package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);
    void save(Phone phone);
    List<Phone> findAll(String query, SortField sortField, SortOrder sortOrder,
                        boolean availability, int offset, int limit);
    Stock getStock(Long id);

    Integer count(String query, boolean availability);
}
