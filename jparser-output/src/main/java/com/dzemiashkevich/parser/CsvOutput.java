package com.dzemiashkevich.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Component
public class CsvOutput extends AbstractOutput {

    private static final String RESOURCE_PATH = "resource.csv";

    private String[] header;

    @Override
    public void write(List<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(RESOURCE_PATH))) {
            header = buildHeader(resources.get(0));
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));

            for (Resource resource : resources) {
                Object[] values = buildParamAsArray(resource);

                if (ArrayUtils.isEmpty(values)) {
                    continue;
                }

                printer.printRecord(values);
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to write to the file by path: " + RESOURCE_PATH);
        }
    }

    private Object[] buildParamAsArray(Resource resource) {
        Map<String, String> params = resource.getParams();

        if (CollectionUtils.isEmpty(params)) {
            return null;
        }

        Object[] values = new Object[header.length];

        for (int i = 0; i < header.length; i++) {
            values[i] = params.get(header[i]);
        }

        return values;
    }

    private String[] buildHeader(Resource resource) {
        return resource.getParams().keySet().toArray(new String[0]);
    }

}
