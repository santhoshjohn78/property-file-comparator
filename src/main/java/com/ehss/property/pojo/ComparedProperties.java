package com.ehss.property.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComparedProperties {

    String propertyKey;

    PropertyFile leftPropertyFileEntry;

    PropertyFile rightPropertyFileEntry;

    PropertyEntryStatus propertyEntryStatus;
}
