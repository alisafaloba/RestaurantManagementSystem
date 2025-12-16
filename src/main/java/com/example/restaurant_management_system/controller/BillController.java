package com.example.restaurant_management_system.controller;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.service.BillService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    // GET /bill → Listet alle Rechnungen mit Sortierung und Filterung auf [cite: 107]
    @GetMapping
    public String listBills(Model model,
                            // Sortier-Parameter [cite: 36, 45]
                            @RequestParam(defaultValue = "id") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir,
                            // Filter-Parameter [cite: 49, 70]
                            @RequestParam(required = false) String orderIdFilter,
                            @RequestParam(required = false) Double minAmount,
                            @RequestParam(required = false) Double maxAmount) {

        // Daten aus dem Service abrufen (gefiltert und sortiert)
        model.addAttribute("bills",
                billService.getAllBills(sortField, sortDir, orderIdFilter, minAmount, maxAmount));

        // Sortier-Infos für das UI (Tabellenköpfe)
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        // Filter-Infos, um die Werte im Filterformular beizubehalten
        model.addAttribute("orderIdFilter", orderIdFilter);
        model.addAttribute("minAmount", minAmount);
        model.addAttribute("maxAmount", maxAmount);

        return "bill/index"; // templates/bill/index.html
    }

    // --- CRUD-Methoden ---

    // GET /bill/new → show form for creating a new bill
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bill", new Bill());
        return "bill/form";
    }

    // POST /bill → handle form submission
    @PostMapping
    public String createBill(@Valid @ModelAttribute Bill bill,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Bei Fehlern zurück zum Formular, damit die Validierungsfehler angezeigt werden
            return "bill/form";
        }

        billService.addBill(bill);
        // Hinweis: Für eine Vollversion sollte hier eine Erfolgsmeldung hinzugefügt werden [cite: 92]
        return "redirect:/bill";
    }

    // POST /bill/{id}/delete → delete a bill by ID
    @PostMapping("/{id}/delete")
    public String deleteBill(@PathVariable String id) {
        billService.deleteBill(Long.valueOf(id));
        // Hinweis: Erfolgsmeldung hinzufügen [cite: 92]
        return "redirect:/bill";
    }

    // GET /bill/{id}/edit → show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Bill bill = billService.getBillById(Long.valueOf(id));
        model.addAttribute("bill", bill);
        return "bill/form";
    }

    // POST /bill/{id}/update → handle edit submission
    @PostMapping("/{id}/update")
    public String updateBill(@PathVariable Long id,
                             @Valid @ModelAttribute Bill bill,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "bill/form";
        }

        bill.setId(id);
        billService.updateBill(bill);
        // Hinweis: Erfolgsmeldung hinzufügen [cite: 92]
        return "redirect:/bill";
    }
}