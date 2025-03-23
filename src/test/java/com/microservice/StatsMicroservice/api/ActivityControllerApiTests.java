package com.microservice.StatsMicroservice.api;

import com.microservice.StatsMicroservice.activity.model.Activity;
import com.microservice.StatsMicroservice.activity.service.ActivityService;
import com.microservice.StatsMicroservice.web.controller.ActivityController;
import com.microservice.StatsMicroservice.web.dto.ActivityPayload;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@WebMvcTest(ActivityController.class)
public class ActivityControllerApiTests extends BaseApiTest{

    @MockitoBean
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;


    @Test
    void testCreateActivity_Success() throws Exception {
        ActivityPayload activityPayload = new ActivityPayload();
        activityPayload.setPath("home");
        activityPayload.setUserId(1L);

        doNothing().when(activityService).create(any(ActivityPayload.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityPayload)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(activityService, times(1)).create(any(ActivityPayload.class));
    }

    @Test
    void testCreateActivity_ValidationFailure() throws Exception {
        ActivityPayload activityPayload = new ActivityPayload();
        activityPayload.setPath("");
        activityPayload.setUserId(2L);

        mockMvc.perform(MockMvcRequestBuilders.post("/activity/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activityPayload)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetAllActivities_Success() throws Exception {

        Activity activity = new Activity();
        activity.setPath("home");
        activity.setUserId(1L);
        Page<Activity> activityPage = new PageImpl<>(List.of(activity));

        when(activityService.getAll(any(Pageable.class))).thenReturn(activityPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/activity")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].path").value("home"));

        verify(activityService, times(1)).getAll(any(Pageable.class));
    }

    @Test
    void testGetAllActivities_NoContent() throws Exception {
        when(activityService.getAll(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/activity")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());

        verify(activityService, times(1)).getAll(any(Pageable.class));
    }


}
