package CRM.project.service;

import CRM.project.dto.Requestdto;
import CRM.project.entity.RequestEntity;
import CRM.project.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@Slf4j
public class RequestService {

    static String DIRECTORY_PATH = "/u01/uploads/";
    @Autowired
    private RequestRepository requestRepository;
    public Map<String, String> uploadImageToFileSystem(MultipartFile file, RequestEntity requestEntity) throws IOException {

        String filePath = saveFileToStorage(file);
        requestEntity.setName(file.getOriginalFilename());
        requestEntity.setType(file.getContentType());
        requestEntity.setFilePath(DIRECTORY_PATH+filePath);
        RequestEntity storeData=requestRepository.save(requestEntity);
        Map<String, String> responseData = new HashMap<>();
        if(storeData != null) {
            responseData.put("code", "00");
            responseData.put("message", "Request saved successfully");
        }

        else {
            responseData.put("code", "90");
            responseData.put("message", "Failed to save request");
        }
        return responseData;
    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<RequestEntity> fileData = requestRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    private String saveFileToStorage(MultipartFile file) {

        String extensionType = file.getContentType();
        String fileName = UUID.randomUUID().toString().replace("-", "") + extensionType;

        try {


            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File outputFile = new File(DIRECTORY_PATH + fileName);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(file.getBytes());
            outputStream.close();

            log.info("File saved successfully to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            log.info("Error saving file: " + e.getMessage());
        }
        return fileName;
    }

    public List<RequestEntity> findAllRequestsByUnit(String unit) {
        return requestRepository.findByUnit(unit);
    }
}
