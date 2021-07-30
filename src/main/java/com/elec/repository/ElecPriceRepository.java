package com.elec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elec.entity.ElecPrice;

@Repository
public interface ElecPriceRepository extends JpaRepository<ElecPrice, Long> {

}
