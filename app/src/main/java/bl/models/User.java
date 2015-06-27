package bl.models;

/**
 * Created by romanismagilov on 16.06.15.
 */
public class User {
    static String percentage;
    // Shortened link to singleton instance
    static DatabaseInstrument dbi;

    // Loading data from Database
    static public void linkToSingleton(){
        dbi = DatabaseInstrument.instance;
    }

    public static int getBalance() {
        return dbi.getBalanceFromDB();
    }

    public static void setBalance(int balance) {
        dbi.setBalanceTo(balance);
    }

    public static void setBalanceChangedBy(int delta) {
        dbi.setBalanceChangedBy(delta);
    }

    public static int getMonthBudget() {
        return dbi.getBudget();
    }

    public static void setMonthBudget(int monthBudget) {
        dbi.setBudget(monthBudget);
    }

    public static int getMonthSpends() {
        return dbi.getAllSpendsFrom(dbi.getFinancialMonthTransactions());
    }

    public static String getPercentage(){
        return (100*(float)getMonthSpends() / (float)getMonthBudget())+"%";
    }

}
