package com.elec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elec.entity.HouseHold;

@Repository
public interface HouseHoldRepository extends JpaRepository<HouseHold, Integer> {
	@Query("SELECT h FROM HouseHold h WHERE lower(h.fullName) like %:fullName")
	List<HouseHold> search(@Param("fullName") String fullName);
}
