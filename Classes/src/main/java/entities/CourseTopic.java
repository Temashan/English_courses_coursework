package entities;

import java.io.Serializable;

public class CourseTopic implements Serializable {

    private int id;
    private Course course;
    private int ordinalNumber;
    private String name;

    public CourseTopic() {
        course = new Course();
        name = "";
    }

    public CourseTopic(int id, Course course, int ordinalNumber, String name) {
        this.id = id;
        this.course = course;
        this.ordinalNumber = ordinalNumber;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseTopic that = (CourseTopic) o;

        if (id != that.id) return false;
        if (ordinalNumber != that.ordinalNumber) return false;
        if (!course.equals(that.course)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + course.hashCode();
        result = 31 * result + ordinalNumber;
        result = 31 * result + name.hashCode();
        return result;
    }
}
