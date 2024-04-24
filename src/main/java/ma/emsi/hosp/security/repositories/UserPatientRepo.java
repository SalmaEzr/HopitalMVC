package ma.emsi.hosp.security.repositories;

import ma.emsi.hosp.security.entities.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPatientRepo extends JpaRepository<UserPatient, String> {
    UserPatient findByUsername(String username);
}