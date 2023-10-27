package chatGPTProject;

public class Buyer extends User {
    public enum CreditCardType { VISA, MasterCard }

    private CreditCardType cardType;
    private String creditCardNumber;
    private double creditCardBalance;

    public Buyer(String fullName, long iin, String phoneNumber, CreditCardType cardType, String creditCardNumber, double creditCardBalance) {
        super(fullName, iin, phoneNumber);
        this.cardType = cardType;

        // Проверка и установка номера кредитной карты
        if (isValidCreditCardNumber(creditCardNumber)) {
            this.creditCardNumber = creditCardNumber;
        } else {
            throw new IllegalArgumentException("Invalid credit card number");
        }

        this.creditCardBalance = creditCardBalance;
    }

    private boolean isValidCreditCardNumber(String cardNumber) {
        // Реализация валидации номера кредитной карты
        return cardNumber.length() == 16;
    }

    @Override
    void changeNumber(String number) {
        setPhoneNumber(number);
    }

    @Override
    void changeFullName(String data) {
        setFullName(data);
    }

    public CreditCardType getCardType() {
        return cardType;
    }

    public void setCardType(CreditCardType cardType) {
        this.cardType = cardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public double getCreditCardBalance() {
        return creditCardBalance;
    }

    public void setCreditCardBalance(double creditCardBalance) {
        this.creditCardBalance = creditCardBalance;
    }

    // Геттеры и сеттеры для полей Buyer
}
