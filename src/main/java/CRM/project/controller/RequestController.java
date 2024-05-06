package CRM.project.controller;

import CRM.project.entity.RequestEntity;
import CRM.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/requests")
@CrossOrigin
public class RequestController {
    @Autowired
    private RequestService requestService;
    @PostMapping("/createRequest")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("attachments") MultipartFile file,
                                                     RequestEntity requestEntity,
                                                     @RequestParam("title") String subject,
                                                     @RequestParam("unit") String unit,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("priority") String priority,
                                                     @RequestParam("category") String category,
                                                     @RequestParam("subcategory") String subCategory,
                                                     @RequestParam("description") String description,
                                                     @RequestParam("technician") String technician) throws IOException {
        requestEntity.setSubject(subject);
        requestEntity.setUnit(unit);
        requestEntity.setPriority(priority);
        requestEntity.setCategory(category);
        requestEntity.setSubCategory(subCategory);
        requestEntity.setDescription(description);
        requestEntity.setTechnician(technician);
        requestEntity.setEmail(email);
        String uploadImage =requestService.uploadImageToFileSystem(file,requestEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=requestService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

}
