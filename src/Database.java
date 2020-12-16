import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Database implements AttendanceDAO, Serializable, PersonDAO, DatabaseDAO {


    private List<Child> childList = new LinkedList<>();
    private List<Caregiver> caregiverList = new LinkedList<>();
    private List<Educator> educatorList = new LinkedList<>();
    private List<Attendance> attendanceToday = new ArrayList<>();
    private List<List<Attendance>> attendanceList = new ArrayList<>();


    public Database (){

        this.childList = deSerialize("Children.ser");
        //this.caregiverList = deSerialize("Caregivers.ser");
        this.educatorList = deSerialize("Educators.ser");
        findAndAddCAregiver();
        setAttendance();



    }

    public void addChild(Child c) {
        this.childList.add(c);
    }


    public void findAndAddCAregiver(){
        for (Child child: childList){
            caregiverList.addAll(child.getCaregivers());
        }
        for (int i = 0; i <caregiverList.size() ; i++) {
            for (int j = i+1; j < caregiverList.size(); j++) {
                if(caregiverList.get(i)==caregiverList.get(j)) {
                    caregiverList.remove(j);
                }
            }
        }
    }

    @Override
    public void deleteChild(Child child) {
        this.childList.remove(child);
    }

    public void addCaregiver(Caregiver caregiver) {
        this.caregiverList.add(caregiver);
    }

    @Override
    public void deleteCaregiver(Caregiver caregiver) {
        this.caregiverList.remove(caregiver);
    }

    public void addEducator(Educator educator) {
        this.educatorList.add(educator);
    }

    @Override
    public void deleteEducator(Educator educator) {
        this.educatorList.remove(educator);
    }

    @Override
    public List<Child> getChildList() {
        return childList;
    }

    public List<Caregiver> getCaregiverList() {
        return caregiverList;
    }

    public List<Educator> getEducatorList() {
        return educatorList;
    }

    @Override
    public <T> void serialize(List <T> list, String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(list);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> deSerialize(String fileName) {
        List<T> list = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));) {
            list = (List<T>) in.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Class not found exception");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("IOException");
        }
        return list;
    }


    @Override
    public String getContactInformation(IContactInformation person) {
        StringBuilder sb = new StringBuilder();
        sb.append("E-mejladress: " + person.getEmailAddress() + '\n');
        sb.append("Telefonnummer: " + person.getPhoneNumber() + '\n');
        sb.append("Postadress: " + person.getPostAddress());

        return sb.toString();
    }

    @Override
    public Child getChild(String name) {
        for(Child c : childList){
            if(c.getFirstName().equalsIgnoreCase(name) || c.getLastName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }

    public Caregiver getCaregiver(String name){
        for (Caregiver c: caregiverList){
            if(c.getFirstName().equalsIgnoreCase(name) || c.getLastName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }

    @Override
    public Educator getEducator(String name) {
        for(Educator e: educatorList){
            if(e.getFirstName().equalsIgnoreCase(name) || e.getLastName().equalsIgnoreCase(name)){
                return e;
            }
        }
        return null;
    }

    @Override
    public void setAttendance() {
        for(Child c : getChildList()) {
            this.attendanceToday.add(new Attendance(c));
        }
    }

    @Override
    public void addAbsence(Child child) {
        for(Attendance a: getAttendanceToday()){
            if(a.getChild()==child) {
                a.setPresent(false);
                break;
            }
        }
    }

    @Override
    public void addChildInAttendance(Child child) {
        attendanceToday.add(new Attendance(child));
    }

    @Override
    public List<Attendance> getAttendanceToday() {
        return this.attendanceToday;
    }

    @Override
    public void addAttendanceTodayInList(List<Attendance> attendanceToday){
        attendanceList.add(attendanceToday);
    }

    @Override
    public List<List<Attendance>> getAttendanceList(){
        return attendanceList;
    }
}
