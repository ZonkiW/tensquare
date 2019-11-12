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

import com.tenquare.article.dao.ColumnRepository;
import com.tenquare.article.po.Column;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ColumnService {

	@Autowired
	private ColumnRepository columnRepository;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 增加
	 * @param column
	 */
	public void saveColumn(Column column) {
		column.setId( idWorker.nextId()+"" );
		columnRepository.save(column);
	}

	/**
	 * 修改
	 * @param column
	 */
	public void updateColumn(Column column) {
		columnRepository.save(column);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteColumnById(String id) {
		columnRepository.deleteById(id);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Column> findColumnList() {
		return columnRepository.findAll();
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Column findColumnById(String id) {
		return columnRepository.findById(id).get();
	}

	/**
	 * 根据条件查询列表
	 * @param whereMap
	 * @return
	 */
	public List<Column> findColumnList(Map whereMap) {
		//构建Spec查询条件
        Specification<Column> specification = getColumnSpecification(whereMap);
		//Specification条件查询
		return columnRepository.findAll(specification);
	}
	
	/**
	 * 组合条件分页查询
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Column> findColumnListPage(Map whereMap, int page, int size) {
		//构建Spec查询条件
        Specification<Column> specification = getColumnSpecification(whereMap);
		//构建请求的分页对象
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return columnRepository.findAll(specification, pageRequest);
	}

	/**
	 * 根据参数Map获取Spec条件对象
	 * @param searchMap
	 * @return
	 */
	private Specification<Column> getColumnSpecification(Map searchMap) {

		return (Specification<Column>) (root, query, cb) ->{
				//临时存放条件结果的集合
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//属性条件
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 专栏名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 专栏简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
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
