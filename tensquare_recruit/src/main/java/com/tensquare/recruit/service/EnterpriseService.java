package com.tensquare.recruit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import utils.IdWorker;

import com.tensquare.recruit.dao.EnterpriseRepository;
import com.tensquare.recruit.po.Enterprise;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class EnterpriseService {

	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	@Autowired
	private IdWorker idWorker;


	/*查询热门企业*/

	public List<Enterprise> findHotEnterprise(String ishot){

		return enterpriseRepository.findByIshot(ishot);
	}
	/**
	 * 增加
	 * @param enterprise
	 */
	public void saveEnterprise(Enterprise enterprise) {
		enterprise.setId( idWorker.nextId()+"" );
		enterpriseRepository.save(enterprise);
	}

	/**
	 * 修改
	 * @param enterprise
	 */
	public void updateEnterprise(Enterprise enterprise) {
		enterpriseRepository.save(enterprise);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteEnterpriseById(String id) {
		enterpriseRepository.deleteById(id);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Enterprise> findEnterpriseList() {
		return enterpriseRepository.findAll();
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Enterprise findEnterpriseById(String id) {
		return enterpriseRepository.findById(id).get();
	}

	/**
	 * 根据条件查询列表
	 * @param whereMap
	 * @return
	 */
	public List<Enterprise> findEnterpriseList(Map whereMap) {
		//构建Spec查询条件
        Specification<Enterprise> specification = getEnterpriseSpecification(whereMap);
		//Specification条件查询
		return enterpriseRepository.findAll(specification);
	}
	
	/**
	 * 组合条件分页查询
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Enterprise> findEnterpriseListPage(Map whereMap, int page, int size) {
		//构建Spec查询条件
        Specification<Enterprise> specification = getEnterpriseSpecification(whereMap);
		//构建请求的分页对象
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return enterpriseRepository.findAll(specification, pageRequest);
	}

	/**
	 * 根据参数Map获取Spec条件对象
	 * @param searchMap
	 * @return
	 */
	private Specification<Enterprise> getEnterpriseSpecification(Map searchMap) {

		return (Specification<Enterprise>) (root, query, cb) ->{
				//临时存放条件结果的集合
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//属性条件
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 企业名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 企业简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 企业地址
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                	predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 标签列表
                if (searchMap.get("labels")!=null && !"".equals(searchMap.get("labels"))) {
                	predicateList.add(cb.like(root.get("labels").as(String.class), "%"+(String)searchMap.get("labels")+"%"));
                }
                // 坐标
                if (searchMap.get("coordinate")!=null && !"".equals(searchMap.get("coordinate"))) {
                	predicateList.add(cb.like(root.get("coordinate").as(String.class), "%"+(String)searchMap.get("coordinate")+"%"));
                }
                // 是否热门
                if (searchMap.get("ishot")!=null && !"".equals(searchMap.get("ishot"))) {
                	predicateList.add(cb.like(root.get("ishot").as(String.class), "%"+(String)searchMap.get("ishot")+"%"));
                }
                // LOGO
                if (searchMap.get("logo")!=null && !"".equals(searchMap.get("logo"))) {
                	predicateList.add(cb.like(root.get("logo").as(String.class), "%"+(String)searchMap.get("logo")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
		
				//最后组合为and关系并返回
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
		};

	}

}
