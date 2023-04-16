package com.magicofbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magicofbook.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
