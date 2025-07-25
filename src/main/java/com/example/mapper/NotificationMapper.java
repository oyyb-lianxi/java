package com.example.mapper;

import com.example.model.domain.Appointment;
import com.example.model.domain.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotificationMapper {

    int saveNotification(Notification notification);

    List<Notification>getAllNotifications();
    Notification getNotificationById(String id);

    Notification getNotificationsByConditions(Notification notification);

    int updateNotification(Notification notification);

    int deleteNotificationById(String id);
}
