package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import com.example.model.domain.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
public class fileController {

    //通过@Value注解 直接给属性注入值D:\img
    @Value("${img.path}")
    private String uploadPath;
    @Autowired
    private UserService userService;

    /**
     * 本地头像下载  回显111
     */
    @GetMapping("/download")
    public void download(Long id, HttpServletResponse response) throws IOException {
        User emp = userService.getById(id);

        if (emp.getPhotoFileName() != null) {
            // 根据头像存储地址创建文件输入流，读取头像
            String filePath = uploadPath + File.separator + emp.getPhotoFileName();
            System.out.println("文件位置：" + filePath);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            // 创建Servlet输出流，用于将文件内容写入响应
            ServletOutputStream outputStream = response.getOutputStream();
            // 设置响应的内容类型为image/jpeg，即图片类型为JPEG格式
            response.setContentType("image/jpeg");
            int len;
            byte[] bytes = new byte[1024];
            // 循环读取文件内容，并将内容写入响应输出流
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            // 关闭文件输入流和输出流
            fileInputStream.close();
            outputStream.close();
        }
    }

    /**
     * 头像上传
     * 方法一：图片上传到本地存储
     */
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("id") Long id) throws IOException {

        Map<String, Object> result = new HashMap<>();
        //获取 原始文件名
        String originalFileName = file.getOriginalFilename();
        System.out.println("原始文件名：" + originalFileName);

        //断言 判断文件名是否有值 没有则抛出异常中断程序执行
        assert originalFileName != null;

        //使用UUID通用唯一识别码 + 后缀名的形式
        //设置唯一文件路径 防止文件名重复 出现覆盖的情况
        String fileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
        //打印查看
        System.out.println("唯一文件名：" + fileName);

        // 指定文件保存的路径
        String filePath = uploadPath + File.separator + fileName;

        //文件名保存到对应数据的头像图片字段
        User emp = new User();
        //将文件名保存到数据库表的头像字段
        emp.setPhotoFileName(fileName);
        emp.setId(id);
        boolean uploadPhoto = userService.updateById(emp);

        //图片路径保存到数据库表成功之后执行  将图片放入对应路径
        if (uploadPhoto) {
            //根据上传路径创建文件夹File对象
            File saveAddress = new File(uploadPath);
            if (!saveAddress.exists()) {
                saveAddress.mkdirs();// 如果文件夹不存在 创建保存文件对应的文件夹
            }
            // 将上传的文件保存到指定路径
            file.transferTo(new File(filePath));
            //封装数据返回

            result.put("filePath", filePath);
            return ResponseEntity.ok(result);
        }
        result.put("filePath", "上传失败,请确认参数信息是否正确");
        return ResponseEntity.ok(result);
    }


}
