package com.warrior.util.service;

import java.util.List;

public interface WarriorBaseService<T> {

    T insert(T t);

    T modified(T t);

    boolean delete(long id);

    T get(long id);

    List<T> getList();

}