package com.tensquare.spit.dao;


import com.tensquare.spit.po.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface SpitRepository extends MongoRepository<Spit,String>{

    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
