package com.example.service.impl;

import com.example.model.CommonUtils.SystemUtil;
import com.example.model.entity.NasProperties;
import com.example.model.entity.Result;
import com.example.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private NasProperties nasProperties;
    @Override
    public Result uploadFile(MultipartFile file) {
        Result result = new Result();
        if (file.isEmpty()) {
            result.setCode(500);
            result.setMsg("上传文件不能为空");
            return result;
        }
        //上传文件
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.indexOf("."),filename.length());
        String filepath = "";
        if(".pdf".equals(suffix)) {
            filepath = nasProperties.getBasepath()+ File.separator+"pdf"+File.separator+suffix;
        } else if(showMaps.containsKey(suffix)) {
            filepath = nasProperties.getBasepath()+ File.separator+"img"+File.separator+suffix;
        }else{
            filepath = nasProperties.getBasepath()+ File.separator+"file"+File.separator+suffix;
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
            byte[] buf = new byte[1024];
            int len=0;
            OutputStream os = new FileOutputStream(new File(filepath));
            while((len=is.read(buf))>-1) {
                os.write(buf, 0, len);
            }
            os.flush();
            os.close();
            is.close();
            result.setCode(200);
            result.setMsg("正常");
            String para = SystemUtil.byte2hex(filepath.getBytes());
            String url = "";
            if(showMaps.containsKey(suffix)) {
                url =nasProperties.getBaseUrl()+"/showFile/"+para;
            }else{
                url =nasProperties.getBaseUrl()+"/download/"+para;
            }
            result.setData(url);
        } catch (IOException e) {
            result.setCode(500);
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }finally {
            return  result;
        }
    }

    public static Map<String, String> showMaps = new HashMap<String, String>(){{
        put(".png", "image/png");
        put(".gif", "image/gif");
        put(".jpeg", "image/jpeg");
        put(".bmp", "image/bmp");
        put(".jpg", "image/jpeg");
        put(".pdf","application/pdf");
    }};
}
