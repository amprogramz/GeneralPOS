/*
Author: 
Alec McDaugale

Description:
This is the general pos program. This file contains all of of the window elements.
I got the idea when my wife had a garage sale
and this program would be suitable to support a small sale and keep track of the 
profits. This program assums that every expense of the item added to the venue
is the cost that you paid for the item wich is added to an expense. The program
also allows you to add a custom mark-up percentage to you to enshure that your
company is profitabe. If you were to use the program to calculate profits of a 
garage sale then you could exclude the markup and follow the total income of 
the sale.
*/


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.paint.Color;




public class GeneralPOS extends Application {
    //make the register and backgrounds public so that all methods can use the class
    public Register register = new Register();
    public BackgroundFill backgroundGray = new BackgroundFill(Color.DARKGRAY, new CornerRadii
        (.2), new Insets(0,0,0,0));
    
    //start the windows here
    @Override
    public void start(Stage primaryStage)
    {
        
        Label welcome = new Label("Welcome to the general POS System. We "
                    + "provide a \ngeneral way for you to conduct business in a "
                    + "store \nwith this generic Point Of Sale system. \n\n"
                    + "Please press \"start\" button to continue...");
        //add buttons start or quit
        Button buttonStart = new Button("Start");
        Button buttonQuit = new Button("Quit");
        
        //pane to hold the default menue
        BorderPane rootMenue = new BorderPane();
        rootMenue.setCenter(welcome);
        rootMenue.setBottom(HBoxButton(buttonStart, buttonQuit));
        
        Scene scene = new Scene(rootMenue, 300, 250);
        
        primaryStage.setTitle("Welcome To The General POS");
        primaryStage.setScene(scene);
        
        primaryStage.show();
        
        
        //the start action enters the program to select the mode
        buttonStart.setOnAction((ActionEvent eventStart)->{
            selectMode(primaryStage, buttonQuit);
            
        });//end select mode
        
        //The quit button is available to any event
        buttonQuit.setOnAction((ActionEvent eventQuit)->{
            quit();
        });//end quit
    }//end start
    public void quit()
    {
        System.exit(0);
    }
    
    public void selectMode(Stage primaryStage, Button buttonQuit)
    {
        //declare the buttons and the label describes the choices available 
        Label salesModeDescription = new Label("Please Select a mode to enter...");
        Button buttonSalesMode = new Button("Sales Mode");
        Button buttonManagement = new Button("Management");
        Button buttonExitToMainMenue = new Button("Exit to Main");
        //use quit from first def

        //set main menue and show
        BorderPane mainMenue = new BorderPane();
        mainMenue.setCenter(salesModeDescription);
        mainMenue.setBottom(HBoxButton(buttonSalesMode, buttonManagement, buttonQuit));

        Scene scene2 = new Scene(mainMenue, 300, 200);

        primaryStage.setTitle("Select Mode");
        primaryStage.setScene(scene2);
        primaryStage.show();

        //enter sales mode
        buttonSalesMode.setOnAction((ActionEvent eventSalesMode) -> {
            salesMode(primaryStage, buttonExitToMainMenue);

        });//end event Sales Mode

        //the management options
        buttonManagement.setOnAction((ActionEvent eventManagement)->{

           managementMode(primaryStage, buttonExitToMainMenue);

        });//end management mode

        //i put this event here so that the button to get to the main menue 
        //would be acessable from either the management mode or the sales mode
        buttonExitToMainMenue.setOnAction((ActionEvent eventExitSales)->{

           selectMode(primaryStage, buttonQuit); 

        });//end exit to main menue
            
    }
    public void salesMode(Stage primaryStage,  Button buttonExitToMainMenue)
    {
        //create the buttons and objects here so that they are easy to update
        Button buttonMakeSale = new Button("Make Sale");
        Button buttonCancleSale = new Button("Cancle Sale");
        //Button buttonExitToMainMenue = new Button("ExitToMainMenue");
        TextArea recipt = new TextArea();
        recipt.setText("Welcome To The General Market...\n");
        ComboBox<String> itemsForSale = updateItems();

        //made methods at bottom to create the register since it was so long
        BorderPane SalesMenue = setSalesMenue(buttonMakeSale, buttonCancleSale, buttonExitToMainMenue, recipt, itemsForSale);

        Scene scene3 = new Scene(SalesMenue, 500, 500);

        primaryStage.setTitle("Sales Mode");
        primaryStage.setScene(scene3);
        primaryStage.show();

        //enter make a sale event
        buttonMakeSale.setOnAction((ActionEvent eventMakeSale)->{

            Label confirmSaleDialog = new Label("Would you like to "
                    + "confirm this sale.");

            //create the final recipt and objects to confirm the sale
            Label reciptOut = new Label(outputRecipt());
            Button buttonConfirmSale = new Button("Confirm Sale");
            Button buttonCancleSale2 = new Button("Cancle Sale");


            BorderPane ConfirmSalesMenue = new BorderPane();
            ConfirmSalesMenue.setTop(confirmSaleDialog);
            ConfirmSalesMenue.setCenter(reciptOut);
            ConfirmSalesMenue.setBottom(HBoxButton(buttonConfirmSale, buttonCancleSale2));

            Scene scene4 = new Scene(ConfirmSalesMenue, 500, 500);

            primaryStage.setScene(scene4);
            primaryStage.show();

            //confirm the sale
            buttonConfirmSale.setOnAction((ActionEvent eventConfirmSale)->{
                //try to set the information and save it as a sale
                
                    register.makeSale();
               
                  
                
                //resets the register object
                register.ResetSales();
                salesMode(primaryStage, buttonExitToMainMenue);
            });//end confirm

            //cancel the sale
            buttonCancleSale2.setOnAction((ActionEvent eventCancelSale2)->{
                //goes back to allow more items to be added or for items to be cleared
                primaryStage.setScene(scene3);
                primaryStage.show();
            });//end cancel
        });//end make sale

        //create a cancle sale to clear the register of sales
        buttonCancleSale.setOnAction((ActionEvent eventCancelSale)->{
            //more eficient way to cancel sale
            register.ResetSales();
            salesMode(primaryStage, buttonExitToMainMenue);
            //reset the sale
        });//end cancel
    }
    
