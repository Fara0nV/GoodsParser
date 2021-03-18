package org.faraon.parser.service;

import org.faraon.parser.model.Good;

import java.io.IOException;
import java.util.*;

public class ParserManager {
    private final Parser parser;
    private final Storage storage;

    public ParserManager(Parser parser, Storage storage) {
        this.parser = parser;
        this.storage = storage;
    }

    public void process(int goodsAmount) throws IOException {
        Map<String, String> categories = new HashMap<String, String>() {{
            put("Smartphones", "https://allegro.pl/kategoria/smartfony-i-telefony-komorkowe-165?ok=1");
            put("Consoles", "https://allegro.pl/kategoria/konsole-i-automaty?ok=1");
            put("TV and video", "https://allegro.pl/kategoria/tv-i-video-717?ok=1");
        }};
        Map<String, List<Good>> parsedGoods = new HashMap<>(goodsAmount * categories.size());
        for (Map.Entry<String, String> entry : categories.entrySet()) {
            parsedGoods.put(entry.getKey(), parser.parse(entry.getValue(), goodsAmount));
        }
        storage.store(parsedGoods);
        print(parsedGoods);
    }

    private void print(Map<String, List<Good>> goods) {
        int counter = 1;
        for (Map.Entry<String, List<Good>> entry : goods.entrySet()) {
            for (Good good : entry.getValue()) {
                System.out.println(counter++ + ". category:" + entry.getKey() + "\t" + good);
            }
        }
    }

}
