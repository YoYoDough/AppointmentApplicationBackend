package com.example.Application.reminder;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String reminderName;
    private LocalDate reminderDate;
    private String reminderTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReminderName() {
        return reminderName;
    }
    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", userId=" + userId +
                ", reminderName='" + reminderName + '\'' +
                ", reminderDate=" + reminderDate +
                ", reminderTime='" + reminderTime + '\'' +
                '}';
    }
}
