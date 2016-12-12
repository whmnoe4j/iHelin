package com.seven.ihelin.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seven.ihelin.db.entity.Advice;
import com.seven.ihelin.manager.AdviceManager;

@Controller
@RequestMapping("admin")
public class AdminAdviceController {
	@Resource
	private AdviceManager adviceManager;
	
	@RequestMapping(value="advice")
	public String Advice(Model model){
		List<Advice> advices = adviceManager.selectAdviceByCondition();
		model.addAttribute("advices", advices);
		return "admin/advice_manager";
	}

}
