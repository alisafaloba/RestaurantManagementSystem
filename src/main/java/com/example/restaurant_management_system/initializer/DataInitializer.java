package com.example.restaurant_management_system.initializer;

import com.example.restaurant_management_system.model.*;
import com.example.restaurant_management_system.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TableRepository tableRepository;
    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final StaffRepository staffRepository;
    private final ServerRepository serverRepository;
    private final ChefRepository chefRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderAssignmentRepository orderAssignmentRepository;


    public DataInitializer(TableRepository tableRepository,
                           BillRepository billRepository,
                           CustomerRepository customerRepository,
                           OrderRepository orderRepository,
                           OrderLineRepository orderLineRepository,
                           StaffRepository staffRepository, ServerRepository serverRepository,
                           ChefRepository chefRepository,
                           MenuItemRepository menuItemRepository, OrderAssignmentRepository orderAssignmentRepository) {
        this.tableRepository = tableRepository;
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.staffRepository = staffRepository;
        this.serverRepository = serverRepository;
        this.chefRepository = chefRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderAssignmentRepository = orderAssignmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // ----------------------- Tables
        if (tableRepository.count() == 0) {
            List<Table> tables = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                tables.add(new Table(i, i % 2 == 0 ? TableStatus.OCCUPIED : TableStatus.FREE));
            }
            tableRepository.saveAll(tables);
        }

        // ----------------------- MenuItems with allergens
        if (menuItemRepository.count() == 0) {
            List<MenuItem> menuItems = List.of(
                    new MenuItem("Pizza Margherita", 8.50, Arrays.asList("Gluten", "Lactose")),
                    new MenuItem("Burger Classic", 7.20, Arrays.asList("Gluten")),
                    new MenuItem("Caesar Salad", 6.50, Arrays.asList("Egg", "Fish")),
                    new MenuItem("Spaghetti Bolognese", 9.00, Arrays.asList("Gluten", "Meat")),
                    new MenuItem("Grilled Salmon", 12.50, Arrays.asList("Fish")),
                    new MenuItem("Steak Medium", 15.00, new ArrayList<>()),
                    new MenuItem("Veggie Wrap", 5.50, Arrays.asList("Gluten")),
                    new MenuItem("Chocolate Cake", 4.50, Arrays.asList("Gluten", "Egg", "Lactose")),
                    new MenuItem("Ice Cream Sundae", 3.50, Arrays.asList("Lactose")),
                    new MenuItem("Minestrone Soup", 4.00, new ArrayList<>())
            );
            menuItemRepository.saveAll(menuItems);
        }

        // ----------------------- Customers
        if (customerRepository.count() == 0) {
            List<Customer> customers = List.of(
                    new Customer("Alice", "alice@example.com"),
                    new Customer("Bob", "bob@example.com"),
                    new Customer("Charlie", "charlie@example.com"),
                    new Customer("David", "david@example.com"),
                    new Customer("Eva", "eva@example.com"),
                    new Customer("Frank", "frank@example.com"),
                    new Customer("Grace", "grace@example.com"),
                    new Customer("Hannah", "hannah@example.com"),
                    new Customer("Ivan", "ivan@example.com"),
                    new Customer("Julia", "julia@example.com")
            );
            customerRepository.saveAll(customers);
        }

        // ----------------------- Staff / Chefs
        if (staffRepository.count() == 0) {
            List<Chef> chefs = List.of(
                    new Chef("Gordon Ramsay", "Italian Cuisine"),
                    new Chef("Jamie Oliver", "British Cuisine"),
                    new Chef("Alice Smith", "Pastry"),
                    new Chef("Bob Johnson", "Grill"),
                    new Chef("Charlie Brown", "Seafood"),
                    new Chef("David White", "Vegetarian"),
                    new Chef("Eva Green", "Sushi"),
                    new Chef("Frank Black", "Desserts"),
                    new Chef("Grace Lee", "BBQ"),
                    new Chef("Hannah Davis", "Vegan")
            );
            chefRepository.saveAll(chefs);
        }

        // ----------------------- Orders
        if (orderRepository.count() == 0) {
            List<Customer> customers = customerRepository.findAll();
            List<Table> tables = tableRepository.findAll();
            List<Order> orders = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Order order = new Order();
                order.setCustomer(customers.get(i));
                order.setTable(tables.get(i));
                order.setStatus(OrderStatus.OPEN);

                // Folosim direct LocalDateTime deoarece Order.date este LocalDateTime
                LocalDateTime date = LocalDateTime.now().minusDays(i);
                order.setDate(date);

                orders.add(order);
            }
            orderRepository.saveAll(orders);
        }

        // ----------------------- OrderLines
        if (orderLineRepository.count() == 0) {
            List<Order> orders = orderRepository.findAll();
            List<MenuItem> menuItems = menuItemRepository.findAll();

            for (int i = 0; i < 10; i++) {
                OrderLine line = new OrderLine();
                line.setOrder(orders.get(i));
                line.setMenuItem(menuItems.get(i));
                line.setQuantity(1.0 + i);
                orderLineRepository.save(line);
            }
        }

        // ----------------------- Bills
        if (billRepository.count() == 0) {
            List<Order> orders = orderRepository.findAll();
            for (int i = 0; i < orders.size(); i++) {
                Bill bill = new Bill();
                bill.setOrderId("Ord" + (i + 1));
                bill.setTotalAmount((i + 1) * 10.0);
                billRepository.save(bill);
            }
        }


// ----------------------- Staff (Chefs + Servers)
        if (serverRepository.count() == 0) {


            // Servers
            List<Staff> servers = List.of(
                    new Server("Liam", "Waiter"),
                    new Server("Olivia", "Waitress"),
                    new Server("Noah", "Waiter"),
                    new Server("Emma", "Waitress"),
                    new Server("Ava", "Waitress"),
                    new Server("William", "Waiter"),
                    new Server("Sophia", "Waitress"),
                    new Server("James", "Waiter"),
                    new Server("Isabella", "Waitress"),
                    new Server("Benjamin", "Waiter")
            );
            staffRepository.saveAll(servers);
        }

            if (chefRepository.count() == 0) {

            // Chefs
            List<Staff> chefs = List.of(
                    new Chef("Gordon Ramsay", "Italian Cuisine"),
                    new Chef("Jamie Oliver", "British Cuisine"),
                    new Chef("Alice Smith", "Pastry"),
                    new Chef("Bob Johnson", "Grill"),
                    new Chef("Charlie Brown", "Seafood"),
                    new Chef("David White", "Vegetarian"),
                    new Chef("Eva Green", "Sushi"),
                    new Chef("Frank Black", "Desserts"),
                    new Chef("Grace Lee", "BBQ"),
                    new Chef("Hannah Davis", "Vegan")
            );
            staffRepository.saveAll(chefs);


        }



// ----------------------- OrderAssignments
        if (orderAssignmentRepository.count() == 0) {
            List<Order> orders = orderRepository.findAll();
            List<Staff> staffList = staffRepository.findAll();
            List<OrderAssignment> assignments = new ArrayList<>();

            // Creează 10 assignment-uri
            for (int i = 0; i < 10; i++) {
                Order order = orders.get(i % orders.size()); // dacă sunt mai puține comenzi
                Staff staff = staffList.get(i % staffList.size()); // alege staff ciclic

                OrderAssignment assignment = new OrderAssignment(order, staff);
                assignments.add(assignment);
            }

            orderAssignmentRepository.saveAll(assignments);
        }




        System.out.println("✅ Database initialized with all entities and relationships!");
    }
}
