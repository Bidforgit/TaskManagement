package chatGPTProject;
public class Salesman extends User {
    public enum LegalEntityType { INDIVIDUAL, LLC }

    private LegalEntityType legalEntityType;
    private long bankAccount;
    private Mobile[] mobiles;
    private Laptop[] laptops;
    private int mobilesSold;
    private int laptopsSold;

    public Salesman(String fullName, long iin, String phoneNumber, LegalEntityType legalEntityType, long bankAccount) {
        super(fullName, iin, phoneNumber);
        this.legalEntityType = legalEntityType;
        this.bankAccount = bankAccount;
        this.mobiles = new Mobile[5]; // Массив мобильных устройств, максимум 5 штук
        this.laptops = new Laptop[5]; // Массив ноутбуков, максимум 5 штук
        this.mobilesSold = 0;
        this.laptopsSold = 0;
    }

    public void addMobile(Mobile mobile) {
        if (mobilesSold < mobiles.length) {
            mobiles[mobilesSold++] = mobile;
        } else {
            System.out.println("No more mobiles can be added.\n");
        }
    }

    public void addLaptop(Laptop laptop) {        if (laptopsSold < laptops.length) {
            laptops[laptopsSold++] = laptop;
        } else {
            System.out.println("No more laptops can be added.\n");
        }
    }

    public void sellDevice(Device device, double price, Buyer buyer) {
        if (device instanceof Mobile) {
            for (int i = 0; i < mobiles.length; i++) {
                if (mobiles[i] == device) {
                    if (buyer.getCreditCardBalance() >= price) {
                        buyer.setCreditCardBalance(buyer.getCreditCardBalance() - price);
                        bankAccount += price;
                        System.out.println("Device sold successfully!");
                        return;
                    } else {
                        System.out.println("Buyer does not have enough balance.");
                        return;
                    }
                }
            }
        } else if (device instanceof Laptop) {
            for (int i = 0; i < laptops.length; i++) {
                if (laptops[i] == device) {
                    if (buyer.getCreditCardBalance() >= price) {
                        buyer.setCreditCardBalance(buyer.getCreditCardBalance() - price);
                        bankAccount += price;
                        System.out.println("Device sold successfully!");
                        return;
                    } else {
                        System.out.println("Buyer does not have enough balance.");
                        return;
                    }
                }
            }
        }
        System.out.println("Device not found in inventory.");
    }

    @Override
    void changeNumber(String number) {
        setPhoneNumber(number);
    }

    @Override
    void changeFullName(String data) {
        setFullName(data);
    }

    // Геттеры и сеттеры для полей Salesman

    public LegalEntityType getLegalEntityType() {
        return legalEntityType;
    }

    public void setLegalEntityType(LegalEntityType legalEntityType) {
        this.legalEntityType = legalEntityType;
    }

    public long getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(long bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Mobile[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(Mobile[] mobiles) {
        this.mobiles = mobiles;
    }

    public Laptop[] getLaptops() {
        return laptops;
    }

    public void setLaptops(Laptop[] laptops) {
        this.laptops = laptops;
    }

    public int getMobilesSold() {
        return mobilesSold;
    }

    public void setMobilesSold(int mobilesSold) {
        this.mobilesSold = mobilesSold;
    }

    public int getLaptopsSold() {
        return laptopsSold;
    }

    public void setLaptopsSold(int laptopsSold) {
        this.laptopsSold = laptopsSold;
    }
}
