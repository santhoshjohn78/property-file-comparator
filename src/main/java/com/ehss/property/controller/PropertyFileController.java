package com.ehss.property.controller;

import com.ehss.property.exceptions.PropertyFileNotFoundException;
import com.ehss.property.pojo.ComparedProperties;
import com.ehss.property.pojo.PropertyFile;
import com.ehss.property.service.PropertyFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/property-file")
@RestController
@Slf4j
public class PropertyFileController {

    @Autowired
    PropertyFileService propertyFileService;

    @PostMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PropertyFile>> createPropertyFile(@PathVariable String name, @RequestBody String payload) throws Exception {
            return ResponseEntity.ok().body(propertyFileService.createPropertyFileFromString(name,payload));
    }
    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PropertyFile>> getPropertyFile(@PathVariable String name) throws PropertyFileNotFoundException {
        return ResponseEntity.ok().body(propertyFileService.getPropertyFile(name));
    }

    @GetMapping(value = "/compare", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ComparedProperties>> compareProperties(@RequestParam(required = true) String leftFileName,
                                                                      @RequestParam(required = true) String rightFileName) throws Exception{
        return ResponseEntity.ok().body(propertyFileService.compareProperties(leftFileName,rightFileName));
    }

    @PostMapping(value = "/merge/{side}")
    public ResponseEntity<String> mergeProperties(@RequestParam(required = true) String leftFileName,
                                                                      @RequestParam(required = true) String rightFileName,
                                                                    @PathVariable String side) throws Exception{
        return ResponseEntity.ok().body(propertyFileService.mergeProperties(leftFileName,rightFileName,side));
    }


}
