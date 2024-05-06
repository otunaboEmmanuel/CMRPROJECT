package CRM.project.repository;

import CRM.project.entity.Department;
import CRM.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByStaffName (String staffName);

    List<Users> findAllByUnitName(String unit);

    List<Users> findByUnitName(Department unit);
}
