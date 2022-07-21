package org.myddd.extensions.security.oauth2;

import com.google.protobuf.StringValue;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@Named
public class SecurityUserService implements UserDetailsService {

    @Inject
    private UserApplication userApplication;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userApplication.queryLocalUserByUserId(StringValue.of(username));
        if(Objects.isNull(userDto)){
            throw new UsernameNotFoundException(username + " not found in local database");
        }

        if(userDto.getDisabled()){
            throw new UsernameNotFoundException(username + " is disabled");
        }

        return new LoginUserDetails(userDto);
    }
}
