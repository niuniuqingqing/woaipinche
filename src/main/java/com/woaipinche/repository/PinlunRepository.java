package com.woaipinche.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woaipinche.model.Pinlun;

@Repository
public interface PinlunRepository extends JpaRepository<Pinlun, String> {
	@Query(value = "select * from pinlun  \n#pageable\n",
			  countQuery = "select count(*) from pinlun ",
			  nativeQuery = true)
	Page<Pinlun> findPinlunPage(Pageable pageable);
}
