package com.example.Application.reminder;

import com.example.Application.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository){
        this.reminderRepository = reminderRepository;
    }

    public void addNewReminder(Reminder reminder){
        reminderRepository.save(reminder);
    }

    public List<Reminder> getUsers() {
        return reminderRepository.findAll();
    }

    public List<Reminder> getRemindersWithId(Long userId) {
        return reminderRepository.findRemindersWithId(userId);
    }
}
