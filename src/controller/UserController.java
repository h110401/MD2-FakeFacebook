package controller;

import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessenger;
import model.friend.Friend;
import model.role.Role;
import model.role.RoleName;
import model.account.User;
import service.friend.FriendServiceIMPL;
import service.friend.IFriendService;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {

    IUserService userService = new UserServiceIMPL();
    IRoleService roleService = new RoleServiceIMPL();
    IFriendService friendService = new FriendServiceIMPL();

    public ResponseMessenger register(SignUpDTO signUpDTO) {
        if (userService.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseMessenger("username_existed");
        }
        if (userService.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseMessenger("email_existed");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.USER));

        User user = new User(
                signUpDTO.getId(),
                signUpDTO.getName(),
                signUpDTO.getUsername(),
                signUpDTO.getEmail(),
                signUpDTO.getPassword(),
                roles
        );

        userService.save(user);
        return new ResponseMessenger("success");
    }

    public ResponseMessenger login(SignInDTO signInDTO) {
        if (!userService.existsByUsername(signInDTO.getUsername())) {
            return new ResponseMessenger("username_not_exist");
        }
        if (!userService.checkLogin(signInDTO.getUsername(), signInDTO.getPassword())) {
            return new ResponseMessenger("password_not_correct");
        }
        if (userService.getByUsername(signInDTO.getUsername()).isPrevent()) {
            return new ResponseMessenger("user_blocked");
        }
        User login = userService.getByUsername(signInDTO.getUsername());

        userService.saveCurrentUser(login);

        return new ResponseMessenger("login_success");
    }

    public ResponseMessenger deleteUser(int id) {
        if (userService.findById(id) == null) {
            return new ResponseMessenger("id_mismatch");
        }
        userService.remove(id);

        for (Friend friend : friendService.getRequestSentOfUserId(id)) {
            friendService.remove(friend.getId());
        }
        //delete comment
        return new ResponseMessenger("delete_success");
    }

    public ResponseMessenger blockUser(int id) {
        if (userService.findById(id) == null) {
            return new ResponseMessenger("id_mismatch");
        }
        if (userService.findById(id).isPrevent()) {
            userService.changeStatus(id);
            return new ResponseMessenger("unblocked");
        }
        userService.changeStatus(id);
        return new ResponseMessenger("block_success");
    }

    public List<User> getUserList() {
        return userService.findAll();
    }


    public User findById(int id) {
        return userService.findById(id);
    }

    public List<User> findByRoleName(RoleName roleName) {
        return userService.findByRoleName(roleName);
    }

    public void setRole(int id, Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        for (String role : strRoles) {
            switch (role) {
                case "admin":
                    roles.add(roleService.findByRoleName(RoleName.ADMIN));
                    break;
                case "pm":
                    roles.add(roleService.findByRoleName(RoleName.PM));
                    break;
                case "user":
                    roles.add(roleService.findByRoleName(RoleName.USER));
                    break;
            }
        }
        userService.changeRole(id, roles);
    }

    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    public void logout() {
        userService.saveCurrentUser(null);
    }

    public List<User> findUsers(String name) {
        return userService.findUserByName(name);
    }

    public int getLastId() {
        return userService.getLastId();
    }

    public ResponseMessenger changePassword(String oldPassword, String newPassword) {
        if (!getCurrentUser().getPassword().equals(oldPassword)) {
            return new ResponseMessenger("not_match");
        }
        User user = getCurrentUser();
        user.setPassword(newPassword);
        userService.updateData();
        return new ResponseMessenger("success");
    }
}
