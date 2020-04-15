package com.landmark.app.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtils {

    private static ModelMapper modelMapper;

    @Autowired
    public MapperUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /** 공통 매퍼 **/
    public static <D, E> D convert(E source, Class<? extends D> classLiteral) {
        try {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return modelMapper.map(source, classLiteral);
        } catch (Exception e) {
            return null;
        }
    }

    /** 공통 매퍼 **/
    public static <D, E> D convert(E source, Type type) {
        try {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return modelMapper.map(source, type);
        } catch (Exception e) {
            return null;
        }
    }

}
