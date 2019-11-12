package com.tenquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenquare.article.po.Column;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ColumnRepository extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{
	
}
