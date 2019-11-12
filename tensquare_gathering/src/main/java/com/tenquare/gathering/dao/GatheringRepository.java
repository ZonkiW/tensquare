package com.tenquare.gathering.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenquare.gathering.po.Gathering;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface GatheringRepository extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{
	
}
