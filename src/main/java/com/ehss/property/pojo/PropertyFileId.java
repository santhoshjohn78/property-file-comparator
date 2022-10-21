package com.ehss.property.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyFileId implements Serializable {

    private String fileName;
    private String propertyKey;
}
