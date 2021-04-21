package me.forcar.backend.configuration;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper getModelMapper(){
        var modelMapper =  new ModelMapper();

        return modelMapper;
    }
}
