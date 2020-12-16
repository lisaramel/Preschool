import javax.xml.crypto.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lisa Ramel
 * Date: 2020-12-04
 * Time: 16:27
 * Project: Preeschool
 * Copywrite: MIT
 */
public enum States {

    LOGIN {
        @Override
        public void output(Object o) {
            System.out.println("Välkommen till förskolan!" + "\nLOGGA IN SOM"
                    + "\n 1. Vårdnadshavare" + "\n 2. Pedagog" + "\n 3. Avsluta programmet");
        }
    },

    USERNAME {
        @Override
        public void output(Object o) {
            System.out.println("Skriv ditt namn: ");

        }
    },

    CAREGIVER {
        @Override
        public void output(Object o) {
            Caregiver caregiver = (Caregiver) o;
            System.out.println();
            System.out.println("Välkommen " + caregiver.getFirstName() +
                    "\nVälj barn:");
            int counter = 1;
            for (Child child : caregiver.getChildren()) {
                System.out.println(counter + " " + child.getFirstName());
                counter++;
            }
            System.out.println( counter + " Logga ut.");
        }
    },

    CHOSE_CHILD {
        @Override
        public void output(Object o) {
            Child child = (Child)o;
            System.out.println();
            System.out.println("Välkommen till sidan för " + child.getFirstName() +
                    "\n 1. Ange omsorgstider" +
                    "\n 2. Registrera frånvaro" +
                    "\n 3. Se pedagogers kontaktuppgifter" +
                    "\n 4. Logga ut");
        }
    },

    CHILD_ABSENCE {
        @Override
        public void output(Object o) {
            Child child = (Child)o;
            System.out.println();
            System.out.println("Registrerat frånvaro för " + child.getFirstName() + " " + LocalDate.now());

        }
    },

    CHILD_ATTENDANCE {
        @Override
        public void output(Object o) {
            Child child = (Child)o;
            System.out.println();
            System.out.println("Var god ange omsorgstider för " + child.getFirstName());
            showCaringTimes(child);
            System.out.println("Vilken dag vill du ändra?");
        }

        @Override
        public void addCaringTime(Child child, Scanner scan) {
            String day = scan.next().toLowerCase();

            if (day.equals("måndag")) {
                System.out.println("Var god ange lämningstid och hämtningstid på måndag: ");
                createCaringTime(0, child, day, scan);
            } else if (day.equals("tisdag")) {
                System.out.println("Var god ange lämningstid och hämtningstid på tisdag: ");
                createCaringTime(1, child, day, scan);
            } else if (day.equals("onsdag")) {
                System.out.println("Var god ange lämningstid och hämtningstid på onsdag: ");
                createCaringTime(2, child, day, scan);
            } else if (day.equals("torsdag")) {
                System.out.println("Var god ange lämningstid och hämtningstid på torsdag: ");
                createCaringTime(3, child, day, scan);
            } else if (day.equals("fredag")) {
                System.out.println("Var god ange lämningstid och hämtningstid på fredag: ");
                createCaringTime(4, child, day, scan);
            } else{
                System.out.println("var god skriv dagen igen: ");
                day = scan.next().toLowerCase();
            }
        }

        @Override
        public void createCaringTime(int dayNumber, Child child, String day, Scanner scan) {
            String time = scan.next();
            String start = time.substring(0, time.indexOf(","));
            String stop = time.substring(time.indexOf(",") + 1);
            child.caringTimes.set(dayNumber, new CaringTime(day, LocalTime.parse(start), LocalTime.parse(stop)));
        }
    },

    EDUCATOR_INFO {
        @Override
        public void output(Object o) {
            List<Educator> educatorList = (List<Educator>) o;
            System.out.println();
            System.out.println("Kontaktuppgifter till pedagogerna:");
            for (Educator educator : educatorList) {
                System.out.println(educator.getFirstName() + " " + educator.getLastName() +
                        "\n" + educator.getEmailAddress() +
                        "\n" + educator.getPhoneNumber());
            }
        }
    },

    EDUCATOR {
        @Override
        public void output(Object o) {
            Educator educator = (Educator)o;
            System.out.println();
            System.out.println("\nVälkommen " + educator.getFirstName() + "!" +
                    "\n 1. Ange frånvaro" +
                    "\n 2. Registrera en ny person till förskolan" +
                    "\n 3. Se närvaro idag" +
                    "\n 4. Se ett barns omsorgstider " +
                    "\n 5. Se vårdnadshavares kontaktuppgifter" +
                    "\n 6. Ta bort en användare från förskolan" +
                    "\n 7. Logga ut");
        }
    },

    EDUCATOR_ABSENCE {
        @Override
        public void output(Object o) {
            List<Child> childList = (List<Child>) o;
            System.out.println();
            System.out.println("Ange frånvaro för: ");
            int counter = 1;
            for (Child child : childList) {
                System.out.println(counter + " " + child.getFirstName());
                counter++;
            }
        }
    },

