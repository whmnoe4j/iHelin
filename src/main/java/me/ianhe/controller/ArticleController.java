package me.ianhe.controller;

import me.ianhe.dao.CommonRedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2017/8/18 09:10
 */
@RestController
@RequestMapping("article")
public class ArticleController extends BaseController {

    private static final String READ_COUNT_KEY = "article:readCount:";

    @Autowired
    private CommonRedisDao commonRedisDao;

    @GetMapping("readCount/{id}")
    public String getReadCount(@PathVariable Integer id) {
        Long readCount = commonRedisDao.getLong(READ_COUNT_KEY + id);
        return success(readCount);
    }

}
