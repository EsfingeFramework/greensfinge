package br.com.ita.greenframework.dao.memory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GreenMemoryDao<T> {

    protected final ConcurrentHashMap<String, T> storage = new ConcurrentHashMap<>();

    protected final ConcurrentHashMap<String, List<T>> listStorage = new ConcurrentHashMap<>();

}
