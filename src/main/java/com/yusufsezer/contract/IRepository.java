package com.yusufsezer.contract;

import java.util.Collection;
import java.util.Optional;

public interface IRepository<T, I> {

    Optional<T> get(I index);

    Collection<T> getAll();

    boolean add(T obj);

    Optional<T> update(I index, T obj);

    Optional<T> remove(I index);

}
