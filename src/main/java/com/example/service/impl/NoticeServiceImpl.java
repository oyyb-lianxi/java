package com.example.service.impl;

import com.example.mapper.NoticeMapper;
import com.example.model.domain.Notice;
import com.example.model.entity.Result;
import com.example.service.AppointmentService;
import com.example.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public Result publishNotice(Notice notice) {
        Result result = new Result();
        int integer = noticeMapper.saveNotice(notice);
        if(integer == 1){
            result.setCode(200);
            result.setMsg("发布成功");
        }else {
            result.setCode(403);
            result.setMsg("发布失败");
        }
        return result;
    }

    @Override
    public Result findNoticeList() {
        Result result = new Result();
        List<Notice> allNotice = noticeMapper.getAllNotice();
        result.setCode(200);
        result.setData(allNotice);
        return result;
    }

    @Override
    public Result updateNotice(Notice notice) {
        Result result = new Result();
        int integer = noticeMapper.updateNoticeById(notice);
        if(integer == 1){
            result.setCode(200);
            result.setMsg("修改成功");
        }else {
            result.setCode(403);
            result.setMsg("修改失败");
        }
        return result;
    }

    @Override
    public Result deleteNotice(Notice notice) {
        Result result = new Result();
        int integer = noticeMapper.deleteNoticeById(notice);
        if(integer == 1){
            result.setCode(200);
            result.setMsg("删除成功");
        }else {
            result.setCode(403);
            result.setMsg("删除失败");
        }
        return result;
    }
}