    public void managementMode(Stage primaryStage,  Button buttonExitToMainMenue)
    {
         //create nodes to allow user to select a mode
        Label selectManagementMode = new Label("Please select a management "
                + "task..");
        Button buttonSalesReport = new Button("Sales Reports");
        Button buttonInventoryManager = new Button("Inventory Manager");

        BorderPane ManagementMenue = new BorderPane();
        ManagementMenue.setCenter(selectManagementMode);
        ManagementMenue.setBottom(HBoxButton(buttonSalesReport, buttonInventoryManager, buttonExitToMainMenue));

        Scene scene4 = new Scene(ManagementMenue, 350, 250);

        primaryStage.setScene(scene4);
        primaryStage.show();

        //Shows a sales report
        buttonSalesReport.setOnAction((ActionEvent eventSalesReport)->
        {
            //only one button which exits menue
            salesReport(primaryStage, buttonExitToMainMenue);
        });//end sales report

        //enter the inventory manager
        buttonInventoryManager.setOnAction((ActionEvent eventInventoryManager)->
        {
            manageInventory(primaryStage, buttonExitToMainMenue);
        });//end inventory
    }
    public void salesReport(Stage primaryStage, Button buttonExitToMainMenue)
    {
        Button buttonOk = new Button("Ok");

        //labels to hold the chart
        Label totalMoney = new Label("Total Money:");
        Label totalSales = new Label("Total Sales:");
        Label expense = new Label("Total Expense:");
        Label tax = new Label("Total Tax:");
        Label profit = new Label("Total Profit:");

        //var reference
        //totalMoney, totalSales, totalExpense, totalTax, totalProfit
        Label money = new Label(String.format("%1s %-10.2f" , "$", register.getTotalMoney() ));
        Label sales = new Label(String.format("%1s %-10.2f" , "$", register.getTotalSales() ));
        Label expensees = new Label(String.format("%1s %-10.2f" , "$", register.getTotalExpense() ));
        Label taxes = new Label(String.format("%1s %-10.2f" , "$", register.getTotalTax() ));
        Label profits = new Label(String.format("%1s %-10.2f" , "$", register.getTotalProfit() ));

        BorderPane SalesReports = new BorderPane();

        //create and format gridpane to hold the sales chart
        GridPane displaySales = new GridPane();
        displaySales.setPadding(new Insets(5, 5, 5, 5));
        displaySales.setHgap(5);
        displaySales.setVgap(5);
        displaySales.setAlignment(Pos.CENTER);
        displaySales.add(totalMoney, 0, 0, 2, 1);
        displaySales.add(money, 3, 0, 2, 1);
        displaySales.add(totalSales, 0, 2, 2, 1);
        displaySales.add(sales, 3, 2, 2, 1);
        displaySales.add(expense, 0, 4, 2, 1);
        displaySales.add(expensees, 3, 4, 2, 1);
        displaySales.add(tax, 0, 6, 2, 1);
        displaySales.add(taxes, 3, 6, 2, 1);
        displaySales.add(profit, 0, 8, 2, 1);
        displaySales.add(profits, 3, 8, 2, 1);

        SalesReports.setCenter(displaySales);
        SalesReports.setBottom(HBoxButton(buttonOk));

        Scene scene5 = new Scene(SalesReports, 300, 300);

        primaryStage.setTitle("Sales Report");
        primaryStage.setScene(scene5);
        primaryStage.show();

        //button ok returns to the management menue
        buttonOk.setOnAction((ActionEvent eventOk)->{
            managementMode(primaryStage, buttonExitToMainMenue);
        });//end ok
    }
    public void manageInventory(Stage primaryStage, Button buttonExitToMainMenue)
    {
        //add all fields and labels for the fields to add inventory
        Button buttonAddInventory = new Button("Add Inventory");
        Button buttonExitInventory = new Button("Exit Inventory");
        //labels needed String item, double cost, double quantity, double markup
        Label inventoryInstructionLabel = new Label("Enter the Item,"
                + " Cost, Quantity, and Price markup\n"
                + " of the item you wish to add to the inventory.");

        Label itemLabel = new Label("Item Name:");
        Label costLabel = new Label("Item Cost:");
        Label quantityLabel = new Label("Item Quantity:");
        Label markupLabel = new Label("Item Markup:");

        TextField itemIn = new TextField();
        TextField costIn = new TextField();
        TextField quantityIn = new TextField();
        TextField markupIn = new TextField();

        BorderPane InventoryManagementMenue = new BorderPane();
        BorderPane inventoryLayoutPane = new BorderPane();
        GridPane inventoryInputLayout = new GridPane();
        inventoryInputLayout.setPadding(new Insets(15, 15, 15, 15));
        inventoryInputLayout.setHgap(5);
        inventoryInputLayout.setVgap(18);

        //inventoryInputLayout.add(inventoryInstructionLabel, 0, 0, 5, 5);
        inventoryInputLayout.add(itemLabel, 0, 0, 1, 2);
        inventoryInputLayout.add(itemIn, 2, 0, 1, 3);
        inventoryInputLayout.add(costLabel, 0, 2, 1, 2);
        inventoryInputLayout.add(costIn, 2, 2, 1, 3);
        inventoryInputLayout.add(quantityLabel, 0, 4, 1, 2);
        inventoryInputLayout.add(quantityIn, 2, 4, 1, 3);
        inventoryInputLayout.add(markupLabel, 0, 6, 1, 2);
        inventoryInputLayout.add(markupIn, 2, 6, 1, 3);

        inventoryLayoutPane.setTop(inventoryInstructionLabel);
        inventoryLayoutPane.setCenter(inventoryInputLayout);
        InventoryManagementMenue.setCenter(inventoryLayoutPane);
        InventoryManagementMenue.setBottom(HBoxButton(buttonAddInventory, buttonExitInventory ));

        Scene scene6 = new Scene(InventoryManagementMenue, 300, 300);

        primaryStage.setTitle("Add Inventory");
        primaryStage.setScene(scene6);
        primaryStage.show();

        //add inventory event
        buttonAddInventory.setOnAction((ActionEvent eventAddInventory)->{
            confirmAddInventory ( primaryStage, buttonExitToMainMenue, inventoryInputLayout, itemIn, costIn, quantityIn, markupIn );
        });//end add inventory

        //exit inventory takes the user back to the management menue
        buttonExitInventory.setOnAction((ActionEvent eventExitInventory)->{
            
            managementMode(primaryStage, buttonExitToMainMenue);
            
        });// end exit inventory
    }
    public void confirmAddInventory (Stage primaryStage, Button buttonExitToMainMenue, GridPane inventoryInputLayout,  TextField itemIn, TextField costIn, TextField quantityIn, TextField markupIn )
    {
        //confirm or back
        Button buttonConfirmAddInventory = new Button("Confirm");
        Button buttonBacktoInventory = new Button("Cancel");
        Label reviewInstructions = new Label("Please review the"
                + "input data, check for error.");

        BorderPane addInventoryMenue = new BorderPane();
        BorderPane inventoryReview = new BorderPane();

        inventoryReview.setTop(reviewInstructions);
        inventoryReview.setCenter(inventoryInputLayout);
        addInventoryMenue.setCenter(inventoryReview);
        addInventoryMenue.setBottom(HBoxButton(buttonConfirmAddInventory, buttonBacktoInventory));

        Scene scene7 = new Scene(addInventoryMenue , 300, 300);

        primaryStage.setTitle("Add Inventory");
        primaryStage.setScene(scene7);
        primaryStage.show();

        //confirm the add event
        buttonConfirmAddInventory.setOnAction((ActionEvent eventConfirmAddInventory)->
        {
            //and clear the input fields 
            manageInventory(primaryStage, buttonExitToMainMenue);
        });//end theconfirm event

        //the back button just goes back to the previous screen, 
        //basicly gives the user a seccond chance when adding 
        //input to make sure item is correctly input
        buttonBacktoInventory.setOnAction((ActionEvent eventBackToInventory)->{
            
           manageInventory(primaryStage, buttonExitToMainMenue);
           
        });//end baack
    }
    
