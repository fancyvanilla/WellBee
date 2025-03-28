package tn.fst.team2.jee.wellbee.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.fst.team2.jee.wellbee.users.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLinkedinId(String linkedinId);

    User findByEmail(String email);

    User findByFullName(String fullName);

    List<User> findByJobTitle(String jobTitle);

    List<User> findByDepartment(String department);
}
