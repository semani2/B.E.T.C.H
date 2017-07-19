package sai.application.betch.alerts.create_alert;

/**
 * Created by sai on 7/19/17.
 */

public class AlertTime {

    private String timeString;

    private double timeValue;

    public AlertTime(String timeString, double timeValue) {
        this.timeString = timeString;
        this.timeValue = timeValue;
    }

    public String getTimeString() {
        return timeString;
    }

    public double getTimeValue() {
        return timeValue;
    }

    @Override
    public String toString() {
        return timeString;
    }
}
