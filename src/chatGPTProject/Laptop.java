package chatGPTProject;

class Laptop implements Device {
    private String model;
    private double price;

    public Laptop(String model, double price) {
        this.model = model;
        this.price = price;
    }

    @Override
    public void discount(int percent) {
        price -= price * percent / 100;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}