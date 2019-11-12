package com.tenquare.article.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import utils.IdWorker;

import com.tenquare.article.dao.ChannelRepository;
import com.tenquare.article.po.Channel;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ChannelService {

	@Autowired
	private ChannelRepository channelRepository;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 增加
	 * @param channel
	 */
	public void saveChannel(Channel channel) {
		channel.setId( idWorker.nextId()+"" );
		channelRepository.save(channel);
	}

	/**
	 * 修改
	 * @param channel
	 */
	public void updateChannel(Channel channel) {
		channelRepository.save(channel);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteChannelById(String id) {
		channelRepository.deleteById(id);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Channel> findChannelList() {
		return channelRepository.findAll();
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Channel findChannelById(String id) {
		return channelRepository.findById(id).get();
	}

	/**
	 * 根据条件查询列表
	 * @param whereMap
	 * @return
	 */
	public List<Channel> findChannelList(Map whereMap) {
		//构建Spec查询条件
        Specification<Channel> specification = getChannelSpecification(whereMap);
		//Specification条件查询
		return channelRepository.findAll(specification);
	}
	
	/**
	 * 组合条件分页查询
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Channel> findChannelListPage(Map whereMap, int page, int size) {
		//构建Spec查询条件
        Specification<Channel> specification = getChannelSpecification(whereMap);
		//构建请求的分页对象
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return channelRepository.findAll(specification, pageRequest);
	}

	/**
	 * 根据参数Map获取Spec条件对象
	 * @param searchMap
	 * @return
	 */
	private Specification<Channel> getChannelSpecification(Map searchMap) {

		return (Specification<Channel>) (root, query, cb) ->{
				//临时存放条件结果的集合
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//属性条件
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 频道名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
		
				//最后组合为and关系并返回
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
		};

	}

}
