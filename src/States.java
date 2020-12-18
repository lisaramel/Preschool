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

            switch (day) {
                case "måndag" -> {
                    System.out.println("Var god ange lämningstid och hämtningstid på måndag: ");
                    createCaringTime(0, child, day, scan);
                }
                case "tisdag" -> {
                    System.out.println("Var god ange lämningstid och hämtningstid på tisdag: ");
                    createCaringTime(1, child, day, scan);
                }
                case "onsdag" -> {
                    System.out.println("Var god ange lämningstid och hämtningstid på onsdag: ");
                    createCaringTime(2, child, day, scan);
                }
                case "torsdag" -> {
                    System.out.println("Var god ange lämningstid och hämtningstid på torsdag: ");
                    createCaringTime(3, child, day, scan);
                }
                case "fredag" -> {
                    System.out.println("Var god ange lämningstid och hämtningstid på fredag: ");
                    createCaringTime(4, child, day, scan);
                }
                default -> {
                    System.out.println("var god skriv dagen igen: ");
                    day = scan.next().toLowerCase();
                }
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
            System.out.println();
            for (Educator educator : educatorList) {
                System.out.println(educator.getFirstName() + " " + educator.getLastName() +
                        "\n" + educator.getEmailAddress() +
                        "\n" + educator.getPhoneNumber());
                System.out.println();
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
                    "\n 6. Redigera användare på förskolan" +
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
            String admin;

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

            System.out.println("Vill du göra " + educator.getFullName() + " till admin? (ja/nej)");
            admin = scan.next();
            if(admin.equalsIgnoreCase("ja"))
            { educator.setAdmin(true); }
            else if(admin.equalsIgnoreCase("nej")){
                educator.setAdmin(false);
            }

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

            return new Child(firstName, lastName, personalNumber);
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

    EDIT_PERSONS {
        @Override
        public void output(Object o) {
            System.out.println("Vad vill du göra?" +
                    "\n1. Ta bort en person från förskolan" +
                    "\n2. Ge administratörsskap till en pedagog");
        }
    },

    ADMIN{
        @Override
        public void output(Object o) {}

        public void addAdmin(Scanner scan, List<Educator> educatorList, Educator e){
            String name;
            boolean educatorFound = false;

            if(e.isAdmin() == true){
                System.out.println("Vem vill du ge administratörs rättigheter?");
                name = scan.next();
                for (Educator educator : educatorList){
                    if(name.equalsIgnoreCase(educator.getFirstName())){
                        if(educator.isAdmin() == false) {
                            educator.setAdmin(true);
                            System.out.println(educator.getFullName() + " har getts administratörsskap");
                            educatorFound = true;
                        }
                        else{
                            System.out.println(educator.getFullName() + " är redan administratör");
                            educatorFound = true;
                        }
                    }

                }
                if(!educatorFound){
                        System.out.println("Den här pedagogen finns inte i systemet, var god lägg till en ny pedagog");
                }
            }
            else{
                System.out.println("Du är inte administratör och kan därför inte göra denna ändring");
            }
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

        public void removeEducator(Scanner scan, List<Educator> educatorList, Educator e, DatabaseDAO d){
            String name;
            boolean foundPerson = false;

            if(e.isAdmin() == true) {
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
                if (!foundPerson) {
                    System.out.println("Personen finns inte registrerad på förskolan" +
                            "\nVar god försök med ett annat namn");
                }
            } else{
                System.out.println("Endast administratörer kan ta bort pedagoger ur systemet");
            }

        }


        public void removeCaregiver(Scanner scan, List<Caregiver> caregiverList, DatabaseDAO d){
            String name;
            boolean foundPerson = false;

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
        public void removeChild(Scanner scan, List<Child> childList, DatabaseDAO d){
            String name;
            boolean foundPerson = false;

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
        return new Educator(null, null, null);
    }

    public Child registerNewChild(Scanner scan) {
        return new Child(null, null, null);
    }

    public Caregiver addCaregiverToNewChild(Scanner scan, String firstName){
        return new Caregiver(null, null, null);
    }

    public void addCaringTime(Child child, Scanner scan) { }

    public void createCaringTime(int dayNumber, Child child, String day, Scanner scan) { }

    public void showCaringTimes(Child child) {
        System.out.println("Här är " + child.getFirstName() + "s omsorgstider: ");
        for (CaringTime ct : child.caringTimes) {
            System.out.println(ct.getDay() + ": " + ct.getStart() + " - " + ct.getStop());
        }
    }

    public void removeChild(Scanner scan, List<Child> childList, DatabaseDAO d){}

    public void removeCaregiver(Scanner scan, List<Caregiver> caregiverList, DatabaseDAO d){}

    public void removeEducator(Scanner scan, List<Educator> educatorList, Educator e, DatabaseDAO d){}

    public void addAdmin(Scanner scan, List<Educator> educatorList, Educator e){}

}
