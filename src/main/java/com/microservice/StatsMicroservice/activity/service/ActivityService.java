package com.microservice.StatsMicroservice.activity.service;

import com.microservice.StatsMicroservice.activity.model.Activity;
import com.microservice.StatsMicroservice.activity.repository.ActivityRepository;
import com.microservice.StatsMicroservice.web.dto.ActivityPayload;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Data
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void create(ActivityPayload activityPayload) {
        Activity activity = new Activity();
        activity.setPath(activityPayload.getPath());
        activity.setUserId(activityPayload.getUserId());

        activityRepository.save(activity);
    }

    public Page<Activity> getAll(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }
}
