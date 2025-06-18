
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
}
