package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitRepository;
import com.tensquare.spit.po.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitRepository spitRepository;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 增加
     * @param
     */
    public void saveSpit(Spit spit) {
        spit.setId( idWorker.nextId()+"" );
        spit.setThumbup(1);
        spit.setComment(0);
        spit.setPublishtime(new Date());
        spit.setState("1");
        spit.setShare(0);
        spit.setVisits(0);

        if (spit.getParentid()!=null&&spit.getParentid()!=" "){
            //父节点对象回复数自增
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitRepository.save(spit);
    }

    /**
     * 修改
     * @param enterprise
     */
    public void updateSpit(Spit enterprise) {
        spitRepository.save(enterprise);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteSpitById(String id) {
        spitRepository.deleteById(id);
    }

    /**
     * 查询全部列表
     * @return
     */
    public List<Spit> findSpitList() {
        return spitRepository.findAll();
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Spit findSpitById(String id) {
        return spitRepository.findById(id).get();
    }

    /**
     * 根据条件查询列表
     * @param whereMap
     * @return
     */
    public List<Spit> findSpitList(Map whereMap) {

        Query query=new Query();
        Criteria criteria = new Criteria();
        if(whereMap.get("_id")!=null&&whereMap.get("_id")!=" "){
            //正则表达式
            Pattern pattern=Pattern.compile("^.*"+whereMap.get("_id")+".*$",Pattern.CASE_INSENSITIVE);
            criteria.and("_id").regex(pattern);
        }
        if(whereMap.get("content")!=null&&whereMap.get("content")!=" "){

            Pattern pattern=Pattern.compile("^.*"+whereMap.get("content")+".*$",Pattern.CASE_INSENSITIVE);
            criteria.and("content").regex(pattern);
        }
        query.addCriteria(criteria);

        return mongoTemplate.find(query,Spit.class);
    }

    /**
     * 组合条件分页查询
     * @param
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findSpitListPage(Map whereMap, int page, int size) {

        Query query=new Query();
        Criteria criteria = new Criteria();
        if(whereMap.get("_id")!=null&&whereMap.get("_id")!=" "){

            Pattern pattern=Pattern.compile("^.*"+whereMap.get("_id")+".*$",Pattern.CASE_INSENSITIVE);
            criteria.and("_id").regex(pattern);
        }
        if(whereMap.get("content")!=null&&whereMap.get("content")!=" "){

            Pattern pattern=Pattern.compile("^.*"+whereMap.get("content")+".*$",Pattern.CASE_INSENSITIVE);
            criteria.and("content").regex(pattern);
        }
        query.addCriteria(criteria);

        Pageable pageable=PageRequest.of(page-1,size);
        long count = mongoTemplate.count(query, Spit.class);
        List<Spit> spits = mongoTemplate.find(query.with(pageable), Spit.class);
        return new PageImpl<>(spits,pageable,count);
    }

    //根据父id查询
    public Page<Spit> findByParentid(String parentid,int page,int size){

        Pageable pageable= PageRequest.of(page-1,size);
        return spitRepository.findByParentid(parentid,pageable);
    }


    public void addThumbup(String spitId) {

        /*Spit spit = spitRepository.findById(spitId).get();
        spit.setThumbup(spit.getThumbup()==null?1:spit.getThumbup()+1);
        spitRepository.save(spit);*/

        //原生mango命令实现自增
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
