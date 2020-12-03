package ca.qc.johnabbott.cs616.server.controller;

import ca.qc.johnabbott.cs616.server.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;

/**
 * User repo class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@RepositoryRestResource(path = "user", excerptProjection = UserRepository.LimitedUser.class)
public interface UserRepository extends CrudRepository<User, String> {

    User findUserByUsername(@Param(value = "username") String username);
    User findUserByEmail(@Param(value = "email") String email);

    /**
     * Hides password from embedded views.
     */
    @Projection(name = "LimitedUser", types = { User.class })
    interface LimitedUser {
        String getUuid();
        String getUsername();
    }
}
