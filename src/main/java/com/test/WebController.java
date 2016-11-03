package com.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

@Controller
public class WebController {
    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) throws Exception {
        model.addAttribute("name", getName());
        return "hello";
    }


    public String getName() throws Exception{
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

        engine.eval(new FileReader("src/main/resources/NameGenerator.js"));

        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("getNameJS");

        return result.toString();
    }
}