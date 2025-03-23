package com.microservice.StatsMicroservice.unit;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import com.microservice.StatsMicroservice.activity.repository.DeletedFilesRepository;
import com.microservice.StatsMicroservice.activity.service.DeletedFilesService;
import com.microservice.StatsMicroservice.web.dto.DeletedFilesPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletedFileServiceTest {

    @InjectMocks
    private DeletedFilesService deletedFilesService;

    @Mock
    private DeletedFilesRepository deletedFilesRepository;

    @Test
    void testCreateDeletedFile() {
        DeletedFilesPayload deletedFilesPayload = new DeletedFilesPayload();
        deletedFilesPayload.setAmount(1L);

        DeletedFiles deletedFile = new DeletedFiles();
        deletedFile.setAmount(deletedFilesPayload.getAmount());

        when(deletedFilesRepository.save(any(DeletedFiles.class))).thenReturn(deletedFile);

        deletedFilesService.create(deletedFilesPayload);

        verify(deletedFilesRepository, times(1)).save(Mockito.any(DeletedFiles.class));
    }

    @Test
    void testGetAll_ShouldReturnPageOfDeletedFiles_WhenDataExists() {

        Pageable pageable = PageRequest.of(0, 10);

        DeletedFiles deletedFile = new DeletedFiles();

        Page<DeletedFiles> deletedFilesPage = new PageImpl<>(List.of(deletedFile));

        when(deletedFilesRepository.findAll(pageable)).thenReturn(deletedFilesPage);

        Page<DeletedFiles> result = deletedFilesService.getAll(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(deletedFilesRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAll_ShouldReturnEmptyPage_WhenNoDataExists() {

        Pageable pageable = PageRequest.of(0, 10);
        Page<DeletedFiles> emptyPage = Page.empty();

        when(deletedFilesRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<DeletedFiles> result = deletedFilesService.getAll(pageable);

        assertTrue(result.isEmpty());
        verify(deletedFilesRepository, times(1)).findAll(pageable);
    }
}

