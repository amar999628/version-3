package com.nkxgen.spring.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.nkxgen.spring.orm.model.Project;
import com.nkxgen.spring.orm.model.ResTaskFilter;
import com.nkxgen.spring.orm.model.Task;
import com.nkxgen.spring.orm.model.User;

@Repository
@Component
public class TaskDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Task> getTasksByUserId(int userId) {
		User user = findById(userId);
		String jpql = "SELECT t FROM Task t WHERE t.taskSupervisor = :user";
		TypedQuery<Task> taskQuery = entityManager.createQuery(jpql, Task.class);
		taskQuery.setParameter("user", user);
		return taskQuery.getResultList();
	}

	public List<Task> viewTasksForUser(int userId) {
		return getTasksByUserId(userId);
	}

	public List<Task> filterTasks(ResTaskFilter resTaskFilter) {
		System.out.println("In filterTasks");

		String jpql = "SELECT t FROM Task t WHERE 1 = 1 AND t.taskSupervisor.id = :userId";

		TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);

		query.setParameter("userId", resTaskFilter.getUserId());

		if (resTaskFilter.getProject() != null && !resTaskFilter.getStatus().isEmpty()
				&& !resTaskFilter.getCategory().isEmpty()) {
			jpql = "SELECT t FROM Task t WHERE 1 = 1 AND t.taskSupervisor.id = :userId AND t.taskCategory = :category AND t.taskStatus = :status AND t.project.id = :projectId";
			Project project = findProjectById(resTaskFilter.getProject());
			query.setParameter("projectId", resTaskFilter.getProject());
		} else {
			if (resTaskFilter.getProject() != null && resTaskFilter.getProject() != 0) {
				Project project = findProjectById(resTaskFilter.getProject());
				query.setParameter("projectId", resTaskFilter.getProject());
			}

			if (resTaskFilter.getStatus() != null && !resTaskFilter.getStatus().isEmpty()) {
				query.setParameter("status", resTaskFilter.getStatus());
			}

			if (resTaskFilter.getCategory() != null && !resTaskFilter.getCategory().isEmpty()) {
				query.setParameter("category", resTaskFilter.getCategory());
			}
		}

		return query.getResultList();
	}

	public Project findProjectById(Short projectId) {
		System.out.println("in findprojbyid");
		return entityManager.find(Project.class, projectId);
	}

	private User findById(int userId) {
		return entityManager.find(User.class, userId);
	}

	public Task getTaskById(int taskId) {
		return entityManager.find(Task.class, taskId);
	}

	public void saveTask(Task task) {
		entityManager.merge(task);
	}

	public boolean updateStatus(int taskId2) {
		String jpql = "UPDATE Task t SET t.taskStatus = :status WHERE t.taskId = :taskId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("status", "rew");
		query.setParameter("taskId", taskId2);

		int rowsAffected = query.executeUpdate();
		return rowsAffected > 0;
	}

	public List<TaskDto> getTasksByUserId(int userId) {
		User user = findById(userId);
		String jpql = "SELECT t FROM Task t WHERE t.taskSupervisor = :user";
		TypedQuery<Task> taskQuery = entityManager.createQuery(jpql, Task.class);
		taskQuery.setParameter("user", user);
		List<Task> tasks = taskQuery.getResultList();

		List<TaskDto> taskDTOs = new ArrayList<>();
		for (Task task : tasks) {
			TaskDto dto = TaskDto.fromEntity(task);
			taskDTOs.add(dto);
		}

		return taskDTOs;
	}

	public List<TaskDto> getAllTasks() {
		String jpql = "SELECT t FROM Task t";// query
		TypedQuery<Task> taskQuery = entityManager.createQuery(jpql, Task.class);
		List<Task> tasks = taskQuery.getResultList();

		List<TaskDto> taskDTOs = new ArrayList<>();
		for (Task task : tasks) {
			TaskDto dto = TaskDto.fromEntity(task);
			taskDTOs.add(dto);
		}

		return taskDTOs;
	}

	private User findById(int userId) {
		return entityManager.find(User.class, userId);
	}

	public List<TaskDto> TfilterTasks(TFilterCriteria filterCriteria) {
		int taskId = filterCriteria.getTaskId();
		String taskStatus = filterCriteria.getTaskStatus();
		int taskSupervisorId = filterCriteria.getTaskSupervisorId();

		StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Task t WHERE 1=1");

		if (taskId != 0) {
			queryBuilder.append(" AND t.taskId = :taskId");
		}
		if (taskStatus != null && !taskStatus.isEmpty()) {
			queryBuilder.append(" AND t.taskStatus = :taskStatus");
		}
		if (taskSupervisorId != 0) {
			queryBuilder.append(" AND t.taskSupervisor.id = :taskSupervisorId");
		}

		TypedQuery<Task> query = entityManager.createQuery(queryBuilder.toString(), Task.class);

		if (taskId != 0) {
			query.setParameter("taskId", taskId);
		}
		if (taskStatus != null && !taskStatus.isEmpty()) {
			query.setParameter("taskStatus", taskStatus);
		}
		if (taskSupervisorId != 0) {
			query.setParameter("taskSupervisorId", taskSupervisorId);
		}

		List<Task> filteredTasks = query.getResultList();

		List<TaskDto> filteredTaskDTOs = new ArrayList<>();
		for (Task task : filteredTasks) {
			TaskDto dto = TaskDto.fromEntity(task);
			filteredTaskDTOs.add(dto);
		}

		return filteredTaskDTOs;
	}
}
	// Implement other methods of the TaskDao interface...
}