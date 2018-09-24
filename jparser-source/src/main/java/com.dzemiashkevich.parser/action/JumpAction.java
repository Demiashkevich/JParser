package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.api.Select;
import com.dzemiashkevich.parser.helper.DocumentLoader;
import com.dzemiashkevich.parser.Resource;
import com.dzemiashkevich.parser.api.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class JumpAction implements Action {

    private static final String URL_SEPARATOR = "/";

    private final DocumentLoader loader;

    @Autowired
    public JumpAction(DocumentLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<Resource> doAction(Deque<Rule> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return Collections.emptyList();
        }

        Rule rule = rules.removeFirst();

        List<String> source = rule.getSource();
        for (String res : source) {
            Document document = loader.load(res);

            if (document == null) {
                continue;
            }

            List<String> href = this.parseContextBySelect(document, rule.getPattern());
            this.prepareRuleToProcessing(rule, rules.getFirst(), href);
        }

        return rules.getFirst().getAction().doAction(rules);
    }

    private void prepareRuleToProcessing(Rule prev, Rule next, List<String> href) {
        List<String> _next = next.getSource();
        List<String> _prev = prev.getSource();

        href = prepareHrefToProcessing(href, _next.get(0), _prev.get(0));

        href = removeRepeatable(href);
        next.getSource().addAll(href);
    }

    private List<String> removeRepeatable(List<String> source) {
        return source.stream().distinct().collect(Collectors.toList());
    }

    private List<String> parseContextBySelect(Document document, Map<Select, String> select) {
        List<String> href = new ArrayList<String>();

        Elements elements = document.select(select.get(Select.OUTER));
        for (Element element : elements) {
            String _href = parseHref(element, select);

            if (StringUtils.isEmpty(_href)) {
                continue;
            }

            href.add(_href);
        }

        return href;
    }

    private String parseHref(Element element, Map<Select, String> select) {
        String _inner = select.get(Select.INNER);
        if (!StringUtils.isEmpty(_inner)) {
            element = element.select(_inner).first();
        }
        return element.attr(select.get(Select.HREF));
    }

    private String common(String href1, String href2) {
        List<String> _href1 = Arrays.asList(href1.trim().split(URL_SEPARATOR));
        List<String> _href2 = Arrays.asList(href2.trim().split(URL_SEPARATOR));

        int minSize = _href1.size() < _href2.size() ? _href1.size() : _href2.size();

        return IntStream.range(0, minSize)
                .filter(i -> _href1.get(i).equals(_href2.get(i)))
                .mapToObj(_href2::get)
                .collect(Collectors.joining(URL_SEPARATOR));

    }

    private List<String> prepareHrefToProcessing(List<String> href, String _res1, String _res2) {
        String common = common(_res1, _res2);

        return href.stream()
                .filter(_href -> !StringUtils.isEmpty(_href))
                .map(_href -> {
                    if (_href.startsWith(common)) {
                        return _href;
                    }
                    if (!_href.startsWith(URL_SEPARATOR)) {
                        return common + URL_SEPARATOR + _href;
                    }
                    return common + _href;
                })
                .collect(Collectors.toList());
    }

}
