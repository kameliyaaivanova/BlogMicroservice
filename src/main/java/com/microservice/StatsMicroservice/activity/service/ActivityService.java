package com.microservice.StatsMicroservice.activity.service;

import com.microservice.StatsMicroservice.activity.model.Activity;
import com.microservice.StatsMicroservice.activity.repository.ActivityRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Data
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void create(Activity activity) {
        activityRepository.save(activity);
    }

    public Page<Activity> getAll(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }
}
