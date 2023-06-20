package com.nkxgen.spring.orm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.nkxgen.spring.orm.model.Project;
import com.nkxgen.spring.orm.model.ProjectDto;
import com.nkxgen.spring.orm.model.ProjectFilter;

@Component
public class ProjectDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<ProjectDto> getAllProjects() {
		TypedQuery<Project> query = entityManager.createQuery("SELECT pt FROM Project pt", Project.class);
		List<Project> projects = query.getResultList();
		List<ProjectDto> convertedTasks = projects.stream().map(ProjectDto::fromEntity).collect(Collectors.toList());
		return convertedTasks;
	}

	public Project findById(short id) {
		return entityManager.find(Project.class, id);
	}

	public List<ProjectDto> getFilterProjects(ProjectFilter projectFilter) {

		Short projectId = projectFilter.getProjectId();
		String status = projectFilter.getStatus();

		System.out.println(projectId);

		System.out.println(status);
		List<Project> getFilterProjects;

		if (projectId != 0 && status != null) {
			// Filter by both project and resource
			TypedQuery<Project> query = entityManager.createQuery(
					"SELECT pt FROM Project pt WHERE pt.projectId = :projectId AND pt.projectStatus = :status",
					Project.class);
			query.setParameter("projectId", projectId);
			query.setParameter("status", status);
			getFilterProjects = query.getResultList();
		}

		else if (projectId == null && status != null) {
			TypedQuery<Project> query = entityManager
					.createQuery("SELECT pt FROM ProjectTask pt WHERE pt.projectStatus= :status", Project.class);
			query.setParameter("status", status);
			getFilterProjects = query.getResultList();

		} else if (projectId != 0 && status == null) {
			// Filter by project only
			TypedQuery<Project> query = entityManager
					.createQuery("SELECT pt FROM Project pt WHERE  pt.projectId  = :projectId", Project.class);
			query.setParameter("projectId", projectId);
			getFilterProjects = query.getResultList();
		} else {
			// No filtering criteria provided, retrieve all tasks
			TypedQuery<Project> query = entityManager.createQuery("SELECT pt FROM Project pt", Project.class);
			getFilterProjects = query.getResultList();
		}
		List<ProjectDto> filteredTaskDTOs = new ArrayList<>();

		for (Project project : getFilterProjects) {
			ProjectDto dto = ProjectDto.fromEntity(project);
			filteredTaskDTOs.add(dto);
		}
		System.out.println(filteredTaskDTOs);
		return filteredTaskDTOs;
	}

}