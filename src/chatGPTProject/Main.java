package chatGPTProject;

public class Main {
    public static void main(String[] args) {
        Buyer buyer = new Buyer("John Doe", 1234567890L, "7-707-543-44-32", Buyer.CreditCardType.VISA, "1234567890123456", 700000.0);
        Salesman salesman = new Salesman("Jane Smith", 9876543210L, "7-701-555-66-77", Salesman.LegalEntityType.LLC, 1456789L);

        Mobile mobile1 = new Mobile("iPhone 13", 100000.0);
        Laptop laptop1 = new Laptop("Dell XPS 15", 150000.0);
        Laptop laptop2 = new Laptop("Lenovo XPS 15", 150000.0);
        Laptop laptop3 = new Laptop("Asus XPS 15", 150000.0);
        Laptop laptop4 = new Laptop("Apple XPS 15", 150000.0);
        Laptop laptop5 = new Laptop("HP XPS 15", 150000.0);
        Laptop laptop6 = new Laptop("Dell XPS 15", 150000.0);

        salesman.addMobile(mobile1);
        salesman.addLaptop(laptop1);
        salesman.addLaptop(laptop2);
        salesman.addLaptop(laptop3);
        salesman.addLaptop(laptop4);
        salesman.addLaptop(laptop5);
        salesman.addLaptop(laptop6);

        Basket basket = new Basket();
        basket.addItem(mobile1);
        basket.addItem(laptop1);
        basket.addItem(laptop2);


        basket.checkout(buyer, salesman);
    }
}
