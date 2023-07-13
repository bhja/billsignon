package com.cloudflare.billonsigndate.controller;

import com.cloudflare.billonsigndate.model.Response;
import com.cloudflare.billonsigndate.service.BatchSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class JobController {

   @Autowired
   private BatchSchedule schedule;
    @PostMapping("/BillOnSignDate")
    public ResponseEntity billOnSignDate(){
          schedule.billOnSignDate();
          return ResponseEntity.ok("Job kicked off");
    }
}
