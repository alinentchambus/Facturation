package com.examen.facturation.service;

import com.examen.facturation.domaine.entities.Command;
import com.examen.facturation.domaine.repositories.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandService {

    private final CommandRepository commandRepository;
    private final PdfGeneratorService pdfGeneratorService;

    private static Command csvRecordToCommand(final CSVRecord csvRecord) {
        return Command.builder().clientId(Integer.parseInt(csvRecord.get("client"))).date(LocalDate.parse(csvRecord.get("date"))).article(csvRecord.get("article")).price(new BigDecimal(csvRecord.get("price"))).Quantity(Long.parseLong(csvRecord.get("quantity"))).build();
    }

    public Resource generateBill(final Integer clientId, final LocalDate date) {

        var optionalCommand = commandRepository.findByClientIdAndAndDate(clientId, date);

        return !optionalCommand.isEmpty() ? pdfGeneratorService.genarateBill(optionalCommand.get()) : null;
    }

    public void processCsvFile(final MultipartFile file) throws IOException {

        String [] FILE_HEADER_MAPPING = {"client","date","article","price","quantity"};
        var fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
        var csvParser = new CSVParser(fileReader, CSVFormat.Builder.create(CSVFormat.DEFAULT).setHeader(FILE_HEADER_MAPPING).build());
        var csvRecords = csvParser.getRecords();

        var commands =
                csvRecords
                        .stream()
                        .skip(1)
                        .map(CommandService::csvRecordToCommand)
                        .collect(Collectors.toList());

        commandRepository.saveAll(commands);
    }
}
