package pt.sousavf.backend.core.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pt.sousavf.backend.core.model.entity.UserEntity;
import pt.sousavf.backend.core.model.mappers.user.UserMapper;
import pt.sousavf.backend.core.repository.UserRepository;

@Component
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.toDto(userEntity);
    }
}
