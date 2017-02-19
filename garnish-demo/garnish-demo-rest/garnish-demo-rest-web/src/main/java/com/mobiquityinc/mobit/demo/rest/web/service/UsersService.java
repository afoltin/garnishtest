package com.mobiquityinc.mobit.demo.rest.web.service;

import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.AddressesMapper;
import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.UsersMapper;
import com.mobiquityinc.mobit.demo.rest.web.dao.model.Address;
import com.mobiquityinc.mobit.demo.rest.web.dao.model.User;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.GeoCodingService;
import com.mobiquityinc.mobit.demo.rest.web.service.geocoding.model.GeoLocation;
import com.mobiquityinc.mobit.demo.rest.web.service.security.CurrentUserProvider;
import com.mobiquityinc.mobit.demo.rest.web.service.security.SecurityUser;
import com.mobiquityinc.mobit.demo.rest.web.web.controllers.users.model.CreateUserRequest;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Nullable;

public final class UsersService {

    @NonNull private final CurrentUserProvider currentUserProvider;
    @NonNull private final UsersMapper usersMapper;
    @NonNull private final AddressesMapper addressesMapper;
    @NonNull private final PasswordEncoder passwordEncoder;
    @NonNull private final GeoCodingService geoCodingService;

    public UsersService(@NonNull final CurrentUserProvider currentUserProvider,
                        @NonNull final UsersMapper usersMapper,
                        @NonNull final AddressesMapper addressesMapper,
                        @NonNull final PasswordEncoder passwordEncoder,
                        @NonNull final GeoCodingService geoCodingService) {
        this.currentUserProvider = currentUserProvider;
        this.usersMapper = usersMapper;
        this.addressesMapper = addressesMapper;
        this.passwordEncoder = passwordEncoder;
        this.geoCodingService = geoCodingService;
    }

    public long createUser(@NonNull final CreateUserRequest request) {
        final long userId = insertUser(request);
        insertAddress(request, userId);

        return userId;
    }

    private long insertUser(@NonNull final CreateUserRequest request) {
        final User user = new User();

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(
                this.passwordEncoder.encode(
                        request.getPassword()
                )

        );

        this.usersMapper.insertUser(user);

        return user.getId();
    }

    private long insertAddress(@NonNull final CreateUserRequest request,
                               final long userId) {
        final Address address = new Address();

        address.setUserId(userId);
        address.setTextualAddress(request.getAddress());

        final GeoLocation geoLocation = this.geoCodingService.geoCode(request.getAddress());

        address.setLatitude(geoLocation.getLatitude());
        address.setLongitude(geoLocation.getLongitude());

        return this.addressesMapper.insertAddress(address);
    }

    @Nullable
    public Long getUserIdByUsernameAndPassword(final String username,
                                               final String password) {
        final User user = this.usersMapper.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        final String encodedPassword = user.getPassword();
        if (!this.passwordEncoder.matches(password, encodedPassword)) {
            return null;
        }

        return user.getId();
    }


    public SecurityUser getCurrentUser() {
        return this.currentUserProvider.getCurrentUser();
    }
}
