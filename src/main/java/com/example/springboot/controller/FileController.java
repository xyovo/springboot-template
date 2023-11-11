package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "fies";


    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        if (file == null) {
            throw new ServiceException("400", "文件为空");
        }
        // 获取文件原始名称
        String originFilename = file.getOriginalFilename();
        // 解析文件名与后缀
        String mainName = FileUtil.mainName(originFilename);
        String extName = FileUtil.extName(originFilename);
        // 创建父级目录文件对象
        File parentFile = new File(ROOT_PATH);
        // 校验父级目录是否存在，若不存在则创建
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);
        }
        // 定义文件保存目录
        String saveFileName = mainName + "-" + IdUtil.simpleUUID() + "." + extName;
        File saveFile = new File(parentFile.getPath() + File.separator + saveFileName);
        // 存储文件
        file.transferTo(saveFile);
        // 获取文件URL
        String url = "http://" + ip + ":" + port + "/file/" + saveFileName;
        return Result.success(url);

    }

    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String parentPath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(parentPath)) {
            throw new ServiceException("400", "文件不存在");
        }
        byte[] bytes = FileUtil.readBytes(parentPath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
