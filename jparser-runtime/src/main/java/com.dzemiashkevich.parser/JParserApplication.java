package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.action.ClickAction;
import com.dzemiashkevich.parser.action.PaginationAction;
import com.dzemiashkevich.parser.action.TakeAction;
import com.dzemiashkevich.parser.builder.RuleBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
        RuleBuilder.class,
        ClickAction.class,
        TakeAction.class,
        PaginationAction.class,
        DStore.class,
        RStore.class,
        JParserController.class
})
public class JParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(JParserApplication.class, args);
    }

}
