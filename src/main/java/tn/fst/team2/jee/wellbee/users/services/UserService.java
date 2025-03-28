package tn.fst.team2.jee.wellbee.users.services;

import tn.fst.team2.jee.wellbee.users.entities.User;

import java.util.List;

public interface UserService {
    public User addOrUpdateUser(User user);
    public void deleteUser(User user);
    public User getUser(Long id);
    public User getUserByLinkedinId(String linkedinId);
    public User getUserByEmail(String email);
    public User getUserByFullName(String fullName);
    public List<User> getUsersByJobTitle(String jobTitle);
    public List<User> getUsersByDepartment(String department);
    public List<User> getAllUsers();
}
