package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.action.JumpAction;
import com.dzemiashkevich.parser.action.TakeAction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = { RuleBuilder.class, JumpAction.class, TakeAction.class})
public class JParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(JParserApplication.class, args);
    }

}
