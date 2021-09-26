package com.example.monitor;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MonitorController {

    @Autowired
    private StateRepo stateRepo;

    private static List<Timer> timerList = new ArrayList();

    @PostMapping("add")
    public String add(@RequestParam String name, Map<String, Object> model) throws InterruptedException {
        URL url = getUrl(name);

        // add timer for monitoring the added url
        if (Objects.nonNull(url)) {
            Timer t = new Timer();
            MonitorSite monitor = new MonitorSite(url, stateRepo);
            t.scheduleAtFixedRate(monitor, 0, 10000);
            timerList.add(t);
        } else {
            State state = new State(LocalDateTime.now(), "wrong url", 0);
            stateRepo.save(state);
        }

        // wait till the site will be checked and the state will be saved in DB
        Thread.sleep(2000);
        updateModel(model);
        return "monitor";
    }

    @GetMapping
    public String monitor(Map<String, Object> model) {
        updateModel(model);
        return "monitor";
    }

    @PostMapping("delete")
    public String delete(Map<String, Object> model) {
        stateRepo.deleteAll();
        updateModel(model);
        return "monitor";
    }

    @PostMapping("refresh")
    public String refresh(Map<String, Object> model) {
        updateModel(model);
        return "monitor";
    }

    @PostMapping("stop")
    public String stopTimers(Map<String, Object> model) {
        timerList.stream().forEach(timer -> timer.cancel());
        updateModel(model);
        return "monitor";
    }

    private void updateModel(Map<String, Object> model) {
        Iterable<State> states = stateRepo.findAllByOrderByDataDesc();
        model.put("states", states);
    }

    private URL getUrl(String url) {
        url = url.trim();
        url = url.replaceAll("^http://", "https://");
        url = url.replaceAll("^ftp://", "ftps://");
        if (!url.startsWith("https://") && !url.startsWith("ftps://")) {
            url = "https://" + url;
        }

        if (url.contains(" ")) {
            return null;
        }

        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
