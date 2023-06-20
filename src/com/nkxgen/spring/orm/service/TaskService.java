package com.nkxgen.spring.orm.service;

import java.util.List;

import com.nkxgen.spring.orm.model.ResTaskFilter;
import com.nkxgen.spring.orm.model.TFilterCriteria;
import com.nkxgen.spring.orm.model.Task;
import com.nkxgen.spring.orm.model.TaskDto;

public interface TaskService {
	List<Task> getTasksByUserId(int userId);

	List<Task> filterTasks(ResTaskFilter resTaskFilter);

	// Other task-related methods...

	List<TaskDto> PtfilterTasks(TFilterCriteria filterCriteria);

	List<TaskDto> getAllTasks();

	Task getTaskById(int taskId);

	void saveTask(Task task);

	Boolean updateStatus(int taskId);

	// List<TaskDto> filterTasks(TFilterCriteria filterCriteria);

	// Add other method declarations if required
}