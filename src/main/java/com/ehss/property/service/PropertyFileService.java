package com.ehss.property.service;

import com.ehss.property.exceptions.PropertyFileNotFoundException;
import com.ehss.property.exceptions.PropertyParsingException;
import com.ehss.property.pojo.ComparedProperties;
import com.ehss.property.pojo.PropertyEntryStatus;
import com.ehss.property.pojo.PropertyFile;
import com.ehss.property.pojo.PropertyFileId;
import com.ehss.property.repository.PropertyFileRepo;
import com.ehss.property.util.PropertyFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PropertyFileService {

    @Autowired
    PropertyFileRepo propertyFileRepo;

    @Autowired
    PropertyFileUtil propertyFileUtil;

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
        List<ComparedProperties> comparedPropertiesList = new ArrayList<>();
        List<PropertyFile> leftFileEntries = propertyFileRepo.findByFileName(leftFile);
        List<PropertyFile> rightFileEntries = propertyFileRepo.findByFileName(rightFile);
        Map<String,PropertyFile> rightMap = rightFileEntries.stream().collect(Collectors.toMap(PropertyFile::getPropertyKey, Function.identity()));
        Map<String,PropertyFile> leftMap = leftFileEntries.stream().collect(Collectors.toMap(PropertyFile::getPropertyKey, Function.identity()));
        for (Map.Entry<String, PropertyFile> entry : leftMap.entrySet()) {
            ComparedProperties comparedProperties = ComparedProperties.builder()
                    .propertyKey(entry.getKey())
                    .leftPropertyFileEntry(entry.getValue()).build();
            PropertyFile rightPropertyFile = rightMap.get(entry.getKey());
            comparedProperties.setRightPropertyFileEntry(rightPropertyFile);
            if (rightPropertyFile==null){
                comparedProperties.setPropertyEntryStatus(PropertyEntryStatus.NOTFOUND);
            }else if (rightPropertyFile.getPropertyValue().equals(entry.getValue().getPropertyValue())){
                comparedProperties.setPropertyEntryStatus(PropertyEntryStatus.SAME);
            }else{
                comparedProperties.setPropertyEntryStatus(PropertyEntryStatus.DIFFERENT);
            }
            comparedPropertiesList.add(comparedProperties);
        }
        for (Map.Entry<String, PropertyFile> entry : rightMap.entrySet()) {
            PropertyFile leftPropertyFile = leftMap.get(entry.getKey());
            if (leftPropertyFile==null){
                ComparedProperties comparedProperties = ComparedProperties.builder()
                        .propertyKey(entry.getKey())
                        .propertyEntryStatus(PropertyEntryStatus.NOTFOUND)
                        .rightPropertyFileEntry(entry.getValue()).build();
                comparedPropertiesList.add(comparedProperties);
            }
        }
        return comparedPropertiesList;
    }


    public String mergeProperties(String leftFile,String rightFile,String mergeSide) throws Exception{
        List<PropertyFile> mergedPropertiesList = new ArrayList<>();
        List<PropertyFile> leftFileEntries = propertyFileRepo.findByFileName(leftFile);
        List<PropertyFile> rightFileEntries = propertyFileRepo.findByFileName(rightFile);

        Map<String,PropertyFile> rightMap = rightFileEntries.stream().collect(Collectors.toMap(PropertyFile::getPropertyKey, Function.identity()));
        Map<String,PropertyFile> leftMap = leftFileEntries.stream().collect(Collectors.toMap(PropertyFile::getPropertyKey, Function.identity()));
        if ("LEFT".equalsIgnoreCase(mergeSide)){
            for (Map.Entry<String, PropertyFile> entry : leftMap.entrySet()) {
                mergedPropertiesList.add(entry.getValue());
            }
            for (Map.Entry<String, PropertyFile> entry : rightMap.entrySet()) {
                PropertyFile leftPropertyFile = leftMap.get(entry.getKey());
                if (leftPropertyFile==null){
                    mergedPropertiesList.add(entry.getValue());
                }
            }

        }else{
            for (Map.Entry<String, PropertyFile> entry : rightMap.entrySet()) {
                mergedPropertiesList.add(entry.getValue());
            }
            for (Map.Entry<String, PropertyFile> entry : leftMap.entrySet()) {
                PropertyFile leftPropertyFile = rightMap.get(entry.getKey());
                if (leftPropertyFile==null){
                    mergedPropertiesList.add(entry.getValue());
                }
            }
        }
        return propertyFileUtil.toString(mergedPropertiesList);
    }
}
