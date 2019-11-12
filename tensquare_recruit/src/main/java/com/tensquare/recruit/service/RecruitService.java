package com.tensquare.recruit.service;

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

import com.tensquare.recruit.dao.RecruitRepository;
import com.tensquare.recruit.po.Recruit;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class RecruitService {

	@Autowired
	private RecruitRepository recruitRepository;
	
	@Autowired
	private IdWorker idWorker;

	//推荐职位
	public List<Recruit> recommendRecruit(String state){

		return recruitRepository.findTop6ByStateOrderByCreatetimeDesc(state);
	}

	//最新职位
	public List<Recruit> newRecruit(String state){

		return recruitRepository.findTop6ByStateNotOrderByCreatetimeDesc(state);
	}
	/**
	 * 增加
	 * @param recruit
	 */
	public void saveRecruit(Recruit recruit) {
		recruit.setId( idWorker.nextId()+"" );
		recruitRepository.save(recruit);
	}

	/**
	 * 修改
	 * @param recruit
	 */
	public void updateRecruit(Recruit recruit) {
		recruitRepository.save(recruit);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteRecruitById(String id) {
		recruitRepository.deleteById(id);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Recruit> findRecruitList() {
		return recruitRepository.findAll();
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Recruit findRecruitById(String id) {
		return recruitRepository.findById(id).get();
	}

	/**
	 * 根据条件查询列表
	 * @param whereMap
	 * @return
	 */
	public List<Recruit> findRecruitList(Map whereMap) {
		//构建Spec查询条件
        Specification<Recruit> specification = getRecruitSpecification(whereMap);
		//Specification条件查询
		return recruitRepository.findAll(specification);
	}
	
	/**
	 * 组合条件分页查询
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Recruit> findRecruitListPage(Map whereMap, int page, int size) {
		//构建Spec查询条件
        Specification<Recruit> specification = getRecruitSpecification(whereMap);
		//构建请求的分页对象
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return recruitRepository.findAll(specification, pageRequest);
	}

	/**
	 * 根据参数Map获取Spec条件对象
	 * @param searchMap
	 * @return
	 */
	private Specification<Recruit> getRecruitSpecification(Map searchMap) {

		return (Specification<Recruit>) (root, query, cb) ->{
				//临时存放条件结果的集合
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//属性条件
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 职位名称
                if (searchMap.get("jobname")!=null && !"".equals(searchMap.get("jobname"))) {
                	predicateList.add(cb.like(root.get("jobname").as(String.class), "%"+(String)searchMap.get("jobname")+"%"));
                }
                // 薪资范围
                if (searchMap.get("salary")!=null && !"".equals(searchMap.get("salary"))) {
                	predicateList.add(cb.like(root.get("salary").as(String.class), "%"+(String)searchMap.get("salary")+"%"));
                }
                // 经验要求
                if (searchMap.get("condition")!=null && !"".equals(searchMap.get("condition"))) {
                	predicateList.add(cb.like(root.get("condition").as(String.class), "%"+(String)searchMap.get("condition")+"%"));
                }
                // 学历要求
                if (searchMap.get("education")!=null && !"".equals(searchMap.get("education"))) {
                	predicateList.add(cb.like(root.get("education").as(String.class), "%"+(String)searchMap.get("education")+"%"));
                }
                // 任职方式
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                	predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
                }
                // 办公地址
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                	predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 企业ID
                if (searchMap.get("eid")!=null && !"".equals(searchMap.get("eid"))) {
                	predicateList.add(cb.like(root.get("eid").as(String.class), "%"+(String)searchMap.get("eid")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 网址
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
                // 标签
                if (searchMap.get("label")!=null && !"".equals(searchMap.get("label"))) {
                	predicateList.add(cb.like(root.get("label").as(String.class), "%"+(String)searchMap.get("label")+"%"));
                }
                // 职位描述
                if (searchMap.get("content1")!=null && !"".equals(searchMap.get("content1"))) {
                	predicateList.add(cb.like(root.get("content1").as(String.class), "%"+(String)searchMap.get("content1")+"%"));
                }
                // 职位要求
                if (searchMap.get("content2")!=null && !"".equals(searchMap.get("content2"))) {
                	predicateList.add(cb.like(root.get("content2").as(String.class), "%"+(String)searchMap.get("content2")+"%"));
                }
		
				//最后组合为and关系并返回
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
		};

	}

}
