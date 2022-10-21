package com.ehss.property.helper;

import com.ehss.property.pojo.PropertyFile;

import java.util.Comparator;

public class PropertySorter implements Comparator<PropertyFile> {

    @Override
    public int compare(PropertyFile o1, PropertyFile o2) {
        return o1.getPropertyKey().compareTo(o2.getPropertyKey());
    }
}
