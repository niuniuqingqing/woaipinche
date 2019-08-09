package com.woaipinche.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woaipinche.model.YuDing;

@Repository
public interface YuDingRepository extends JpaRepository<YuDing, String> {
	
	
	//所有发布信息
	@Query(value = "select * from yu_ding u where u.start_point=?1 and u.end_point=?2 and set_out_date >= sysdate() \n#pageable\n",
		  countQuery = "select count(*) from yu_ding u where u.start_point=?1 and u.end_point=?2 and set_out_date >= sysdate()",
		  nativeQuery = true)
	Page<YuDing> findYuDingPointPage(String startPoint, String endPoint,Pageable pageable);
	
	
	//我的发布信息
	@Query(value = "select * from yu_ding where phone=?1 and  set_out_date>sysdate() \n#pageable\n",
			  countQuery = "select count(*) from yu_ding where phone=?1 and  set_out_date>sysdate()",
			  nativeQuery = true)
    Page<YuDing> findWodefabuPage(String phone,Pageable pageable);
}
