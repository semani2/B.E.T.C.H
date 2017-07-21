package sai.application.betch.cache.cachemodel;

import com.orm.SugarRecord;

import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/21/17.
 */

public class Currency extends SugarRecord<Currency> {
    private String currencyId;
    private String name;
    private String symbol;
    private String rank;
    private String priceUsd;
    private String priceBtc;
    private String _24hVolumeUsd;
    private String marketCapUsd;
    private String availableSupply;
    private String totalSupply;
    private String percentChange1h;
    private String percentChange24h;
    private String percentChange7d;
    private String lastUpdated;

    public Currency() {

    }

    public Currency(CryptoCurrency currency) {
        this.setCurrencyId(currency.getId());
        this.setName(currency.getName());
        this.setSymbol(currency.getSymbol());
        this.setRank(currency.getRank());
        this.setPriceUsd(currency.getPriceUsd());
        this.setPriceBtc(currency.getPriceBtc());
        this.set24hVolumeUsd(currency.get24hVolumeUsd());
        this.setMarketCapUsd(currency.getMarketCapUsd());
        this.setAvailableSupply(currency.getAvailableSupply());
        this.setTotalSupply(currency.getTotalSupply());
        this.setPercentChange1h(currency.getPercentChange1h());
        this.setPercentChange7d(currency.getPercentChange7d());
        this.setPercentChange24h(currency.getPercentChange24h());
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String id) {
        this.currencyId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(String priceBtc) {
        this.priceBtc = priceBtc;
    }

    public String get24hVolumeUsd() {
        return _24hVolumeUsd;
    }

    public void set24hVolumeUsd(String _24hVolumeUsd) {
        this._24hVolumeUsd = _24hVolumeUsd;
    }

    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(String marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public String getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(String availableSupply) {
        this.availableSupply = availableSupply;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(String percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(String percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(String percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