    REGISTER_NEW_PERSON {
        @Override
        public void output(Object o) {
            System.out.println();
            System.out.println("Registrera en ny person till förskolan" +
                    "\n1. Nytt barn" +
                    "\n2. Pedagog");
        }

        public Educator registerNewEducator(Scanner scan){
            String firstName;
            String lastName;
            String personalNumber;
            String email;
            String phoneNumber;
            String address;

            System.out.print("Ange pedagogens förnamn: ");
            firstName = scan.next();
            System.out.print("Ange pedagogens efternamn: ");
            lastName = scan.next();
            System.out.print("Ange pedagogens personnummer: ");
            personalNumber = scan.next();

            Educator educator = new Educator(firstName, lastName, personalNumber);

            System.out.print("Ange pedagogens e-mail: ");
            email = scan.next();
            educator.setEmailAddress(email);

            System.out.print("Ange pedagogens telefonnummer: ");
            phoneNumber = scan.next();
            educator.setPhoneNumber(phoneNumber);

            System.out.print("Ange pedagogens adress: ");
            address = scan.next();
            educator.setPostAddress(address);

            System.out.println();

            return educator;
        }

        public Child registerNewChild(Scanner scan) {
            String firstName;
            String lastName;
            String personalNumber;

            System.out.print("Ange barnets förnamn: ");
            firstName = scan.next();

            System.out.print("Ange barnets efternamn: ");
            lastName = scan.next();

            System.out.print("Ange barnets personnummer: ");
            personalNumber = scan.next();

            Child child = new Child(firstName, lastName, personalNumber);

            return child;
        }

        public Caregiver addCaregiverToNewChild(Scanner scan, String firstName) {

            String lastName;
            String personalNumber;
            String email;
            String phoneNumber;
            String address;

            System.out.println("Denna vårdnadshavare finns inte registrerad");

            System.out.print("Ange vårdnadhavarens efternamn: ");
            lastName = scan.next();
            System.out.print("Ange vårdnadshavarens personnummer: ");
            personalNumber = scan.next();

            Caregiver caregiver = new Caregiver(firstName, lastName, personalNumber);

            System.out.print("Ange vårdnadshavarens e-mail: ");
            email = scan.next();
            caregiver.setEmailAddress(email);

            System.out.print("Ange vårdnadshavarens telefonnummer: ");
            phoneNumber = scan.next();
            caregiver.setPhoneNumber(phoneNumber);

            System.out.print("Ange vårdnadshavarens adress: ");
            address = scan.next();
            caregiver.setPostAddress(address);

            System.out.println();

            return caregiver;
        }


    },

    ATTENDANCE {
        @Override
        public void output(Object o) {
            System.out.println();
            System.out.println("Vilken lista vill du skriva ut?");
            System.out.println(" 1. Alla barn" +
                    "\n 2. Närvarande barn" +
                    "\n 3. Frånvarande barn");
        }
    },

    PRINT_ALL {
        @Override
        public void output(Object o) {
            List<Attendance> attendanceList = (List<Attendance>) o;
            String present;
            System.out.println();
            System.out.println("Närvaro " + LocalDate.now());
            for (Attendance a : attendanceList) {
                if (!a.getPresent())
                    present = "Frånvarande";
                else
                    present = "Närvarande";
                System.out.println(a.getChild().getFirstName() + " " + a.getChild().getLastName() +
                        " " + present);
            }
            System.out.println();
        }
    },

    PRINT_ABSENT {
        @Override
        public void output(Object o) {
            List<Attendance> attendanceList = (List<Attendance>) o;
            System.out.println();
            System.out.println("Frånvarande " + LocalDate.now());
            for (Attendance a : attendanceList) {
                if (!a.getPresent())
                    System.out.println(a.getChild().getFirstName() + " " + a.getChild().getLastName());
            }
        }
    },

    PRINT_PRESENT {
        @Override
        public void output(Object o) {
            List<Attendance> attendanceList = (List<Attendance>) o;
            System.out.println();
            System.out.println("Närvarande " + LocalDate.now());
            for (Attendance a : attendanceList) {
                if (a.getPresent())
                    System.out.println(a.getChild().getFirstName() + " " + a.getChild().getLastName());
            }
        }
    },

    CAREGIVER_INFO {
        @Override
        public void output(Object o) {
            System.out.println();
            System.out.println("Vilket barn?");
        }
    },

    CAREGIVER_INFO_PRINT {
        @Override
        public void output(Object o) {
            Child child = (Child) o;
            List<Caregiver> caregiverList = child.getCaregivers();
            System.out.println();
            for(Caregiver caregiver : caregiverList){
                System.out.println(caregiver.getFirstName() + " " + caregiver.getLastName()+
                        "\n" + caregiver.getPhoneNumber() +
                        "\n" + caregiver.getEmailAddress());
            }
        }
    },


