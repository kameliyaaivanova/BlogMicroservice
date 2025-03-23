package com.microservice.StatsMicroservice.integration;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import com.microservice.StatsMicroservice.activity.repository.DeletedFilesRepository;
import com.microservice.StatsMicroservice.activity.service.DeletedFilesService;
import com.microservice.StatsMicroservice.web.dto.DeletedFilesPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeletedFilesServiceTest {

    @Autowired
    private DeletedFilesService deletedFilesService;

    @Autowired
    private DeletedFilesRepository deletedFilesRepository;

    @AfterEach
    void cleanDatabase() {
        deletedFilesRepository.deleteAll();
    }

    @Test
    void testCreate_ValidPayload() {
        DeletedFilesPayload payload = new DeletedFilesPayload();
        payload.setAmount(5L);

        deletedFilesService.create(payload);

        assertEquals(1, deletedFilesRepository.count());
        DeletedFiles savedFile = deletedFilesRepository.findAll().get(0);
        assertEquals(5, savedFile.getAmount());
    }

    @Test
    void testCreate_NullPayload_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> deletedFilesService.create(null));
    }

    @Test
    void testGetAll_ReturnsPaginatedResults() {
        DeletedFiles file1 = new DeletedFiles();
        file1.setAmount(3L);
        DeletedFiles file2 = new DeletedFiles();
        file2.setAmount(7L);
        deletedFilesRepository.save(file1);
        deletedFilesRepository.save(file2);

        Page<DeletedFiles> response = deletedFilesService.getAll(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
    }

    @Test
    void testGetAll_ReturnsEmptyPageWhenNoData() {
        Page<DeletedFiles> response = deletedFilesService.getAll(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(0, response.getTotalElements());
    }
}
