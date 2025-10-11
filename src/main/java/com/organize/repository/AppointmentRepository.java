package com.organize.repository;

import com.organize.model.Appointment;
import com.organize.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a WHERE a.client.id = :clientId AND a.startTime BETWEEN :start AND :end")
    List<Appointment> findAppointmentsByClientAndDateRange(@Param("clientId") UUID clientId,
                                                           @Param("start") LocalDateTime start,
                                                           @Param("end") LocalDateTime end);

    List<Appointment> findByClient(User client);

    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
        FROM Appointment a
        WHERE a.employee.id = :employeeId
        AND ((a.startTime < :endTime) AND (a.endTime > :startTime))
    """)
    boolean isEmployeeUnavailable(@Param("employeeId") UUID employeeId,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM Appointment a WHERE a.establishment.id = :establishmentId AND a.startTime BETWEEN :start AND :end")
    List<Appointment> findAppointmentsByEstablishmentAndDateRange(@Param("establishmentId") UUID establishmentId,
                                                                  @Param("start") LocalDateTime start,
                                                                  @Param("end") LocalDateTime end);
}
