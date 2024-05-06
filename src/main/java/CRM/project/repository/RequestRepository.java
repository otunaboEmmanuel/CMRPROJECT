package CRM.project.repository;

import CRM.project.entity.Department;
import CRM.project.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity,Integer> {
    Optional<RequestEntity> findByName(String fileName);

    List<RequestEntity> findByUnit(String unit);

    Optional<RequestEntity> findByStatus(String status);

    List<RequestEntity> findByStatusAndUnit(String status, Department department);
}
