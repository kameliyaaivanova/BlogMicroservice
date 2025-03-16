package com.microservice.StatsMicroservice.unit;

import com.microservice.StatsMicroservice.activity.model.Activity;
import com.microservice.StatsMicroservice.activity.repository.ActivityRepository;
import com.microservice.StatsMicroservice.activity.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testCreateActivity() {
        Mockito.when(activityRepository.save(Mockito.any())).then(v -> v.getArguments()[0]);

        Activity activity = new Activity();

        when(activityRepository.save(activity)).thenReturn(activity);

        activityService.create(activity);

        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    void testGetAllActivities_Success() {

        Pageable pageable = PageRequest.of(0, 10);
        Activity activity1 = new Activity();
        Activity activity2 = new Activity();
        List<Activity> activities = List.of(activity1, activity2);
        Page<Activity> activityPage = new PageImpl<>(activities, pageable, activities.size());

        when(activityRepository.findAll(pageable)).thenReturn(activityPage);

        Page<Activity> result = activityService.getAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(activityRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAllActivities_EmptyPage() {

        Pageable pageable = PageRequest.of(0, 10);
        List<Activity> activities = List.of();
        Page<Activity> activityPage = new PageImpl<>(activities, pageable, 0);

        when(activityRepository.findAll(pageable)).thenReturn(activityPage);

        Page<Activity> result = activityService.getAll(pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(activityRepository, times(1)).findAll(pageable);
    }
}
