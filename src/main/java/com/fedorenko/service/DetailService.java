package com.fedorenko.service;


import com.fedorenko.model.Detail;
import com.fedorenko.repository.DetailRepository;
import com.fedorenko.util.ExecuteThreads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DetailService {
    private static DetailRepository detailRepository;
    private static DetailService detailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailService.class);

    private DetailService() {
    }

    public static DetailService getInstance() {
        if (detailService == null) {
            detailService = new DetailService();
            LOGGER.info("Detail Service instance was created.");
        }
        detailRepository = DetailRepository.getInstance();
        return detailService;
    }

    public Detail save(final Detail detail) {
        Optional.of(detail).ifPresentOrElse(
                detailRepository::save,
                IllegalArgumentException::new);
        LOGGER.info("Detail with {} - id saved", detail.getId());
        return detail;
    }

    public Detail findById(final String id) {
        final Detail detail = Optional.ofNullable(detailRepository.findById(id))
                .orElseThrow(IllegalAccessError::new);
        LOGGER.info("Detail with {} - id is found.", detail.getId());
        return detail;
    }

    public List<Detail> getAll() {
        LOGGER.info("Got all details.");
        return Optional.ofNullable(detailRepository.getAll())
                .orElseThrow(IllegalAccessError::new);
    }

    public void delete(final String id) {
        Optional.of(id).ifPresentOrElse(
                detailRepository::delete,
                IllegalArgumentException::new);
    }

    public int getAmountOfBrokenMicroSchemas() {
        AtomicInteger count = new AtomicInteger();
        getAll().forEach(detail -> count.addAndGet(detail.getCountOfBrokenMicroSchemas()));
        return count.get();
    }

    public int getTotalAmountOfGas() {
        AtomicInteger count = new AtomicInteger();
        getAll().forEach(detail -> count.addAndGet(detail.getAmountOfGas()));
        return count.get();
    }

    public Detail createDetailAndSave() {
        return ExecuteThreads.executeThreads();
    }

    public List<String> getAllId() {
        return Optional.ofNullable(detailRepository.getAll())
                .map(list -> list.stream().map(Detail::getId).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
