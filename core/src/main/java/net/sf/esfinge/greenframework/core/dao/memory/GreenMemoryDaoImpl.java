package net.sf.esfinge.greenframework.core.dao.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GreenMemoryDaoImpl<T> {

    protected final ConcurrentHashMap<String, T> storage = new ConcurrentHashMap<>();

    protected final ConcurrentHashMap<String, List<T>> listStorage = new ConcurrentHashMap<>();

    protected void addToList(String configurationKey, T config) {
        listStorage.computeIfAbsent(configurationKey, k -> new ArrayList<T>())
                .add(config);
    }

    protected List<T> get(String configurationKey) {
        return listStorage.getOrDefault(configurationKey, Collections.emptyList());
    }

}
