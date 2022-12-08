package entities;

import java.io.Serializable;

public class Course implements Serializable {

    private int id;
    private String name;
    private String description;
    private int rate;
    private float cost;
    private String dataPath;

    public Course() {
        name = "";
        description = "";
        dataPath = "";
    }

    public Course(int id, String name, String description,
                  int rate, float cost, String dataPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.cost = cost;
        this.dataPath = dataPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (rate != course.rate) return false;
        if (Float.compare(course.cost, cost) != 0) return false;
        if (!name.equals(course.name)) return false;
        if (!description.equals(course.description)) return false;
        return dataPath.equals(course.dataPath);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + rate;
        result = 31 * result + (cost != +0.0f ? Float.floatToIntBits(cost) : 0);
        result = 31 * result + dataPath.hashCode();
        return result;
    }
}
