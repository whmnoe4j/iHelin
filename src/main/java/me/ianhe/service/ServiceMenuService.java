package me.ianhe.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.ianhe.db.entity.ServiceMenu;
import me.ianhe.dao.ServiceMenuMapper;
import me.ianhe.model.Button;
import me.ianhe.model.ClickButton;
import me.ianhe.model.Menu;
import me.ianhe.model.ViewButton;
import me.ianhe.utils.JSON;
import me.ianhe.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ServiceMenuService {
    @Resource
    private ServiceMenuMapper serviceMenuMapper;

    private static final Integer PARENT_MENU_ID = 0;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public int insertMenu(ServiceMenu menu) {
        return serviceMenuMapper.insert(menu);
    }

    public int updateMenu(ServiceMenu menu) {
        return serviceMenuMapper.updateByPrimaryKey(menu);
    }

    public int deleteMenu(Integer id) {
        return serviceMenuMapper.deleteByPrimaryKey(id);
    }

    public ServiceMenu getMenuById(Integer id) {
        return serviceMenuMapper.selectByPrimaryKey(id);
    }

    public List<ServiceMenu> getAllMenus() {
        return serviceMenuMapper.getAllMenus();
    }

    // 同步微信菜单
    public String syncServiceMenuToWeiXin(String token) {
        Menu menu = new Menu();
        List<ServiceMenu> parentServiceMenus = getMenuByCondition(PARENT_MENU_ID);
        List<Button> pBtns = Lists.newArrayList();
        for (ServiceMenu pServiceMenu : parentServiceMenus) {
            List<ServiceMenu> subServiceMenus = getMenuByCondition(pServiceMenu.getId());
            List<Button> subBtns = Lists.newArrayList();
            if (pServiceMenu.getContentType() == ServiceMenu.TEXT_MENU) {
                ClickButton pClickButton = new ClickButton();
                pClickButton.setName(pServiceMenu.getName());
                if (!subServiceMenus.isEmpty()) {
                    for (ServiceMenu subServiceMenu : subServiceMenus) {
                        if (subServiceMenu.getContentType() == ServiceMenu.TEXT_MENU) {
                            ClickButton subClickButton = new ClickButton();
                            subClickButton.setName(subServiceMenu.getName());
                            subClickButton.setType("click");
                            subClickButton.setKey(String.valueOf(subServiceMenu.getId()));
                            subBtns.add(subClickButton);
                        } else if (subServiceMenu.getContentType() == ServiceMenu.LINK_MENU) {
                            ViewButton subViewButton = new ViewButton();
                            subViewButton.setName(subServiceMenu.getName());
                            subViewButton.setType("view");
                            subViewButton.setUrl(subServiceMenu.getContent());
                            subBtns.add(subViewButton);
                        }
                    }
                    pClickButton.setSub_button(subBtns);
                } else {
                    pClickButton.setType("click");
                    pClickButton.setKey(pServiceMenu.getId() + "");
                }
                pBtns.add(pClickButton);
            } else if (pServiceMenu.getContentType() == ServiceMenu.LINK_MENU) {
                ViewButton patBtn = new ViewButton();
                patBtn.setName(pServiceMenu.getName());
                if (!subServiceMenus.isEmpty()) {
                    for (ServiceMenu subMenu : subServiceMenus) {
                        if (subMenu.getContentType() == ServiceMenu.TEXT_MENU) {
                            ClickButton subBtn = new ClickButton();
                            subBtn.setName(subMenu.getName());
                            subBtn.setType("click");
                            subBtn.setKey(subMenu.getId() + "");
                            subBtns.add(subBtn);
                        } else if (subMenu.getContentType() == ServiceMenu.LINK_MENU) {
                            ViewButton subBtn = new ViewButton();
                            subBtn.setName(subMenu.getName());
                            subBtn.setType("view");
                            subBtn.setUrl(subMenu.getContent());
                            subBtns.add(subBtn);
                        }
                    }
                    patBtn.setSub_button(subBtns);
                } else {
                    patBtn.setType("view");
                    patBtn.setUrl(pServiceMenu.getContent());
                }
                pBtns.add(patBtn);
            } else if (pServiceMenu.getContentType() == ServiceMenu.PIC_MENU) {
                // 图文消息
            }
        }
        menu.setButton(pBtns);
        String res = WechatUtil.createMenu(token, JSON.toJson(menu));
        logger.info(res);
        return res;
    }

    public List<ServiceMenu> getMenusByParentId(Integer parentId) {
        return serviceMenuMapper.getMenustByParentId(parentId);
    }

    public List<ServiceMenu> getMenuByCondition(Integer parentId) {
        Map<String, Object> res = Maps.newHashMap();
        if (parentId != null) {
            res.put("parentId", parentId);
        }
        return serviceMenuMapper.getMenuByCondition(res);
    }

}
