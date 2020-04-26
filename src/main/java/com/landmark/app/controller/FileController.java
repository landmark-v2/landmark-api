package com.landmark.app.controller;

import com.landmark.app.model.dto.TourReviewDTO;
import com.landmark.app.utils.LoggerUtils;
import com.landmark.app.service.FileService;
import com.landmark.app.utils.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.landmark.app.utils.Constants.FILE_API;

@RestController
@RequestMapping(value = FILE_API)
public class FileController extends LoggerUtils {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * file 불러오기
     * TODO 이미지 파일 url 전달 방식 생각 해 보기
     */
    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam int id) {
        try {
            TourReviewDTO.FileDTO fileDTO = fileService.load(id);
            String filePath = fileDTO.getPath();

            if (filePath != null || !filePath.equals("")) {
                String[] filePathArr = filePath.split("/");
                String fileName = filePathArr[filePathArr.length - 1];
                String fileType = UploadFileUtils.getFileType(fileName);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(filePath.getBytes("UTF-8"), "ISO-8859-1") + "\"");
                MediaType mediaType = fileType.equalsIgnoreCase("JPG") || fileType.equalsIgnoreCase("JPEG") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
                headers.setContentType(mediaType);
                Resource resource = fileService.loadAsResource(filePath);
                return ResponseEntity.ok().headers(headers).body(resource);
            }
        } catch (Exception e) {
            logger.error("getFile : " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("The File is not exist.", HttpStatus.OK);
    }

    /**
     * file 저장
     */
    @PostMapping
    public ResponseEntity<?> saveFile(@RequestParam int id, @RequestParam("file") MultipartFile file) {
        try {
            logger.info("saveFile : " + file.getOriginalFilename());
            return new ResponseEntity<>(fileService.store(id, file), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("saveFile : " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * file 삭제
     */
    @DeleteMapping
    public ResponseEntity<?> deleteFile(@RequestParam int id) {
        return new ResponseEntity<>(fileService.delete(id), HttpStatus.OK);
    }

}
