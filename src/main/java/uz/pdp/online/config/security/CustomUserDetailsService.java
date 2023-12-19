package uz.pdp.online.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.online.model.AuthPermission;
import uz.pdp.online.model.AuthRole;
import uz.pdp.online.model.AuthUser;
import uz.pdp.online.repository.AuthPermissionRepository;
import uz.pdp.online.repository.AuthRoleRepository;
import uz.pdp.online.repository.AuthUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final AuthPermissionRepository authPermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (authUser.isBlocked()) {
            throw new LockedException("User is blocked");
        }

        fetchUserRolesAndPermissions(authUser);

        return new CustomUserDetails(authUser);
    }

    private void fetchUserRolesAndPermissions(AuthUser authUser) {
        Long userId = authUser.getId();
        List<AuthRole> roles = authRoleRepository.findAllByUserId(userId);

        for (AuthRole role : roles) {
            List<AuthPermission> permissions = authPermissionRepository.findAllByRoleId(role.getId());
            role.setPermissions(permissions);
        }

        authUser.setRoles(roles);
    }

}
