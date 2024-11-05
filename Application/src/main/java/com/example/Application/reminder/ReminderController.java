package com.example.Application.reminder;

import com.example.Application.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public void updateReminder(@RequestBody Reminder reminderToUpdate) {
        if (reminderToUpdate.getReminderTime() != null) {
            String dateTimeString = reminderToUpdate.getReminderDate() + " " + reminderToUpdate.getReminderTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

            // Parse the date-time string into LocalDateTime
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
                Instant reminderTimeUtc = localDateTime.atZone(ZoneId.of(reminderToUpdate.getUserTimeZone())).toInstant();
                reminderToUpdate.setReminderTimeUtc(reminderTimeUtc);
            } catch (DateTimeParseException e) {
                // Handle exception
                System.err.println("Failed to parse date time: " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date or time format", e);
            }
        }
        reminderService.updateReminder(reminderToUpdate);
    }
}
