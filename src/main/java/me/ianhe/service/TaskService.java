package me.ianhe.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.ianhe.controller.ArticleController;
import me.ianhe.dao.ArticleMapper;
import me.ianhe.dao.CommonRedisDao;
import me.ianhe.dao.PoemMapper;
import me.ianhe.entity.Article;
import me.ianhe.entity.Poem;
import me.ianhe.model.ding.FeedCard;
import me.ianhe.model.ding.Link;
import me.ianhe.model.douban.Movie;
import me.ianhe.model.douban.Subject;
import me.ianhe.utils.JsonUtil;
import me.ianhe.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/6/13 15:53
 */
@Service
public class TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PoemMapper poemMapper;

    @Autowired
    private DingService dingService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommonRedisDao commonRedisDao;

    public Poem getPoemByRandom() {
        return poemMapper.getByRandom();
    }

    /**
     * 工作日18点执行
     *
     * @author iHelin
     * @since 2017/12/21 10:24
     */
    public void runWorkDay18() {
        poemRun();
    }

    /**
     * 工作日12点执行
     *
     * @author iHelin
     * @since 2017/12/21 10:24
     */
    public void runWorkDay12() {
//        poemRun();
    }

    /**
     * 每天0点执行
     *
     * @author iHelin
     * @since 2017/12/21 10:21
     */
    public void runEveryDay0() {
        syncReadCount();
    }

    /**
     * 每天7点执行
     *
     * @author iHelin
     * @since 2018/1/10 09:07
     */
    public void runEveryDay7() {
        dailyEnglish();
    }

    /**
     * 每天同步文章阅读数
     *
     * @author iHelin
     * @since 2017/12/21 10:17
     */
    private void syncReadCount() {
        List<Integer> ids = articleMapper.selectAllId();
        for (Integer id : ids) {
            Long readCount = commonRedisDao.getLong(ArticleController.READ_COUNT_KEY + id);
            Article article = new Article();
            article.setId(id);
            article.setReadNum(readCount);
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }

    /**
     * 古诗
     *
     * @author iHelin
     * @since 2017/11/13 17:17
     */
    private void poemRun() {
        Poem poem = poemMapper.getByRandom();
        String msg = poem.getContent() + "      --" + poem.getTitle();
        dingService.sendTextMsg(msg);
    }

    /**
     * 英语
     *
     * @author iHelin
     * @since 2017/11/13 17:17
     */
    private void dailyEnglish() {
        Calendar terminalDate = new Calendar.Builder().setDate(2018, Calendar.DECEMBER, 23)
                .build();
        long terminalLong = terminalDate.getTimeInMillis();
        long nowLong = System.currentTimeMillis();
        long betweenDays = (terminalLong - nowLong) / (1000L * 3600 * 24);
        if (betweenDays > 0) {
            Map<String, Object> contentMap = Maps.newHashMap();
            String res = WechatUtil.doGetStr("http://open.iciba.com/dsapi");
            Map<String, Object> resMap = JsonUtil.parseMap(res);
            contentMap.put("title", "葫芦娃学英语");
            String text = "## 距离2019【ky】还剩" + betweenDays + "天！\n" +
                    "![葫芦娃学英语](" + resMap.get("picture") + ")\n" +
                    "##### " + resMap.get("content") + " \n" +
                    "> " + resMap.get("note") + " \n";
            contentMap.put("text", text);
            Map<String, Object> data = Maps.newHashMap();
            data.put("msgtype", "markdown");
            data.put("markdown", contentMap);
            String jsonData = JsonUtil.toJson(data);
            dingService.doSend(jsonData);
        }
    }

    /**
     * 电影
     *
     * @author iHelin
     * @since 2017/12/1 15:57
     */
    public void sendMovie() {
        String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0";
        String res = WechatUtil.doGetStr(url);
        Movie movie = JsonUtil.parseObject(res, Movie.class);
        List<Subject> subjectList = movie.getSubjects();
        if (subjectList == null || subjectList.isEmpty()) {
            dingService.sendTextMsg("电影接口有问题啦，快去看看咋回事！");
            return;
        }
        List<Link> links = Lists.newArrayList();
        Link link = new Link();
        link.setTitle("又要到周末啦，快来看看最近的热门电影吧！");
        link.setMessageURL(subjectList.get(0).getUrl());
        link.setPicURL(subjectList.get(0).getCover());
        links.add(link);
        for (int i = 1; i < 5; i++) {
            link = new Link();
            link.setTitle(subjectList.get(i).getTitle());
            link.setMessageURL(subjectList.get(i).getUrl());
            link.setPicURL(subjectList.get(i).getCover());
            links.add(link);
        }
        FeedCard feedCard = new FeedCard();
        feedCard.setLinks(links);
        dingService.sendFeedCardMsg(feedCard);
    }

}
