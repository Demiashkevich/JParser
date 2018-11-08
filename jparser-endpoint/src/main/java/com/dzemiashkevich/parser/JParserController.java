package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.api.Selector;
import com.dzemiashkevich.parser.api.StandardRule;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JParserController {

    private final ResourceManager manager;

    @Autowired
    public JParserController(ResourceManager manager) {
        this.manager = manager;
    }

    @PostConstruct
    public void init() {
        List<StandardRule> rules1 = new ArrayList<>();

        HashMap<Selector, String> pattern1 = new HashMap<>();
        pattern1.put(Selector.OUTER, "#le_menu_ul > ul > li > a");
        pattern1.put(Selector.HREF, "href");

        StandardRule standardRule1 = new StandardRule();
        standardRule1.setSource("http://www.medknigaservis.ru");
        standardRule1.setActionType("click");
        standardRule1.setPattern(pattern1);

        HashMap<Selector, String> pattern2 = new HashMap<>();
        pattern2.put(Selector.OUTER, "#content > div > div > div:nth-child(2) > div > div:nth-child(2) > a");
        pattern2.put(Selector.HREF, "href");

        StandardRule standardRule2 = new StandardRule();
        standardRule2.setSource("http://www.medknigaservis.ru/catalogue/med_spec.html");
        standardRule2.setActionType("click");
        standardRule2.setPattern(pattern2);

        HashMap<Selector, String> pattern3 = new HashMap<>();
        pattern3.put(Selector.OUTER, "#sres-navigator-top");
        pattern3.put(Selector.PAGINATION_FROM, "#a-goto-1");
        pattern3.put(Selector.PAGINATION_TO, "#a-goto-5");
        pattern3.put(Selector.PAGINATION_CONTEXT, "div > div > a");
        pattern3.put(Selector.HREF, "href");

        StandardRule standardRule3 = new StandardRule();
        standardRule3.setSource("http://www.medknigaservis.ru/catalogue/med_spec/0001.html");
        standardRule3.setActionType("pagination");
        standardRule3.setPattern(pattern3);

        HashMap<Selector, String> pattern4 = new HashMap<>();
        pattern4.put(Selector.OUTER, "#content > div > div > div > div > div > div > div > table > tbody");

        Map<String, Pair<String, String>> param = new HashMap<>();
        param.put("name", Pair.of("tr > td.desc > div > a span", null));

        StandardRule standardRule4 = new StandardRule();
        standardRule4.setSource("http://www.medknigaservis.ru/catalogue/med_spec/0001/-esf2k2z11-year-dec-page-2.html");
        standardRule4.setActionType("take");
        standardRule4.setParam(param);
        standardRule4.setPattern(pattern4);
        standardRule4.setName("First file");

        rules1.add(standardRule1);
        rules1.add(standardRule2);
        rules1.add(standardRule3);
        rules1.add(standardRule4);

        manager.process(rules1);

        List<StandardRule> rules2 = new ArrayList<>();

        HashMap<Selector, String> pattern2_1 = new HashMap<>();
        pattern2_1.put(Selector.OUTER, "#le_menu_ul-14 > ul > li > a");
        pattern2_1.put(Selector.HREF, "href");

        StandardRule standardRule2_1 = new StandardRule();
        standardRule2_1.setSource("http://www.medknigaservis.ru");
        standardRule2_1.setActionType("click");
        standardRule2_1.setPattern(pattern2_1);

        HashMap<Selector, String> pattern2_4 = new HashMap<>();
        pattern2_4.put(Selector.OUTER, "#content > div > div > div > div > div > div > div > table > tbody");

        Map<String, Pair<String, String>> param2 = new HashMap<>();
        param2.put("name", Pair.of("tr > td.desc > div > a span", null));

        StandardRule standardRule2_4 = new StandardRule();
        standardRule2_4.setSource("http://www.medknigaservis.ru/catalogue/med_spec/0001/-esf2k2z11-year-dec-page-2.html");
        standardRule2_4.setActionType("take");
        standardRule2_4.setParam(param2);
        standardRule2_4.setPattern(pattern2_4);
        standardRule2_4.setName("Second file");

        rules2.add(standardRule2_1);
        rules2.add(standardRule2);
        rules2.add(standardRule3);
        rules2.add(standardRule2_4);

        manager.process(rules2);
    }

}
