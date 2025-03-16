package com.microservice.StatsMicroservice.web;

import com.microservice.StatsMicroservice.activity.model.Activity;
import com.microservice.StatsMicroservice.activity.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(@Valid @RequestBody Activity activity){
        activityService.create(activity);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<Activity>> getAll(Pageable pageable){
        return ResponseEntity.ok(activityService.getAll(pageable));
    }
}
