package com.example.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.entiity.Vaccine;
import com.example.service.VaccineService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;


/**
 * 文件上传接口
 */

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    VaccineService vaccineService;

//    文件上传储存路径
    private static final String filepath = System.getProperty("user.dir")+"/vaccinepicfile/";

    @PostMapping("/upload")
    public Result uploaad(MultipartFile file,String vaccinename){
        System.out.println(vaccinename+"");
//        给上传加个锁
        synchronized (FileController.class){

//            加时间戳
            String flag = System.currentTimeMillis()+"";
//            获取上传文件的原始文件名
            String originname = file.getOriginalFilename();
            try {
//                如果没有这个路径下的文件夹就自动创建一个
                if(!FileUtil.isDirectory(filepath)){
                    FileUtil.mkdir(filepath);
                }else{
//                    保存文件，保存形式文件名+时间戳
                    FileUtil.writeBytes(file.getBytes(),filepath+vaccinename+"-"+flag+originname);
                    System.out.println(originname+"文件上传成功");
                    Thread.sleep(1l);

                }
            }catch (Exception e){
                System.err.println(originname+"文件上传失败");
                return Result.error(e+"");
            }
            return Result.success(flag);
        }

    }


    /**
     * 文件下载
     */
    @GetMapping("/{flag}")
    public void filepath(@PathVariable String flag, HttpServletResponse httpServletResponse){
        OutputStream os;
        List<String> picname = FileUtil.listFileNames(filepath);
        String pic = picname.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(pic)){
                httpServletResponse.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(pic,"UTF-8"));
                httpServletResponse.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filepath+pic);
                os = httpServletResponse.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){

        }
    }
}
