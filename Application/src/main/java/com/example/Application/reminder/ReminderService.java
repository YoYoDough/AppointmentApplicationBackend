package com.example.Application.reminder;

import com.example.Application.User.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository){
        this.reminderRepository = reminderRepository;
    }

    public void addNewReminder(Reminder reminder){
        reminderRepository.save(reminder);
    }
}
