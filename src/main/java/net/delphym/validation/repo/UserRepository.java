package net.delphym.validation.repo;

import org.springframework.data.repository.CrudRepository;

import net.delphym.validation.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
