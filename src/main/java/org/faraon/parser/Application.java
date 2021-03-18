package org.faraon.parser;

import org.faraon.parser.service.ParserManager;
import org.faraon.parser.service.impl.AllegroSaleParser;
import org.faraon.parser.service.impl.CsvStorage;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        ParserManager parserManager = new ParserManager(new AllegroSaleParser(), new CsvStorage());
        parserManager.process(100);
    }

}
