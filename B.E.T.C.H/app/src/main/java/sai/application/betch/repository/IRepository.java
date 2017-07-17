package sai.application.betch.repository;


import java.util.List;

import io.reactivex.Observable;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/16/17.
 */

public interface IRepository {

    /**
     * This method is used to load data from the network
     */
    Observable<List<CryptoCurrency>> loadDataFromNetwork();

    /**
     * This method is used to load data from the memory
     */
    Observable<List<CryptoCurrency>>  loadDataFromMemory();


    /**
     * This method is used by the model to load data.
     * This method will internally decide whether to load from memory or network
     */
    Observable<List<CryptoCurrency>>  loadData();
}
