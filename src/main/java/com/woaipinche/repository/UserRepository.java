package com.woaipinche.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woaipinche.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value = "select * from user where tuijian_phone=?1 \n#pageable\n",
			  countQuery = "select count(*) from user where tuijian_phone=?1",
			  nativeQuery = true)
	Page<User> findWodeChengyuanPage(String phone,Pageable pageable);
}
