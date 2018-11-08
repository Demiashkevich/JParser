package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClickAction extends JumpAction {

    private static final String URL_SEPARATOR = "/";

    @Override
    protected List<String> parseContextBySelect(Document document, Map<Selector, String> select) {
        List<String> href = new ArrayList<String>();

        Elements context = document.select(select.get(Selector.OUTER));
        for (Element element : context) {
            String _href = parseHref(element, select);

            if (StringUtils.isEmpty(_href)) {
                continue;
            }

            href.add(_href);
        }

        return href;
    }

    @Override
    public void prepareRuleToProcessing(Rule prev, Rule next, List<String> href) {
        List<String> _next = next.getSource();
        List<String> _prev = prev.getSource();

        href = prepareHrefToProcessing(href, _next.get(0), _prev.get(0));

        next.getSource().addAll(href);
        next.setSource(removeRepeatable(next.getSource()));
    }

    private String parseHref(Element element, Map<Selector, String> select) {
        String _inner = select.get(Selector.INNER);
        if (!StringUtils.isEmpty(_inner)) {
            element = element.select(_inner).first();
        }
        return element.attr(select.get(Selector.HREF));
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

    private String common(String href1, String href2) {
        List<String> _href1 = Arrays.asList(href1.trim().split(URL_SEPARATOR));
        List<String> _href2 = Arrays.asList(href2.trim().split(URL_SEPARATOR));

        int minSize = _href1.size() < _href2.size() ? _href1.size() : _href2.size();

        return IntStream.range(0, minSize)
                .filter(i -> _href1.get(i).equals(_href2.get(i)))
                .mapToObj(_href2::get)
                .collect(Collectors.joining(URL_SEPARATOR));

    }

}
