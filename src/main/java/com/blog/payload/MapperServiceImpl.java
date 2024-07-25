package com.blog.payload;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements  MapperService{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public <S, D> D map(S sourceClass, Class<D> destinationClass) {
        return this.modelMapper.map(sourceClass,destinationClass);
    }

    @Override
    public ModelMapper modelMapper() {
        return  modelMapper();
    }
}
