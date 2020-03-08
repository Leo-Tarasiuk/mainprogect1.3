package by.epam.traning.tarasiuk.hotel.entity;

public enum UserRole {
    USER("user"),
    ADMIN("admin");

    String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * @return
     */
    public static UserRole getUserRole(String role) {
        switch (role) {
            case "user" :
                return USER;
            case "admin" :
                return ADMIN;
        }

        return null;
    }
}
