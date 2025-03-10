package com.jordep.Event.Manager.Repository;

import com.jordep.Event.Manager.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
