package com.tenquare.qa.service;

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

import com.tenquare.qa.dao.ReplyRepository;
import com.tenquare.qa.po.Reply;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 增加
	 * @param reply
	 */
	public void saveReply(Reply reply) {
		reply.setId( idWorker.nextId()+"" );
		replyRepository.save(reply);
	}

	/**
	 * 修改
	 * @param reply
	 */
	public void updateReply(Reply reply) {
		replyRepository.save(reply);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteReplyById(String id) {
		replyRepository.deleteById(id);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Reply> findReplyList() {
		return replyRepository.findAll();
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Reply findReplyById(String id) {
		return replyRepository.findById(id).get();
	}

	/**
	 * 根据条件查询列表
	 * @param whereMap
	 * @return
	 */
	public List<Reply> findReplyList(Map whereMap) {
		//构建Spec查询条件
        Specification<Reply> specification = getReplySpecification(whereMap);
		//Specification条件查询
		return replyRepository.findAll(specification);
	}
	
	/**
	 * 组合条件分页查询
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Reply> findReplyListPage(Map whereMap, int page, int size) {
		//构建Spec查询条件
        Specification<Reply> specification = getReplySpecification(whereMap);
		//构建请求的分页对象
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return replyRepository.findAll(specification, pageRequest);
	}

	/**
	 * 根据参数Map获取Spec条件对象
	 * @param searchMap
	 * @return
	 */
	private Specification<Reply> getReplySpecification(Map searchMap) {

		return (Specification<Reply>) (root, query, cb) ->{
				//临时存放条件结果的集合
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//属性条件
                // 编号
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 问题ID
                if (searchMap.get("problemid")!=null && !"".equals(searchMap.get("problemid"))) {
                	predicateList.add(cb.like(root.get("problemid").as(String.class), "%"+(String)searchMap.get("problemid")+"%"));
                }
                // 回答内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 回答人ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 回答人昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
		
				//最后组合为and关系并返回
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
		};

	}

}
