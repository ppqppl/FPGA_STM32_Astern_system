package com.example.ppqppl_inpatient_ward_server.repository;


import com.example.ppqppl_inpatient_ward_server.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StarttestRepository extends JpaRepository<Score,String> {

    public List<Score> findAllById(@Param("id")String id);

    public void deleteById(@Param("id")String id);
}
