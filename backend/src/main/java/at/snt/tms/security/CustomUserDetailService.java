package at.snt.tms.security;

import at.snt.tms.model.operator.User;
import at.snt.tms.repositories.operator.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code CustomUserDetailService}
 *
 * @author Oliver Sommer
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) {
        User user = userRepository.findByMailIgnoreCase(mail);
        if(user == null) {
            throw new UsernameNotFoundException(mail);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ADMIN"));  // TODO authorities
//        user.getGroup().getPermissions().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getDesignation())));

        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), authorities);
    }
}
