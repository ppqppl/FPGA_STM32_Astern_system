package com.example.ppqppl_inpatient_ward_server.repository;

import com.example.ppqppl_inpatient_ward_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
//    public User findById(@Param("id")String id);
    public List<User> findAllByKind(@Param("kind")String kind);
    public List<User> findAllById(@Param("id")String id);

    public void deleteById(@Param("id")String id);
}
