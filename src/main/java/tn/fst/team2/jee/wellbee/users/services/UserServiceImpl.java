package tn.fst.team2.jee.wellbee.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.fst.team2.jee.wellbee.users.entities.User;
import tn.fst.team2.jee.wellbee.users.repositories.UserRepository;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByLinkedinId(String linkedinId) {
        return userRepository.findByLinkedinId(linkedinId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }

    @Override
    public List<User> getUsersByJobTitle(String jobTitle) {
        return userRepository.findByJobTitle(jobTitle);
    }

    @Override
    public List<User> getUsersByDepartment(String department) {
        return userRepository.findByDepartment(department);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
