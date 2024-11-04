package com.example.Application.reminder;

import com.example.Application.User.UserRepository;
import com.example.Application.User.UserService;
import com.example.Application.emailService.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private EmailSenderService emailSenderService;
    private UserService userService;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository, EmailSenderService emailSenderService, UserService userService) {
        this.reminderRepository = reminderRepository;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
    }

    public void addNewReminder(Reminder reminder) {
        reminderRepository.save(reminder);
    }

    public List<Reminder> getReminders() {
        return reminderRepository.findAll();
    }

    public List<Reminder> getRemindersWithId(Long userId) {
        return reminderRepository.findRemindersWithId(userId);
    }


    public void deleteSelectedReminder(Reminder selectedReminder) {
        reminderRepository.delete(selectedReminder);
    }

    public Reminder getReminderWithId(Long id) {
        Optional<Reminder> findReminder = reminderRepository.findById(id);
        if (findReminder.isPresent()) {
            return findReminder.get();
        }
        return null;
    }

    @Scheduled(cron = "0 * * * * *") // This runs every minute
    public void sendDueReminders() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        System.out.println(now);
        LocalTime oneHourFromNow = now.plusHours(1).withSecond(0).withNano(0); // Set seconds and nanoseconds to zero for accuracy
        List<Reminder> dueReminders = reminderRepository.findByReminderDate(today);
        String emailText = "";

        for (Reminder reminder : dueReminders) {
            Long userId = reminder.getUserId();
            String userEmail = userService.getUserEmailFromId(userId);
            LocalTime reminderTime = reminder.getReminderTime() != null
                    ? LocalTime.parse(reminder.getReminderTime(), DateTimeFormatter.ofPattern("h:mm a"))
                    : null; // Parse the reminder time

            System.out.println(reminderTime);
            // If reminderTime is null, send the email in the morning
            if (reminderTime == null) {
                if (LocalTime.now().equals(LocalTime.of(8, 0)))
                {
                    emailText = "This is a reminder for: " + reminder.getReminderName() +
                            " scheduled for today.";
                    emailSenderService.sendReminderEmail(userEmail, "Reminder due today", emailText);
                }
            }
            else if (reminderTime.getHour() == oneHourFromNow.getHour() && reminderTime.getMinute() == oneHourFromNow.getMinute()) {
                // Send email if the reminder is one hour away
                emailText = "This is a reminder for: " + reminder.getReminderName() +
                        " scheduled for today at " + reminder.getReminderTime();
                emailSenderService.sendReminderEmail(userEmail, "Reminder due in 1 hour", emailText);
            }
        }
    }
        public void updateReminder (Reminder updateReminder){
            reminderRepository.save(updateReminder);
        }
    }

