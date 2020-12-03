package ca.qc.johnabbott.cs616.server.controller;

import ca.qc.johnabbott.cs616.server.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "task")
public interface TaskRepository extends CrudRepository<Task, String> {
}
