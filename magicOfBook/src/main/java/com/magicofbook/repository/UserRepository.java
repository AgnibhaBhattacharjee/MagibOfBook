package com.magicofbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magicofbook.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
