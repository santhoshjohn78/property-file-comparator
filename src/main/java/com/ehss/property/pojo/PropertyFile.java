package com.ehss.property.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.List;

@Entity
@IdClass(PropertyFileId.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyFile {

    @Id
    @Column
    private String fileName;

    @Id
    @Column
    private String propertyKey;

    @Column
    private String propertyValue;
}
