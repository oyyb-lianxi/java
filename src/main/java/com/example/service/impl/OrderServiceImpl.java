package com.example.service.impl;
@Service
public class OrderServiceImpl implements OrderService {
  @Override
      public Order createOrder(Long userId, Long teacherId, Date startTime, Date endTime) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Teacher teacher = teacherRepository.findById(teacherId).orElse(() -> new RuntimeException("Teacher not found"));

        if(user.isMember()) {
            throw new RuntimeException("User is not a member");
        }

        if(teacher.getAvailability().contains(startTime.toString())) {
            throw new RuntimeException("Teacher not at the specified time");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTeacher(teacher);
        order.setOrderTime(new Date());
        order.setStartTime(startTime);
}

