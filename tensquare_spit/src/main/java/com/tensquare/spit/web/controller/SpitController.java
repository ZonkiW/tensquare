package com.tensquare.spit.web.controller;

import com.tensquare.spit.po.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

	@Autowired
	private SpitService spitService;

	@Autowired
    private RedisTemplate redisTemplate;
	
	/**
	 * 增加
	 * @param
	 */
	@PostMapping
	public Result add(@RequestBody Spit spit  ){
		spitService.saveSpit(spit);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Spit spit, @PathVariable String id ){
		spit.setId(id);
		spitService.updateSpit(spit);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		spitService.deleteSpitById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",spitService.findSpitList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",spitService.findSpitById(id));
	}

	@GetMapping("/comment/{parentid}/{page}/{size}")
	public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){

		Page<Spit> byParentid = spitService.findByParentid(parentid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(byParentid.getTotalElements(),byParentid.getContent()));
	}

	@PutMapping("/thumbup/{spitId}")
	public Result addThumbup(@PathVariable String spitId){

        String userid="Worm";
        if (redisTemplate.opsForValue().get("thumb_"+userid)!=null){

            return new Result(true,StatusCode.OK,"不能重复点赞");
        }
        spitService.addThumbup(spitId);
        redisTemplate.opsForValue().set("thumb_"+userid,1);
        return new Result(true,StatusCode.OK,"点赞成功");
	}

/**
     * 根据条件查询
     * @param searchMap
     * @return
     */

    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findSpitList(searchMap));
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
		Page<Spit> pageResponse = spitService.findSpitListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Spit>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}



}
