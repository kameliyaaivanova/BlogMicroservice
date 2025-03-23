package com.microservice.StatsMicroservice.api;

import com.microservice.StatsMicroservice.activity.model.DeletedFiles;
import com.microservice.StatsMicroservice.activity.service.DeletedFilesService;
import com.microservice.StatsMicroservice.web.controller.DeletedFilesController;
import com.microservice.StatsMicroservice.web.dto.DeletedFilesPayload;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(DeletedFilesController.class)
public class DeletedFilesControllerTests extends BaseApiTest {

    @MockitoBean
    private DeletedFilesService deletedFilesService;

    @Test
    void testCreateDeletedFile_Success() throws Exception {

        DeletedFilesPayload deletedFilesPayload = new DeletedFilesPayload();
        deletedFilesPayload.setAmount(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/files/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletedFilesPayload)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(deletedFilesService, Mockito.times(1)).create(Mockito.any(DeletedFilesPayload.class));
    }

    @Test
    void testCreateDeletedFile_ValidationFailure() throws Exception {

        DeletedFilesPayload deletedFilesPayload = new DeletedFilesPayload();
        deletedFilesPayload.setAmount(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/files/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deletedFilesPayload)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetAllDeletedFiles_Success() throws Exception {

        DeletedFiles deletedFile = new DeletedFiles();
        deletedFile.setAmount(1L);

        Page<DeletedFiles> deletedFilesPage = new PageImpl<>(List.of(deletedFile));

        Mockito.when(deletedFilesService.getAll(Mockito.any(Pageable.class))).thenReturn(deletedFilesPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/files")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Expecting HTTP 200 OK
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))  // Expecting JSON response
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].amount").value(1L));  // Verify file name

        Mockito.verify(deletedFilesService, Mockito.times(1)).getAll(Mockito.any(Pageable.class));
    }

    @Test
    void testGetAllDeletedFiles_NoContent() throws Exception {

        Page<DeletedFiles> deletedFilesPage = Page.empty();

        Mockito.when(deletedFilesService.getAll(Mockito.any(Pageable.class))).thenReturn(deletedFilesPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/files")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());

        Mockito.verify(deletedFilesService, Mockito.times(1)).getAll(Mockito.any(Pageable.class));
    }

    @Test
    void testGetAllDeletedFiles_EmptyPageRequest() throws Exception {

        Page<DeletedFiles> deletedFilesPage = Page.empty();

        Mockito.when(deletedFilesService.getAll(Mockito.any(Pageable.class))).thenReturn(deletedFilesPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/files")
                        .param("page", "100")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());

        Mockito.verify(deletedFilesService, Mockito.times(1)).getAll(Mockito.any(Pageable.class));
    }
}
