package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.repository.BillRepository;
import com.example.restaurant_management_system.repository.MenuItemRepository;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    // CRUD standard
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(String id) {
        return billRepository.findById(id);
    }

    public void addBill(Bill bill) {
        billRepository.save(bill);
    }

    //public void updateBill(Bill bill) {
      //  billRepository.update(bill);
    //}

    public void deleteBill(String id) {
        billRepository.delete(id);
    }
}