    //defined the register aka"SalesMenue" to return from this method
    public BorderPane setSalesMenue(Button buttonMakeSale, Button buttonCancleSale, Button buttonExitToMainMenue, TextArea recipt, ComboBox<String> itemsForSale)
    {
        BorderPane SalesMenue = new BorderPane();
        BorderPane reciptArea = new BorderPane();
        
        HBox totalsBox = getTotalsBox(0,0,0);
        
        //set to non editable because it is for user reference only
        recipt.setPrefSize(295, 250);
        recipt.setEditable(false);
        //put recipt in scrolepane so when large it may be viewed with ease
        ScrollPane salesRecipt = new ScrollPane(recipt);
        
        //set the recipt to the recipt area
        reciptArea.setCenter(salesRecipt);
        reciptArea.setBottom(totalsBox);
         
        //set the recipt BorderPane to the center of the main border pane
        SalesMenue.setCenter(reciptArea);
                    
        //set actions for the combo box
        ObservableList<String> items = FXCollections.observableArrayList(register.itemName);
        itemsForSale.setOnAction((itemChosen) ->
        {
            //get index of object name
            int index = items.indexOf(itemsForSale.getValue());
            //get the sale
            register.getIndexSaleSelection(index);
            //set the recipt test with the add items method
            recipt.setText(AddItems(index, recipt.getText()));
            //clear the totals and reset with the total, tax, and total with tax acumulated with item
            totalsBox.getChildren().clear();
            register.sumTotal(index);
            register.taxTotal(index);
            register.sumWithTaxTotal(index);
            totalsBox.getChildren().add(getTotalsBox(register.getTotalSalePrice(),register.getTotalSaleTax(),register.getTotalSalePriceWithTax()));
        });
                            
                
        //set the items for sale on the left        
        SalesMenue.setLeft(itemsForSale);
        //set the bottom with the buttons formated with my method
        SalesMenue.setBottom(HBoxButton(buttonMakeSale, buttonCancleSale, buttonExitToMainMenue));
        
        //return the menue
        return SalesMenue;
    }
    //method to fromat the strings that enter the recipt box with the item name and price
    public String AddItems(int index, String existing)
    {
        String itemForSale;
        itemForSale = String.format("%s \n %-10s $%-10.2f",existing, register.itemName[index], register.getItemPrice(index));
        
        //return the string
        return itemForSale;
    }
    
