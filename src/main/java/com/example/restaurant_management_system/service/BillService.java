package com.example.restaurant_management_system.service;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.repository.BillRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    /**
     * Ruft alle Rechnungen ab, wendet Sortierung und Filterung an[cite: 24, 41, 66].
     *
     * @param sortField Das Attribut, nach dem sortiert werden soll (z.B. "orderId")
     * @param sortDir Die Sortierrichtung ("asc" oder "desc") [cite: 46]
     * @param orderIdFilter Filter für die Order ID (Teilstring)
     * @param minAmount Filter für den minimalen Gesamtbetrag (Wertebereich) [cite: 57, 59, 60]
     * @param maxAmount Filter für den maximalen Gesamtbetrag (Wertebereich) [cite: 57, 59, 60]
     * @return Liste der gefilterten und sortierten Rechnungen
     */
    public List<Bill> getAllBills(String sortField, String sortDir,
                                  String orderIdFilter, Double minAmount, Double maxAmount) {

        // Erstellung des Sortierobjekts [cite: 42]
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();

        // Aufruf der Repository-Methode für kombiniertes Filtern und Sortieren [cite: 71]
        return billRepository.findFilteredBills(orderIdFilter, minAmount, maxAmount, sort);
    }

    // --- CRUD-Methoden ---

    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    public void addBill(Bill bill) {
        billRepository.save(bill);
    }

    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
}