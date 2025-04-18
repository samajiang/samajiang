package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.entiity.GongGao;
import com.example.service.GonggaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequestMapping("/gonggao")
@RestController
public class GonggaoController {
    private static final Logger log = LoggerFactory.getLogger(GonggaoController.class);

    @Resource
    GonggaoService gonggaoService;

    @GetMapping("/fetchgonggao")
    public List<GongGao> fetchgonggao(){
        List<GongGao>  list = gonggaoService.searchgongg();
        return list;
    }

    @PostMapping("/addgonggao")
    public Result Addgonggao(@RequestBody GongGao gongGao){
        System.out.println("gonggao"+gongGao);
        List<GongGao>list = fetchgonggao();
        System.out.println(list.size());
        if(gongGao.getGonggao_id()!=null){
            gonggaoService.updatagonggao(gongGao);
        }else if(list.size()>3){
            return Result.error("公告最多添加四个");
        }else {
            gonggaoService.addgonggao(gongGao);
        }
        return Result.success();
    }
    //    公告文件上传储存路径
    private static final String filepath = System.getProperty("user.dir")+"/gonggaopicfile/";

    @PostMapping("/uploadgonggao")
    public Result uploadgonggao(MultipartFile file,String gonggaoname){

        synchronized (GonggaoController.class){

//            加时间戳
            String gonggaoflag = System.currentTimeMillis()+"";
//            获取上传文件的原始文件名
            String originname = file.getOriginalFilename();
            try {
//                如果没有这个路径下的文件夹就自动创建一个
                if(!FileUtil.isDirectory(filepath)){
                    FileUtil.mkdir(filepath);
                }else{
//                    保存文件，保存形式文件名+时间戳
                    FileUtil.writeBytes(file.getBytes(),filepath+gonggaoname+"-"+gonggaoflag+originname);
                    System.out.println(originname+"文件上传成功");
                    Thread.sleep(1l);

                }
            }catch (Exception e){
                System.err.println(originname+"文件上传失败");
                return Result.error(e+"");
            }
            return Result.success(gonggaoflag);
        }
    }


    /**
     * 文件下载
     */
    @GetMapping("/{gonggaoflag}")
    public void filepath(@PathVariable String gonggaoflag, HttpServletResponse httpServletResponse){
        OutputStream os;
        List<String> picname = FileUtil.listFileNames(filepath);
        String pic = picname.stream().filter(name -> name.contains(gonggaoflag)).findAny().orElse("");
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
//    根据公告id删除公告
    @DeleteMapping("/deletgonggaobyid/{gonggaoid}")
    public Result deletgonggaobyid(@PathVariable Integer gonggaoid){
        gonggaoService.deletegonggaobyid(gonggaoid);
        return Result.success();
    }
}
