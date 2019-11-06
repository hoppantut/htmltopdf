package com.example.htmltopdf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.context.Context;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        Context context = new Context();
        context.setVariable("TITTEL", "Tittel");
        context.setVariable("NAVN", "Navn Navnesen");
        context.setVariable("ADDRESSE", "En eller annen gate 3, 0143 Oslo");
        //context.setVariable("CONDITIONAL", "LANG");
        context.setVariable("CONDITIONAL", "KORT");
        new HtmlToPDF().convertToPDF(context);
    }

}
