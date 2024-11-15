package com.example.Application.reminder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    @Query("SELECT r FROM Reminder r WHERE r.userId = :userId")
    List<Reminder> findRemindersWithId(@Param("userId") Long userId);
    @Query("SELECT r FROM Reminder r WHERE r.reminderDate IN :dates")
    List<Reminder> findByReminderDate(@Param("dates") List<LocalDate> dates);


}
