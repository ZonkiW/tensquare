package com.tensquare.base.service;


import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

/**
 * \标签业务逻辑类
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 保存一个标签
     * @param label
     */
    public void saveLabel(Label label){
        //设置ID
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }
    /**
     * 更新一个标签
     * @param label
     */
    public void updateLabel(Label label){
        labelDao.save(label);
    }

    /**
     * 删除一个标签
     * @param id
     */
    public void deleteLabelById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 查询全部标签
     *
     * @return
     */
    public List<Label> findLabelList() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     *
     * @return
     */
    public Label findLabelById(String id) {
        return labelDao.findById(id).get();
    }

    public List<Label> findSearch(Label label) {

        return labelDao.findAll(new Specification<Label>(){

            List<Predicate> list=new ArrayList<>();
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                if(label.getLabelname()!=null&&!label.getLabelname().equals(" ")){
                    Predicate predicate= criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
                    list.add(predicate);
                }
                if(label.getState()!=null&&!label.getState().equals(" ")){
                    Predicate predicate= criteriaBuilder.equal(root.get("state").as(String.class),label.getState());
                    list.add(predicate);
                }

                Predicate[] predicates=new Predicate[list.size()];
                predicates=list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {

        Pageable pageable= PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>(){

            List<Predicate> list=new ArrayList<>();
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                if(label.getLabelname()!=null&&!label.getLabelname().equals(" ")){
                    Predicate predicate= criteriaBuilder.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");
                    list.add(predicate);
                }
                if(label.getState()!=null&&!label.getState().equals(" ")){
                    Predicate predicate= criteriaBuilder.equal(root.get("state").as(String.class),label.getState());
                    list.add(predicate);
                }

                Predicate[] predicates=new Predicate[list.size()];
                predicates=list.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        },pageable);

    }
}