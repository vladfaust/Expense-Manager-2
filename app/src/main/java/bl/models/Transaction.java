package bl.models;

/**
 * Created by romanismagilov on 13.06.15.
 */
public class Transaction {

    int id;
    String comment;
    int amount;
    String subCategory;
    String date;
    Category category;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }


    public int getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
