package com.ehss.property.util;

import com.ehss.property.pojo.PropertyFile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyFileUtil {


    public String toString(List<PropertyFile> propertyFileList){
        StringBuilder str = new StringBuilder();
        propertyFileList.forEach( p->{
            str.append(p.getPropertyKey()).append("=").append(p.getPropertyValue()).append(System.lineSeparator());
        });
        return str.toString();
    }
}
