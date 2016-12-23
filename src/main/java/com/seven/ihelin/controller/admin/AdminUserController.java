package com.seven.ihelin.controller.admin;

import com.seven.ihelin.db.entity.User;
import com.seven.ihelin.db.plugin.Pagination;
import com.seven.ihelin.utils.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AdminUserController extends BaseAdminController {

    @RequestMapping("user_admin")
    public String userAdmin(Model model, String nickName, Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        List<User> users = userManager.listUserByCondition(nickName, null, (pageNum - 1) * PAGE_LENGTH, PAGE_LENGTH);
        int totalCount = userManager.listUserCount(nickName, null);
        model.addAttribute("nickName", nickName);
        model.addAttribute("users", users);
        model.addAttribute("pagination", new Pagination(totalCount, pageNum, PAGE_LENGTH));
        return ftl("user_admin");
    }

    @RequestMapping("delete_user")
    public void deleteProduct(Integer id, HttpServletResponse response) {
        userManager.deleteUserById(id);
        ResponseUtil.writeSuccessJSON(response);
    }

}
