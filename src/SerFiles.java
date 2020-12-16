/**
 * Created by Sara Carlsson
 * Date: 08/12/2020
 * Time:15:47
 * Project: Preeschool
 * Copywright: MIT
 */
public enum SerFiles {
    CHILDREN("Children.ser"),
    EDUCATOR("Educators.ser"),
    ATTENDANCE("Attendance.ser"),
    LIST_OF_ATTENDANCES("ListOfAttendance.ser");
    public final String serFiles;

    SerFiles(String serFiles) {
        this.serFiles = serFiles;
    }
}
