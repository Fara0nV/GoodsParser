package org.faraon.parser.service;

import org.faraon.parser.model.Good;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Storage {
    void store(Map<String, List<Good>> goods) throws IOException;
}
