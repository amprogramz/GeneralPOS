//Alec McDaugale///////////// 
//This file is used to retrive and write data to MySQL GeneralPOS Database 
//Will contain all queries needed for final//

import java.sql.*; 
import java.util.*;

public class DatabaseConection 
{
    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/general_pos";
    private static String username = "root";
    private static String password = "Toor";
    
    //item var
    private static Vector <Integer> itemId = new Vector <Integer>();
    private static Vector <String> itemName = new Vector <String>();
    private static Vector <Double> itemPrice = new Vector <Double>();
    private static Vector <Integer> itemQuantity = new Vector <Integer>();
    
    
    public static void setItems()
    {
        //reset var
        itemId.clear();
        itemName.clear();
        itemPrice.clear();
        itemQuantity.clear();
        
        //query
        String query = "select items.item_id, item_name, item_price, on_hands\n" +
                        "from items \n" +
                            "join inventory\n" +
                                "on items.item_id = inventory.item_id\n" +
                        "order by item_name\n" +
                        ";"; 
        try (Connection connect = DriverManager.getConnection(
                dbUrl, username, password);
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(query)
            ){
            while(rs.next())
            {
                itemId.add(rs.getInt("item_id"));
                itemName.add(rs.getString("item_name"));
                itemPrice.add(rs.getDouble("item_price"));
                itemQuantity.add(rs.getInt("on_hands"));
                
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static int getItemId (int index)
    {
        return itemId.elementAt(index);
    }
    public static String getItemName (int index)
    {
        return itemName.elementAt(index);
    }
    public static double getItemPrice (int index)
    {
        return itemPrice.elementAt(index);
    }
    public static int getItemQuantity (int index)
    {
        return itemQuantity.elementAt(index);
    }
    public static int getTotalNumberOfItems()
    {
        return itemName.size();
    }
    
    
    
    
    
    public static int getlastSaleId(int employeeId)
    {
        int saleId = 0;
        String query = "select * "
                + "from sale "
                + "where employee_id = 1 "
                + "order by sale_id"
                + ";";
        try (Connection connect = DriverManager.getConnection(
                dbUrl, username, password);
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(query)
            ){
            while(rs.next())
            {
                saleId = rs.getInt("sale_id");
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        
        System.out.println(saleId);
         return saleId;   
     
    }
    //1st
    public static int MakeSale(int employeeId)
    {
        String query = "insert into sale (sale_id,Employee_id) values\n" +
                        "(null," + employeeId + ")\n" +
                        ";";
    
        updateQuery(query);
        return getlastSaleId(employeeId);
    }
    //2nd in loop
    public static void MakeTransaction(int saleId, int itemId, int quantity)
    {
        String query = "insert into make_transaction (transacton_id, sale_id, item_id, quantity) values \n" +
                        "(null, " + saleId + ", " + itemId + ", " + quantity + ")\n" +
                        ";";
        updateQuery(query);
        //send itemId quantity
        updateInventory(itemId, quantity);
    }
    public static void updateInventory(int itemId, int quantity)
    {
        String query = "update inventory\n" +
                        "set on_hands = on_hands - " + quantity +
                        " where item_id = " + itemId + 
                        ";";
        updateQuery(query);
    }
    public static void updateQuery(String query)
    {
        try 
        {
                Connection connect = DriverManager.getConnection(
                dbUrl, username, password);
                Statement statement = connect.createStatement();
                statement.execute(query);
            connect.close();
            System.out.println("Update database sucessfull");
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
  /*  public static void makeQuery(String query)
    {
        try (Connection connect = DriverManager.getConnection(
                dbUrl, username, password);
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(query)
            ){
            while(rs.next())
            {
                
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }*/
   /* public static void employeeQuery(String query)
    {
        try (Connection connect = DriverManager.getConnection(
                dbUrl, username, password);
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(query)
            ){
            while(rs.next())
            {
                int employee_id = rs.getInt("employee_id");
                String employee_first  = rs.getString("employee_first"); 
                String employee_last  = rs.getString("employee_last");
                String  street = rs.getString("street");
                String city = rs.getString("city");
                String state = rs.getString("state");
                int zip_code = rs.getInt("zip_code");
                String date_start = rs.getString("date_start");
                
                System.out.println(employee_id + " " + employee_first + " " + employee_last + " " + street + " " + city + " " + state + " " + zip_code+ " " + date_start);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
            
    }
*/
    
    
}
