package CRM.project.controller;

import CRM.project.dto.BulkDto;
import CRM.project.dto.Requestdto;
import CRM.project.entity.Department;
import CRM.project.entity.RequestEntity;
import CRM.project.entity.Status;
import CRM.project.repository.DepartmentRepository;
import CRM.project.repository.RequestRepository;
import CRM.project.response.Responses;
import CRM.project.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.apache.logging.log4j.util.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/requests")
@CrossOrigin
@Slf4j
public class RequestController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @PostMapping("/createRequest")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam(value = "attachments", required = false) MultipartFile file,
                                                     @RequestParam("requester") String requester,
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
        requestEntity.setUnit(unit.split("~~")[0]);
        requestEntity.setPriority(priority);
        requestEntity.setCategory(category);
        requestEntity.setSubCategory(subCategory);
        requestEntity.setDescription(description);
        requestEntity.setTechnician(technician);
        requestEntity.setEmail(email);
        Map<String, String> uploadImage =requestService.uploadImageToFileSystem(file,requestEntity);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("/projectFile/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=requestService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
    @PostMapping("/findRequestByUnit")
    public ResponseEntity<?> findRequestByUnit(@RequestBody Requestdto requestdto) {

        Optional<Department> department = departmentRepository.findByDepartmentName(requestdto.getDepartmentName());
        if(department.isPresent()) {
            List<RequestEntity> requests = requestRepository.findByStatusAndUnit(Status.valueOf(requestdto.getStatus()), department.get().getDepartmentName());
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } else return new ResponseEntity<>(new Responses("90","Request could not be found"),HttpStatus.OK);
    }


    @PostMapping("/updateRequestTechnician")
    public ResponseEntity<?> assignRequestToTechnician(@RequestBody Map<String, String> data) {

        Map<String, String> response = new HashMap<>();
        RequestEntity request = requestRepository.findById(Integer.parseInt(data.get("requestId"))).orElse(null);

        if(request != null) {
            request.setTechnician(data.get("newTechnician"));
            requestRepository.save(request);
            response.put("code", "00");
        }

        else {
            response.put("code", "99");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping("/closeRequest")
    public ResponseEntity<?> closeTicket(@RequestBody Map<String, String> data) {

        Map<String, String> response = new HashMap<>();
        log.info("Closing Request::: "+data.toString());
        RequestEntity request = requestRepository.findById(Integer.parseInt(data.get("requestId"))).orElse(null);

        if(request != null) {
            request.setStatus(Status.valueOf(data.get("status")));
            request.setClosureComments(data.get("closureComments"));
            request.setClosureTime(LocalDateTime.now());
            requestRepository.save(request);
            response.put("code", "00");
        }
        else {
            response.put("code", "99");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/updateRequestTechnicianMultiple")
    public ResponseEntity<?> pickBulkRequests(@RequestBody BulkDto bulkDto) {


        log.info("Incoming request for bulk assigning "+bulkDto.toString());
        Map<String, String> response = new HashMap<>();
        for(RequestEntity request : bulkDto.getRequests()) {
            RequestEntity findRequest = requestRepository.findById((request.getId())).orElse(null);

            if(request != null) {
                request.setTechnician(bulkDto.getTechnician());
                requestRepository.save(request);
                response.put("code", "00");
            }

            else {
                response.put("code", "99");
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @PostMapping("/pickupclosemultiple")
    public ResponseEntity<?> pickAndCloseBulkRequests(@RequestBody BulkDto bulkDto) {


        log.info("Incoming request for bulk assigning "+bulkDto.toString());
        Map<String, String> response = new HashMap<>();
        for(RequestEntity request : bulkDto.getRequests()) {
            RequestEntity findRequest = requestRepository.findById((request.getId())).orElse(null);

            if(request != null) {
                request.setTechnician(bulkDto.getTechnician());
                request.setStatus(Status.CLOSED);
                request.setClosureTime(LocalDateTime.now());
                request.setClosureComments("Closed Automatically");
                requestRepository.save(request);
                response.put("code", "00");
            }

            else {
                response.put("code", "99");
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping("/allData")
    public ResponseEntity<?> findAllData(@RequestBody Map<String, String> data) {
        Map<String, Integer> allRecordByUser = requestService.findAllRecordByUser(data.get("userName"));
        return new ResponseEntity<>(allRecordByUser, HttpStatus.OK);
    }
}
