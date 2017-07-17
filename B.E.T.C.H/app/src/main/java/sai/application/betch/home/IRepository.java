package sai.application.betch.home;

/**
 * Created by sai on 7/16/17.
 */

public interface IRepository {

    /**
     * This method is used to load data from the network
     */
    void loadDataFromNetwork();

    /**
     * This method is used to load data from the memory
     */
    void loadDataFromMemory();


    /**
     * This method is used by the model to load data.
     * This method will internally decide whether to load from memory or network
     */
    void loadData();
}
