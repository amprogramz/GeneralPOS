//Alec Mcdaugale
//This class contains all of the methods to controle the cash register.
//*It keeps track of the sales expense tax, calculates sales, stores the inventory in arrays
//*Saves file to a test file for increased reliability

//uses basic packages 
//import java.io.*;
import java.util.*;

//register object
public class Register 
    
{
    //these values hold holds the total values of all sales for the sales report
    public double totalMoney, totalSales, totalExpense, totalTax, totalProfit ;
    //these are the values to hold the registers acumulated totals
    public double totalSaleSum, totalSaleTax, totalSaleSumWithTax, totalSaleCost;
    //this is an array to store the selected item indexes to access later, max 200
    //max index holds the max index for the selected item index array for recovery
    public int indexSaleSelection[] = new int[200];
    public int maxSalesIndex = 0;
    //These arrays are parallell so that all are accessable by the previous method
    public Vector <Integer> itemId = new Vector <>();
    //array to hold the item name
    public String itemName[] = new String[200];
    //aray to store the items cost quantity and markup
    public double itemCost_Quantity_markup[][] = new double[200][3];
    //keeps track of the total number of item names for reference
    public int totalItems;
    //Tax rate in colorado, may vary based on state
    public final double taxRate = .029;
    
    public DatabaseConection data = new DatabaseConection();
    
    
    
    public Register()
           
    {
            data.setItems();
            for (int index = 0; index < data.getTotalNumberOfItems(); index++)
            {
                itemId.add(data.getItemId(index));
                itemName[index] = data.getItemName(index);
                itemCost_Quantity_markup[index][0] = data.getItemPrice(index);
                itemCost_Quantity_markup[index][1] = data.getItemQuantity(index);
                itemCost_Quantity_markup[index][2] = 0;
                        //inventoryIN.nextDouble();
            }
           
            System.out.println("inventory recived sucessfully....");
       
    }
    
    //make sale method makes sale by updating all of the effected variables
    public void makeSale()
    {
       
        //////////update w emoloyee id//////////
        int saleId = data.MakeSale(1);
        for (int index = 0; index < maxSalesIndex; index++)
        {
            data.MakeTransaction(saleId, itemId.elementAt(indexSaleSelection[index]), 1);
        }
    }
    
    //OUTDATED////////////////////////////////////////////////////////////////////
    //method to add inventory to the arrays with the fill in values
    public void addInventory(String item, double cost, double quantity, double markup)
            
    {
        //make the variables the value of the input variables
        //itemId.add(totalItems);
        itemName[totalItems] = item;
        itemCost_Quantity_markup[totalItems][0] = cost;
        itemCost_Quantity_markup[totalItems][1] = quantity;
        itemCost_Quantity_markup[totalItems][2] = markup;
        
        //update the expens and the ammount of money available
        totalExpense += itemCost_Quantity_markup[totalItems][0];
        totalMoney -= itemCost_Quantity_markup[totalItems][0];
        
        //add a new item to the counter variable
        totalItems++;
        
    }
    
  
    //pricing methods
    //get the price by adding the markup with the cost
    public double getItemPrice(int index)
    {
        return getItemProfit(index) + itemCost_Quantity_markup[index][0];
    }
    //get the tax by multiplying the price by the tax
    public double getItemTax(int index)
    {
        return getItemPrice( index)* taxRate;
    }
    //get the price with the tax by adding them togeather
    public double getItemPriceWithTax(int index)
    {
        return getItemTax(index) + getItemPrice(index);
    }
    
    //accumulate the prices for grand total with methods to return the values
    public double getTotalSalePrice()
    {
        return totalSaleSum;
    }
    public double getTotalSaleTax()
    {
        return totalSaleTax;
    }
    public double getTotalSalePriceWithTax()
    {
        return totalSaleSumWithTax;
    }
    public void sumTotal(int index)
    {
        totalSaleSum += getItemPrice(index);
    }
    public void taxTotal(int index)
    {
        totalSaleTax += getItemTax(index);
    }
    public void sumWithTaxTotal(int index)
    {
        totalSaleSumWithTax += getItemPriceWithTax(index);
    }
    //method to set the indexes selected
    public void getIndexSaleSelection(int index)
    {
         indexSaleSelection[maxSalesIndex] = index;
         maxSalesIndex++;
    }
    //method to accumulate the cost
    public void totalSaleCost(int index)
    {
        totalSaleCost += itemCost_Quantity_markup[index][0];
    }
    //method to reset the sale, set all to 0
    public void ResetSales()
    {
        totalSaleSum = 0;
        totalSaleTax = 0;
        totalSaleSumWithTax = 0; 
        
        for (int index = 0; index < maxSalesIndex; index++)
        {
            indexSaleSelection[index] = 0;
        }
        maxSalesIndex = 0;
    }
    //method to retrieve the item profit
    public double getItemProfit(int index)
    {
        return itemCost_Quantity_markup[index][0] * itemCost_Quantity_markup[index][2];
    }
    //method to get the quantity
    public double getQuantity(int index)
    {
        return itemCost_Quantity_markup [index][2];
    }
    //method to get the markup rate
    public double getMarkupRate(int index)
    {
        return itemCost_Quantity_markup [index][3];
    }
    //method to return the tax rate
    public double getTaxRate()
    {
        return taxRate;
    }
    //method to return the sale cost
    public double getSaleCost()
    {
        return totalSaleCost;
    }
    
    //expense report returns
    //totalMoney, totalSales, totalExpense, totalTax, totalProfit
    public double getTotalMoney()
    {
        return totalMoney;
    }
    public double getTotalSales()
    {
        return totalSales;
    }
    public double getTotalExpense()
    {
        return totalExpense;
    }
    public double getTotalTax()
    {
        return totalTax;
    }
    public double getTotalProfit()
    {
        return totalProfit;
    }
    //get methods
}
