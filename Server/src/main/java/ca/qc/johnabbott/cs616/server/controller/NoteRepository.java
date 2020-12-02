package ca.qc.johnabbott.cs616.server.controller;

import ca.qc.johnabbott.cs616.server.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Note repo class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@RepositoryRestResource(path = "note")
public interface NoteRepository extends CrudRepository<Note, String> {
}
