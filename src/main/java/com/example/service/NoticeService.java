package com.example.service;

import com.example.model.domain.Notice;
import com.example.model.entity.Result;

public interface NoticeService {
    Result publishNotice(Notice notice);

    Result findNoticeList();

    Result updateNotice(Notice notice);

    Result deleteNotice(Notice notice);
}
