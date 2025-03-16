package net.sf.esfinge.greenframework.dao;

import net.sf.esfinge.greenframework.dao.memory.GreenConfigurationDaoImpl;
import net.sf.esfinge.greenframework.dao.memory.GreenMetricDaoImpl;
import net.sf.esfinge.greenframework.exception.GreenException;
import net.sf.esfinge.greenframework.util.GreenConstant;
import net.sf.esfinge.greenframework.util.GreenEnvironment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GreenFactoryDao {

    private static GreenFactoryDao instance;
    private final Map<Class<?>, Object> daoMemory = new HashMap<>();

    public GreenFactoryDao () {
        populateData();
    }

    protected void populateData() {
        daoMemory.put(GreenMetricDaoImpl.class, new GreenMetricDaoImpl());
        daoMemory.put(GreenConfigurationDaoImpl.class, new GreenConfigurationDaoImpl());
    }

    public static GreenFactoryDao getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GreenFactoryDao.class) {
                if (Objects.isNull(instance)) {
                    instance = new GreenFactoryDao();
                    instance.populateData();
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> greenDaoClass) {
        String persistenceType = GreenEnvironment.getPersistenceType();
        if (GreenConstant.PERSISTENCE_TYPE_MEMORY.equals(persistenceType)) {
            return (T) daoMemory.get(greenDaoClass);
        }
        throw new GreenException(String.format("Persistence type '%s' not implemented", persistenceType));
    }
}

