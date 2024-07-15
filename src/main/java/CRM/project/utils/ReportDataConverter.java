package CRM.project.utils;

import CRM.project.dto.ReportData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;

public class ReportDataConverter implements AttributeConverter<ReportData, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ReportData reportData) {
        try {
            return objectMapper.writeValueAsString(reportData);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert report data to JSON", e);
        }
    }

    @Override
    public ReportData convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ReportData.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert JSON to report data", e);
        }
    }
}
