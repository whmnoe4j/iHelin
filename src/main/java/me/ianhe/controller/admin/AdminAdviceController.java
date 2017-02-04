package me.ianhe.controller.admin;

import me.ianhe.db.entity.Advice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminAdviceController extends BaseAdminController {

    @RequestMapping(value = "advice", method = RequestMethod.GET)
    public String Advice(Model model) {
        List<Advice> advices = adviceManager.selectAdviceByCondition();
        model.addAttribute("advices", advices);
        return "admin/advice_manager";
    }

}
