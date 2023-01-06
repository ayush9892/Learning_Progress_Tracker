package tracker;

public enum CoursesList {
    JAVA(600), DSA(400), DATABASES(480), SPRING(550);

    private int coursePoint;
    CoursesList(int points) {
        coursePoint = points;
    }
    public int getCoursePoint() {
        return coursePoint;
    }

}
