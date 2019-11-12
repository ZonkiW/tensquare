package com.tenquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tenquare.article.po.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleRepository extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    //文章审核
    @Modifying
    @Query(value="UPDATE tb_article SET state=1 WHERE id=?",nativeQuery = true)
    public void updateState(String id);
    //文章点赞
    @Modifying
    @Query(value="UPDATE tb_article SET thumbup=thumbup+1 WHERE id=?",nativeQuery = true)
    public void addThumbup(String id);
}
