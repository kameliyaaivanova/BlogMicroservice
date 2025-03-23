package com.microservice.StatsMicroservice.web.controller;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import com.microservice.StatsMicroservice.activity.service.DeletedFilesService;
import com.microservice.StatsMicroservice.web.dto.DeletedFilesPayload;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class DeletedFilesController {
    private final DeletedFilesService deletedFilesService;

    @Autowired
    public DeletedFilesController(DeletedFilesService deletedFilesService) {
        this.deletedFilesService = deletedFilesService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(@Valid @RequestBody DeletedFilesPayload deletedFilesPayload){
        deletedFilesService.create(deletedFilesPayload);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<DeletedFiles>> getAll(Pageable pageable){
        return ResponseEntity.ok(deletedFilesService.getAll(pageable));
    }
}
