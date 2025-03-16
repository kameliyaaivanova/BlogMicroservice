package com.microservice.StatsMicroservice.activity.repository;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedFilesRepository extends JpaRepository<DeletedFiles, Long> {
}
