package com.example.mapper;

import com.example.model.domain.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int saveNotice(Notice notice);

    List<Notice> getAllNotice();

    int updateNoticeById(Notice notice);

    int deleteNoticeById(Notice notice);
}
