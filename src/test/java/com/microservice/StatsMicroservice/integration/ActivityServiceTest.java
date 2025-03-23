package com.microservice.StatsMicroservice.integration;

import com.microservice.StatsMicroservice.activity.model.Activity;
import com.microservice.StatsMicroservice.activity.repository.ActivityRepository;
import com.microservice.StatsMicroservice.activity.service.ActivityService;
import com.microservice.StatsMicroservice.web.dto.ActivityPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRepository activityRepository;

    @AfterEach
    void cleanDatabase() {
        activityRepository.deleteAll();
    }

    @Test
    void testCreate_ValidPayload() {
        ActivityPayload payload = new ActivityPayload();
        payload.setPath("/home");
        payload.setUserId(1L);

        activityService.create(payload);

        assertEquals(1, activityRepository.count());
        Activity savedActivity = activityRepository.findAll().get(0);
        assertEquals("/home", savedActivity.getPath());
        assertEquals(1L, savedActivity.getUserId());
    }

    @Test
    void testCreate_NullPayload_ShouldThrowException() {
        assertThrows(NullPointerException.class, () -> activityService.create(null));
    }

    @Test
    void testGetAll_ReturnsPaginatedResults() {
        Activity activity1 = new Activity();
        activity1.setPath("/home");
        activity1.setUserId(1L);
        Activity activity2 = new Activity();
        activity2.setPath("/dashboard");
        activity2.setUserId(2L);
        activityRepository.save(activity1);
        activityRepository.save(activity2);

        Page<Activity> response = activityService.getAll(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
    }

    @Test
    void testGetAll_ReturnsEmptyPageWhenNoData() {
        Page<Activity> response = activityService.getAll(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(0, response.getTotalElements());
    }
}
