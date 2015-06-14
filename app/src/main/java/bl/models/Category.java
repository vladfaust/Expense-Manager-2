package bl.models;

/**
 * Created by romanismagilov on 14.06.15.
 */
public class Category {
    int cid;
    String name;
    String type;

    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
