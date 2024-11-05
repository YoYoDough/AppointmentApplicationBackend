package com.example.Application.reminder;

import com.example.Application.User.UserRepository;
import com.example.Application.User.UserService;
import com.example.Application.emailService.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
        // Get today's date in UTC
        LocalDate todayUtc = LocalDate.now(ZoneOffset.UTC);
        LocalDate yesterdayUtc = todayUtc.minusDays(1);

        // Get current time in UTC
        LocalTime now = LocalTime.now(ZoneOffset.UTC);
        System.out.println("Current UTC Time: " + now);
        System.out.println(now);
        LocalTime oneHourFromNow = now.plusHours(1).withSecond(0).withNano(0); // Set seconds and nanoseconds to zero for accuracy
        System.out.println(oneHourFromNow);
        List<Reminder> dueReminders = reminderRepository.findByReminderDate(Arrays.asList(todayUtc, yesterdayUtc));
        String emailText = "";

        for (Reminder reminder : dueReminders) {
            System.out.println("Reminder's UTC time: " + reminder.getReminderTimeUtc().toString());
            Long userId = reminder.getUserId();
            String userEmail = userService.getUserEmailFromId(userId);
            // Parse the UTC reminder time
            Instant reminderTimeUtc = reminder.getReminderTimeUtc() != null ? reminder.getReminderTimeUtc() : null; // Assuming this is stored as an Instant
            System.out.println("Reminder UTC Time: " + reminderTimeUtc);
            LocalTime reminderTime = reminderTimeUtc.atZone(ZoneOffset.UTC).toLocalTime();
            System.out.println(oneHourFromNow);
            System.out.println("Reminder Time (UTC): " + reminderTime);

            // If reminderTime is null, send the email in the morning
            if (reminderTime == null && (reminder.getReminderDate().equals(todayUtc) || reminder.getReminderDate().equals(yesterdayUtc))) {
                if (now.equals(LocalTime.of(16, 0)))
                {
                    emailText = "This is a reminder for: " + reminder.getReminderName() +
                            " scheduled for today.";
                    emailSenderService.sendReminderEmail(userEmail, "Reminder due today", emailText);
                }
            }
            else if (reminderTime != null && reminderTime.getHour() == oneHourFromNow.getHour() && reminderTime.getMinute() == oneHourFromNow.getMinute() && (reminder.getReminderDate().equals(todayUtc) || reminder.getReminderDate().equals(yesterdayUtc))) {
                System.out.println("One hour from now message running: " + reminderTime);
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

