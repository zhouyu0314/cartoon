package com.cartoon.util;

import com.cartoon.exceptions.UploadFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ToolUpLoad {

    private static String headImgFormat;


    @Value("${headImgFormat}")
    public void setHeadImgFormat(String headImgFormat){
        ToolUpLoad.headImgFormat = headImgFormat;
    }






    /**
     * @描述:上传文件到临时目录
     * 
     * @param file 上传的文件
     * @param tempPath 上传文件存放路径
     * @return 
     */
    public static String fileUpload(MultipartFile file, String tempPath) throws UploadFileException{
         List<String> headImgFormatList = new ArrayList<>();
        String[] formats = headImgFormat.split(",");
        String filename = null;
        for (String format : formats) {
            headImgFormatList.add(format);
        }
        if (file == null || file.isEmpty()) {
           throw new UploadFileException("上传文件异常，请重新上传！");
        }else {
            //加载路径
            File fileDir = new File(tempPath);
            //如果路径不存在
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            //获取初始文件名
            filename = file.getOriginalFilename();
            String[] filenames = filename.split("\\.");
            //判断格式
            if (!headImgFormatList.contains(filenames[1])) {
                throw new UploadFileException("不支持的文件格式！");
            }

            filename = System.currentTimeMillis()+"."+filenames[1];
            File dest = new File(fileDir,filename);
            //保存文件
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                throw new UploadFileException("不支持的文件格式！");
            }
        }

        return filename;
    }
}