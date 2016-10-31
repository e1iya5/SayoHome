/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpstuff;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import dbstuff.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import jsstuff.EnviromentScript;
import jsstuff.LogEntryObject;
import rules.TimePattern;
import rules.TimeRule;
import sayohome.SayoHome;
import spark.Spark;
import static spark.Spark.*;

/**
 *
 * @author elias
 */
public class MainServer {

    public MainServer() {
        port(2621);
        Spark.staticFileLocation("/public");

        get("/", (request, response) -> {
            response.redirect("index.html");
            return null;
        });

        post("/api/login", (request, response) -> {
            Gson gson = new Gson();
            Map<String, String> data = gson.fromJson(request.body(), Map.class);
            String username = data.get("username");
            String pwd = data.get("pwd");
            Session s = null;
            try {
                s = SayoHome.getDbGuru().login(username, pwd);
            } catch (java.lang.IllegalArgumentException e) {
                halt(401, "wrong username or pwd!");
            }
            Map resData = new HashMap();
            resData.put("token", s.getCode());
            return gson.toJson(resData);
        });

        get("/api/timerule/all", (request, response) -> {
            ArrayList<TimeRule> rules = SayoHome.getDbGuru().getAllTimeRules();
            ArrayList<Map> result = new ArrayList<>();
            Gson gson = new Gson();
            rules.stream().map((rule) -> {
                HashMap<String, Object> ruleMap = new HashMap<>();
                ArrayList<Map> patternList = new ArrayList<>();
                rule.getPatterns().stream().map((pattern) -> {
                    HashMap<String, Integer> patternMap = new HashMap<>();
                    patternMap.put("year", pattern.getYear() < 0 ? null : pattern.getYear());
                    patternMap.put("month", pattern.getMonth() < 0 ? null : pattern.getMonth());
                    patternMap.put("dayOfMonth", pattern.getDayOfMonth() < 0 ? null : pattern.getDayOfMonth());
                    patternMap.put("dayOfWeek", pattern.getDayOfWeek() < 0 ? null : pattern.getDayOfWeek());
                    patternMap.put("hour", pattern.getHour() < 0 ? null : pattern.getHour());
                    patternMap.put("minute", pattern.getMinute() < 0 ? null : pattern.getMinute());
                    return patternMap;
                }).forEach((patternMap) -> {
                    patternList.add(patternMap);
                });
                ruleMap.put("id", rule.getId());
                ruleMap.put("name", rule.getName());
                ruleMap.put("ruleBody", rule.getCode());
                ruleMap.put("active", rule.isActive());
                ruleMap.put("timePattern", patternList);
                return ruleMap;
            }).forEach((ruleMap) -> {
                result.add(ruleMap);
            });
            return gson.toJson(result);
        });

        post("/api/timerule/:id/save", (request, response) -> {
            Gson gson = new Gson();
            Map<String, Object> data = gson.fromJson(request.body(), Map.class);
            String name = (String) data.get("name");
            String ruleBody = (String) data.get("ruleBody");
            boolean active = (boolean) data.get("active");
            ArrayList<LinkedTreeMap<String, Double>> timePattern = (ArrayList<LinkedTreeMap<String, Double>>) data.get("timePattern");
            ArrayList<TimePattern> patternList = new ArrayList<>();
            timePattern.stream().map((p) -> {
                int year = p.containsKey("year") && p.get("year") != null ? (int) p.get("year").intValue() : -1;
                int month = p.containsKey("month") && p.get("month") != null ? (int) p.get("month").intValue() : -1;
                int dayOfMonth = p.containsKey("dayOfMonth") && p.get("dayOfMonth") != null ? p.get("dayOfMonth").intValue() : -1;
                int dayOfWeek = p.containsKey("dayOfWeek") && p.get("dayOfWeek") != null ? (int) p.get("dayOfWeek").intValue() : -1;
                int hour = p.containsKey("hour") && p.get("hour") != null ? (int) p.get("hour").intValue() : -1;
                int minute = p.containsKey("minute") && p.get("minute") != null ? p.get("minute").intValue() : -1;
                TimePattern pattern = new TimePattern(year, month, dayOfMonth, dayOfWeek, hour, minute);
                return pattern;
            }).forEach((pattern) -> {
                patternList.add(pattern);
            });
            TimeRule rule = new TimeRule(name, active, patternList, ruleBody);
            rule.setId(Integer.parseInt(request.params("id")));
            SayoHome.getDbGuru().updateTimeRule(rule);
            SayoHome.refreshTimeRules();
            return "ok";
        });
        
        get("/api/log/entries", (request, response) -> {
            ArrayList<LogEntryObject> entries = SayoHome.getDbGuru().getAllLogEntries();
            ArrayList<HashMap<String, String>> results = new ArrayList<>();
            Gson gson = new Gson();
            entries.stream().map((entry) -> {
                HashMap<String, String> entryMap = new HashMap<>();
                entryMap.put("time", entry.getTime().toGMTString());
                entryMap.put("mode", entry.getEntryMode() == 3 ? "error" : (entry.getEntryMode() == 2 ? "warning" : "info"));
                entryMap.put("message", entry.getMessage());
                return entryMap;
            }).forEach((entryMap) -> {
                results.add(entryMap);
            });
            return gson.toJson(results);
        });
        
        get("/api/enviromentscripts/all", (request, response) -> {
            ArrayList<EnviromentScript> scripts = SayoHome.getDbGuru().getAllEnviromentScripts();
            Gson gson = new Gson();
            return gson.toJson(scripts);
        });
        
        get("/api/enviromentscripts/active", (request, response) -> {
            ArrayList<EnviromentScript> scripts = SayoHome.getDbGuru().getActiveEnviromentScripts();
            Gson gson = new Gson();
            return gson.toJson(scripts);
        });
    }
}
