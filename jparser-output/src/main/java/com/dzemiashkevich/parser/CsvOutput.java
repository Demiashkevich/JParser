package com.dzemiashkevich.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CsvOutput extends AbstractOutput {

    private static final String RESOURCE_PATH = "/resource.csv";

    @Override
    public void write(List<Resource> resources) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(RESOURCE_PATH))) {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header(resources.get(0))));

        } catch (IOException e) {
            throw new RuntimeException("Unable to write to the file by path: " + RESOURCE_PATH);
        }
    }

    private String[] header(Resource resource) {
        return (String[]) resource.getParams().keySet().toArray();
    }

}
