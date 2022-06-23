package com.examen.facturation.api.v1.controller;

import com.examen.facturation.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.BillingApi;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class Facturation implements BillingApi {

    private final CommandService commandService;

    @Override
    public ResponseEntity<Resource> downloadBill(Integer clientId, LocalDate date) {

        var file = commandService.generateBill(clientId, date);

        return Objects.nonNull(file) ?
                ResponseEntity.ok(file) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Void> processBilling(MultipartFile fileName) {

        try {
            commandService.processCsvFile(fileName);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
