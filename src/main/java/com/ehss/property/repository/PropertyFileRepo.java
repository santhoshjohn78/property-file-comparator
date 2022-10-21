package com.ehss.property.repository;

import com.ehss.property.pojo.PropertyFile;
import com.ehss.property.pojo.PropertyFileId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyFileRepo extends CrudRepository<PropertyFile, PropertyFileId> {

    List<PropertyFile> findByFileName(String name);

    PropertyFile findByFileNameAndPropertyKey(String fileName,String propertyKey);
}
