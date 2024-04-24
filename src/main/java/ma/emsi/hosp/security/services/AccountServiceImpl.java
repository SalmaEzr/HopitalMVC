package ma.emsi.hosp.security.services;

import lombok.AllArgsConstructor;
import ma.emsi.hosp.security.entities.RolePatient;
import ma.emsi.hosp.security.entities.UserPatient;
import ma.emsi.hosp.security.repositories.RolePatientRepo;
import ma.emsi.hosp.security.repositories.UserPatientRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private UserPatientRepo userPatientRepo;
    private RolePatientRepo rolePatientRepo;
    private PasswordEncode passwordEncoder;

    @Override
    public UserPatient saveUser(String username, String password, String email, String confirmPassword) {
        UserPatient user = userPatientRepo.findByUsername(username);
        if (user != null) throw new RuntimeException("User already exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Please confirm your password");
        UserPatient userP = new UserPatient();
        userP.setUserId(UUID.randomUUID().toString());
        userP.setUsername(username);
        userP.setPassword(passwordEncoder.encodse(password));
        userP.setEmail(email);
        return userPatientRepo.save(userP);
    }

    @Override
    public RolePatient saveRole(String role) {
        RolePatient roleP = rolePatientRepo.findById(role).orElse(null);
        if (roleP != null) throw new RuntimeException("Role already exists");
        roleP = new RolePatient();
        roleP.setRole(role);
        return rolePatientRepo.save(roleP);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        UserPatient user = userPatientRepo.findByUsername(username);
        RolePatient role = rolePatientRepo.findById(roleName).get();
        user.getRoles().add(role);

    }

    @Override
    public void removeRoleToUser(String username, String roleName) {
        UserPatient user = userPatientRepo.findByUsername(username);
        RolePatient role = rolePatientRepo.findById(roleName).get();
        user.getRoles().remove(role);

    }

    @Override
    public UserPatient loadUserByUsername(String username) {
        return userPatientRepo.findByUsername(username);
    }
}

