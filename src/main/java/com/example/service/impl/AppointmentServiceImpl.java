
package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.model.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void makeAppointment(Long studentId, Long teacherId, String subject, String appointmentDate, String appointmentTime) {
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
        boolean hasSubject = teacherMapper.hasSubject(teacherId, subject);
        if (!hasSubject) {
            throw new RuntimeException("Teacher does not teach this subject");
        }

        // 创建预约
        Appointment appointment = new Appointment();
        appointment.setStudentId(studentId);
        appointment.setTeacherId(teacherId);
        appointment.setSubject(subject);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus("PENDING");

        appointmentMapper.saveAppointment(appointment);

        // 生成通知
        Notification notification = new Notification();
        notification.setUserId(teacherId);
        notification.setMessage("New appointment request from student: " + student.getName());
        notification.setCreated(new Date().toString());
        notificationMapper.saveNotification(notification);
    }
//确认预约
      public void confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentMapper.getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found");
        }

        if (!"PENDING".equals(appointment.getStatus())) {
            throw new RuntimeException("Appointment is not in pending status");
        }

        // 更新预约状态
        appointment.setStatus("CONFIRMED");
        appointmentMapper.updateAppointment(appointment);

        // 生成通知
        Student student = studentMapper.getStudentById(appointment.getStudentId());
        Teacher teacher = teacherMapper.getTeacherById(appointment.getTeacherId());

        Notification notification = new Notification();
        notification.setUserId(appointment.getStudentId());
        notification.setMessage("Your appointment with teacher " + teacher.getName() + " has been confirmed");
        notification.setCreated(new Date().toString());
        notificationMapper.saveNotification(notification);
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
        notification.setUserId(appointment.getTeacherId());
        notification.setMessage("Appointment with student " + student.getName() + " has been cancelled");
        notification.setCreated(new Date().toString());
        notificationMapper.saveNotification(notification);
    }
  //查询学生的所有预约
    public List<Appointment> getStudentsAppointments(Long studentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("studentId", studentId);
        return appointmentMapper.getAppointmentsByConditions(params);
    }
  //查询老师的所有预约
      public List<Appointment> getTeachersAppointments(Long teacherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        return appointmentMapper.getAppointmentsByConditions(params);
    }
}
