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
    public static ArrayList<Category> categoryArrayList = new ArrayList<Category>();
    public ArrayList<String> subCategoryList = new ArrayList<>();

    // Take category from list with specified db index
    public static Category getCategoryByID(int _cid){
        for (int i=0; i<categoryArrayList.size()-1; i++){
            if (categoryArrayList.get(i).getCid() == _cid){
                return categoryArrayList.get(i);
            }
        }
        return null;
    }

    public static Category getCategoryByString(String _cat){
        for (int i=0; i<categoryArrayList.size()-1; i++){
            if (("'"+categoryArrayList.get(i).getName()+"'").equals(_cat)){
                return categoryArrayList.get(i);
            }
        }
        return null;
    }

    // Check if category already contains selected subcategory
    public boolean containsSelectedSubcategory(String subcat){
        for (String subcategory: subCategoryList)
            if (subcategory.equals(subcat))
                return true;
        return false;
    }

    public int getSummaryForSubcategory(String subcategory, ArrayList<Transaction> transactions){
        int sum = 0;
        for (Transaction transaction: transactions)
            if (transaction.category == this)
                for (String selectedCategorySubcategory: subCategoryList)
                    if (selectedCategorySubcategory.equals(subcategory))
                        sum += ((this.getType().equals("spends"))?-1:1)*transaction.amount;
        return sum;
    }

    //public static void swapSubCategories
}
