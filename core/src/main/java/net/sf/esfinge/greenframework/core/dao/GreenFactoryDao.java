package net.sf.esfinge.greenframework.core.dao;

import net.sf.esfinge.greenframework.core.dao.memory.GreenConfigurationDaoImpl;
import net.sf.esfinge.greenframework.core.dao.memory.GreenCustomMockDaoImpl;
import net.sf.esfinge.greenframework.core.dao.memory.GreenMetricDaoImpl;
import net.sf.esfinge.greenframework.core.dao.memory.GreenScanConfigurationDaoImpl;
import net.sf.esfinge.greenframework.core.entity.enuns.Messages;
import net.sf.esfinge.greenframework.core.exception.GreenBusinessException;
import net.sf.esfinge.greenframework.core.util.GreenConstant;
import net.sf.esfinge.greenframework.core.util.GreenEnvironment;

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
        daoMemory.put(GreenScanConfigurationDaoImpl.class, new GreenScanConfigurationDaoImpl());
        daoMemory.put(GreenConfigurationDaoImpl.class, new GreenConfigurationDaoImpl());
        daoMemory.put(GreenCustomMockDaoImpl.class, new GreenCustomMockDaoImpl());
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
        throw new GreenBusinessException(Messages.ERROR_PERSISTENCE_TYPE, persistenceType);
    }
}

