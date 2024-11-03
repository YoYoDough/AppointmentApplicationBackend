package com.example.Application.reminder;

import com.example.Application.User.UserRepository;
import com.example.Application.User.UserService;
import com.example.Application.emailService.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private EmailSenderService emailSenderService;
    private UserService userService;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository, EmailSenderService emailSenderService, UserService userService){
        this.reminderRepository = reminderRepository;
        this.emailSenderService = emailSenderService;
        this.userService = userService;
    }

    public void addNewReminder(Reminder reminder){
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
        if (findReminder.isPresent())
        {
            return findReminder.get();
        }
        return null;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendDueReminders(){
        LocalDate today = LocalDate.now();
        List<Reminder> dueReminders = reminderRepository.findByReminderDate(today);
        String emailText = "";
        for (Reminder reminder: dueReminders){
            Long userId = reminder.getUserId();
            String userEmail = userService.getUserEmailFromId(userId);
            if (reminder.getReminderTime() == null){
                emailText = "This is a reminder for: " + reminder.getReminderName() +
                        " scheduled for today";
            }
        }

    }

    public void updateReminder(Reminder updateReminder) {
        reminderRepository.save(updateReminder);
    }
}
