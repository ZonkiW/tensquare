package com.tenquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenquare.qa.po.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemRepository extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    //最新问题
    @Query(value="SELECT * FROM tb_pl tpl,tb_problem tpm WHERE tpm.id=tpl.problemid AND tpl.labelid=? ORDER BY tpm.replytime DESC",nativeQuery = true)
    public Page<Problem>  newlist(String labelid, Pageable pageable);
    //热门问题
    @Query(value="SELECT * FROM tb_pl tpl,tb_problem tpm WHERE tpm.id=tpl.problemid AND tpl.labelid=? ORDER BY tpm.reply DESC",nativeQuery = true)
    public Page<Problem>  hotlist(String labelid, Pageable pageable);
    //等待回答问题
    @Query(value="SELECT * FROM tb_pl tpl,tb_problem tpm WHERE tpm.id=tpl.problemid AND tpl.labelid=? AND tpm.reply=0",nativeQuery = true)
    public Page<Problem>  waitlist(String labelid, Pageable pageable);
    //label全部问题
    @Query(value="SELECT * FROM tb_pl tpl,tb_problem tpm WHERE tpm.id=tpl.problemid AND tpl.labelid=? ",nativeQuery = true)
    public Page<Problem>  allLabelList(String labelid, Pageable pageable);
}
