
package com.example.service.impl;

import com.example.mapper.*;
import com.example.model.domain.*;
import com.example.model.dto.AppointmentDto;
import com.example.model.vo.AppointmentVo;
import com.example.service.AppointmentService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private NotificationMapper notificationMapper;

//预约
    public void makeAppointment(String studentId, String teacherId,
                                String subject, LocalDateTime appointmentDate,
                                LocalDateTime appointmentStartTime,LocalDateTime appointmentEndTime) {
        // 验证学生和老师是否存在
        Student student = studentMapper.getStudentById(studentId);
        Teacher teacher = teacherMapper.getTeacherById(teacherId);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }

        // 检查老师是否有该科目
//        boolean hasSubject = teacherMapper.hasSubject(teacherId, subject);
//        if (!hasSubject) {
//            throw new RuntimeException("Teacher does not teach this subject");
//        }

        // 创建预约
        Appointment appointment = new Appointment();
        appointment.setStudentId(studentId);
        appointment.setTeacherId(teacherId);
        appointment.setSubject(subject);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentStartTime(appointmentStartTime);
        appointment.setAppointmentEndTime(appointmentEndTime);
        appointment.setStatus("PENDING");

        appointmentMapper.saveAppointment(appointment);

        // 生成通知
        Notification notification = new Notification();
        notification.setTeacherId(teacherId);
        notification.setStudentId(studentId);
        notification.setMessage("New appointment request from student: " + student.getName());
        notificationMapper.saveNotification(notification);
    }

    //确认预约
      public Boolean confirmAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.getAppointmentById(appointmentDto.getId());
        if (appointment == null) {
            return false;
        }

        if (!"PENDING".equals(appointment.getStatus())) {
            return false;
        }

        // 更新预约状态
        appointment.setStatus("CONFIRMED");
          appointment.setAdminPhone(appointmentDto.getAdminPhone());
        appointmentMapper.updateAppointment(appointment);

        // 生成通知
        Student student = studentMapper.getStudentById(appointment.getStudentId());
        Teacher teacher = teacherMapper.getTeacherById(appointment.getTeacherId());

        Notification notification = new Notification();
        notification.setStudentId(appointment.getStudentId());
        notification.setTeacherId(appointment.getTeacherId());
        notification.setMessage("student"+ student.getName()+ "appointment with teacher " + teacher.getName() + " has been confirmed");
        notificationMapper.saveNotification(notification);
        return true;
    }

    //确认完成
      public Boolean successFinishAppointment(Long appointmentId) {
        Appointment appointment = appointmentMapper.getAppointmentById(appointmentId);
        if (appointment == null) {
            return false;
        }

        if (!"CONFIRMED".equals(appointment.getStatus())) {
            return false;
        }

        // 更新预约状态
        appointment.setStatus("SUCCESS");
        appointmentMapper.updateAppointment(appointment);

        // 生成通知
        Student student = studentMapper.getStudentById(appointment.getStudentId());
        Teacher teacher = teacherMapper.getTeacherById(appointment.getTeacherId());

        Notification notification = new Notification();
        notification.setStudentId(appointment.getStudentId());
        notification.setTeacherId(appointment.getTeacherId());
        notification.setMessage("student"+ student.getName()+ "appointment with teacher " + teacher.getName() + " has been SUCCESS");
        notificationMapper.saveNotification(notification);
        return true;
    }
  //取消预约
  public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentMapper.getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found");
        }

        if ("CANCELLED".equals(appointment.getStatus())) {
            throw new RuntimeException("Appointment is already cancelled");
        }

        // 更新预约状态
        appointment.setStatus("CANCELLED");
        appointmentMapper.updateAppointment(appointment);

        // 生成通知
        Student student = studentMapper.getStudentById(appointment.getStudentId());
        Teacher teacher = teacherMapper.getTeacherById(appointment.getTeacherId());

        Notification notification = new Notification();
        notification.setTeacherId(appointment.getTeacherId());
        notification.setStudentId(appointment.getStudentId());
        notification.setMessage("Appointment with student " + student.getName() + " has been cancelled");
        notificationMapper.saveNotification(notification);
    }
  //查询学生的所有预约
  //查询预约
      public List<AppointmentVo> getAppointmentsByConditions(AppointmentDto appointment) {
        return appointmentMapper.getAppointmentsByConditions(appointment);
    }
    public List<AppointmentVo> getAppointmentsByAdmin(AppointmentDto appointment) {
        return appointmentMapper.getAppointmentsByAdmin(appointment);
    }
    @Override
    public Appointment getAppointmentsById(Long appointmentId) {
        return appointmentMapper.getAppointmentById(appointmentId);
    }

    @Transactional
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentMapper.getAllAppointments();
    }

    @Transactional
    @Override
    public Integer createAppointment(Appointment appointment) {

            return appointmentMapper.saveAppointment(appointment);
    }

    public boolean isTimeSlotAvailable(Appointment appointment) {
        //该时间段已经被其他学生的预约则返回false
        boolean otherAppointment = !appointmentMapper.existsByAppointmentDate(appointment);
        return otherAppointment;
    }

}
