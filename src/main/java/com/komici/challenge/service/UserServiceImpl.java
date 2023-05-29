package com.komici.challenge.service;

import com.komici.challenge.exception.BTNoEntityFoundException;
import com.komici.challenge.persistence.UserEntity;
import com.komici.challenge.persistence.UserRepository;
import com.komici.challenge.rest.model.user.AddUser;
import com.komici.challenge.rest.model.user.UpdateUser;
import com.komici.challenge.rest.model.user.UserListResponse;
import com.komici.challenge.rest.model.user.UserModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel getUser(Long id) {

        Optional<UserEntity> userEntityOpt = userRepository.findById(id);

        if (!userEntityOpt.isPresent()) {
            throw new BTNoEntityFoundException(id);
        }

        return convertToModel(userEntityOpt.get());
    }

    @Override
    public UserListResponse getAllUsers() {

        List<UserEntity> userEntities = userRepository.findAll();
        UserListResponse userListResponse = new UserListResponse();
        List<UserModel> userList = new ArrayList<>();

        userEntities.forEach(userEntity -> userList.add(convertToModel(userEntity)));

        userListResponse.setUserList(userList);

        return userListResponse;
    }

    @Override
    public UserModel saveUser(AddUser addUser) {

        UserEntity userEntity = convertToEntity(addUser);

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return convertToModel(savedUserEntity);
    }

    @Override
    public UserModel updateUser(UpdateUser updateUser) {

        if (!userRepository.existsById(updateUser.getId()))
            throw new BTNoEntityFoundException("The requested user does not exists");

        UserEntity userEntity = convertToEntity(updateUser);
        userEntity.setId(updateUser.getId());

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return convertToModel(savedUserEntity);
    }

    @Override
    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId))
            throw new BTNoEntityFoundException("The requested user does not exists");

        userRepository.deleteById(userId);
    }



    private UserEntity convertToEntity(AddUser userModel) {

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userModel.getUsername());
        userEntity.setName(userModel.getFirstname());
        userEntity.setSurname(userModel.getSurname());
        userEntity.setEmail(userModel.getEmail());
        userEntity.setEnable(userModel.isEnable());

        return userEntity;
    }

    private static UserModel convertToModel(UserEntity userEntity) {

        UserModel userModel = new UserModel();

        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setFirstname(userEntity.getName());
        userModel.setSurname(userEntity.getSurname());
        userModel.setEmail(userEntity.getEmail());
        userModel.setEnable(userEntity.isEnable());

        return userModel;
    }
}
