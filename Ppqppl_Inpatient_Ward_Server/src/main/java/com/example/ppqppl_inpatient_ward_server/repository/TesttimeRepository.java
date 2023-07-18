package com.example.ppqppl_inpatient_ward_server.repository;

import com.example.ppqppl_inpatient_ward_server.entity.Testtime;
import com.example.ppqppl_inpatient_ward_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TesttimeRepository extends JpaRepository<Testtime,String> {
    public List<Testtime> findAllById(@Param("id")String id);

    public void deleteAllById(@Param("id")String id);
}
