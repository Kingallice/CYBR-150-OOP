/*
Assignment: Final Project
Name: Noah Mosel
Project: ATM
 */
package atm;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javax.swing.JOptionPane;

public class StartATM extends Application{
    Button btnLoginStart, btnCreateAcc, //First Menu's Buttons
            btnLogin, //Pin Entry Menu Buttons
            btnDepoScene, btnWithScene, btnTranScene, btnBillScene, //Action Select Menu Buttons
            btnBackD, btnBackW, btnBackT, btnBackP, //Back Buttons for each Action Scene
            btnDeposit, btnWithdraw, btnTransfer, btnBill, btnLogOut, //Buttons to perform Actions
            btnPayInterest, btnRefill, btnDisable, btnPrintReport, btnALogOut, //Buttons in Admin Menu
            btnCreateAccount;
    RadioButton rdbSavingsD,rdbCheckingD, //Account type during Deposit
                    rdbSavingsW,rdbCheckingW, //Account type during Withdrawal
                    rdbSavingsT,rdbCheckingT; //Account transfer direction
    TextField txtName, txtAccNum,txtAmountD, txtAmountW, txtAmountT, txtAmountP, //Text fields
            txtCreateName,txtInitialDeposit; 
    Label lblCBalance, lblSBalance, lblBalance; //
    PasswordField pfPin, pfCreatePin, pfConfirmPin; //Pin entry field
    
    Customer customer;
    Checking CheckingAcc;
    Savings SavingsAcc;
    Bank BankAcc;
    
    String strName, strAccNum,strPin, strFileCustomer, strFileAcc,strAmount;
    String strAccPath = "..\\ATM\\src\\atm\\Accounts.txt", 
            strCustomerPath = "..\\ATM\\src\\atm\\Customers.txt";
    String[] strArrayFileCustomer, strArrayFileAcc;
    
    boolean blnCustomer = false, blnLocked = false, blnAdminMode = false;
    
