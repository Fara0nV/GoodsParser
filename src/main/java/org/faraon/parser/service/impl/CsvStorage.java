package org.faraon.parser.service.impl;

import com.opencsv.CSVWriter;
import org.faraon.parser.model.Good;
import org.faraon.parser.service.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CsvStorage implements Storage {

    @Override
    public void store(Map<String, List<Good>> goods) throws IOException {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        String destinationFile = projectPath + File.separator + buildTimePrefix() + "-allegro.csv" ;

        List<String[]> csvData = createCsvDataSimple(goods);

        try (CSVWriter writer = new CSVWriter(new FileWriter(destinationFile))) {
            writer.writeAll(csvData);
        }

    }

    private List<String[]> createCsvDataSimple(Map<String, List<Good>> goods) {
        List<String[]> csvDataSimple = new ArrayList<>();
        String[] header = {"id", "category", "name", "price", "salePrice", "sale", "url"};
        csvDataSimple.add(header);

        int counter = 1;
        for (Map.Entry<String, List<Good>> entry : goods.entrySet()) {
            for (Good good : entry.getValue()) {
                csvDataSimple.add(new String[]{String.valueOf(counter++),
                        entry.getKey(),
                        good.getName(),
                        good.getPrice(),
                        good.getSalePrice(),
                        good.getSale(),
                        good.getUrl()});
            }
        }
        return csvDataSimple;
    }

    private String buildTimePrefix() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        return formatter.format(date);
    }

}
