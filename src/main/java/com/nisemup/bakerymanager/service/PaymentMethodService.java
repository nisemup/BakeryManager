package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.PaymentMethod;
import com.nisemup.bakerymanager.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    public Optional<PaymentMethod> findById(Long id) {
        return paymentMethodRepository.findById(id);
    }

    public void create(PaymentMethod paymentMethod) {
        if (paymentMethodRepository.findByType(paymentMethod.getType()).isPresent())
            return;

        paymentMethodRepository.save(paymentMethod);
    }

    public void update(Long id, PaymentMethod paymentMethod) {
        PaymentMethod updatablePaymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("PaymentMethod not found!"));

        updatablePaymentMethod.setType(paymentMethod.getType());
        updatablePaymentMethod.setAllowed(paymentMethod.getAllowed());

        paymentMethodRepository.save(updatablePaymentMethod);
    }

    public void deleteById(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
