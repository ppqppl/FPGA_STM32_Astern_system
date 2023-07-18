package com.example.ppqppl_inpatient_ward_server.repository;

import com.example.ppqppl_inpatient_ward_server.entity.Admin;
import com.example.ppqppl_inpatient_ward_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,String> {

    public List<Admin> findByAccount(@Param("account")String account);

    public List<Admin> findAllById(@Param("id")String id);

    public List<Admin> findByAccountAndPwd(@Param("account")String account, @Param("pwd")String pwd);

    public List<Admin> findByPhone(@Param("phone")String phone);
}
