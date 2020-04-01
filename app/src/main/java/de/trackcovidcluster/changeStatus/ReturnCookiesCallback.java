package de.trackcovidcluster.changeStatus;

import java.util.List;

import de.trackcovidcluster.models.Cookie;

public interface ReturnCookiesCallback {
    public void returnCookiesCallback(List<Cookie> sensorData);
}
