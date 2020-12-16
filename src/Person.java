import java.io.Serializable;

/**
 * Created by Sara Carlsson
 * Date: 30/11/2020
 * Time:14:07
 * Project: Preeschool
 * Copywright: MIT
 */
public abstract class Person implements Serializable {

    private String firstName;
    private String lastName;
    private String personalNumber;

    Person(String firstName, String lastName, String personalNumber){

        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() { return getFirstName() + " " + getLastName(); }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

}
