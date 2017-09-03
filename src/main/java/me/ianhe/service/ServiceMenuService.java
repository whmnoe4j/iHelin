package me.ianhe.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.riversoft.weixin.common.menu.Menu;
import com.riversoft.weixin.common.menu.MenuItem;
import com.riversoft.weixin.common.menu.MenuType;
import me.ianhe.dao.ServiceMenuMapper;
import me.ianhe.db.entity.ServiceMenu;
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
        List<MenuItem> pBtns = Lists.newArrayList();
        for (ServiceMenu pServiceMenu : parentServiceMenus) {
            List<ServiceMenu> subServiceMenus = getMenuByCondition(pServiceMenu.getId());
            List<MenuItem> subBtns = Lists.newArrayList();
            if (pServiceMenu.getContentType() == ServiceMenu.TEXT_MENU) {
                MenuItem pClickButton = new MenuItem();
                pClickButton.setName(pServiceMenu.getName());
                if (!subServiceMenus.isEmpty()) {
                    for (ServiceMenu subServiceMenu : subServiceMenus) {
                        if (subServiceMenu.getContentType() == ServiceMenu.TEXT_MENU) {
                            MenuItem subClickButton = new MenuItem();
                            subClickButton.setName(subServiceMenu.getName());
                            subClickButton.setType(MenuType.click);
                            subClickButton.setKey(String.valueOf(subServiceMenu.getId()));
                            subBtns.add(subClickButton);
                        } else if (subServiceMenu.getContentType() == ServiceMenu.LINK_MENU) {
                            MenuItem subViewButton = new MenuItem();
                            subViewButton.setName(subServiceMenu.getName());
                            subViewButton.setType(MenuType.view);
                            subViewButton.setUrl(subServiceMenu.getContent());
                            subBtns.add(subViewButton);
                        }
                    }
                    pClickButton.setSubItems(subBtns);
                } else {
                    pClickButton.setType(MenuType.click);
                    pClickButton.setKey(pServiceMenu.getId() + "");
                }
                pBtns.add(pClickButton);
            } else if (pServiceMenu.getContentType() == ServiceMenu.LINK_MENU) {
                MenuItem patBtn = new MenuItem();
                patBtn.setName(pServiceMenu.getName());
                if (!subServiceMenus.isEmpty()) {
                    for (ServiceMenu subMenu : subServiceMenus) {
                        if (subMenu.getContentType() == ServiceMenu.TEXT_MENU) {
                            MenuItem subBtn = new MenuItem();
                            subBtn.setName(subMenu.getName());
                            subBtn.setType(MenuType.click);
                            subBtn.setKey(subMenu.getId() + "");
                            subBtns.add(subBtn);
                        } else if (subMenu.getContentType() == ServiceMenu.LINK_MENU) {
                            MenuItem subBtn = new MenuItem();
                            subBtn.setName(subMenu.getName());
                            subBtn.setType(MenuType.view);
                            subBtn.setUrl(subMenu.getContent());
                            subBtns.add(subBtn);
                        }
                    }
                    patBtn.setSubItems(subBtns);
                } else {
                    patBtn.setType(MenuType.view);
                    patBtn.setUrl(pServiceMenu.getContent());
                }
                pBtns.add(patBtn);
            } else if (pServiceMenu.getContentType() == ServiceMenu.PIC_MENU) {
                // 图文消息
            }
        }
        menu.setMenus(pBtns);
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
