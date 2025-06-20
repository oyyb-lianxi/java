package com.example.controller;

import com.example.model.domain.Appointment;
import com.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

   @PostMapping("/createAppointment")
   public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
       String appointmentDate = appointment.getAppointmentDate();
       String appointmentStartTime = appointment.getAppointmentStartTime();
       String appointmentEndTime = appointment.getAppointmentEndTime();
       String studentId = appointment.getStudentId();
       String teacherId = appointment.getTeacherId();
       if (appointmentService.isTimeSlotAvailable(teacherId,studentId,
               appointmentDate,appointmentStartTime,appointmentEndTime)) {
           appointmentService.createAppointment(appointment);
           return ResponseEntity.ok("Appointment created successfully");
       } else {
           return ResponseEntity.badRequest().body("Selected time slot is not available");
       }
   }

    @GetMapping("/getAppointmentsByUserId/{userId}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable String userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }
}


