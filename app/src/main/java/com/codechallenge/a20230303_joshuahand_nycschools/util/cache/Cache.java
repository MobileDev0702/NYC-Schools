package com.codechallenge.a20230303_joshuahand_nycschools.util.cache;

public interface Cache<T> {

    boolean contains(String key);

    T get(String key);

    void put(String key, T value);

}
