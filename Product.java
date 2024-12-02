import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private double cost;

    public Product(String id, String name, String description, double cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + cost +
                '}';
    }

    public String toCSV() {
        return id + "," + name + "," + description + "," + cost;
    }

    public String toJSON() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"price\":" + cost +
                '}';
    }

    public String toXML() {
        return "<Product>" +
                "<id>" + id + "</id>" +
                "<name>" + name + "</name>" +
                "<description>" + description + "</description>" +
                "<price>" + cost + "</price>" +
                "</Product>";
    }

    public String getPaddedName() {
        return String.format("%-35s", name);
    }

    public String getPaddedDescription() {
        return String.format("%-75s", description);
    }

    public String getPaddedID() {
        return String.format("%-6s", id);
    }
}