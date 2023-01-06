package tracker.CoursesDetails;

public class CourseDTO {
    private int Java, DSA, Databases, Spring;

    public void setJava(int java) {
        Java += java;
    }

    public void setDSA(int DSA) {
        this.DSA += DSA;
    }

    public void setDatabases(int databases) {
        Databases += databases;
    }

    public void setSpring(int spring) {
        Spring += spring;
    }

    public int getJava() {
        return Java;
    }

    public int getDSA() {
        return DSA;
    }

    public int getDatabases() {
        return Databases;
    }

    public int getSpring() {
        return Spring;
    }

}
