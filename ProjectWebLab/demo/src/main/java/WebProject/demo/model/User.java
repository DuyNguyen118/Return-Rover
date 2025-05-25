package WebProject.ReRover.model;

public class User {
    private int id;
    private String fullname;
    private String password;
    private String role;
    private String gmail;
    private String phoneNumber;
    private String address;
    private String socials;
    private String profilePicture;
    private int meritPoint;

    public void setId(int id) {
        this.id = id;
    }


    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setSocials(String socials) {
        this.socials = socials;
    }


    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


    public void setMeritPoint(int meritPoint) {
        this.meritPoint = meritPoint;
    }


    public int getId() {
        return id;
    }


    public String getFullname() {
        return fullname;
    }


    public String getPassword() {
        return password;
    }


    public String getRole() {
        return role;
    }


    public String getGmail() {
        return gmail;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getAddress() {
        return address;
    }


    public String getSocials() {
        return socials;
    }


    public String getProfilePicture() {
        return profilePicture;
    }


    public int getMeritPoint() {
        return meritPoint;
    }


    public User() {
    }

    
    public User(int id, String fullname, String password, String role, String gmail, String phoneNumber,
            String address, String socials, String profilePicture, int meritPoint) {
        this.id = id;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.gmail = gmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.socials = socials;
        this.profilePicture = profilePicture;
        this.meritPoint = meritPoint;
    }

    
}
