package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{key}/{page}/{size}")
    public Result findBySearch(@PathVariable String key,@PathVariable int page,@PathVariable int size){

        Page<Article> bySearch = articleService.findBySearch(key, page, size);
        return new Result(true, StatusCode.OK,"搜索成功",new PageResult<Article>(bySearch.getTotalElements(),bySearch.getContent()));
    }
}
