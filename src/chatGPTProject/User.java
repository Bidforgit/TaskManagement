package chatGPTProject;
public abstract class User {
    private String fullName;
    private long iin;
    private String phoneNumber;

    public User(String fullName, long iin, String phoneNumber) {
        this.fullName = fullName;
        this.iin = iin;
        this.phoneNumber = phoneNumber;
    }

    abstract void changeNumber(String number);

    abstract void changeFullName(String data);

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getIin() {
        return iin;
    }

    public void setIin(long iin) {
        this.iin = iin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

