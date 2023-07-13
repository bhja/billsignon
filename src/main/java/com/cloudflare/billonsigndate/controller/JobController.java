package com.cloudflare.billonsigndate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class JobController {

    /*@Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;*/

    /*@PostMapping("/BillOnSignDate")
    public void billOnSignDate(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }*/
}
