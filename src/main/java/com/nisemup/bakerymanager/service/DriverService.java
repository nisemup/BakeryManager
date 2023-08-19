package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Driver;
import com.nisemup.bakerymanager.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Optional<Driver> findById(Long id) {
        return driverRepository.findById(id);
    }

    public void create(Driver driver) {
        if (driverRepository.findByLicense(driver.getLicense()).isPresent())
            return;

        driverRepository.save(driver);
    }

    public void update(Long id, Driver driver) {
        Driver updatableDriver = driverRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Driver not found!"));

        updatableDriver.setTruck(driver.getTruck());
        updatableDriver.setUser(driver.getUser());
        updatableDriver.setLicense(driver.getLicense());
    }

    public void deleteById(Long id) {
        driverRepository.deleteById(id);
    }
}
