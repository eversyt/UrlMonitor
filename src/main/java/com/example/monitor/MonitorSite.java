package com.example.monitor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.TimerTask;

public class MonitorSite extends TimerTask {

    private static StateRepo stateRepo;
    private URL url;

    public MonitorSite(URL url, StateRepo stateRepo) {
        this.url = url;
        this.stateRepo = stateRepo;
    }

    @Override
    public void run() {
        int response = 0;
        HttpURLConnection monitor1;
        try {
            monitor1 = (HttpURLConnection) url.openConnection();
            response = monitor1.getResponseCode();
            monitor1.disconnect();
        } catch (IOException e) {
            response = -1;
        }

        State state = new State(LocalDateTime.now(), url.getHost(), response);
        stateRepo.save(state);
    }
}
