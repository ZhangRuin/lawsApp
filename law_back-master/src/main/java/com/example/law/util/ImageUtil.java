package com.example.law.util;

public class ImageUtil {
    public static boolean verify(String imgVerify) {
        // 可以随意添加图片类型 无需改代码
        String[] imgformat = { ".jpg", ".png", ".jpeg", ".pjg", ".pjeg", ".jfif" };
        for (String img : imgformat) {
            if (img.equals(imgVerify)) {
                return true;
            }
        }
        return false;
    }

    // 根据文件路径获取图片类型
    public static String getImageContentType(String filePath) {
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filePath.endsWith(".png")) {
            return "image/png";
        } else if (filePath.endsWith(".pjg")) {
            return "image/pjg";
        } else if (filePath.endsWith(".pjeg")) {
            return "image/pjeg";
        } else if (filePath.endsWith(".jfif")) {
            return "image/jfif";
        } else {
            // 默认返回image/jpg，可以根据需要进行调整
            return "image/jpg";
        }
    }
}
