package chatGPTProject;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Device> items = new ArrayList<>();

    public void addItem(Device item) {
        items.add(item);
    }

    public void checkout(Buyer buyer, Salesman salesman) {
        double totalCost = calculateTotalCost();
        if (buyer.getCreditCardBalance() >= totalCost) {
            for (Device item : items) {
                salesman.sellDevice(item, item.getPrice(), buyer);
                System.out.println("-" + item.getPrice());
            }
            items.clear(); // Очистить корзину после покупки
            System.out.println("Purchase successful!");
            System.out.println("Buyer balance " +buyer.getCreditCardBalance());
            System.out.println("Salesman balance " + salesman.getBankAccount());
        } else {
            System.out.println("Insufficient balance in the buyer's credit card.");
            System.out.println("Buyer balance " + buyer.getCreditCardBalance());
            System.out.println("Salesman balance " + salesman.getBankAccount());
        }
    }

    private double calculateTotalCost() {
        double totalCost = 0.0;
        for (Device item : items) {
            totalCost += item.getPrice();
        }
        return totalCost;
    }
}
