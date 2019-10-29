package domain.user;

public class Admin extends User {
    public Admin(int id, String username, String password, int role, String firstName, String lastName,
                 int gender, String email, String phone, String address) {
        super(id, username, password, role, firstName, lastName, gender, email, phone, address);
    }
}
