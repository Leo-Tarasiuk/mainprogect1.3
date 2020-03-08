package by.epam.traning.tarasiuk.hotel.entity;

import java.sql.Date;

public class Client {
    private int id;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private String reasonBan;

    public Client() {

    }

    public Client(int id, String lastName, String email, String password, UserRole role) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getReasonBan() {
        return reasonBan;
    }

    public void setReasonBan(String reasonBan) {
        this.reasonBan = reasonBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        if (id != client.id) return false;
        if (!lastName.equals(client.lastName)) return false;
        if (!email.equals(client.email)) return false;

        return role == client.role;
    }

    @Override
    public int hashCode() {
        return (int) (31 * id + email.hashCode());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", reasonBan='" + reasonBan + '\'' +
                '}';
    }

    /**
     *
     */
    public class Passport {
        private int passportId = id;
        private String lastName;
        private String name;
        private String patronymic;
        private String country;
        private Date dateOfBirth;
        private String sex;
        private String identificationNo;
        private String passportNo;

        public Passport() {

        }

        public Passport(String lastName, String name, String patronymic, String country,
                        Date dateOfBirth, String sex, String identificationNo, String passportNo) {
            this.lastName = lastName;
            this.name = name;
            this.patronymic = patronymic;
            this.country = country;
            this.dateOfBirth = dateOfBirth;
            this.sex = sex;
            this.identificationNo = identificationNo;
            this.passportNo = passportNo;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getPassportId() {
            return passportId;
        }

        public void setPassportId(int passportId) {
            this.passportId = passportId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdentificationNo() {
            return identificationNo;
        }

        public void setIdentificationNo(String identificationNo) {
            this.identificationNo = identificationNo;
        }

        public String getPassportNo() {
            return passportNo;
        }

        public void setPassportNo(String passportNo) {
            this.passportNo = passportNo;
        }

        @Override
        public String toString() {
            return "Passport{" +
                    "passportId=" + passportId +
                    ", lastName='" + lastName + '\'' +
                    ", name='" + name + '\'' +
                    ", patronymic='" + patronymic + '\'' +
                    ", country='" + country + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    ", sex='" + sex + '\'' +
                    ", identificationNo='" + identificationNo + '\'' +
                    ", passportNo='" + passportNo + '\'' +
                    '}';
        }
    }
}
