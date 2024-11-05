package com.example.Application.reminder;

import jakarta.persistence.*;

import java.time.Instant;
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
    private Instant reminderTimeUtc; // Store reminder time in UTC
    private String userTimeZone; // Store user's time zone

    public Reminder(){

    }

    public Reminder (Long id, Long userId, String reminderName, LocalDate reminderDate, String reminderTime, Instant reminderTimeUtc, String userTimeZone){
        this.id = id;
        this.userId = userId;
        this.reminderName = reminderName;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.reminderTimeUtc = reminderTimeUtc;
        this.userTimeZone = userTimeZone;
    }

    public Reminder(Long userId, String reminderName, LocalDate reminderDate, String reminderTime, Instant reminderTimeUtc, String userTimeZone){
        this.userId = userId;
        this.reminderName = reminderName;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.reminderTimeUtc = reminderTimeUtc;
        this.userTimeZone = userTimeZone;
    }

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

    public String getUserTimeZone() {
        return userTimeZone;
    }

    public void setUserTimeZone(String userTimeZone) {
        this.userTimeZone = userTimeZone;
    }

    public Instant getReminderTimeUtc() {
        return reminderTimeUtc;
    }

    public void setReminderTimeUtc(Instant reminderTimeUtc) {
        this.reminderTimeUtc = reminderTimeUtc;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", userId=" + userId +
                ", reminderName='" + reminderName + '\'' +
                ", reminderDate=" + reminderDate +
                ", reminderTime='" + reminderTime + '\'' +
                ", reminderTimeUtc=" + reminderTimeUtc +
                ", userTimeZone='" + userTimeZone + '\'' +
                '}';
    }
}
