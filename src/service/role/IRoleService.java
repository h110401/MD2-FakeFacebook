package service.role;


import model.role.Role;
import model.role.RoleName;

public interface IRoleService {

    Role findByRoleName(RoleName roleName);

}
