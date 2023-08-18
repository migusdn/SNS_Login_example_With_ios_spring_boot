package com.migusdn.snslogindemo.repositroy;

import com.migusdn.snslogindemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : SNSLoginDemo
 * @name : UserRepository
 * @date : 2023/08/17 10:16 PM
 * @modifyed : $
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
