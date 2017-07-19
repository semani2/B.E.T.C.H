package sai.application.betch.cache.cachemodel;

import com.orm.SugarRecord;

/**
 * Created by sai on 7/18/17.
 */

public class Alert extends SugarRecord<Alert> {

    String guid;
    String currencyId;
    String currencyName;
    String currencySymbol;
    String createdDate;
    boolean isActive;
    boolean isTimeTrigger;
    boolean isLessThan;
    double triggerUnit;

    public Alert() {}

    public Alert(String guid) {
        this.guid = guid;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setTimeTrigger(boolean timeTrigger) {
        isTimeTrigger = timeTrigger;
    }

    public void setLessThan(boolean lessThan) {
        isLessThan = lessThan;
    }

    public void setTriggerUnit(double triggerUnit) {
        this.triggerUnit = triggerUnit;
    }


}
