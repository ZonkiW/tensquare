package com.tensquare.recruit.web.controller;
import java.util.List;
import java.util.Map;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.recruit.po.Recruit;
import com.tensquare.recruit.service.RecruitService;

/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	//推荐职位
	@GetMapping("/search/recommend")
	public Result recommendRecruit(){

		List<Recruit> recruits = recruitService.recommendRecruit("2");
		return new Result(true,StatusCode.OK,"查询成功",recruits);
	}

	//最新职位
	@GetMapping("/search/newlist")
	public Result newRecruit(){

		List<Recruit> recruits = recruitService.newRecruit("0");
		return new Result(true,StatusCode.OK,"查询成功",recruits);
	}

	/**
	 * 增加
	 * @param recruit
	 */
	@PostMapping
	public Result add(@RequestBody Recruit recruit  ){
		recruitService.saveRecruit(recruit);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param recruit
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Recruit recruit, @PathVariable String id ){
		recruit.setId(id);
		recruitService.updateRecruit(recruit);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		recruitService.deleteRecruitById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",recruitService.findRecruitList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",recruitService.findRecruitById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",recruitService.findRecruitList(searchMap));
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
		Page<Recruit> pageResponse = recruitService.findRecruitListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Recruit>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
