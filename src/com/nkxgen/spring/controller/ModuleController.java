package com.nkxgen.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nkxgen.spring.orm.model.Module;

@Controller
public class ModuleController {
	Module module;

	public ModuleController() {
		this.module = new Module(1, "module-11", "module descjklpmcf", 11);
	}

	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public String getModule(Model m) {
		System.out.println("modules page returnss");
		m.addAttribute("mod", module);
		return "module";
	}

	@RequestMapping(value = "/createModule", method = RequestMethod.GET)
	public String Createmodule() {
		System.out.println("createModule jsp called");
		return "createModule";
	}

}