package org.faraon.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Good {
    private String name;
    private String price;
    private String salePrice;
    private String sale;
    private String url;

}
