package com.example.Application.reminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin("http://localhost:3000")
public class ReminderController {

    private ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService){
        this.reminderService = reminderService;
    }
    @PostMapping
    public void addReminder(@RequestBody Reminder reminder){
        reminderService.addNewReminder(reminder);
    }
}
