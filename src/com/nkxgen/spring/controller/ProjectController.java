package com.nkxgen.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nkxgen.spring.orm.model.ProjectDto;
import com.nkxgen.spring.orm.model.ProjectFilter;
import com.nkxgen.spring.orm.service.ProjectService;

@RestController
public class ProjectController {
	private final ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String getAllProjects(Model model) {
		List<ProjectDto> projectDto = projectService.getAllProjects();

		model.addAttribute("projectDto", projectDto);
		return "ProjectList";
	}

	@RequestMapping(value = "/projectfilter", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String filterProjects(@Validated ProjectFilter projectFilter, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			// Return appropriate error response
			return "Validation Error";
		}
		System.out.println(projectFilter.getProjectId());
		System.out.println(projectFilter.getStatus());
		System.out.println("System.out.println(\"getFilterProjects called\"); called");
		List<ProjectDto> filteredProjects = projectService.filterProjects(projectFilter);
		System.out.println("filteredProjects");

		Gson gson = new Gson();
		String json = gson.toJson(filteredProjects);

		return json;
	}

}
// @PostMapping
// public ProjectDto createProject(@RequestBody ProjectInput projectInput) {
// return projectService.createProject(projectInput);
// }
