package com.yusufsezer.repository;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.model.BaseModel;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ObjectRepository<T extends BaseModel<Integer>, I extends Integer> implements IRepository<T, I> {

    private final Map<I, T> items;
    private I COUNTER;

    public ObjectRepository(Map<I, T> items) {
        this.items = items;
        COUNTER = (I) Integer.valueOf(0);
    }

    @Override
    public Optional<T> get(I index) {
        if (this.items.containsKey(index)) {
            return Optional.of(this.items.get(index));
        }

        return Optional.empty();
    }

    @Override
    public Collection<T> getAll() {
        return this.items.values();
    }

    @Override
    public boolean add(T obj) {
        obj.setId(++COUNTER);
        return null != this.items.put((I) obj.getId(), obj);
    }

    @Override
    public Optional<T> update(I index, T item) {
        if (this.items.containsKey(index)) {
            this.items.put(index, item);
            return Optional.of(item);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> remove(I index) {
        return Optional.ofNullable(this.items.remove(index));
    }

}