    //an HBox to return the total prices in an HBox
    public HBox getTotalsBox(double price, double tax, double priceWithTax )
    {
        //created two vboxes and an hbox to hold and format the totals
        VBox totalsLabelBox = new VBox();
        VBox totalsBox = new VBox();
        HBox total = new HBox();
        
        Label totalPriceL = new Label("Price:");
        Label totalTaxL = new Label("Tax:");
        Label totalPriceWithTaxL = new Label("Total:");
        
        Label totalPrice = new Label(String.format("$%8.2f", price));
        Label totalTax = new Label(String.format("$%8.2f", tax));
        Label totalPriceWithTax = new Label(String.format("$%8.2f", priceWithTax));
        
        //put the labels in one v box
        totalsLabelBox.getChildren().addAll(totalPriceL, totalTaxL, totalPriceWithTaxL);
        //along with the totals in the other vbox
        totalsBox.getChildren().addAll(totalPrice, totalTax, totalPriceWithTax);
        //add both to the horizontal box for a nice simple format
        total.getChildren().addAll(totalsLabelBox, totalsBox);
        //set the background to grey for contrast
        Background grey = new Background(backgroundGray);
        total.setBackground(grey);
        
        //return the totals HBox
        return total;
    }
    //method to create the combo box with the items for sale, i did this in an 
    //attempt to make the items update dynamicly, but still working on this
    public ComboBox<String> updateItems()
    {
        //create the combobox with the item names to select for sale
        ComboBox<String> itemsForSale = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList(register.itemName);         
        itemsForSale.getItems().addAll(items);
        //I set the value to Items so it explains to the user what this field is for
        itemsForSale.setValue("Items");
        
        //return the combobox
        return itemsForSale;
    }
    
