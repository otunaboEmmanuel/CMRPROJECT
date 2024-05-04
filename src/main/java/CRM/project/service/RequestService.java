package CRM.project.service;

import CRM.project.dto.Requestdto;
import CRM.project.entity.RequestEntity;
import CRM.project.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    private final String FOLDER_PATH="C:/Users/otuna/Documents/MyFiles/";
    public String uploadImageToFileSystem(MultipartFile file, RequestEntity requestEntity) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();
        requestEntity.setName(file.getOriginalFilename());
        requestEntity.setType(file.getContentType());
        requestEntity.setFilePath(filePath);


        RequestEntity fileData=requestRepository.save(requestEntity);
        file.transferTo(new File(filePath));


        if (fileData!= null) {
            return " uploaded successfully : " + filePath;
        }
        return null;
    }
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<RequestEntity> fileData = requestRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
