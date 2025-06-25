package com.example.controller;

import com.example.model.CommonUtils.SystemUtil;
import com.example.model.entity.NasProperties;
import com.example.model.entity.Result;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import com.example.model.domain.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    @Autowired
    private FileService fileService;
    @Autowired
    private NasProperties nasProperties;
    private Logger logger = LoggerFactory.getLogger(fileController.class);
    /**
     * 本地头像下载  回显111
     */
    @GetMapping("/download")
    public void download(String id, HttpServletResponse response) throws IOException {
        User emp = userService.getByOpenId(id);

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
                                 @RequestParam("id") String id) throws IOException {

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
        log.info("唯一文件名：" + fileName);
        // 指定文件保存的路径
//        String filePath = uploadPath + File.separator + fileName;
        String filePath = uploadPath + "/img" + fileName;

        //文件名保存到对应数据的头像图片字段
        User emp = new User();
        //将文件名保存到数据库表的头像字段
        emp.setPhotoFileName(fileName);
        emp.setOpenid(id);
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

    /**
     * 上传文件
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public Result uploadFile(@RequestParam("file") MultipartFile file)  {
        return fileService.uploadFile(file);
    }

    /**
     * 展示图片、pdf
     * @param para
     * @param response
     */
    @GetMapping(value = "/showFile/{para}")
    @ResponseBody
    public void showFile(@PathVariable String para, HttpServletResponse response){
        String parameter;
        try {
            parameter = new String(SystemUtil.hex2byte(para),"UTF-8");
            System.out.println("parameter"+parameter);
            if(new File(parameter).exists()) {
                String sb =parameter.substring(parameter.indexOf("."),parameter.length());
                if (showMaps.get(sb) != null) {
                    response.setContentType(showMaps.get(sb));//设置预览文件类型
                    response.setHeader("Access-Control-Allow-Origin", "*");//设置跨域可访问
                    FileInputStream fis = new FileInputStream(parameter);
                    byte data[]=new byte[fis.available()];
                    fis.read(data);  //读数据
                    fis.close();
                    OutputStream os = response.getOutputStream();
                    os.write(data);
                    os.flush();
                    os.close();
                }else {
                    response.setContentType("text/html;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.print("此类型不支持!");
                }
            }else{
                response.setContentType("text/html;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.print("抱歉!该文件不存在");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 下载文件
     * @param para
     * @param response
     * @return
     */
    @RequestMapping(value = "/downloadFile/{para}",method = RequestMethod.GET)
    public void downloadFile(@PathVariable String para, HttpServletResponse response){
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        //获取文件
        try {
            byte[] bytes = SystemUtil.hex2byte(para);
            String parameter = new String(bytes, "UTF-8");
            logger.info("resource-下载文件:"+parameter);
            String name = parameter.substring(parameter.indexOf("\\"));
            if(new File(parameter).exists()) {
                response.setHeader("contend-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
                fis = new FileInputStream(parameter);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int len = 0;
                byte [] b = new byte[1024];
                while ((len=bis.read(b)) !=-1) {
                    os.write(b, 0,len);
                }
                fis.close();
                bis.close();
            }else{
                response.setContentType("text/html;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.print("抱歉!该文件不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
