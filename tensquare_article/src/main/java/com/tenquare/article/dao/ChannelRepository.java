package com.tenquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenquare.article.po.Channel;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ChannelRepository extends JpaRepository<Channel,String>,JpaSpecificationExecutor<Channel>{
	
}
