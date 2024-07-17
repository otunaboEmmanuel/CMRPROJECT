package CRM.project.repository;

import CRM.project.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReportRepository extends JpaRepository<Reports, Long> {
    List<Reports> findByCreatedBy(String createdBy);

    Optional<Reports> findByReportId(Long reportId);
}
