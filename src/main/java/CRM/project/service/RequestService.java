package CRM.project.service;

import CRM.project.dto.Requestdto;
import CRM.project.entity.RequestEntity;
import CRM.project.entity.Status;
import CRM.project.entity.SubCategory;
import CRM.project.entity.Users;
import CRM.project.repository.RequestRepository;
import CRM.project.repository.SubCategoryRepository;
import CRM.project.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {

    static String DIRECTORY_PATH = "/u01/uploads/";
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    public Map<String, String> uploadImageToFileSystem(MultipartFile file, RequestEntity requestEntity) throws IOException {
        Map<String, String> responseData = new HashMap<>();
        SubCategory sub = subCategoryRepository.findBySubCategoryName(requestEntity.getSubCategory()).orElse(null);
        if(sub != null) {
            requestEntity.setSla(sub.getSla());
            requestEntity.setDueDate(LocalDateTime.now().plusHours((long) sub.getSla()));

            if (file != null) {
                String filePath = saveFileToStorage(file);
                requestEntity.setFilePath(DIRECTORY_PATH + filePath);
                requestEntity.setType(file.getContentType());
                requestEntity.setName(file.getOriginalFilename());
            }

            if(requestEntity.getTechnician().equalsIgnoreCase("Unassigned")) {
                String technician = findLeastAssignedTechnician(requestEntity.getUnit());
                if(technician != null) {
                    requestEntity.setTechnician(technician);
                }
                else {
                    requestEntity.setTechnician("Unassigned");
                }
            }
            requestEntity.setStatus(Status.OPEN);
            requestEntity.setLogTime(LocalDateTime.now());
            log.info("Request Entity:::::: "+requestEntity);
            RequestEntity storeData = requestRepository.save(requestEntity);

            if (storeData != null) {
                responseData.put("code", "00");
                responseData.put("message", "Request saved successfully");
                responseData.put("requestId", String.valueOf(storeData.getId()));
            } else {
                responseData.put("code", "90");
                responseData.put("message", "Failed to save request");
            }
        }

        else {
            responseData.put("code", "99");
            responseData.put("message", "Invalid Sub-Category");
        }
        return responseData;
    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        byte[] images = Files.readAllBytes(new File(fileName).toPath());
        return images;
    }

    private String findLeastAssignedTechnician(String unitName) {

        List<RequestEntity> allRequestsForTheDay = requestRepository.findByCreatedTimeBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));

        Map<String, Long> technicianRequestCount = allRequestsForTheDay.stream()
                .collect(Collectors.groupingBy(RequestEntity::getTechnician, Collectors.counting()));

        log.info(technicianRequestCount.toString());

        long minRequestCount = technicianRequestCount.values().stream().min(Long::compare).orElse(Long.MAX_VALUE);

        log.info(""+minRequestCount);
        // Collect technicians with the minimum number of requests
        List<String> leastBusyTechnicians = technicianRequestCount.entrySet().stream()
                .filter(entry -> entry.getValue() == minRequestCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        log.info(leastBusyTechnicians.toString());

        if (!leastBusyTechnicians.isEmpty()) {
            Random random = new Random();
            return leastBusyTechnicians.get(random.nextInt(leastBusyTechnicians.size()));
        }

        return null;

    }

    private String saveFileToStorage(MultipartFile file) {

        String extensionType = file.getContentType();

        String extension = "";

        if (extensionType != null && !extensionType.isEmpty()) {
            String[] parts = extensionType.split("/");
            if (parts.length > 1) {
                extension = "." + parts[1];
            }
        }
        String fileName = UUID.randomUUID().toString().replace("-", "") + extension;

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

    public Map<String, Integer> findAllRecordByUser(String userName) {
        log.info("User is "+userName);
        Users user = usersRepository.findByUserEmail(userName).orElse(null);
        Map<String, Integer> result = new HashMap<>();
        if(user != null) {
            List<RequestEntity> allRequests = requestRepository.findByTechnician(user.getStaffName());

            int totalRequests = allRequests.size();
            log.info(""+totalRequests);

            int sumOfClosedRequests = allRequests.stream()
                    .filter(request -> Status.CLOSED.equals(request.getStatus()))
                    .mapToInt(request -> 1)
                    .sum();

            int sumOfResolvedRequests = allRequests.stream()
                    .filter(request -> Status.RESOLVED.equals(request.getStatus()))
                    .mapToInt(request -> 1)
                    .sum();

            int  sumOfOpenRequests = allRequests.stream()
                    .filter(request -> Status.OPEN.equals(request.getStatus()))
                    .mapToInt(request -> 1)
                    .sum();

            int sumOfRequestsWithinSla = allRequests.stream()
                    .filter(request -> LocalDateTime.now().isAfter(request.getDueDate()))
                    .mapToInt(request -> 1)
                    .sum();
            result.put("openRequest", sumOfOpenRequests);
            result.put("closedRequest", sumOfClosedRequests);
            result.put("totalRequest", totalRequests);
            result.put("resolvedTicket", sumOfResolvedRequests);
            result.put("withinSla", sumOfRequestsWithinSla);
            result.put("breachedSla", totalRequests - sumOfRequestsWithinSla);
        }
        return result;
    }
}
