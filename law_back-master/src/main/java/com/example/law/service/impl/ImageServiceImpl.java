package com.example.law.service.impl;

import com.example.law.pojo.vo.common.BizException;
import com.example.law.pojo.vo.common.ExceptionEnum;
import com.example.law.pojo.vo.common.ResultResponse;
import com.example.law.service.ImageService;
import com.example.law.util.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${file-service.profile}")
    private String BASE_DIR;

    @Override
    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BizException(ExceptionEnum.EMPTY_IMAGE);
        }
        // 获取文件的名称
        String fileName = file.getOriginalFilename();
        try {
            assert fileName != null;
            // 获得文件后缀名
            String hzName = fileName.substring(fileName.lastIndexOf("."));
            if (!ImageUtil.verify(hzName)) {
                throw new BizException(ExceptionEnum.IMAGE_FORMAT_ERROR);
            }
            String uuidFileName = UUID.randomUUID() + hzName;
            fileName = BASE_DIR + File.separator + uuidFileName;
            // 新建一个文件路径
            File uploadFile = new File(fileName);
            // 当父级目录不存在时，自动创建
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            // 存储文件到电脑磁盘
            file.transferTo(uploadFile);
            return uuidFileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException(ExceptionEnum.SERVER_ERROR);
        }
    }


    @Override
    public void viewImage(String fileName, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String filePath = BASE_DIR + File.separator + fileName; // 构建完整的文件路径
        File file = new File(filePath);
        // 判断文件是否存在如果不存在就返回默认图片或者进行异常处理
        if (!(file.exists() && file.canRead())) {
            // 返回默认图片或者其他处理逻辑
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.getWriter().write("文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.read(data); // 读取文件内容

            // 设置响应内容类型为图片类型（例如，image/jpeg、image/png等）
            response.setContentType(ImageUtil.getImageContentType(filePath));

            OutputStream stream = response.getOutputStream();
            stream.write(data); // 将文件内容写入响应流
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close(); // 关闭输入流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getUrlDownload(String fileName, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String filePath = BASE_DIR + File.separator + fileName; // 构建完整的文件路径
        try {
            // 进行文件路径的验证和处理
            if (fileName != null && !fileName.isEmpty()) {
                // 将文件名转换为文件路径
                File file = new File(filePath);

                // 判断文件是否存在并可读
                if (file.exists() && file.canRead()) {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    int length = inputStream.read(data);
                    inputStream.close();

                    // 设置响应头信息
                    response.setContentType("application/force-download");
                    response.addHeader("Content-Disposition",
                            String.format("attachment; filename=\"%s\"", file.getName()));

                    OutputStream stream = response.getOutputStream();
                    stream.write(data);
                    stream.flush();
                } else {
                    // 文件不存在或不可读，返回错误响应
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("文件不存在");
                }
            } else {
                // 文件名无效，返回错误响应
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("无效的文件名");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 处理其他可能的异常
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void deleteImage(String fileName) {
        String filePath = BASE_DIR + File.separator + fileName; // 构建完整的文件路径
        try {
            // 构建要删除的文件对象
            File fileToDelete = new File(filePath);

            // 判断文件是否存在并可读
            if (fileToDelete.exists() && fileToDelete.canRead()) {
                // 删除文件
                if (fileToDelete.delete()) {
                    return;
                } else {
                    throw new BizException(ExceptionEnum.DELETE_IMAGE_ERROR);
                }
            } else {
                // 文件不存在或不可读，抛出异常
                throw new BizException(ExceptionEnum.EMPTY_IMAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ExceptionEnum.SERVER_ERROR);
        }
    }
}
