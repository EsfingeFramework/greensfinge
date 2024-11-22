package br.com.ita.greenframework.dao;

import br.com.ita.greenframework.GreenException;
import br.com.ita.greenframework.configuration.GreenEnvironment;
import br.com.ita.greenframework.dao.memory.GreenConfigurationDao;
import br.com.ita.greenframework.dao.memory.GreenMetricDao;
import br.com.ita.greenframework.util.GreenConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenFactoryDao {

    private static GreenFactoryDao instance;
    private final Map<Class<?>, Object> daoMemory = new HashMap<>();

    protected void populateData() {
        daoMemory.put(GreenMetricDao.class, new GreenMetricDao());
        daoMemory.put(GreenConfigurationDao.class, new GreenConfigurationDao());
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

