package org.example.plain.domain.calendar.repository;

import org.example.plain.domain.calendar.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long> {
}
