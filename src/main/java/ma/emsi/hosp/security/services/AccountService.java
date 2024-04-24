package ma.emsi.hosp.security.services;

import ma.emsi.hosp.security.entities.RolePatient;
import ma.emsi.hosp.security.entities.UserPatient;

public interface AccountService {
    UserPatient saveUser(String username, String password, String email, String confirmPassword);
    RolePatient saveRole(String role);
    void addRoleToUser(String username, String roleName);
    void removeRoleToUser(String username, String roleName);
    UserPatient loadUserByUsername(String username);


}
