package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import com.dzemiashkevich.parser.api.StandardRule;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class JParserController {

    private final ResourceManager manager;

    @Autowired
    public JParserController(ResourceManager manager) {
        this.manager = manager;
    }

    @PostConstruct
    public void init() {
        List<StandardRule> rules = new ArrayList<>();

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

        rules.add(standardRule1);
        rules.add(standardRule2);
        rules.add(standardRule3);
        rules.add(standardRule4);

        manager.process(rules);
    }

}
