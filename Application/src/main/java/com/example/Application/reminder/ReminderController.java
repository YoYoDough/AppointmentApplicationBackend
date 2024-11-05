package com.example.Application.reminder;

import com.example.Application.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    public List<Reminder> getReminders(){
        System.out.println(reminderService.getReminders());
        return reminderService.getReminders();
    }
    @GetMapping("/id")
    public List<Reminder> getRemindersForUser(@RequestParam String email){
        Long userId = userService.getUserIdFromEmail(email);
        if (userId == null)
        {
            throw new IllegalStateException("User not found!");
        }
        List<Reminder> userReminders = reminderService.getRemindersWithId(userId);
        return userReminders;
    }
    @PostMapping
    public void addReminder(@RequestBody Reminder reminder, @RequestParam String userEmail){
        Long userId = userService.getUserIdFromEmail(userEmail);
        if (userId == null) {
            throw new IllegalStateException("User not found!");
        }
        reminder.setUserId(userId);
        // Combine reminder date and time
        if (reminder.getReminderTime() != null)
        {
            String dateTimeString = reminder.getReminderDate().toString() + " " + reminder.getReminderTime();

            // Parse the combined date and time string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);

            // Convert the LocalDateTime to UTC Instant
            Instant reminderTimeUtc = localDateTime.atZone(ZoneId.of(reminder.getUserTimeZone())).toInstant();
            reminder.setReminderTimeUtc(reminderTimeUtc);
        }

        System.out.println(reminder);
        reminderService.addNewReminder(reminder);
    }

    @DeleteMapping
    public void deleteReminder(@RequestBody List <Long> remindersToDelete){
        for (Long id: remindersToDelete){
            Reminder selectedReminder = reminderService.getReminderWithId(id);
            reminderService.deleteSelectedReminder(selectedReminder);
        }
    }

    @PutMapping
    public void updateReminder(@RequestBody Reminder reminderToUpdate){
        LocalDateTime localDateTime = LocalDateTime.parse(reminderToUpdate.getReminderTime(), DateTimeFormatter.ofPattern("h:mm a"));
        Instant reminderTimeUtc = localDateTime.atZone(ZoneId.of(reminderToUpdate.getUserTimeZone())).toInstant();
        reminderToUpdate.setReminderTimeUtc(reminderTimeUtc);
        reminderService.updateReminder(reminderToUpdate);
    }
}
