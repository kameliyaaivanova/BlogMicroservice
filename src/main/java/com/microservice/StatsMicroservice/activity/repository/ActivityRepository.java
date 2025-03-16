package com.microservice.StatsMicroservice.activity.repository;

import com.microservice.StatsMicroservice.activity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
