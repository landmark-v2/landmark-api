package com.landmark.app.utils.helper;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUploadHelper {

    public static String fileSave(String uploadPath, MultipartFile file, int id) {
        try {
            File uploadPathDir = new File(uploadPath);

            if ( !uploadPathDir.exists() ){
                uploadPathDir.mkdirs();
            }

            String saveFileName = "tour_review_id_" + id + "." + file.getContentType();

            File target = new File(uploadPath, saveFileName);
            FileCopyUtils.copy(file.getBytes(), target);

            return makeFilePath(uploadPath, saveFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 파일 이름으로부터 확장자를 반환
     */
    public static String getFileType(String fileName) {
        int dotPosition = fileName.lastIndexOf('.');

        if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
            return fileName.substring(dotPosition + 1);
        } else {
            return "";
        }
    }

    private static String makeFilePath(String uploadPath, String fileName) throws IOException {
        String filePath = uploadPath + File.separator + fileName;
        return filePath.replace(File.separatorChar, '/');
    }

}
