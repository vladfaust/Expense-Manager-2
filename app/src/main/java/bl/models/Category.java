package bl.models;

import java.util.ArrayList;

/**
 * Created by romanismagilov on 14.06.15.
 */
public class Category {
    int cid;
    String name;
    String type;
    int colorID;

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

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public int getColorID() {
        return colorID;
    }

    // List of all categories
    public static ArrayList<Category> List = new ArrayList<Category>();

    // Take category from list with specified db index
    public static Category getCategoryByID(int _cid){
        for (int i=0; i<List.size()-1; i++){
            if (List.get(i).getCid() == _cid){
                return List.get(i);
            }
        }
        return null;
    }

    public static Category getCategoryByString(String _cat){
        for (int i=0; i<List.size()-1; i++){
            if (("'"+List.get(i).getName()+"'").equals(_cat)){
                return List.get(i);
            }
        }
        return null;
    }

    //public static void swapSubCategories
}
