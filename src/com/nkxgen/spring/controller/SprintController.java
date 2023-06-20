package com.nkxgen.spring.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nkxgen.spring.orm.model.Sprint;
import com.nkxgen.spring.orm.service.SprintService;

@Controller
public class SprintController {

	@Autowired
	private SprintService sprintService;

	@RequestMapping(value = "/sprints", method = RequestMethod.GET)
	public String getAllSprints(Model model) {
		System.out.println("Sprint List JSP Requested");

		// Retrieve all sprints from the database
		List<Sprint> sprints = sprintService.getAllSprints();

		// Add the sprints to the model
		model.addAttribute("sprints", sprints);

		return "Tarak_sprint_home";
	}

	@RequestMapping(value = "/sprint_details", method = RequestMethod.GET)
	public String getSprintDetails(@RequestParam("sprintId") int sprintId, Model model) {
		// Retrieve the selected sprint details from the database and add them to the model

		System.out.println("Sprint DETAILS JSP Requested");
		Sprint sprint = sprintService.getSprintById(sprintId);
		model.addAttribute("sprint", sprint);
		return "sprint_details";
	}

	@RequestMapping(value = "/new_sprint", method = RequestMethod.GET)
	public String addSprint(Model model) {
		System.out.println("Add Sprint JSP Requested");

		// Add any necessary data to the model for rendering the add sprint page

		return "newsprint";
	}

	@RequestMapping(value = "/create_sprint", method = RequestMethod.POST)
	public String createSprint(@RequestParam("moduleId") String moduleId, @RequestParam("masterId") String masterId,
			@RequestParam("projectId") String projectId, @RequestParam("sprintName") String sprintName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model) {
		// Create a new sprint and save it to the database

		System.out.println("Create Sprint Requested");

		Sprint sprint = new Sprint();
		sprint.setModuleId(Integer.parseInt(moduleId));
		sprint.setMasterId(Integer.parseInt(masterId));
		sprint.setProjectId(Integer.parseInt(projectId));
		sprint.setSprintName(sprintName);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Replace with your date format
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = dateFormat.parse(startDate);
			parsedEndDate = dateFormat.parse(endDate);
		} catch (ParseException e) {
			// Handle the parsing exception
			e.printStackTrace();
			return endDate; // or throw an exception
		}

		Timestamp timestampStartDate = new Timestamp(parsedStartDate.getTime());
		Timestamp timestampEndDate = new Timestamp(parsedEndDate.getTime());

		sprint.setStartDate(timestampStartDate);
		sprint.setEndDate(timestampEndDate);

		sprintService.createSprint(sprint);

		// Redirect to the sprint list page
		return "redirect:/sprints";
	}

	@RequestMapping(value = "/addsprint", method = RequestMethod.POST)
	public String saveNewsprint(@Validated Sprint sprint, Model model) {
		System.out.println("Save New sprint Page Requested");
		// get all employees from DAO

		sprintService.createSprint(sprint);
		// set it to the model
		// model.addAttribute("emp",emp);

		return "sprints";
	}
}