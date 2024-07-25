package com.blog.payload;

import org.modelmapper.ModelMapper;

public interface MapperService {

    <S,D> D map(S sourceClass,  Class<D> destinationClass);


    ModelMapper modelMapper();
}
