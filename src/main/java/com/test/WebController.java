package com.test;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.script.ScriptEngineManager;
import java.io.*;

@Controller
public class WebController {
    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) throws Exception {
        model.addAttribute("name", renderRegularJS());
        model.addAttribute("serverSideReact", renderReact());

        return "hello";
    }

    public String renderRegularJS() throws Exception{
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine)scriptEngineManager.getEngineByName("nashorn");

        engine.eval(getReaderForResource("static/js/namegenerator.js"));

        return engine.invokeFunction("getNameJS").toString();
    }

    public String renderReact() throws Exception{
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) scriptEngineManager.getEngineByName("nashorn");

        engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.2/react.js')");
        engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.2/react-dom-server.js')");

        return engine.eval("ReactDOMServer.renderToString(React.createElement('div', null, 'Hello Server Side ReactJS!'));").toString();
    }

    private Reader getReaderForResource(String path) {
        return new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path));
    }

}