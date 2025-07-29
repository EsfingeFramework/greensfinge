package net.sf.esfinge.greenframework.core.dao.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.dao.contract.GreenConfigurationDao;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.entity.GreenConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class GreenConfigurationDaoImpl extends GreenMemoryDaoImpl<GreenConfiguration> implements GreenConfigurationDao {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Optional<GreenConfiguration> getConfiguration(String configurationKey, String keyContext) {
        return Optional.ofNullable(listStorage.get(configurationKey))
                .flatMap(list ->
                        list.stream()
                                .filter(config -> config.getKey().equals(configurationKey))
                                .filter(config -> Objects.nonNull(config.getKeyContext()) &&
                                        config.getKeyContext().equals(keyContext))
                                .findFirst()
                                .or(() ->
                                        list.stream()
                                                .filter(config -> config.getKey().equals(configurationKey))
                                                .filter(config -> config.getKeyContext() == null)
                                                .findFirst()
                                )
                );
    }

    @Override
    public List<GreenConfiguration> getAllConfigurations() {
        return listStorage.values().stream()
                .flatMap(List::stream)
                .toList();
    }

    @Override
    @SneakyThrows
    public void updateGeneralConfiguration(GreenDefaultConfiguration greenConfiguration) {
        GreenConfiguration config = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .configurations(objectMapper.writeValueAsString(greenConfiguration.toMap()))
                .build();

        Optional.ofNullable(listStorage.get(greenConfiguration.getConfigurationKey()))
                .ifPresent(list ->
                        IntStream.range(0, list.size())
                                .filter(i -> {
                                    GreenConfiguration configBD = list.get(i);
                                    return configBD.getKey().equals(greenConfiguration.getConfigurationKey()) &&
                                            Objects.isNull(configBD.getKeyContext());
                                })
                                .findFirst()
                                .ifPresent(i -> list.set(i, config))
                );
    }

    @Override
    @SneakyThrows
    public void insertGeneralConfiguration(GreenDefaultConfiguration greenConfiguration) {
        GreenConfiguration config = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .configurations(objectMapper.writeValueAsString(greenConfiguration.toMap()))
                .build();
        addToList(greenConfiguration.getConfigurationKey(), config);
    }

    @Override
    public Optional<GreenConfiguration> findGeneralConfiguration(String configurationKey) {
        return Optional.ofNullable(listStorage.get(configurationKey))
                .flatMap(list -> list.stream()
                        .filter(config -> config.getKey().equals(configurationKey) &&
                                Objects.isNull(config.getKeyContext()))
                        .findFirst()
                );
    }

    @Override
    public Optional<GreenConfiguration> findPersonalConfiguration(String configurationKey, String keyContext) {
        return Optional.ofNullable(listStorage.get(configurationKey))
                .flatMap(list -> list.stream()
                        .filter(config -> config.getKey().equals(configurationKey) &&
                                Objects.nonNull(config.getKeyContext()) && config.getKeyContext().equals(keyContext))
                        .findFirst()
                );
    }

    @Override
    @SneakyThrows
    public void insertPersonalConfiguration(GreenDefaultConfiguration greenConfiguration) {
        GreenConfiguration config = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .keyContext(greenConfiguration.getKeyContext())
                .configurations(objectMapper.writeValueAsString(greenConfiguration.toMap()))
                .build();
        addToList(greenConfiguration.getConfigurationKey(), config);
    }

    @Override
    @SneakyThrows
    public void updatePersonalConfiguration(GreenDefaultConfiguration greenConfiguration) {
        GreenConfiguration config = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .keyContext(greenConfiguration.getKeyContext())
                .configurations(objectMapper.writeValueAsString(greenConfiguration.toMap()))
                .build();

        Optional.ofNullable(listStorage.get(greenConfiguration.getConfigurationKey()))
                .ifPresent(list ->
                        IntStream.range(0, list.size())
                                .filter(i -> {
                                    GreenConfiguration configBD = list.get(i);
                                    return configBD.getKey().equals(greenConfiguration.getConfigurationKey()) &&
                                            Objects.nonNull(configBD.getKeyContext()) &&
                                            configBD.getKeyContext().equals(greenConfiguration.getKeyContext());
                                })
                                .findFirst()
                                .ifPresent(i -> list.set(i, config))
                );
    }
}
