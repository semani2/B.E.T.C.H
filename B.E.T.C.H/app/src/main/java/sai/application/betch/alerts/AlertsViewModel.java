package sai.application.betch.alerts;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsViewModel {
    private String guid;
    private String currencySymbol;
    private String currencyName;
    private boolean isActive;
    private String date;
    private boolean isTimeTrigger;
    private boolean isPriceTrigger;
    private boolean isLessThan;
    private double triggerUnit;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isTimeTrigger() {
        return isTimeTrigger;
    }

    public void setTimeTrigger(boolean timeTrigger) {
        isTimeTrigger = timeTrigger;
    }

    public boolean isPriceTrigger() {
        return isPriceTrigger;
    }

    public void setPriceTrigger(boolean priceTrigger) {
        isPriceTrigger = priceTrigger;
    }

    public boolean isLessThan() {
        return isLessThan;
    }

    public void setLessThan(boolean lessThan) {
        isLessThan = lessThan;
    }

    public double getTriggerUnit() {
        return triggerUnit;
    }

    public void setTriggerUnit(double triggerUnit) {
        this.triggerUnit = triggerUnit;
    }

    public String getAlertTitle() {
        return null;
    }

    public String getAlertCreatedDate() {
        return null;
    }

    public boolean isAlertActive() {
        return true;
    }

}
