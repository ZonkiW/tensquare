package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制层
 */
@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelService labelService;
    /**
     * 添加一个
     * @param label
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Label label){
        labelService.saveLabel(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 修改编辑
     * @param label
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result edit(@RequestBody Label label, @PathVariable String id){
        label.setId(id);
        labelService.updateLabel(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 根据id删除一个
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable String id){
        labelService.deleteLabelById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }


    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result list(){
        List<Label> list = labelService.findLabelList();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    /**
     * 根据id查询一个
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result listById(@PathVariable String id){
        Label label = labelService.findLabelById(id);
        return new Result(true, StatusCode.OK,"查询成功",label);
    }

    /**
    *条件查询
    **/
    @PutMapping("/search")
    public Result findSearch(@RequestBody Label label){

        List<Label> list=labelService.findSearch(label);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @PostMapping("/search/{page}/{size}")
    public  Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){

        Page<Label> pageData=labelService.pageQuery(label,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }
}