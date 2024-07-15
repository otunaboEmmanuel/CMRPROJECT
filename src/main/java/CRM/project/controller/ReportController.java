package CRM.project.controller;

import CRM.project.dto.ReportData;
import CRM.project.entity.Reports;
import CRM.project.repository.ReportRepository;
import CRM.project.response.Responses;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/reports")
@CrossOrigin
@Slf4j
public class ReportController {


    @Autowired
    ReportRepository reportRepository;

    @PostMapping("/create-report")
    public ResponseEntity<?> createReport(@RequestBody Map<String, Object> request) {
        log.info("Incoming request is "+request.toString());

        Reports reports = new Reports();
        reports.setStartDate(String.valueOf(request.get("startDate")));
        reports.setEndDate(String.valueOf(request.get("endDate")));
        reports.setReportName(String.valueOf(request.get("reportName")));
        List<String> fields = (List<String>) request.get("selectedField");
        reports.setRequiredData(fields);
        reports.setReportData(mapReportContents(fields));
        reports.setCreatedBy(String.valueOf(request.get("createdBy")));
        reports.setCreatedTime(LocalDateTime.now());

        try{
            reportRepository.save(reports);
            return new ResponseEntity<>(new Responses("00", "Report Saved Successfully"), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(new Responses("90", "Report Could not be saved"), HttpStatus.OK);

        }
    }


    @PostMapping("/getReports")
    public ResponseEntity<?> fetchReports(@RequestBody Map<String, String> data) {

        log.info("Incoming request is::: "+data.toString());
        List<Reports> allReports = reportRepository.findByCreatedBy(data.get("createdBy"));
        if(!allReports.isEmpty()) {
            return new ResponseEntity<>(allReports, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new Responses("90", "No Request Yet"), HttpStatus.OK);
        }
    }

    public ReportData mapReportContents(List<String> content) {

        ReportData data = new ReportData();
        for(String item: content) {
            if(item.equalsIgnoreCase("Requester"))
                data.setRequester(true);
            if(item.equalsIgnoreCase("Created Date"))
                data.setLogTime(true);
            if(item.equalsIgnoreCase("Closure Time"))
                data.setClosureTime(true);
            if(item.equalsIgnoreCase("Priority"))
                data.setPriority(true);
            if(item.equalsIgnoreCase("Closure Comments"))
                data.setPriority(true);
            if(item.equalsIgnoreCase("Technician"))
                data.setTechnician(true);
            if(item.equalsIgnoreCase("Description"))
                data.setDescription(true);
            if(item.equalsIgnoreCase("Subject"))
                data.setSubject(true);
            if(item.equalsIgnoreCase("Unit"))
                data.setUnit(true);
            if(item.equalsIgnoreCase("Category"))
                data.setCategory(true);
            if(item.equalsIgnoreCase("SLA"))
                data.setSla(true);
            if(item.equalsIgnoreCase("Sub-Category"))
                data.setSubCategory(true);

        }
        return data;
    }
}
