package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.Truck;
import com.nisemup.bakerymanager.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    public List<Truck> findAll() {
        return truckRepository.findAll();
    }

    public Optional<Truck> findById(Long id) {
        return truckRepository.findById(id);
    }

    public void create(Truck truck) {
        if (truckRepository.findByNumberPlate(truck.getNumberPlate()).isPresent())
            return;

        truckRepository.save(truck);
    }

    public void update(Long id, Truck truck) {
        Truck updatableTruck = truckRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Truck not found!"));

        updatableTruck.setBrand(truck.getBrand());
        updatableTruck.setModel(truck.getModel());
        updatableTruck.setColor(truck.getColor());
        updatableTruck.setNumberPlate(truck.getNumberPlate());

        truckRepository.save(updatableTruck);
    }

    public void deleteById(Long id) {
        truckRepository.deleteById(id);
    }
}
