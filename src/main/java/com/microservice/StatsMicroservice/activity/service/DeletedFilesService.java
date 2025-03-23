package com.microservice.StatsMicroservice.activity.service;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import com.microservice.StatsMicroservice.activity.repository.DeletedFilesRepository;
import com.microservice.StatsMicroservice.web.dto.DeletedFilesPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeletedFilesService {

    private final DeletedFilesRepository deletedFilesRepository;

    @Autowired
    public DeletedFilesService(DeletedFilesRepository deletedFilesRepository) {
        this.deletedFilesRepository = deletedFilesRepository;
    }

    public void create(DeletedFilesPayload deletedFilesPayload) {
        DeletedFiles deletedFile = new DeletedFiles();
        deletedFile.setAmount(deletedFilesPayload.getAmount());

        deletedFilesRepository.save(deletedFile);
    }

    public Page<DeletedFiles> getAll(Pageable pageable) {
        return deletedFilesRepository.findAll(pageable);
    }
}
