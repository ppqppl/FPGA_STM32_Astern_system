package com.example.ppqppl_inpatient_ward_server.repository;

import com.example.ppqppl_inpatient_ward_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoginRepository extends JpaRepository<User,String> {

    public List<User> findByAccount(@Param("account")String account);

    public List<User> findByAccountAndPwd(@Param("account")String account,@Param("pwd")String pwd);
}