    SEE_CARINGTIMES {
        @Override
        public void output(Object o) {
            System.out.println();
            List<Child> childList = (List<Child>) o;
            System.out.println("Vilket barns omsorgstider vill du se? ");
            int counter = 1;
            for (Child c : childList) {
                System.out.println(counter + " " + c.getFirstName());
                counter++;
            }
        }

        @Override
        public void showCaringTimes(Child child) {
            System.out.println("Här är " + child.getFirstName() + "s omsorgstider: ");
            for (CaringTime ct : child.caringTimes) {
                System.out.println(ct.getDay() + ": " + ct.getStart() + " - " + ct.getStop());
            }
        }
    },

    LOG_OUT {
        @Override
        public void output(Object o) {
            System.out.println();
            System.out.println("Du har loggats ut");
        }
    },

    REMOVE_PERSON {
        @Override
        public void output(Object o) {
            System.out.println();
            System.out.println("Välj om du vill ta bort" +
                    "\n1. Barn" +
                    "\n2. Vårdnadshavare" +
                    "\n3. Pedagog" +
                    "\n4. Gå tillbaka till menyn");
        }

        public void removeEducator(Scanner scan, PersonDAO p, DatabaseDAO d){
            String name;
            boolean foundPerson = false;
            List<Educator> educatorList = (List<Educator>) p;

            System.out.println("Vem vill du ta bort ur systemet?");
            name = scan.next();

            for (int i = 0; i < educatorList.size(); i++) {
                Educator educator = educatorList.get(i);
                if (educator.getFirstName().equalsIgnoreCase(name)) {
                    System.out.println("Vill du radera " + educator.getFullName() + " från systemet? (ja/nej)");
                    String answer = scan.next();
                    foundPerson = true;
                    if (answer.equalsIgnoreCase("ja")) {
                        d.deleteEducator(educator);
                        System.out.println(educator.getFullName() + " har tagits bort från förskolan");
                    }
                }
            }
            if(!foundPerson){
                System.out.println("Personen finns inte registrerad på förskolan" +
                        "\nVar god försök med ett annat namn");
            }

        }


        public void removeCaregiver(Scanner scan, PersonDAO p, DatabaseDAO d){
            String name;
            boolean foundPerson = false;
            List<Caregiver> caregiverList = (List<Caregiver>) p;

            System.out.println("Vem vill du ta bort ur systemet?");
            name = scan.next();

                for (int i = 0; i < caregiverList.size(); i++) {
                    Caregiver caregiver = caregiverList.get(i);
                    if (caregiver.getFirstName().equalsIgnoreCase(name)) {
                        System.out.println("Vill du radera " + caregiver.getFullName() + " från systemet? (ja/nej)");
                        String answer = scan.next();
                        foundPerson = true;
                        if (answer.equalsIgnoreCase("ja")) {
                            d.deleteCaregiver(caregiver);
                            System.out.println(caregiver.getFullName() + " har tagits bort från förskolan");
                        }
                    }
                }

            if(!foundPerson){
                System.out.println("Personen finns inte registrerad på förskolan" +
                        "\nVar god försök med ett annat namn");
            }
        }

        @Override
        public void removeChild(Scanner scan, PersonDAO p, DatabaseDAO d){
            String name;
            boolean foundPerson = false;
            List<Child> childList = (List<Child>) p;

            System.out.println("Vem vill du ta bort ur systemet?");
            name = scan.next();

            for (int i = 0; i < childList.size(); i++) {
                Child child = childList.get(i);
                if (child.getFirstName().equalsIgnoreCase(name)) {
                    System.out.println("Vill du radera " + child.getFullName() + " från systemet? (ja/nej)");
                    String answer = scan.next();
                    foundPerson = true;
                    if (answer.equalsIgnoreCase("ja")) {
                        d.deleteChild(child);
                        System.out.println(child.getFullName() + " har tagits bort från förskolan");
                    }
                }
            }
            if(!foundPerson){
                System.out.println("Personen finns inte registrerad på förskolan" +
                        "\nVar god försök med ett annat namn");
            }
        }
    },

    SHUT_DOWN {
        @Override
        public void output(Object o) {
            System.out.println();
            System.out.println("Programmet avslutas");
        }

    };

    public abstract void output(Object o);

    public Educator registerNewEducator(Scanner scan) {
        Educator educator = new Educator(null, null, null);
        return educator;
    }

    public Child registerNewChild(Scanner scan) {
        Child child = new Child(null, null, null);
        return child;
    }

    public Caregiver addCaregiverToNewChild(Scanner scan, String firstName){
        Caregiver caregiver = new Caregiver(null, null, null);
        return caregiver;
    }

    public void addCaringTime(Child child, Scanner scan) { }

    public void createCaringTime(int dayNumber, Child child, String day, Scanner scan) { }

    public void showCaringTimes(Child child) {
        System.out.println("Här är " + child.getFirstName() + "s omsorgstider: ");
        for (CaringTime ct : child.caringTimes) {
            System.out.println(ct.getDay() + ": " + ct.getStart() + " - " + ct.getStop());
        }
    }

    public void removeChild(Scanner scan, PersonDAO p, DatabaseDAO d){}

    public void removeCaregiver(Scanner scan, PersonDAO p, DatabaseDAO d){}

    public void removeEducator(Scanner scan, PersonDAO p, DatabaseDAO d){}

}
