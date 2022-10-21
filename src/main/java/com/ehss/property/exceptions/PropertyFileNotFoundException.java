package com.ehss.property.exceptions;

import lombok.Data;

@Data
public class PropertyFileNotFoundException extends Exception {

    String propertyName;
    public PropertyFileNotFoundException(String propertyName){

        this.propertyName = propertyName;

    }
}