    int indexCustomer = -1, intAttempts = 0, indexAcc = 0, indexSAcc, indexCAcc;  
    double dblAmount = 0;
    
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage)
    {
        stage.setTitle("ATM");
        
        //Initializes scenes
        Scene sceneStart = startUpScene();
        Scene sceneLogin = LoginScene();
        Scene sceneAction = ActionScene();
        Scene sceneDeposit = DepositScene();
        Scene sceneWithdraw = WithdrawScene();
        Scene sceneTransfer = TransferScene();
        Scene sceneBillPay = BillPayScene();
        Scene sceneAdmin = AdminScene();
        Scene sceneCreate = CreateScene();
        
        //Sets scene to starting scene
        stage.setScene(sceneStart);
        stage.show();
        
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight());
        
        
        //Checks if name entered is a customer.
        //If customer, then the ATM asks for the pin to the account.
        btnLoginStart.setOnAction(event -> {
        //<editor-fold>
        try {
                strName = txtName.getText();
                File fileCustomers = new File(strCustomerPath);
                strArrayFileCustomer = FiletoArray(fileCustomers);                
                int len = strArrayFileCustomer.length;
                for (int i = 0; i < len; i++) {
                    //System.out.println("("+strName+")");
                    //System.out.println("("+strArrayFileCustomer[i] +")");
                    if (strArrayFileCustomer[i].compareTo("Locked") == 0)
                        blnLocked = true;
                    if (strName.compareTo(strArrayFileCustomer[i]) == 0) {
                        //System.out.println("Name Found");
                        blnCustomer = true;
                        indexCustomer = i;
                        break;
                    }
                }
                if(strName.compareTo("") == 0)
                {
                    JOptionPane.showMessageDialog(null, "Name is a required field.");
                }
                else if (blnLocked && strName.compareTo("Admin") != 0)
                {
                    JOptionPane.showMessageDialog(null, "This machine is out of service.");
                }
                else if (intAttempts >= 3)
                {
                    JOptionPane.showMessageDialog(null, "Too many failed attempts. Please come back later");
                }
                else if (!blnCustomer)
                {
                    JOptionPane.showMessageDialog(null, strName + " is not a customer. Please make an account.");
                }
                else if ((blnCustomer && !blnLocked) || strName.compareTo("Admin") == 0)
                {
                    stage.setScene(sceneLogin);
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
//</editor-fold>
        
        //Brings up account creation menu
        btnCreateAcc.setOnAction(event ->{
            stage.setScene(sceneCreate);
        });
        //Creates account
        btnCreateAccount.setOnAction(event ->{
            try {
                strName = txtCreateName.getText();
                strPin = pfCreatePin.getText();
                String strAmount = txtInitialDeposit.getText();
                dblAmount = Double.parseDouble(strAmount);
                if(dblAmount < 0)
                {
                    JOptionPane.showMessageDialog(null, "Initial deposit must be positive.");
                }
                else if (strPin.compareTo(pfConfirmPin.getText()) != 0) 
                {
                    JOptionPane.showMessageDialog(null, "Pins do not match");
                } 
                else 
                {
                    CreateAccount(strName, strPin, dblAmount);
                    stage.setScene(sceneStart);
                    txtCreateName.setText("");
                    pfCreatePin.setText("");
                    pfConfirmPin.setText("");
                    txtInitialDeposit.setText("");
                }
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Initial deposit must be a number!");
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        
        //Checks if the pin entered is the same as the pin on file
        //If pin is incorrect intAttempt is increased, if attempt >= 3, ATM is locked
        btnLogin.setOnAction(event -> {
            //<editor-fold>
            try {
                strAccNum = txtAccNum.getText();
                strPin = pfPin.getText();
                File fileAcc = new File(strAccPath);
                strArrayFileAcc = FiletoArray(fileAcc);
                int len = strArrayFileAcc.length;
                
                BankAcc = new Bank(strArrayFileAcc[indexAcc + 1],
                            strArrayFileAcc[indexAcc + 2], 
                            Double.parseDouble(strArrayFileAcc[indexAcc + 3]));
                BankAcc.RefillATM();
                
                if (intAttempts >= 3) {
                    JOptionPane.showMessageDialog(null, "Too many failed attempts. Please come back later.");
                    stage.setScene(sceneStart);
                } 
                else if(strName.compareTo("Admin") == 0)
                {
                    if(strPin.compareTo(strArrayFileCustomer[indexCustomer + 1].trim()) == 0)
                    {
                        //System.out.println("Admin logged in");
                        stage.setScene(sceneAdmin);
                        blnAdminMode = true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Incorrect Pin");
                        intAttempts++;
                    }
                }
                else
                {   
                    if(strAccNum.compareTo("") == 0)
                        JOptionPane.showMessageDialog(null, "Account number is a required field.");
                    else if(strPin.compareTo("") == 0)
                        JOptionPane.showMessageDialog(null, "Pin is a required field.");
                    else
                    {
                        //System.out.println(BankAcc.getAccNum());
                        //System.out.println(BankAcc.getBalance());

                            for (int i = 2; i < len; i += 4) 
                            {
                                //System.out.println("("+strAccNum+")");
                                //System.out.println("("+strArrayFileAcc[i] +")");
                                if(strAccNum.compareTo(strArrayFileAcc[i]) == 0)
                                {
                                    indexAcc = i - 2;
                                    if(strArrayFileAcc[indexAcc].compareTo("C") == 0)
                                    {
                                        indexCAcc = indexAcc;
                                        CheckingAcc = new Checking(strArrayFileAcc[indexCAcc + 1],
                                            strArrayFileAcc[indexCAcc + 2], 
                                            Double.parseDouble(strArrayFileAcc[indexCAcc + 3]));
    //                                    System.out.println("Checking Account Found");
    //                                    System.out.println("Index: " + indexCAcc);
    //                                    System.out.println("Pin: " + strArrayFileAcc[indexCAcc + 1]);
    //                                    System.out.println("Account Number: " + strArrayFileAcc[indexCAcc + 2]);
    //                                    System.out.println("Balance: " + strArrayFileAcc[indexCAcc + 3]);  
                                    }
                                    else if(strArrayFileAcc[indexAcc].compareTo("S") == 0)
                                    {
                                        indexSAcc = indexAcc;
                                        SavingsAcc = new Savings(strArrayFileAcc[indexSAcc + 1],
                                            strArrayFileAcc[indexSAcc + 2], 
                                            Double.parseDouble(strArrayFileAcc[indexSAcc + 3]));
                                    }
                                }      
                            }    
                        if (!blnLocked) 
                        {
                            String strCustomerPin = strArrayFileCustomer[indexCustomer + 1].trim();
                            //System.out.println(strPin);
                            //System.out.println(strCustomerPin);
                            if (strPin.compareTo(strCustomerPin) == 0 && 
                                    strArrayFileAcc[indexAcc + 1].compareTo(strCustomerPin) == 0) 
                            {
                                //System.out.println("Access Granted");
                                lblSBalance.setText("Savings Balance: $" + SavingsAcc.getBalance());
                                lblCBalance.setText("Checking Balance: $" + CheckingAcc.getBalance());
                                customer = new Customer(strName, strPin);
                                stage.setScene(sceneAction);
                                intAttempts = 0;
                                indexAcc = 0;
                            } 
                            else 
                            {
                            //System.out.println("Wrong Pin");
                            JOptionPane.showMessageDialog(null, "Incorrect Pin");
                            intAttempts++;
                            }
                        }
                    }
                }
                lblBalance.setText("Bank Balance: $" + BankAcc.getBalance());
                strArrayFileAcc[indexAcc + 3] = BankAcc.getBalance() + "";
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
//</editor-fold>
        
        //Logs out the user
        btnLogOut.setOnAction(event ->{
            stage.setScene(sceneStart);
            strName = "";
            strPin = "";
            txtName.setText("");
            txtAccNum.setText("");
            pfPin.setText("");
            CheckingAcc = new Checking();
            SavingsAcc = new Savings();
        });
        
        //Sets scene to Deposit scene
        btnDepoScene.setOnAction(event ->{
            stage.setScene(sceneDeposit);
        });
        
        //Sets scene to Withdraw scene
        btnWithScene.setOnAction(event ->{
            stage.setScene(sceneWithdraw);
        });
        
        //Sets scene to Transfer scene
        btnTranScene.setOnAction(event ->{
            stage.setScene(sceneTransfer);
        });
        
        //Sets scene to Bill Payment scene
        btnBillScene.setOnAction(event ->{
            stage.setScene(sceneBillPay);
        });
        
        //Sets scene back to Action Selection scene
        //Clears txtAmount
        //<editor-fold>
        btnBackD.setOnAction(event ->{
            txtAmountD.setText("");
            stage.setScene(sceneAction);
        });
        btnBackW.setOnAction(event ->{
            txtAmountW.setText("");
            stage.setScene(sceneAction);
        });
        btnBackT.setOnAction(event ->{
            txtAmountT.setText("");
            stage.setScene(sceneAction);
        });
        btnBackP.setOnAction(event ->{
            txtAmountP.setText("");
            stage.setScene(sceneAction);
        });
        //</editor-fold>
        
        //Asks user what type of account to deposit in
        //Deposits entered amount        
        btnDeposit.setOnAction(event ->{
            try {
                strAmount = txtAmountD.getText();
                dblAmount = Double.parseDouble(strAmount);
                if(rdbCheckingD.isSelected())
                {
                    CheckingAcc.Deposit(dblAmount);
                }
                else
                {
                    SavingsAcc.Deposit(dblAmount);
                }
                strArrayFileAcc[indexSAcc + 3] = SavingsAcc.getBalance() + "";
                strArrayFileAcc[indexCAcc + 3] = CheckingAcc.getBalance() + "";
                ArraytoFile(strArrayFileAcc,strAccPath);
                
                lblSBalance.setText("Savings Balance: $" + SavingsAcc.getBalance());
                lblCBalance.setText("Checking Balance: $" + CheckingAcc.getBalance());
                rdbCheckingD.setSelected(true);
                txtAmountD.setText("");
                stage.setScene(sceneAction);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number!");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        
        //Asks user what type of account to withdraw from
        //Withdraws entered amount
        btnWithdraw.setOnAction(event ->{
            try {
                strAmount = txtAmountW.getText();
                dblAmount = Double.parseDouble(strAmount);
                if(rdbCheckingW.isSelected())
                {
                    CheckingAcc.Withdraw(dblAmount);
                }
                else
                {
                    SavingsAcc.Withdraw(dblAmount);
                }
                if((dblAmount % 10 == 0) && (dblAmount < 1000))
                    BankAcc.Withdraw(dblAmount);
                strArrayFileAcc[indexSAcc + 3] = SavingsAcc.getBalance() + "";
                strArrayFileAcc[indexCAcc + 3] = CheckingAcc.getBalance() + "";
                strArrayFileAcc[indexAcc + 3] = BankAcc.getBalance() + "";
                ArraytoFile(strArrayFileAcc, strAccPath);
                
                lblSBalance.setText("Savings Balance: $" + SavingsAcc.getBalance());
                lblCBalance.setText("Checking Balance: $" + CheckingAcc.getBalance());
                rdbCheckingW.setSelected(true);
                txtAmountW.setText("");
                stage.setScene(sceneAction);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number!");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        
        //Transfers entered amount from checking to savings, vice versa
        btnTransfer.setOnAction(event ->{
            try {
                strAmount = txtAmountT.getText();
                dblAmount = Double.parseDouble(strAmount);
                if(rdbCheckingT.isSelected())
                {
                    if(SavingsAcc.getBalance() - dblAmount > 0)
                    {
                        CheckingAcc.TransferIn(dblAmount);
                        SavingsAcc.TransferOut(dblAmount);
                    }
                    else
                        JOptionPane.showMessageDialog(null, 
                        "Your savings account balance is too low for this transaction." + "\n" + 
                                "Balance: " + SavingsAcc.getBalance());
                }
                else
                {
                    if(CheckingAcc.getBalance() - dblAmount > 0)
                    {
                    SavingsAcc.TransferIn(dblAmount);
                    CheckingAcc.TransferOut(dblAmount);
                    }
                    else
                        JOptionPane.showMessageDialog(null, 
                        "Your checking account balance is too low for this transaction." + "\n" + 
                                "Balance: " + CheckingAcc.getBalance());
                }
                strArrayFileAcc[indexSAcc + 3] = SavingsAcc.getBalance() + "";
                strArrayFileAcc[indexCAcc + 3] = CheckingAcc.getBalance() + "";
                
                ArraytoFile(strArrayFileAcc, strAccPath);
                
                lblSBalance.setText("Savings Balance: $" + SavingsAcc.getBalance());
                lblCBalance.setText("Checking Balance: $" + CheckingAcc.getBalance());
                rdbCheckingT.setSelected(true);
                txtAmountT.setText("");
                stage.setScene(sceneAction);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number!");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        
        //Pays bill of entered amount and charges an extra fee
        btnBill.setOnAction(event ->{
            try {
                strAmount = txtAmountP.getText();
                dblAmount = Double.parseDouble(strAmount);
                
                CheckingAcc.PayBill(dblAmount);
                strArrayFileAcc[indexCAcc + 3] = CheckingAcc.getBalance() + "";
                ArraytoFile(strArrayFileAcc, strAccPath);
                
                lblSBalance.setText("Savings Balance: $" + SavingsAcc.getBalance());
                lblCBalance.setText("Checking Balance: $" + CheckingAcc.getBalance());
                txtAmountP.setText("");
                stage.setScene(sceneAction);
            } 
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number!");
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        //Calculates interest for every account
        btnPayInterest.setOnAction(event ->{
            int len = strArrayFileAcc.length;
            for(int i = 0; i < len; i += 4)
            {
                if(strArrayFileAcc[i].compareTo("S") == 0)
                {
                    SavingsAcc = new Savings(strArrayFileAcc[i+1],strArrayFileAcc[i+2],
                            Double.parseDouble(strArrayFileAcc[i+3]));
                    SavingsAcc.PayInterest();
                    strArrayFileAcc[i+3] = SavingsAcc.getBalance() + "";
                }
            }
            JOptionPane.showMessageDialog(null,"Interest paid.");
            ArraytoFile(strArrayFileAcc, strAccPath);
        });
        
        btnRefill.setOnAction(event ->{
            BankAcc.RefillATM();
            lblBalance.setText("Bank Balance: $" + BankAcc.getBalance());
            strArrayFileAcc[indexAcc + 3] = BankAcc.getBalance() + "";
            ArraytoFile(strArrayFileAcc, strAccPath);
        });
        btnDisable.setOnAction(event ->{
            if(blnLocked)
            {
                blnLocked = false;
                strArrayFileCustomer[0] = "Unlocked";
                JOptionPane.showMessageDialog(null, "Machine is now Active.");
            }
            else
            {
                blnLocked = true;
                strArrayFileCustomer[0] = "Locked";
                JOptionPane.showMessageDialog(null,"Machine is now Locked.");
            }
            ArraytoFile(strArrayFileCustomer, strCustomerPath);
        });
        btnPrintReport.setOnAction(event -> {
            PrintReport(strArrayFileAcc);
            JOptionPane.showMessageDialog(null, "Report can be found in the Report.txt file.");
        });
        btnALogOut.setOnAction(event ->{
            stage.setScene(sceneStart);
            strName = "";
            strPin = "";
            txtName.setText("");
            txtAccNum.setText("");
            pfPin.setText("");
            CheckingAcc = new Checking();
            SavingsAcc = new Savings();
        });
    }
    //Creates the starting scene
    public Scene startUpScene()
    {
        Label lblName = new Label("Name:");
        txtName = new TextField();
        VBox vboxName = new VBox(lblName,txtName);
        vboxName.setSpacing(5);
        btnLoginStart = new Button("Login");
        btnLoginStart.setDefaultButton(true);
        btnCreateAcc = new Button("Create Account");
        
        btnLoginStart.setPrefSize(150, 40);
        btnCreateAcc.setPrefSize(btnLoginStart.getPrefWidth(), btnLoginStart.getPrefHeight());
        
        GridPane gridLogin = new GridPane();
        gridLogin.setPadding(new Insets(150));
        gridLogin.setAlignment(Pos.CENTER);
        gridLogin.setVgap(15);
        gridLogin.add(vboxName, 0, 0);
        gridLogin.add(btnLoginStart, 0, 1);
        gridLogin.add(btnCreateAcc, 0, 2);

        Scene sceneStart = new Scene(gridLogin);
        return sceneStart;
    }
    //Creates the login scene
    public Scene LoginScene()
    {
        Label lblAccNum = new Label("Account Number:");
        txtAccNum = new TextField();
        Label lblInfo = new Label("Pin:");
        pfPin = new PasswordField();
        btnLogin = new Button("Login");  
        btnLogin.setDefaultButton(true);
        
        GridPane gridLogin = new GridPane();
        gridLogin.add(lblAccNum, 0, 0);
        gridLogin.add(txtAccNum, 0, 1);
        gridLogin.add(lblInfo, 0, 2);
        gridLogin.add(pfPin,0,3);
        gridLogin.add(btnLogin,0,4);
        gridLogin.setVgap(5);

        Scene sceneLogin = new Scene(gridLogin);
        gridLogin.setAlignment(Pos.CENTER);
        return sceneLogin;
    }
    //Sets up scene for account creation
    public Scene CreateScene()
    {
        btnCreateAccount = new Button("Create Account");
        
        txtCreateName = new TextField();
        pfCreatePin = new PasswordField();
        pfConfirmPin = new PasswordField();
        txtInitialDeposit = new TextField();
        
        GridPane gridCreate = new GridPane();
        gridCreate.add(new Label("Name:"), 0, 0);
        gridCreate.add(txtCreateName, 1, 0);
        gridCreate.add(new Label("Pin:"), 0, 1);
        gridCreate.add(pfCreatePin, 1, 1);
        gridCreate.add(new Label("Confirm Pin:"), 0, 2);
        gridCreate.add(pfConfirmPin, 1, 2);
        gridCreate.add(new Label("Initial Deposit:"), 0, 3);
        gridCreate.add(txtInitialDeposit, 1, 3);
        gridCreate.setAlignment(Pos.CENTER);
        gridCreate.setVgap(5);
        gridCreate.setHgap(2);
        
        VBox vboxCreate = new VBox(new Label("Create Account"),gridCreate,btnCreateAccount);
        vboxCreate.setAlignment(Pos.CENTER);
        vboxCreate.setSpacing(10);
        
        Scene sceneCreate = new Scene(vboxCreate);
        return sceneCreate;
    }
    //Creates scene that has options of transactions
    public Scene ActionScene()
    {
        lblSBalance = new Label("Savings Balance: ");
        lblCBalance = new Label("Checking Balance: ");
        btnDepoScene = new Button("Deposit");
        btnWithScene = new Button("Withdraw");
        btnTranScene = new Button("Transfer");
        btnBillScene = new Button("Bill Payment");
        btnLogOut = new Button("Log Out");
        btnLogOut.setCancelButton(true);
        
        btnDepoScene.setPrefSize(100, 50);
        btnWithScene.setPrefSize(100, 50);
        btnTranScene.setPrefSize(100, 50);
        btnBillScene.setPrefSize(100, 50);
        
        GridPane gridAction = new GridPane();
        gridAction.add(btnDepoScene, 0, 1);
        gridAction.add(btnWithScene, 0, 2);
        gridAction.add(btnTranScene, 0, 3);
        gridAction.add(btnBillScene, 0, 4);
        gridAction.setVgap(5);
        
        VBox vboxBalance = new VBox(lblSBalance,lblCBalance);
        vboxBalance.setSpacing(5);
        VBox vboxAction = new VBox(vboxBalance, gridAction, btnLogOut);
        vboxAction.setSpacing(20);
        vboxBalance.setAlignment(Pos.CENTER);
        gridAction.setAlignment(Pos.CENTER);        
        Scene sceneAction = new Scene(vboxAction);
        vboxAction.setAlignment(Pos.CENTER);
        return sceneAction;
    }
    //Sets up deposit scene
    public Scene DepositScene()
    {
        rdbSavingsD = new RadioButton("Savings");
        rdbCheckingD = new RadioButton("Checking");
        rdbCheckingD.selectedProperty().set(true);

        ToggleGroup grpAccType = new ToggleGroup();
        grpAccType.getToggles().addAll(rdbCheckingD, rdbSavingsD);
        VBox vboxAccType = new VBox(new Label("Account Type:"), rdbCheckingD, rdbSavingsD);
        vboxAccType.setSpacing(3);
        
        txtAmountD = new TextField();
        VBox vboxAmount = new VBox(new Label("Amount:"),txtAmountD);
        
        btnDeposit = new Button("Deposit");
        btnDeposit.setDefaultButton(true);
        btnBackD = new Button("Back");
        btnBackD.setCancelButton(true);
        btnBackD.setPrefSize(70, 30);
        btnDeposit.setPrefSize(70,30);
        HBox hboxButtons = new HBox(btnBackD, btnDeposit);
        hboxButtons.setSpacing(10);        
        
        GridPane gridDeposit = new GridPane();
        gridDeposit.add(vboxAccType, 0, 0);
        gridDeposit.add(vboxAmount, 0, 1);
        gridDeposit.add(hboxButtons, 0, 2);
        gridDeposit.setVgap(5);
        gridDeposit.setAlignment(Pos.CENTER);
                
        Scene sceneDeposit = new Scene(gridDeposit);
        return sceneDeposit;
    }
    //Sets up withdraw scene
    public Scene WithdrawScene()
    {
        rdbSavingsW = new RadioButton("Savings");
        rdbCheckingW = new RadioButton("Checking");
        rdbCheckingW.selectedProperty().set(true);

        ToggleGroup grpAccType = new ToggleGroup();
        grpAccType.getToggles().addAll(rdbCheckingW, rdbSavingsW);
        VBox vboxAccType = new VBox(new Label("Account Type:"), rdbCheckingW, rdbSavingsW);
        vboxAccType.setSpacing(3);
        
        txtAmountW = new TextField();
        VBox vboxAmount = new VBox(new Label("Amount:"),txtAmountW);
        
        btnWithdraw = new Button("Withdraw");
        btnWithdraw.setDefaultButton(true);
        btnBackW = new Button("Back");
        btnBackW.setCancelButton(true);
        btnBackW.setPrefSize(70, 30);
        btnWithdraw.setPrefSize(70,30);
        HBox hboxButtons = new HBox(btnBackW, btnWithdraw);
        hboxButtons.setSpacing(10);       

        GridPane gridWithdraw = new GridPane();
        gridWithdraw.add(vboxAccType, 0, 0);
        gridWithdraw.add(vboxAmount, 0, 1);
        gridWithdraw.add(hboxButtons, 0, 2);
        gridWithdraw.setVgap(5);
        gridWithdraw.setAlignment(Pos.CENTER);
                      
        Scene sceneWithdraw = new Scene(gridWithdraw);
        return sceneWithdraw;
    }
    //Sets up transfer scene
    public Scene TransferScene()
    {
        rdbSavingsT = new RadioButton("Checking to Savings");
        rdbCheckingT = new RadioButton("Savings to Checking");
        rdbCheckingT.selectedProperty().set(true);

        ToggleGroup grpAccType = new ToggleGroup();
        grpAccType.getToggles().addAll(rdbCheckingT, rdbSavingsT);
        VBox vboxAccType = new VBox(new Label("Transfer Type:"), rdbCheckingT, rdbSavingsT);
        vboxAccType.setSpacing(3);
        
        txtAmountT = new TextField();
        VBox vboxAmount = new VBox(new Label("Amount:"),txtAmountT);
        
        btnTransfer = new Button("Transfer");
        btnTransfer.setDefaultButton(true);
        btnBackT = new Button("Back"); 
        btnBackT.setCancelButton(true);
        btnBackT.setPrefSize(70, 30);
        btnTransfer.setPrefSize(70,30);
        HBox hboxButtons = new HBox(btnBackT, btnTransfer);
        hboxButtons.setSpacing(10);      
        
        GridPane gridTransfer = new GridPane();
        gridTransfer.add(vboxAccType, 0, 0);
        gridTransfer.add(vboxAmount, 0, 1);
        gridTransfer.add(hboxButtons, 0, 2);
        gridTransfer.setVgap(5);
        gridTransfer.setAlignment(Pos.CENTER);
        
        Scene sceneTransfer = new Scene(gridTransfer);
        return sceneTransfer;
    }
    //Sets up bill payment scene
    public Scene BillPayScene()
    {        
        txtAmountP = new TextField();
        VBox vboxAmount = new VBox(new Label("Amount: ($1.25 Fee)"),txtAmountP);
        vboxAmount.setSpacing(1);
        
        btnBill = new Button("Pay Bill");
        btnBill.setDefaultButton(true);
        btnBackP = new Button("Back");
        btnBackP.setCancelButton(true);
        
        btnBackP.setPrefSize(70, 30);
        btnBill.setPrefSize(70,30);
        HBox hboxButtons = new HBox(btnBackP, btnBill);
        hboxButtons.setSpacing(10);  
        
        GridPane gridBillPay = new GridPane();
        gridBillPay.add(vboxAmount, 0, 0);
        gridBillPay.add(hboxButtons, 0, 1);
        gridBillPay.setVgap(5);
        gridBillPay.setAlignment(Pos.CENTER);

        Scene sceneBillPay = new Scene(gridBillPay);
        return sceneBillPay;
    }
    //Sets up Admin Scene
    public Scene AdminScene()
    {
        Label lblAdmin = new Label("Admin Menu");
        lblBalance = new Label("Bank Balance: $");
        
        btnPayInterest = new Button("Pay Interest");
        btnRefill = new Button("Refill ATM");
        btnDisable = new Button("Toggle Status");
        btnPrintReport = new Button("Print Report");
        btnALogOut = new Button("Log Out");
        
        btnPayInterest.setPrefSize(100, 50);
        btnRefill.setPrefSize(100, 50);
        btnDisable.setPrefSize(100, 50);
        btnPrintReport.setPrefSize(100, 50);
        
        GridPane gridAdmin = new GridPane();
        gridAdmin.add(btnPayInterest, 0, 0);
        gridAdmin.add(btnRefill, 0, 1);
        gridAdmin.add(btnDisable, 0, 2);
        gridAdmin.add(btnPrintReport, 0, 3);
        gridAdmin.setVgap(5);
        gridAdmin.setAlignment(Pos.CENTER);
        
        VBox vboxMain = new VBox(lblAdmin, lblBalance, gridAdmin, btnALogOut);
        vboxMain.setAlignment(Pos.CENTER);
        vboxMain.setSpacing(2);
        
        Scene sceneAdmin = new Scene(vboxMain);
        return sceneAdmin;
    }
    //Creates an String array using a file
    public String[] FiletoArray(File file)
    {
        String[] strArrayFile = new String[0];
        try {
            Scanner readFile = new Scanner(file);
            String strFile = "";
            while (readFile.hasNextLine()) {
                
                strFile = (strFile + readFile.nextLine());
            }
            strArrayFile = strFile.split(",");
            int len = strArrayFile.length;
            for(int i = 0; i < len; i++)
            {
                strArrayFile[i] = strArrayFile[i].trim();
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + file.getName() + " does not exist.");
        }
        return strArrayFile;
    }
    //Saves array as a file with path
    public void ArraytoFile(String[] Array, String Path)
    {
        String strFile = "";
        try {
            int len = Array.length;
            for(int i = 0; i < len; i++)
            {
                if(Array[i].compareTo("") != 0)
                {
                    strFile += Array[i] + ",";
                    if ((i+1)%4 == 0 && Path.compareTo(strAccPath) == 0)
                        strFile += "\n";
                    else if(((i+1)%2 == 0) && Path.compareTo(strCustomerPath) == 0)
                        strFile += "\n";
                }
            }
            File file = new File(Path);
            FileWriter fileWriter = new FileWriter(file);
            System.out.print(strFile);
            fileWriter.write(strFile);
            fileWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    //Prints array to a file that can be accessed later
    public void PrintReport(String[] Array)
    {
        String strFile = "";
        try {
            int len = Array.length;
            for(int i = 0; i < len; i++)
            {
                strFile += Array[i] + ",";
                if ((i+1)%4 == 0)
                    strFile += "\n";
            }
            File file = new File("..\\ATM\\src\\atm\\Report.txt");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(strFile);
            fileWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    //Creates Account
    public void CreateAccount(String Name, String Pin, double Balance)
    {
        String strAccFile = "", strCustomerFile = "", AccNum = "";
        try {
            File fileCustomer = new File(strCustomerPath);
            File fileAcc = new File(strAccPath);
            
            String[] ArrayCustomers = FiletoArray(fileCustomer);
            int lenC = ArrayCustomers.length;
            
            FileWriter fileWriterC = new FileWriter(fileCustomer, true);
            fileWriterC.append("\n" + Name + "," + Pin + ",");
            fileWriterC.close();
            
            String [] ArrayAccounts = FiletoArray(fileAcc);
            int lenA = ArrayAccounts.length;
            AccNum = ((Integer.parseInt(ArrayAccounts[lenA - 2]) + 1) + "");
           
            FileWriter fileWriterA = new FileWriter(fileAcc, true);
            fileWriterA.append("\n" + "C" + "," + Pin + "," + AccNum + "," + "0" + ",");
            fileWriterA.append("\n" + "S" + "," + Pin + "," + AccNum + "," + Balance + ",");
            fileWriterA.close();

            JOptionPane.showMessageDialog(null,"IMPORTANT:" + "\n" + "Account Number: " + AccNum);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}