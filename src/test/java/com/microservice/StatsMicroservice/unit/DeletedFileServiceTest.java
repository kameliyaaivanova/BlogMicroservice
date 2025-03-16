package com.microservice.StatsMicroservice.unit;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import com.microservice.StatsMicroservice.activity.repository.DeletedFilesRepository;
import com.microservice.StatsMicroservice.activity.service.DeletedFilesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletedFileServiceTest {

    @InjectMocks
    private DeletedFilesService deletedFilesService;

    @Mock
    private DeletedFilesRepository deletedFilesRepository;

    @Test
    void testCreateDeletedFile() {
        DeletedFiles deletedFile = new DeletedFiles();

        when(deletedFilesRepository.save(deletedFile)).thenReturn(deletedFile);

        deletedFilesService.create(deletedFile);

        verify(deletedFilesRepository, times(1)).save(deletedFile);
    }
}

