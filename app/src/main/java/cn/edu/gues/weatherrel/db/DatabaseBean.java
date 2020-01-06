package cn.edu.gues.weatherrel.db;

public class DatabaseBean {
    private int id;
    private String city;
    private String content;

    public DatabaseBean() {
    }

    public DatabaseBean(int id, String city, String content) {
        this.id = id;
        this.city = city;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DatabaseBean{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
