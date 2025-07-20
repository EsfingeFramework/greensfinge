package net.sf.esfinge.greenframework.core.dao.memory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GreenMemoryDaoImpl<T> {

    protected final ConcurrentHashMap<String, T> storage = new ConcurrentHashMap<>();

    protected final ConcurrentHashMap<String, List<T>> listStorage = new ConcurrentHashMap<>();

}
