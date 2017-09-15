package me.ianhe.test;

import com.google.common.collect.Lists;
import me.ianhe.dao.PoemMapper;
import me.ianhe.db.entity.Poem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/9/15 15:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class JsoupTest {

    @Autowired
    private PoemMapper poemMapper;

    @Test
    public void testJsoup() throws Exception {
        for (int i = 1; i <= 114; i++) {
            Document doc = Jsoup.connect("http://so.gushiwen.org/mingju/Default.aspx?p=" + i + "&c=&t=").get();
            Element total = doc.getElementsByClass("sons").get(0);
            Elements sons = total.getElementsByClass("cont");
            List<Poem> poemList = Lists.newArrayList();
            Poem poem;
            for (Element son : sons) {
                String content = son.getElementsByTag("a").first().text();
                String title = son.getElementsByTag("a").last().text();
                poem = new Poem();
                poem.setTitle(title);
                poem.setContent(content);
                poemList.add(poem);
            }
            poemMapper.insertBatch(poemList);
        }
    }

}
