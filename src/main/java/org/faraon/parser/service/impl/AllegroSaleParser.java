package org.faraon.parser.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.faraon.parser.model.Good;
import org.faraon.parser.service.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllegroSaleParser implements Parser {

    @Override
    public List<Good> parse(String baseUrl, int goodsAmount) {
        List<Good> goods = new ArrayList<>(goodsAmount);
        Document doc;
        String url = "";
        int pageNum = 1;

        try {
            do {
                url = baseUrl + "&p=" + pageNum++;
                doc = getDocument(url);
                List<Good> docGoods = parseAllGoods(doc);
                int amountToAdd = goods.size() + docGoods.size() <= goodsAmount ?
                        docGoods.size() :
                        goodsAmount - goods.size();
                goods.addAll(docGoods.subList(0, amountToAdd));
                if (!hasNextPage(doc)) {
                    break;
                }
            } while (goods.size() < goodsAmount);
        } catch (IOException ex) {
            System.err.println("Error during process url " + url);
            ex.printStackTrace();
        }
        return goods;
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup
                .connect(url)
                .timeout(5 * 1000)
                .get();
    }

    private List<Good> parseAllGoods(Document doc) {
        List<Good> goods = new ArrayList<>();

        Element goodsContainer = doc.select("div.opbox-listing > div > section").get(1);
        for (Element parsedGood : goodsContainer.getElementsByClass("mx7m_1 mnyp_co mlkp_ag")) {
            Element goodDescription = parsedGood.getElementsByClass("mpof_ki myre_zn _9c44d_1Hxbq").first();

            String salePercentageOriginal = goodDescription.getElementsByClass("_9c44d_1uHr2").text();
            if (salePercentageOriginal.startsWith("-")) {
                String salePercentage = salePercentageOriginal.substring(0, salePercentageOriginal.indexOf("%") + 1);
                Element title = goodDescription.getElementsByClass("mgn2_14 m9qz_yp mqu1_16 mp4t_0 m3h2_0 mryx_0 munh_0").first();
                String name = title.select("h2").text();
                String url = title.select("a").attr("href");
                String price = goodDescription.getElementsByClass("_9c44d_3AMmE").text();
                String salePrice = goodDescription.getElementsByClass("mpof_uk mqu1_ae _9c44d_18kEF m9qz_yp _9c44d_2BSa0  _9c44d_KrRuv").text();

                goods.add(new Good(name, price, salePrice, salePercentage, url));
            }
        }
        return goods;
    }

    private boolean hasNextPage(Document doc) {
        Element nextLink = doc.getElementsByClass("_lsy4e _1y3c2 _3db39_mcaVQ _ls4gg").first();
        return nextLink != null;
    }

}
