import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Application {

    public static void main(String[] args) throws Exception {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");

        engine.eval("print(\"test\")");
    }
}
