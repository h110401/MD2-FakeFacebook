package service.user;

import model.role.Role;
import model.role.RoleName;
import model.account.User;
import service.IGenericService;

import java.util.List;
import java.util.Set;

public interface IUserService extends IGenericService<User> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    User getByUsername(String username);

    boolean checkLogin(String username, String password);
    void changeStatus(int id);

    List<User> findByRoleName(RoleName roleNames);

    void changeRole(int id, Set<Role> roles);

    User getCurrentUser();

    void saveCurrentUser(User user);

    List<User> findUserByName(String name);

}
