package com.landmark.app.service;

import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.utils.helper.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService extends LoggerUtils {

    private final Path rootLocation;
    private TourReviewService tourReviewService;

    @Autowired
    public FileService(String uploadPath, TourReviewService tourReviewService) {
        this.rootLocation = Paths.get(uploadPath);
        this.tourReviewService = tourReviewService;
    }

    /** file 정보 가져오기 **/
    public TourReviewDTO.FileDTO load(int reviewId) {
        return tourReviewService.findFileInfoById(reviewId);
    }

    /** file **/
    public Resource loadAsResource(String filePath) {
        try {
            if (filePath.toCharArray()[0] == '/') {
                filePath = filePath.substring(1);
            }

            Path file = loadPath(filePath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read file: " + filePath);
            }
        } catch (Exception e) {
            logger.error("Could not read file: " + filePath);
            return null;
        }
    }

    /** file 경로 **/
    public Path loadPath(String fileName) {
        return rootLocation.resolve(fileName);
    }

    /** file 저장 **/
    public String store(int reviewId, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                logger.error("Failed to save empty file " + file.getOriginalFilename());
                return "Failed to save empty file " + file.getOriginalFilename();
            }

            if (!(file.getContentType().equals(MediaType.IMAGE_JPEG) || file.getContentType().equals(MediaType.IMAGE_PNG))) {
                return "Invalid file content type (valid type : JPG, JPEG, PNG)";
            }

            String savedFilePath = FileUploadHelper.fileSave(rootLocation.toString(), file, reviewId);

            if (savedFilePath.equals("")) {
                return "Failed to save file";
            }

            String[] savedFilePathArr = savedFilePath.split("/");
            String savedFileName = savedFilePathArr[savedFilePathArr.length - 1];

            TourReviewDTO.FileDTO fileDTO = new TourReviewDTO.FileDTO();
            fileDTO.setReviewId(reviewId);
            fileDTO.setPath(savedFilePath);
            fileDTO = tourReviewService.saveFile(fileDTO);

            if (fileDTO != null) {
                logger.info("Saved tour review [id = " + fileDTO.getReviewId() + "] file path : " + fileDTO.getPath());
                return savedFileName;
            }
        } catch (Exception e) {
            logger.error("Failed to store file " + file.getOriginalFilename() + " - " + e.getMessage());
        }

        return "";
    }

    /** file 삭제 **/
    public boolean delete(int reviewId) {
        try {
            TourReviewDTO.FileDTO fileDTO = tourReviewService.findFileInfoById(reviewId);
            new File(fileDTO.getPath()).delete();
            fileDTO.setPath(null);
            tourReviewService.saveFile(fileDTO);
            return true;
        } catch (Exception e) {
            logger.error("delete : " + e.getMessage());
            return false;
        }
    }

}
