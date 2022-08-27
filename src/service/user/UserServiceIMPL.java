package service.user;

import config.Config;
import model.role.Role;
import model.role.RoleName;
import model.account.User;
import service.role.RoleServiceIMPL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceIMPL implements IUserService {

    private static final String PATH_USER = "src/data/user.txt";
    private static final String PATH_USER_PRINCIPAL = "src/data/user_principal.txt";
    private static final Config<List<User>> config = new Config<>();
    private static List<User> userList = config.read(PATH_USER);

    static {
        if (userList == null || userList.size() == 0) {
            userList = new ArrayList<>();
            Set<Role> roles = new HashSet<>();
            roles.add(new RoleServiceIMPL().findByRoleName(RoleName.ADMIN));
            userList.add(new User(1, "Admin", "admin", "admin@gmail.com", "admin", roles));
        }
    }

    @Override
    public List<User> findAll() {
        config.write(PATH_USER, userList);
        return userList;
    }

    @Override
    public void save(User user) {
        userList.add(user);
        updateData();
    }

    @Override
    public User findById(int id) {
        for (User user : userList) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    @Override
    public void remove(int id) {
        userList.remove(findById(id));
        updateData();
    }

    @Override
    public void updateData() {
        config.write(PATH_USER, userList);
    }

    @Override
    public int getLastId() {
        return userList.isEmpty() ? 1 : userList.get(userList.size() - 1).getId() + 1;
    }


    @Override
    public boolean existsByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) return true;
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) return true;
        }
        return false;
    }

    @Override
    public User getByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    @Override
    public void changeStatus(int id) {
        User user = findById(id);
        user.setPrevent(!user.isPrevent());
        config.write(PATH_USER, userList);
    }

    @Override
    public List<User> findByRoleName(RoleName roleName) {
        List<User> list = new ArrayList<>();
        for (User user : userList) {
            if (user.getMaxRole() == roleName) {
                userList.add(user);
            }
        }
        return list;
    }

    @Override
    public void changeRole(int id, Set<Role> roles) {
        findById(id).setRoles(roles);
        config.write(PATH_USER, userList);
    }

    @Override
    public User getCurrentUser() {
        User user = new Config<User>().read(PATH_USER_PRINCIPAL);
        if (user == null) return null;
        return getByUsername(user.getUsername());
    }

    @Override
    public void saveCurrentUser(User currentUser) {
        new Config<User>().write(PATH_USER_PRINCIPAL, currentUser);
    }

    @Override
    public List<User> findUserByName(String name) {
        List<User> list = new ArrayList<>();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(name.toLowerCase()) && user != getCurrentUser()) {
                list.add(user);
            }
        }
        return list;
    }


}
