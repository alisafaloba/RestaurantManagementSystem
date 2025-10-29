package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.model.MenuItem;
import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.model.OrderLine;
import com.example.restaurant_management_system.repository.BillRepository;
import com.example.restaurant_management_system.repository.MenuItemRepository;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    public BillService(BillRepository billRepository,
                       OrderRepository orderRepository,
                       MenuItemRepository menuItemRepository) {
        this.billRepository = billRepository;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(String id) {
        return billRepository.findById(id);
    }

    public void addBill(Bill bill) {
        billRepository.save(bill);
    }

    public void updateBill(Bill bill) {
        billRepository.update(bill);
    }

    public void deleteBill(String id) {
        billRepository.delete(id);
    }

    /**
     * Generează o factură pentru o comandă existentă:
     * calculează totalul parcurgând orderLines și găsind prețul din menuItemRepository.
     * Returnează factura creată sau null dacă comanda nu există.
     */
    public Bill generateBillForOrder(String orderNumber) {
        Order order = orderRepository.findById(orderNumber);
        if (order == null) return null;

        double total = 0.0;
        if (order.getOrderLines() != null) {
            for (OrderLine line : order.getOrderLines()) {
                String menuItemId = line.getMenuItemId(); // ex "M1"
                MenuItem menuItem = menuItemRepository.findAll().stream()
                        .filter(mi -> mi.getId().equals(menuItemId))
                        .findFirst()
                        .orElse(null);
                double price = (menuItem != null) ? menuItem.getPrice() : 0.0;
                total += price * line.getQuantity();
            }
        }

        Bill bill = new Bill("B" + (billRepository.findAll().size() + 1), order.getId(), total);
        billRepository.save(bill);
        return bill;
    }
}
