package ma.emsi.hosp.security.repositories;


import ma.emsi.hosp.security.entities.RolePatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePatientRepo extends JpaRepository<RolePatient, String> {
}