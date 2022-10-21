package com.ehss.property.service;

import com.ehss.property.exceptions.PropertyFileNotFoundException;
import com.ehss.property.exceptions.PropertyParsingException;
import com.ehss.property.pojo.ComparedProperties;
import com.ehss.property.pojo.PropertyFile;
import com.ehss.property.pojo.PropertyFileId;
import com.ehss.property.repository.PropertyFileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class PropertyFileService {

    @Autowired
    PropertyFileRepo propertyFileRepo;


    public List<PropertyFile> parsePropertyFile(String name, String payload) throws PropertyParsingException {

        InputStream targetStream = new ByteArrayInputStream(payload.getBytes());
        Properties properties = new Properties();
        List<PropertyFile> propertyFileEntries = new ArrayList<>();
        try {
            properties.load(targetStream);
        } catch (IOException e) {
            throw new PropertyParsingException(name,e);
        }
        properties.forEach( (k , v) ->{
            PropertyFile propertyFile = PropertyFile.builder().fileName(name)
                    .propertyKey((String)k).propertyValue((String)v).build();


            propertyFileEntries.add(propertyFile);
        });

        return propertyFileEntries;
    }

    public List<PropertyFile> createPropertyFileFromString(String name,String payload) throws Exception{
        List<PropertyFile> propertyFileEntries = parsePropertyFile(name,payload);
        propertyFileEntries.forEach( p -> propertyFileRepo.save(p));

        return propertyFileEntries;
    }

    public List<PropertyFile> getPropertyFile(String name) {
        return propertyFileRepo.findByFileName(name);

    }


    public List<ComparedProperties> compareProperties(String leftFile,String rightFile) throws Exception{
        List<ComparedProperties> comparedProperties = new ArrayList<>();
        List<PropertyFile> leftFileEntries = propertyFileRepo.findByFileName(leftFile);
        List<PropertyFile> rightFileEntries = propertyFileRepo.findByFileName(rightFile);

        return comparedProperties;
    }
}
