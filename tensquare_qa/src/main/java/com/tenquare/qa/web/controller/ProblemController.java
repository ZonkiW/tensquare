package com.tenquare.qa.web.controller;
import java.util.List;
import java.util.Map;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tenquare.qa.po.Problem;
import com.tenquare.qa.service.ProblemService;

/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	
	/**
	 * 增加
	 * @param problem
	 */
	@PostMapping
	public Result add(@RequestBody Problem problem  ){
		problemService.saveProblem(problem);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.updateProblem(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		problemService.deleteProblemById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemList(searchMap));
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
		Page<Problem> pageResponse = problemService.findProblemListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	//最新问题
	@GetMapping("/newlist/{labelid}/{page}/{size}")
	public Result newList(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){

		Page<Problem> problems = problemService.newListByLabel(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalElements(),problems.getContent()));
	}
	//热门问题
	@GetMapping("/hotlist/{labelid}/{page}/{size}")
	public Result hotList(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){

		Page<Problem> problems = problemService.hotListByLabel(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalElements(),problems.getContent()));
	}
	//等待回答问题
	@GetMapping("/waitlist/{labelid}/{page}/{size}")
	public Result waitList(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){

		Page<Problem> problems = problemService.waitListByLabel(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalElements(),problems.getContent()));
	}
	//全部问题by label
	@PostMapping("/all/{labelid}/{page}/{size}")
	public Result allList(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){

		Page<Problem> problems = problemService.allListByLabel(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalElements(),problems.getContent()));
	}

}
