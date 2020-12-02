package ca.qc.johnabbott.cs616.server.controller;

import ca.qc.johnabbott.cs616.server.model.Collaborator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Collaborator repo class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@RepositoryRestResource(path = "collaborator")
public interface CollaboratorRepository extends CrudRepository<Collaborator, Long> {
}
