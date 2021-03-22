package com.currencyconverter.web.repository;

import com.currencyconverter.web.model.SearchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchDataRepository extends JpaRepository<SearchData, Long> {
}
