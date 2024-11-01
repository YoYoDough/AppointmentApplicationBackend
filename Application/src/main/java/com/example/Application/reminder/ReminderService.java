package com.example.Application.reminder;

import com.example.Application.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository){
        this.reminderRepository = reminderRepository;
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

    public void updateReminder(Reminder updateReminder) {
        reminderRepository.save(updateReminder);
    }
}
