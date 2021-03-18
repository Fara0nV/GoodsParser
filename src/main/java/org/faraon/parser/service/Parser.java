package org.faraon.parser.service;

import org.faraon.parser.model.Good;

import java.util.List;

public interface Parser {
    List<Good> parse(String baseUrl, int goodsAmount);
}
