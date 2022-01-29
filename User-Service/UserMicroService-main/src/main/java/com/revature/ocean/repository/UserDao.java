package com.revature.ocean.repository;

import com.revature.ocean.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Dao layer to communicate with the database and perform CRUD operations
 */
@Repository("userDao")
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    /**
     * Queries the database to return a User object specific to the username passed to the method
     *
     * @param username  username of the user object to be retrieved
     * @return          returns the user object that matches the username
     */
    User findUserByUsername(String username);


}
