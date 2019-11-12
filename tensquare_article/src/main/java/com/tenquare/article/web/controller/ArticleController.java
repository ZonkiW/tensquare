package com.tenquare.article.web.controller;
import java.util.List;
import java.util.Map;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tenquare.article.po.Article;
import com.tenquare.article.service.ArticleService;

/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	
	/**
	 * 增加
	 * @param article
	 */
	@PostMapping
	public Result add(@RequestBody Article article  ){
		articleService.saveArticle(article);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.updateArticle(article);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		articleService.deleteArticleById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findArticleList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findArticleById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findArticleList(searchMap));
    }

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@PostMapping("/search/{page}/{size}")
	public Result listPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Article> pageResponse = articleService.findArticleListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Article>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}

	//文章审核
	@PutMapping("/examine/{articleId}")
	public Result updateState(@PathVariable String articleId){

		articleService.updateState(articleId);
		return new Result(true,StatusCode.OK,"审核成功");
	}
	//文章点赞
	@PutMapping("/thumbup/{articleId}")
	public Result addThumbup(@PathVariable String articleId){

		articleService.addThumbup(articleId);
		return new Result(true,StatusCode.OK,"点赞成功");
	}

}