    //method to format a recipt for the sale
    public String outputRecipt()
    {
        //uses old to save the previously created output to add the new output to it
        String old;               
        String outputRecipt = "";
        for(int index = 0; index < register.maxSalesIndex; index++)
        {
            old = outputRecipt;
            //indexSaleSelection[],  itemName[], itemCost_Quantity_markup[][]
            outputRecipt = String.format("%s \n%-10s %1s %-10.2f",
                    old,
                    register.itemName[register.indexSaleSelection[index]],
                    "$",
                    register.getItemPrice(register.indexSaleSelection[index]));
        }
        String confirmPrice = String.format("%-10s %1s %-10.2f", "Price:", "$", register.getTotalSalePrice());
        String confirmTax = String.format("%-10s %1s %-10.2f", "Tax:", "$", register.getTotalSaleTax());
        String confirmPriceWithTax = String.format("%-10s %1s %-10.2f", "Total:", "$", register.getTotalSalePriceWithTax());
        old = outputRecipt;
        outputRecipt = String.format("%s \n\n%s \n%s \n%s", old, confirmPrice, confirmTax, confirmPriceWithTax);
          
        //return the string
        return outputRecipt;
    }
    //a method to arrange buttons in an HBox to place in the border pane
    // *Accepts 2 buttons
    public HBox HBoxButton(Button B1, Button B2)
    {
        HBox boxButton = new HBox();
        boxButton.setPadding(new Insets(10, 10, 10, 10));
        boxButton.setSpacing(10);
        boxButton.setStyle("-fx-background-color: red;");
        boxButton.getChildren().addAll(B1, B2);
               
        return boxButton;
    }
    // *overloaded to accept 3 buttons
    public HBox HBoxButton(Button B1, Button B2, Button B3)
    {
        HBox boxButton = new HBox();
        boxButton.setPadding(new Insets(10, 10, 10, 10));
        boxButton.setSpacing(10);
        boxButton.setStyle("-fx-background-color: red;");
        boxButton.getChildren().addAll(B1, B2, B3);
               
        return boxButton;
    }
    //overload for 1
    public HBox HBoxButton(Button B1)
    {
        HBox boxButton = new HBox();
        boxButton.setPadding(new Insets(10, 10, 10, 10));
        boxButton.setSpacing(10);
        boxButton.setStyle("-fx-background-color: red;");
        boxButton.getChildren().addAll(B1);
               
        return boxButton;
    }
    
    //main method
    public static void main(String[] args) {
        launch(args);
    }
    
}
