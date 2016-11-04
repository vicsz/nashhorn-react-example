package com.test;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.script.ScriptEngineManager;
import java.io.InputStreamReader;
import java.io.Reader;

@Controller
public class WebController {
    @RequestMapping("/hello")
    public String hello(Model model) throws Exception {
        model.addAttribute("serverSideReact", renderReact());
        return "hello";
    }

    public String renderReact() throws Exception{
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) scriptEngineManager.getEngineByName("nashorn");

        engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.2/react.js')");
        engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.2/react-dom-server.js')");
        engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.2/react-dom.js')");
        engine.eval(getReaderForResource("static/js/component.js"));

        return engine.eval("ReactDOMServer.renderToString(React.createElement(HelloMessage));").toString();
    }

    private Reader getReaderForResource(String path) {
        return new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path));
    }

}