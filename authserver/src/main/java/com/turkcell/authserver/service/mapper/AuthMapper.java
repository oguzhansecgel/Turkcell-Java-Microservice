package com.turkcell.authserver.service.mapper;

import com.turkcell.authserver.entities.User;
import com.turkcell.authserver.service.dtos.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);


    User userFromRequest(RegisterRequest request);
}
