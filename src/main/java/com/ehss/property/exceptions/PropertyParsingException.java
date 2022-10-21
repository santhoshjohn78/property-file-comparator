package com.ehss.property.exceptions;


import lombok.Data;

@Data
public class PropertyParsingException extends Exception{

    String propertyName;
    public PropertyParsingException(String propertyName,Exception exception){
        super(exception);
        this.propertyName = propertyName;

    }
}
