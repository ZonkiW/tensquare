package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.po.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseRepository extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{
	//查询热门企业
    public List<Enterprise> findByIshot(String ishot);
}
