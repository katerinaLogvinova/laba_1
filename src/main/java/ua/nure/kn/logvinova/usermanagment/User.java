package ua.nure.kn.logvinova.usermanagment;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {


    private Long id;
    private String firstName;
    private String LastName;
    private Date dateofBirth;

    public User() { }

    public User(Long id, String firstName, String lastName, Date dateofBirth) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.dateofBirth = dateofBirth;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return LastName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    public Date getDateofBirth() {
        return dateofBirth;
    }
    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((LastName == null) ? 0 : LastName.hashCode());
        result = prime * result + ((dateofBirth == null) ? 0 : dateofBirth.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (LastName == null) {
            if (other.LastName != null)
                return false;
        } else if (!LastName.equals(other.LastName))
            return false;
        if (dateofBirth == null) {
            if (other.dateofBirth != null)
                return false;
        } else if (!dateofBirth.equals(other.dateofBirth))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", LastName=" + LastName + ", dateofBirth=" + dateofBirth
                + "]";
    }

    public String getFullName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LastName)
                .append(", ")
                .append(firstName);
        return stringBuilder.toString();
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDate = calendar.get(Calendar.DATE);

        calendar.setTime(dateofBirth);
        final int birthYear = calendar.get(Calendar.YEAR);
        final int birthMonth = calendar.get(Calendar.MONTH);
        final int birthDate = calendar.get(Calendar.DATE);

        int age = currentYear - birthYear;

        if ((currentMonth == birthMonth && birthDate > currentDate) || currentMonth < birthMonth)
        {
            age--;
        }
        return age;
    }
}