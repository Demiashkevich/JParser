package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.Resource;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Select;
import com.dzemiashkevich.parser.helper.DocumentLoader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TakeAction implements Action {

    private static final String EMPTY = "";

    private final DocumentLoader loader;

    @Autowired
    public TakeAction(DocumentLoader loader) {
        this.loader = loader;
    }

    @Override
    public List<Resource> doAction(Deque<Rule> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return Collections.emptyList();
        }

        Rule rule = rules.removeFirst();

        List<Resource> allResources = new ArrayList<>();

        List<String> source = rule.getSource();
        for (String res : source) {
            Document document = loader.load(res);

            if (document == null) {
                continue;
            }

            List<Resource> resources = parseDocumentByParamAndPattern(document, rule.getPattern(), rule.getParam());

            if (CollectionUtils.isEmpty(resources)) {
                continue;
            }

            allResources.addAll(resources);
        }

        return allResources;
    }

    private List<Resource> parseDocumentByParamAndPattern(Document document, Map<Select, String> pattern, Map<String, String> param) {
        Elements context = getContextFromDocument(document, pattern);

        if (context == null || context.isEmpty()) {
            return Collections.emptyList();
        }

        List<Resource> resources = new ArrayList<>();
        for (Element _context : context) {
            Resource res = getResourceFromElement(_context, param);
            if (!res.isEmpty()) {
                resources.add(res);
            }
        }

        return resources;
    }

    private Resource getResourceFromElement(Element element, Map<String, String> param) {
        Resource resource = new Resource();

        Map<String, String> params = new HashMap<>();

        for (Map.Entry<String, String> _param : param.entrySet()) {
            String _key = _param.getKey();
            String _value = _param.getValue();

            Elements elements = element.select(_value);

            if (elements == null || elements.isEmpty()) {
                params.put(_key, EMPTY);
                continue;
            }

            _value = elements.first().text();

            params.put(_key, _value);
        }

        resource.setParams(params);

        return resource;
    }

    private Elements getContextFromDocument(Document document, Map<Select, String> pattern) {
        return document.select(pattern.get(Select.OUTER));
    }

}
