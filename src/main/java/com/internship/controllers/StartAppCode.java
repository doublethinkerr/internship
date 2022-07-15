package com.internship.controllers;

import com.internship.models.Checkpoint;
import com.internship.models.Product;
import com.internship.models.Unit;
import com.internship.models.repo.CheckpointRepository;
import com.internship.models.repo.ProductRepository;
import com.internship.models.repo.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartAppCode {

    @Autowired
    CheckpointRepository checkpointRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setName("первый пункт");
        checkpointRepository.save(checkpoint);

        Unit unit = new Unit();
        unit.setName("кг");
        unitRepository.save(unit);

        Product product = new Product();
        product.setBusy(false);
        product.setCount(100);
        product.setName("продукт первый");
        product.setUnit(unit);
        productRepository.save(product);


    }
}
