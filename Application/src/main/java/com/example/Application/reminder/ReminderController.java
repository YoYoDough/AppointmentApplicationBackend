package com.example.Application.reminder;

import com.example.Application.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin("http://localhost:3000")
public class ReminderController {

    private ReminderService reminderService;
    private UserService userService;

    @Autowired
    public ReminderController(ReminderService reminderService, UserService userService){
        this.reminderService = reminderService;
        this.userService = userService;
    }

    @GetMapping
    public List<Reminder> getRemindersForUser(){
        return reminderService.getUsers();
    }
    @PostMapping
    public void addReminder(@RequestBody Reminder reminder, @RequestParam String userEmail){
        Long userId = userService.getUserIdFromEmail(userEmail);
        if (userId == null) {
            throw new IllegalStateException("User not found!");
        }
        reminder.setUserId(userId);
        System.out.println(reminder);
        reminderService.addNewReminder(reminder);
    }
}
