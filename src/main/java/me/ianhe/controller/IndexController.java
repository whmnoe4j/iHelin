package me.ianhe.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.ianhe.db.entity.Article;
import me.ianhe.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author iHelin
 * @create 2017-03-15 19:36
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping(value = {"/", "index"})
    public String indexPage(Model model) {
        int pageLength = 5;
        int pageNum = 1;
        List<Article> articles = articleService.listByCondition(null, (pageNum - 1)
                * pageLength, pageLength);
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping(value = "home")
    public String homePage() {
        return "home";
    }

    @GetMapping(value = "config")
    public String configPage(Model model) {
        Properties props = System.getProperties();
        model.addAttribute("props", props);
        return "config";
    }

    @GetMapping(value = "image")
    public String imagePage(Model model) {
        List<Map<String, Object>> fileInfos = FileUtil.getFileList();
        model.addAttribute("fileInfos", fileInfos);
        return "image";
    }

    @GetMapping("mapping")
    public String showMappings(Model model) {
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        List<Map> array = Lists.newArrayList();
        for (RequestMappingInfo key : map.keySet()) {
            Map<String, Object> modelMap = Maps.newHashMap();
            String urls = Joiner.on(',').join(key.getPatternsCondition().getPatterns());
            modelMap.put("url", urls);
            modelMap.put("method", Joiner.on(',').join(key.getMethodsCondition().getMethods()));
            modelMap.put("className", map.get(key).getBeanType().getName());
            modelMap.put("classMethod", map.get(key).getMethod().getName());
            array.add(modelMap);
        }
        model.addAttribute("mappings", array);
        return "mapping";
    }

}
