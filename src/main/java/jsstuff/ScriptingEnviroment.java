/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsstuff;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import rules.TimeRule;

/**
 *
 * @author elias
 */
public class ScriptingEnviroment {

    /* Context cx;
    Scriptable scope;

    public ScriptingEnviroment() {
        cx = Context.enter();
        scope = cx.initStandardObjects();
        try {
            ScriptableObject.defineClass(scope, SayoHomeObject.class);
            ScriptableObject.defineClass(scope, HttpGetRequestObject.class);
            ScriptableObject.defineClass(scope, LogEntryObject.class);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            Logger.getLogger(TimeRule.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scriptable sayohome = cx.newObject(scope, "sayohome");
        scope.put("sayohome", scope, sayohome);
        
        this.runScript("var LogMode = {'INFO': 1, 'WARNING': 2, 'ERROR': 3};");
    }

    public void runScript(String script) {
        this.cx.evaluateString(this.scope, script, "<cmd>", 1, null);
    }*/
    private ScriptEngine engine;

    public ScriptingEnviroment() {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
        
        this.runScript("var sayohome = Java.type('jsstuff.SayoHomeObject');");
        this.runScript("var HttpGet = Java.type('jsstuff.HttpGetRequestObject');");
        this.runScript("var LogEntry = Java.type('jsstuff.LogEntryObject');");
    }
    
     public void runScript(String script) {
        try {
            this.engine.eval(script);
        } catch (ScriptException ex) {
            // TODO: Print script error to SayoHome logs
            Logger.getLogger(ScriptingEnviroment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
