package me.ianhe.controller.admin;

import me.ianhe.db.entity.ServiceMenu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminMenuController extends BaseAdminController {

    @RequestMapping(value = "menu_admin", method = RequestMethod.GET)
    public String menuAdmin(Model model) {
        List<ServiceMenu> menus = serviceMenuManager.getAllMenus();
        model.addAttribute("menus", menus);
        return ftl("menu_admin");
    }

    @RequestMapping(value = "service_menu_sync", method = RequestMethod.GET)
    public String syncMenu() {
        String token = accessTokenManager.getAccessToken().getToken();
        try {
            serviceMenuManager.syncServiceMenuToWeiXin(token);
        } catch (Exception e) {
            logger.error("同步菜单出现异常", e);
        }
        return "redirect:menu_admin";
    }

    @RequestMapping(value = "service_menu_update", method = RequestMethod.POST)
    public String updateServiceMenu(Integer menuId, String menuName, String content, String url, Integer articleId,
                                    Integer menuType, Integer parentId, Integer sort) {
        if (parentId == null)
            parentId = 0;
        if (menuId == null) {
            ServiceMenu menu = new ServiceMenu();
            menu.setName(menuName);
            if (menuType == ServiceMenu.TEXT_MENU) {
                menu.setContent(content);
            } else if (menuType == ServiceMenu.LINK_MENU) {
                menu.setContent(url);
            } else if (menuType == ServiceMenu.PIC_MENU) {
                menu.setContent(String.valueOf(articleId));
            }
            menu.setContentType(menuType);
            menu.setParentId(parentId);
            if (sort == null)
                sort = 100;
            menu.setSort(sort);
            serviceMenuManager.insertMenu(menu);
        } else {
            ServiceMenu menu = serviceMenuManager.getMenuById(menuId);
            menu.setName(menuName);
            if (menuType == ServiceMenu.TEXT_MENU) {
                menu.setContent(content);
            } else if (menuType == ServiceMenu.LINK_MENU) {
                menu.setContent(url);
            } else if (menuType == ServiceMenu.PIC_MENU) {
                menu.setContent(String.valueOf(articleId));
            }
            menu.setContentType(menuType);
            menu.setParentId(parentId);
            if (sort == null)
                sort = 100;
            menu.setSort(sort);
            serviceMenuManager.updateMenu(menu);
        }
        return "redirect:menu_admin";
    }

    @PostMapping("delete_menu")
    public String deleteMenu(Integer id) {
        ServiceMenu menu = serviceMenuManager.getMenuById(id);
        if (menu.getParentId() == null) {
            List<ServiceMenu> subMenus = serviceMenuManager.getMenusByParentId(id);
            for (ServiceMenu serviceMenu : subMenus) {
                serviceMenuManager.deleteMenu(serviceMenu.getId());
            }
        }
        serviceMenuManager.deleteMenu(id);
        return success();
    }
}
