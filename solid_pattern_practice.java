//here we will implement the solid principle with the help of the problem 
/*
Here are some introductory problems that focus on each SOLID principle to help you build a solid foundation in low-level design (LLD):

No.	Problem	SOLID Principle Focus
1	Single Responsibility Principle (SRP)	
Create a simple Book class that has methods for adding and retrieving book details. Ensure it doesn’t handle file I/O directly; separate file handling into a different class (BookPersistence).		
2	Open/Closed Principle (OCP)	
Design a class named ShapeAreaCalculator that calculates the area of different shapes (Circle, Rectangle). Use interfaces so that adding new shapes (like Triangle) doesn’t require changing existing code.		
3	Liskov Substitution Principle (LSP)	
Implement a Bird class with a fly() method. Create subclasses for Sparrow and Ostrich, where Ostrich cannot fly. Ensure your design doesn’t violate LSP by avoiding the fly() method for flightless birds.		
4	Interface Segregation Principle (ISP)	
Design interfaces for Printer and Scanner. Create a MultiFunctionPrinter class that implements both. Ensure that classes needing only scanning or printing don’t depend on the other functionalities.		
5	Dependency Inversion Principle (DIP)	
Create a simple Notification class that uses EmailService to send notifications. Reverse the dependency by introducing an INotificationService interface, allowing you to easily switch to SMSService in the future without altering the Notification class.	

*/

//example-1
//implement the SOLID Principle 

//below is the incorrect code - 

public class ReportManager {

    public void sendReportByEmail(String reportContent, String email) {
        // Code to send via SMTP
        System.out.println("Sending to " + email + ": " + reportContent);
    }
}
//my implementaiton 
public class ReportManagerGenerateReport
{
	public String generateReport() {
        // Generate business report
        return "Sales Report Content";
    }
}



public class saveReportToDatabase
{
	 public void saveReportToDatabase(String reportContent) {
        // Code to save report in database
        System.out.println("Saving to DB: " + reportContent);
    }
}

public class ReportManagerPrintReport
{
	public void printReport(String reportContent) {
        // Code to format and send to printer
        System.out.println("Printing: " + reportContent);
    }
}


// incorrect code for single responsiblity principle  
public class OrderProcessor {

   

    

   

   

   
}

//here below will be my correct code 

public class GenerateInvoicePdf
{
	
	 public void generateInvoicePdf(Order order) {
        // 5. Reporting: PDF generation
        PdfGenerator pdf = new PdfGenerator();
        pdf.createInvoice(order);
        System.out.println("Invoice PDF generated.");
    }
}

public class SendOrderConfirmationEmail
{
	
	 public void sendOrderConfirmationEmail(Order order) {
        // 4. Email notification
        EmailService smtp = new EmailService();
        smtp.send(order.getCustomer().getEmail(), "Your order is confirmed");
        System.out.println("Confirmation email sent.");
    }
}

public class SaveOrderToDatabase

{
	
	 public void saveOrderToDatabase(Order order) {
        // 3. Persistence logic
        DatabaseConnection conn = DatabaseConnection.open();
        conn.execute("INSERT INTO orders ...");
        conn.close();
        System.out.println("Order saved to database.");
    }
	
}


public class CalculateTotalPrice

{
	
	public void calculateTotal(Order order) {
        // 2. Business logic: compute total price
        double total = 0;
        for (Item item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotal(total);
        System.out.println("Total calculated: $" + total);
    }
	
}

public class CreateOrder

{
	
	 public Order createOrder(Customer customer, List<Item> items) {
        // 1. Business logic: assemble order
        Order order = new Order(customer, items);
        System.out.println("Order created for customer: " + customer.getName());
        return order;
    }
}


//here is the incorrect code 
public class InventoryManager {

    

}

//below is the correct order 
public class SaveInventoryToDatabase
{
	
	 public void saveInventoryToDatabase(List<Item> items) {
        // 3. Persistence logic: store inventory records in DB
        DatabaseConnection conn = DatabaseConnection.open();
        conn.execute("INSERT INTO inventory ...");
        conn.close();
        System.out.println("Inventory saved to database.");
    }
	
	public void exportInventoryReportToCSV(List<Item> items) {
        // 4. Reporting: generate CSV file for external use
        CsvExporter exporter = new CsvExporter();
        exporter.export(items, "inventory_report.csv");
        System.out.println("Inventory report exported.");
    }
	
}

public class SendLowStockAlert
{
	
	    public void sendLowStockAlert(String itemCode) {
        // 5. Notification: send email if stock below threshold
        EmailService smtp = new EmailService();
        smtp.send("manager@company.com", "Low Stock Alert for: " + itemCode);
        System.out.println("Low stock alert sent.");
    }
	
}

public class StockLevel

{
	public void addNewItem(String itemCode, String description, int quantity) {
        // 1. Business logic: update in-memory stock
        System.out.println("Item added: " + itemCode + " - " + description + " (" + quantity + ")");
    }

    public int getStockLevel(String itemCode) {
        // 2. Business logic: calculate stock level
        return 42; // mocked stock level
    }
	
}


//exmple-2
//O - open for extension and closed for modification

//this is the incorrect code 
public class ShippingCalculator {

    public double calculateShippingCost(String shippingMethod, double weight) {
        if (shippingMethod.equals("Standard")) {
            return weight * 1.5; // Standard shipping rate
        } else if (shippingMethod.equals("Express")) {
            return weight * 3.0; // Express shipping rate
        } else if (shippingMethod.equals("Overnight")) {
            return weight * 5.0; // Overnight shipping rate
        }
        // Adding a new shipping method requires changing this method
        return 0.0; // No valid shipping method
    }
}

//here is the correct code  , which implment the open -close principle (O of SOLID) 
// only one class calculate the cost for different type  , if in future we need add another type of cost then i have to modify the existing running class 
//so lets separate each type of cost 


// Online Java Compiler
// Use this editor to write, compile and run your Java code online
 public class ShippingCalculator 
{
	public ICalculateCost costStrategy ;
	ShippingCalculator( ICalculateCost calculateCost )
	{
		this.costStrategy  = calculateCost;
		
	}
	
	public double calculateShippingCost(double weight)
	{
	    return costStrategy .calculateCost(weight);
	}
	
}

public interface ICalculateCost
{
	public double calculateCost(double weight);
}

public class calulateExpressCost implements ICalculateCost
{
	public double calculateCost(double weight)
	{
		return weight*1.5;
		
	}
}

public class calulateOvernightCost implements ICalculateCost
{
	public double calculateCost(double weight)
	{
		return weight*3;
	}
}


public class calulateStandardCost implements ICalculateCost
{
	public double calculateCost(double weight)
	{
		return weight*5;
	}
}

class Main {
   
    public static void main(String[] args) {
        System.out.println("Try programiz.pro");
        
        ICalculateCost standardcalculator = new calulateStandardCost();
        ShippingCalculator obj1 = new ShippingCalculator(standardcalculator);
        double weight = 23;
        double ans = obj1.calculateShippingCost(weight);
        System.out.println(ans);
        
        
    }
}


//below is the incorrect code 
// File: Shape.java
public abstract class Shape {
    public abstract String getType();
}

// File: Rectangle.java
public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width  = width;
        this.height = height;
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

// File: Circle.java
public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public String getType() {
        return "Circle";
    }

    public double getRadius() {
        return radius;
    }
}

// File: AreaCalculator.java
public class AreaCalculator {

    public double calculateArea(Shape shape) {
        if (shape.getType().equals("Rectangle")) {
            Rectangle rect = (Rectangle) shape;
            return rect.getWidth() * rect.getHeight();
        } 
        else if (shape.getType().equals("Circle")) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        }
        // To add a new shape type, you must modify this method:
        // else if (shape.getType().equals("Triangle")) { ... }

        throw new UnsupportedOperationException("Shape type not supported");
    }
}



//here we want to correct it -(below is the modified code that might not be correct )  
public abstract class Shape {
    public abstract String getType();
}

// File: Rectangle.java
public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width  = width;
        this.height = height;
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

// File: Circle.java
public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public String getType() {
        return "Circle";
    }

    public double getRadius() {
        return radius;
    }
}

// File: AreaCalculator.java
public class AreaCalculator {
    public shape shapeStrategy;
    AreaCalculator(shape shapeStrategyobj)
    {
        this.shapeStrategy = shapeStrategyobj;
    }
    
    public String extractType(shape shapeStrategy)
    {
        return shapeStrategy.getType();
    }

    // public double calculateArea(Shape shape) {
    //     if (shape.getType().equals("Rectangle")) {
    //         Rectangle rect = (Rectangle) shape;
    //         return rect.getWidth() * rect.getHeight();
    //     } 
    //     else if (shape.getType().equals("Circle")) {
    //         Circle circle = (Circle) shape;
    //         return Math.PI * circle.getRadius() * circle.getRadius();
    //     }
        // To add a new shape type, you must modify this method:
        // else if (shape.getType().equals("Triangle")) { ... }

        // throw new UnsupportedOperationException("Shape type not supported");
    }
}

class Main {
   
    public static void main(String[] args) {
        System.out.println("Try programiz.pro");
        
        ICalculateCost standardcalculator = new calulateStandardCost();
        ShippingCalculator obj1 = new ShippingCalculator(standardcalculator);
        double weight = 23;
        double ans = obj1.calculateShippingCost(weight);
        System.out.println(ans);
        
        
    }
}


//example 3 : [not following open-close principle and Liskov substitution principle - correc the design ]
//below is the incorrect code with regard to (oPEN -Close ) principle and Liskov substitution principle .
// Base class representing a payment method
public class PaymentProcessor {
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("CreditCard")) {
            processCreditCardPayment(amount);
        } else if (paymentType.equals("Paypal")) {
            processPaypalPayment(amount);
        }
    }

    private void processCreditCardPayment(double amount) {
        // Process credit card payment
        System.out.println("Processing Credit Card Payment: " + amount);
    }

    private void processPaypalPayment(double amount) {
        // Process PayPal payment
        System.out.println("Processing PayPal Payment: " + amount);
    }
}

// Derived class that attempts to extend functionality (Invalid LSP)
public class GiftCardPayment extends PaymentProcessor {
    @Override
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("GiftCard")) {
            processGiftCardPayment(amount);
        } else {
            super.processPayment(paymentType, amount); // Calls the base class method
        }
    }

    private void processGiftCardPayment(double amount) {
        // Process gift card payment
        System.out.println("Processing Gift Card Payment: " + amount);
    }
}
//------------------------------------------------------------------------------------------------------------------------------------//
//Example3 - modified design to adhere open-close principle and Liskove-substitution princple 

public class PaymentProcessor {
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("CreditCard")) {
            processCreditCardPayment(amount);
        } else if (paymentType.equals("Paypal")) {
            processPaypalPayment(amount);
        }
    }

    private void processCreditCardPayment(double amount) {
        // Process credit card payment
        System.out.println("Processing Credit Card Payment: " + amount);
    }

    private void processPaypalPayment(double amount) {
        // Process PayPal payment
        System.out.println("Processing PayPal Payment: " + amount);
    }
}

// Derived class that attempts to extend functionality (Invalid LSP)
public class GiftCardPayment extends PaymentProcessor {
    @Override
    public void processPayment(String paymentType, double amount) {
        if (paymentType.equals("GiftCard")) {
            processGiftCardPayment(amount);
        } else {
            super.processPayment(paymentType, amount); // Calls the base class method
        }
    }

    private void processGiftCardPayment(double amount) {
        // Process gift card payment
        System.out.println("Processing Gift Card Payment: " + amount);
    }
}

//first of all this is not using single resposibilty principle - 
//first make it single responsibility principle to follow 
public interface IPayement
{
	public void payment();
}

//now implement this payment for each type of payment 
public class CreditCardPayemntMethod implement IPayement
{
	public void payment()
	{
		System.out.println("payment done by credit card");
	}
}

//class for credit card payment 
public class CreditCardPayemntMethod implement IPayement
{
	public void payment()
	{
		System.out.println("payment done by credit card");
	}
}


//clas for paypal payment 
public class PayPalPayemntMethod implement IPayement
{
	public void payment()
	{
		System.out.println("payment done by payPal");
	}
}



public class GiftCardPayemntMethod implement IPayement
{
	public void payment()
	{
		System.out.println("payment done by Gift card");
	}
}

public class processPayment
{
	//here first of we need object of Ipayemtn 
	private IPayement paymentHandler;
	
	processPayment(IPayement payment)
	{
		this.paymentHandler = payment;//the object of class we will receive will be initialized 
		
	}
	
	public doPayement()
	{
		paymentHandler.payment();//respective method will be called according to the object 
	}
	
	
}

public class Main()
{
	Ipayement GiftCardPayemntMethod = new GiftCardPayemntMethod();
	processPayment processPaymentObj = new processPayment(GiftCardPayemntMethod);
	
	processPayment.doPayement();//finally payment will be done 
}



//----------------------------------------------------------------------------------------------------------------------------
//Exampe 4 in SOLID pattern practice  

//Below is a more complex Java example that violates multiple SOLID principles at once — particularly SRP (Single Responsibility),
//OCP (Open/Closed), LSP (Liskov), and DIP (Dependency Inversion).
public class ReportService {
    
    // Generates a report and also decides how to send it — violates SRP
    public void generateReport(String type) {
        if (type.equals("PDF")) {
            System.out.println("Generating PDF report...");
        } else if (type.equals("Excel")) {
            System.out.println("Generating Excel report...");
        }
    }

    // Hardcoded sending — violates OCP and DIP
    public void sendReport(String deliveryMethod) {
        if (deliveryMethod.equals("Email")) {
            sendEmail();
        } else if (deliveryMethod.equals("FTP")) {
            sendViaFtp();
        } else if (deliveryMethod.equals("Print")) {
            sendToPrinter();
        }
    }

    private void sendEmail() {
        // Complex email sending logic
        System.out.println("Sending report via Email...");
    }

    private void sendViaFtp() {
        // Complex FTP sending logic
        System.out.println("Sending report via FTP...");
    }

    private void sendToPrinter() {
        // Complex printing logic
        System.out.println("Printing the report...");
    }
}

// A derived class overriding behavior incorrectly — violates LSP -- still need improvements.
class SecureReportService extends ReportService {
    @Override
    public void sendReport(String deliveryMethod) {
        if (!deliveryMethod.equals("Email")) {
            throw new UnsupportedOperationException("Secure reports can only be sent via Email!");
        }
        super.sendReport(deliveryMethod);
    }
}

// Main driver code with tight coupling
public class Main {
    public static void main(String[] args) {
        ReportService service = new ReportService();
        service.generateReport("PDF");
        service.sendReport("FTP");
    }
}


//modified code by me ------------------------------------------------------------------------------------------
//this is a little bit complex , lets solve first apply single responsibility principle 

//there are two service - first of all apply for each service independently and the club these two services 

/*
Report service have two main responsibility 
1.1. generate report 
1.2.send report 

secure report service 
2.1 it its juse a secured way to privide reports it extends the Report service 

*/

//1.1 generate report - first apply SRP for this 
public interface IgenerateReport
{
	private void generateReport();
}

//now create class for each type of report which will implement the genearte report 

public class generateReportInPdfFormat implements IgenerateReport
{
	
	private void generateReport()
	{
		System.out.println("REPORT generated in PDF format");
	}
	
}

public class generateReportInExcelFormat implements IgenerateReport
{
	
	private void generateReport()
	{
		System.out.println("REPORT generated in Excel format");
	}
}


//1.2 send Report 
public interface IsendReport
{
	public void sendReport();
}

//now create class for each method through which report will be send 

//send report via mail 
public class sendReportViaEmail implement IsendReport
{
	public void sendReport()
	{
		System.out.println("REPORT is sent via email");
	}
}

//send report via FTP
public class sendReportViaFTP implement IsendReport
{
	public void sendReport()
	{
		System.out.println("REPORT is sent via FTP");
	}
}

//send report to printer 
public class sendReportToPrinter implement IsendReport
{
	public void sendReport()
	{
		System.out.println("REPORT is sent to printer");
	}
}

public class SendReportViaSecureMail implement IsendReport
{
	
	public void sendReport()
	{
		System.out.println("send report via secure Mail");
	}
}

//similarly we can extend method to implement the secure report sender 
//now implement the final report service  ( here i have not implemented the Report service 

public calss ReportService 
{
	private IgenerateReport reportGenerator;
	private IsendReport reportSender;
	
	public ReportService(IgenerateReport generateReport ,IsendReport sendReport)
	{
		this.reportGenerator = generateReport;
		this.reportSender = sendReport;
	}
	
	public void ReportProcessor()
	{
		reportGenerator.generateReport();
		reportSender.sendReport(); //it will call the respective send report according to the bject passed to the ReportProcessor
	}
	
}

//now create an interface that will extend both send report and generate report 
/*
public interface IReportService extends IsendReport , IgenerateReport
{
	
}
*/

//this is really interesting and real case scenario  - (this is still pending need to be implemented ) 

class SecureReportService extends ReportService {
	
	//how we can implement the SecureReportService (this is a king of extended class of Report service ) 
	
	private  IsendReport reportSendor;
	//here we can also use generate method if that method will be implemented then we will inject that object as well 
	
	
	//we have to inject an object of report sender 
	
	public SecureReportService(IsendReport sendReport)
	{
		this.reportSendor = sendReport;
	}
	
	
	public sendReportViaSecureMail (IsendReport secureReport)
	{
		
		secureReport.secureReport();
	}
	//when we will call send report this ovveride sendreport will be called 
	
	
	/*
	//need to change the below logic 
    public void sendReport(String deliveryMethod) {
        if (!deliveryMethod.equals("Email")) {
            throw new UnsupportedOperationException("Secure reports can only be sent via Email!");
        }
        super.sendReport(deliveryMethod);
    }
	
	*/
}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//example 4 . below is the incorrect code in term of following SOLID  pattern 
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Single class handling data retrieval, processing, export and authentication
public class ProductionManager {
    public List<String> productionData = new ArrayList<>();

    // Data fetching, tightly coupled to a DB placeholder
    public void getProductionData() {
        // Simulate DB fetch
        productionData.add("FurnaceA: 1200 tons");
        productionData.add("FurnaceB: 1100 tons");
    }

    // Business logic mixed with console output
    public void calculateAndScheduleMaintenance() {
        System.out.println("Starting maintenance scheduling...");
        for (String record : productionData) {
            System.out.println("Scheduled maintenance for " + record);
        }
    }

    // Direct file I/O for CSV export
    public void exportToCSV() {
        try (FileWriter writer = new FileWriter("production_report.csv")) {
            for (String record : productionData) {
                writer.write(record + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New export format implemented by modifying the same class
    public void exportToPDF() {
        System.out.println("Export to PDF not implemented yet...");
    }

    // Authentication logic stuffed into this class
    public boolean authenticateOperator(String username, String password) {
        return "admin".equals(username) && "1234".equals(password);
    }
}

public class Main {
    public static void main(String[] args) {
        ProductionManager pm = new ProductionManager();

        if (pm.authenticateOperator("admin", "1234")) {
            pm.getProductionData();
            pm.calculateAndScheduleMaintenance();
            pm.exportToCSV();
        } else {
            System.out.println("Authentication failed.");
        }
    }
}
//-----------------------------------------------------------------------------------------------------------------------------------------------------
//here i am trying to correct this code  , i think before implementing first i will check if the gieven code is folowing  SOLID pattern or not 

/*
first of all separate out the responsibility 
1. ExportData 
2.DB operation [fetching data form db]
3.public class ExportToPdf implement IExport
{
	public void export()
	{
		//here implement the export business logic 
		System.out.println("export to pdf");
	}
}
 
4.Maintainance realted things - calculate maintanance and schedule that mainatainance 

*/

//now implement one by one 


//we will have a class named production - manager that will manage all these things 
//in this class we will inject all the other classes boject to interact within the manager- class 
//1.Export data in different format 
public interface IExport
{
	//in this function we can add the export function  , which will be implemented by all the type of the export 
	public void export();
	
}

//lets create class for each type of the export 

public class ExportToPdf implement IExport
{
	public void export()
	{
		//here implement the export business logic 
		System.out.println("export to pdf");
	}
}



public class ExportToCsv implement IExport
{
	public void export()
	{
		//here implement the export business logic 
		System.out.println("export to Csv");
	}
}


//2.now implement for the DB operation 
public interface IGetProductionData 
{
	public void getProductionData();
}

//now define the class for each type with which data is fetched 

public class FetchDataFromDb implements IGetProductionData
{
	public void getProductionData()
	{
		System.out.println("fetching production data from the DB");
	}
}


//similarly we can extend this if in future we have to fetch the data from the file or any other method 


//3. now implement the Authentication related operation 

public interface IAuthentication
{
	
	//define all the method required in authenticaiton 
	public boolean authenticateOperator(String user , String password);
}

public class AuthenticateOperator implements IAuthentication
{
	public boolean authenticateOperator(String user , String password)
	{
		//add the business logic 
		System.out.println("AuthencticateOpeartor is in progress...");
		return false;
	}
}

//if any other type of authenctication is required we can extend it 

//4.now implement the calculate schedule and maintanence 
//i will create two interface one for calculate and other for maintanance
public interface ICalculateRecord
{
	public void calculateRecord(); //here we can define the method according to the requirements 
}


public interface IScheduleMaintanance
{
	public void scheduleMaintanance();
}

public class CalculateRecord implements ICalculateRecord
{
	
	public void calculateRecord()
	{
		System.out.println("Calculating record ... in calculateRecord() ");
	}
}

public class ScheduleMaintainence implements IScheduleMaintanance
{
	public void scheduleMaintanance()
	{
		System.out.println("Scheduling maintainence ... inside scheduleMaintanance()");
	}
}
//here we have two interface which decouple the record calculation and the maintanance scheduling 

public class ProcessRecordCalculationAndRecorManager 
{
	private IScheduleMaintanance processScheduleMaintanance;
	private ICalculateRecord processCalculateRecord;
	
	//here both can be done independently on they can be done in combined way 
	/*
	public ProcessRecordCalculationAndRecorManager(IScheduleMaintanance scheduleMaintananceParam)
	{
		this.processScheduleMaintanance = scheduleMaintananceParam;
	}
	
	public ProcessRecordCalculationAndRecorManager(ICalculateRecord calculateRecordParam)
	{
		this.processCalculateRecord = calculateRecordParam;
	}
	*/
	
	public ProcessRecordCalculationAndRecorManager(IScheduleMaintanance scheduleMaintananceParam ,  ICalculateRecord calculateRecordParam)
	{
		this.processScheduleMaintanance = scheduleMaintananceParam;
		this.processCalculateRecord = calculateRecordParam;
	}
	
	public void processCalculateRecord()
	{
		processCalculateRecord.calculateRecord();
	}
	
	public void processScheduleMaintanance()
	{
		processScheduleMaintanance.scheduleMaintanance();
	}
	
	
	//here we want to handle both the operation calculation of recor and schedule maintainence - but want to decouple it as well 
	
}


//this will manage overall - all the operation , this is pending 
public class ProductionManager
{
	//and here i will manage all the 4 operation 
	private IGetProductionData dataFethcer;
	private IAuthentication authenticator;
	private IExport exporter;
	private ProcessRecordCalculationAndRecorManager recordProcessor;
	
		//define the constructor 
	public 	ProductionManager(
							IGetProductionData dataFethcer,IAuthentication authenticator , IExport exporter , 
							ProcessRecordCalculationAndRecorManager recordProcessor
							)
	{
		this.dataFethcer = dataFethcer;
		this.authenticator = authenticator;
		this.exporter = exporter;
		this.recordProcessor = recordProcessor;
		
	}
	
	public void runProductionManager(String user , String password)
	{
		//first autheticate and then allow user to do so 
		if(authenticator.authenticateOperator(user, password))
		{
			dataFethcer.getProductionData();
			exporter.export();
			recordProcessor.processScheduleMaintanance();
			recordProcessor.processCalculateRecord();
		}else{
			
			log.info("Authentication faied , user or password wrong try again !!!");
		}
		
		
	}
}

public class Main {
    public static void main(String[] args) {
        IGetProductionData fetcher = new FetchDataFromDb();
        IAuthentication auth = new AuthenticateOperator();
        IExport csvExporter = new ExportToCsv();

        // Inject interfaces into manager classes
        ProcessRecordCalculationAndRecorManager recordMgr =
            new ProcessRecordCalculationAndRecorManager(
                new ScheduleMaintainence(),
                new CalculateRecord()
            );

        ProductionManager manager = new ProductionManager(fetcher, auth, csvExporter, recordMgr);

        manager.runProductionManager("admin", "1234");
    }
}

//Exmaple 5.-------------------------------------------------------------------------------------------------------------------------------------------------------------------
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SteelPlantControlSystem {

    private Map<String, Integer> productionData = new HashMap<>();
    private String plantOperatorName;
    private boolean connectionActive = false;

    // Connect to database and fetch data
    public void connectAndFetchData(String dbUrl, String username, String password) {
        System.out.println("Connecting to DB at " + dbUrl);
        if ("admin".equals(username) && "1234".equals(password)) {
            connectionActive = true;
            productionData.put("BlastFurnace1", 1500);
            productionData.put("BlastFurnace2", 1700);
            System.out.println("Data fetched successfully.");
        } else {
            System.out.println("Invalid credentials. Cannot fetch data.");
        }
    }

    // Mixed logic for calculating maintenance schedules directly in data fetching class
    public void calculateMaintenance() {
        for (Map.Entry<String, Integer> entry : productionData.entrySet()) {
            System.out.println("Calculating maintenance for " + entry.getKey() + " with output: " + entry.getValue() + " tons.");
        }
    }

    // Export data to CSV & PDF inside same method
    public void exportData(String format) {
        if (!connectionActive) {
            System.out.println("Cannot export data without active connection.");
            return;
        }
        if ("CSV".equalsIgnoreCase(format)) {
            try (FileWriter writer = new FileWriter("steel_report.csv")) {
                for (Map.Entry<String, Integer> entry : productionData.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue() + "\n");
                }
                System.out.println("Exported data to CSV.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("PDF".equalsIgnoreCase(format)) {
            System.out.println("Generating PDF export... (Not implemented, but still here!)");
        }
    }

    // Change KPI calculation by modifying the same method - violating OCP
    public void calculateKPIs(String type) {
        if ("EnergyEfficiency".equalsIgnoreCase(type)) {
            System.out.println("Calculating Energy Efficiency KPI...");
        } else if ("ProductionRate".equalsIgnoreCase(type)) {
            System.out.println("Calculating Production Rate KPI...");
        } else {
            System.out.println("Unknown KPI type.");
        }
    }

    // Direct authentication check - no abstraction
    public boolean authenticatePlantOperator(String name, String badgeId) {
        plantOperatorName = name;
        return "badge-001".equals(badgeId);
    }

    // Alerting functionality mixed into same class
    public void sendAlert(String msg, String severity) {
        System.out.println("ALERT [" + severity + "]: " + msg);
    }

    public static void main(String[] args) {
        SteelPlantControlSystem control = new SteelPlantControlSystem();

        if (control.authenticatePlantOperator("John Doe", "badge-001")) {
            control.connectAndFetchData("jdbc://steelplantdb", "admin", "1234");
            control.calculateMaintenance();
            control.calculateKPIs("ProductionRate");
            control.exportData("CSV");
            control.sendAlert("Temperature too high in BlastFurnace1", "CRITICAL");
        } else {
            System.out.println("Operator authentication failed.");
        }
    }
}



//here i will try to correct the code , which will be implementing the SOLID principle ----------------------------------------------------------------------------------------
/*
listing out the responsibilities 
1.// Connect to database and fetch data
2.// Mixed logic for calculating maintenance schedules directly in data fetching class
3.// Export data to CSV & PDF inside same method
4.// Change KPI calculation by modifying the same method - violating OCP
5.// Direct authentication check - no abstraction// Direct authentication check - no abstraction
6.// Alerting functionality mixed into same class
*/

//Lets first implement the connect database and fectch data reponsiblity separately 

public interface IConnectDB
{
	
	public void ConnectDB(String connectionString);//we have pass the connecton string 
}

//with the help of this if in future connection db method is added then we can extend it 
public class ConnectDatabase implements IConnectDB
{
	public void ConnectDB(String connectionString)
	{
		System.out.println("Connecting Database");
	}
	
}

public interface  IFetchDataFromDatabase
{
	public void FetchData();
}

//now implement the fetch data from DB 
public class FetchDataFromDB
{
	public void FetchData()
	{
		System.out.println("fetching data from DB");
	}
	
}

//2.Now decouple the logic of calculating maintenance schedules giving a class only one responsibility 
public interface ICalculateMaintainanceSchedule
{
	
	public void CalculateMaintainanceSchedule(); //this is function not constructor 
}

public class CalculateMaintainanceSchedule implements ICalculateMaintainanceSchedule
{
	
	public void CalculateMaintainanceSchedule()
	{
		System.out.println("Calculating Maintainance Schedule");
	}
	
}


//3. Export data to CSV & PDF decouple it by introducing interface 
public interface IExport
{
	//in this function we can add the export function  , which will be implemented by all the type of the export 
	public void export();
	
}

//lets create class for each type of the export 

public class ExportToPdf implement IExport
{
	public void export()
	{
		//here implement the export business logic 
		System.out.println("export to pdf");
	}
}



public class ExportToCsv implement IExport
{
	public void export()
	{
		//here implement the export business logic 
		System.out.println("export to Csv");
	}
}
//4.calculatingKPI i could not understand this functionalities related to which  , is it realted to calculateAndScheduleMaintenance , if so then i will introduce another
//class and will implement the interface IcalculateAndScheduleMaintenance

 



//5.decouple the authentication 
public interface IAuthentication
{
	
	//define all the method required in authenticaiton 
	public boolean authenticateOperator(String user , String password);
}

public class AuthenticateOperator implements IAuthentication
{
	public boolean authenticateOperator(String user , String password)
	{
		//add the business logic 
		System.out.println("AuthencticateOpeartor is in progress...");
		return false;
	}
}

//6.decouple the alerting functionalities 


public void interface IAlert
{
	public void SendAlert();
}

public class AlertService implements IAlert
{
	public void SendAlert()
	{
		System.out.println("sending alert");
	}
	
}


public class SteelPlantControlSystem {
	//now here we have to inject the interface reference 
	private IAlert alertSender;
	private IConnectDB DbConnnector;
	private IFetchDataFromDatabase dataFetcher;
	private ICalculateMaintainanceSchedule maintainanceCalculator;
	private IExport dataExporter;
	private IAuthentication authenticator;
	
	//now initialize the injected object using constructor 
	public SteelPlantControlSystem(IAuthentication authenticator , IExport dataExporter , ICalculateMaintainanceSchedule maintainanceCalculator,
									IFetchDataFromDatabase dataFetcher , IConnectDB DbConnnector , IAlert alertSender
									)
	{
		this.alertSender = alertSender;
		this.DbConnnector = DbConnnector;
		this.dataFetcher = dataFetcher;
		this.maintainanceCalculator = maintainanceCalculator;
		this.dataExporter = dataExporter;
		this.authenticator = authenticator;
		
	}
	
	public void ProcessSteelPlantControlSystem()
	{
		//now we can use the logic to call any function we want 
		
		
	}
	
}

public static void main(String[] args) {
	//now create object of class using interface and then pass it into the  SteelPlantControlSystem object as a pramater 
	//and then call ProcessSteelPlantControlSystem to run the business logic 
     //   SteelPlantControlSystem control = new SteelPlantControlSystem();

       
    }




//---------------------------------------------------TASK on the basis of design pattern ---------------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
🚫 Incorrect Code for Each Design Pattern in Java
Here are the incorrect Java implementations for each of the five key design patterns.
1. Singleton Pattern
javaCopyDownloadpublic class Singleton {
    private static Singleton instance;

    // Private constructor prevents instantiation
    private Singleton() {}

    // Incorrect method: does not check for instance before returning
    public static Singleton getInstance() {
        return instance; // Should be null initially
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2);  // Should be true, but may not be
    }
}
2. Factory Method Pattern
javaCopyDownloadabstract class Product {
    public abstract void use();
}

class ConcreteProduct extends Product {
    @Override
    public void use() {
        System.out.println("Using Concrete Product");
    }
}

abstract class Creator {
    // Incorrect: method not meant to return Product but should be abstract
    public Product factoryMethod() {
        return new ConcreteProduct(); // Should not be determined here
    }
}

class ConcreteCreator extends Creator {
    @Override
    public Product factoryMethod() {
        return new ConcreteProduct();
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Creator creator = new ConcreteCreator();
        Product product = creator.factoryMethod();
        product.use();  // Should correctly return and use Concrete Product
    }
}
3. Observer Pattern
javaCopyDownloadimport java.util.ArrayList;
import java.util.List;

abstract class Observer {
    public abstract void update();
}

class ConcreteObserver extends Observer {
    @Override
    public void update() {
        System.out.println("Observer updated!");
    }
}

class Subject {
    private List<Observer> observers = new ArrayList<>();

    // Incorrectly adds observer without checking duplicates
    public void attach(Observer observer) {
        observers.add(observer);
        // Optional: Cannot remove or check
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();
        ConcreteObserver observer = new ConcreteObserver();
        subject.attach(observer);
        subject.notifyObservers();  // Should notify all observers
    }
}
4. Strategy Pattern
javaCopyDownloadinterface Strategy {
    void apply();
}

class ConcreteStrategyA implements Strategy {
    @Override
    public void apply() {
        System.out.println("Applying Strategy A");
    }
}

class Context {
    // Incorrect: context should not require a strategy
    public void executeStrategy() {
        // Implementation missing
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.executeStrategy();  // Should apply the selected strategy, but does not
    }
}
5. Decorator Pattern
javaCopyDownloadabstract class Component {
    public abstract String operation();
}

class ConcreteComponent extends Component {
    @Override
    public String operation() {
        return "Base Component";
    }
}

class Decorator extends Component {
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    // Incorrect: Should add behavior but does not properly overwrite
    public String operation() {
        return component.operation();
        // Missing additional behavior
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        Component decorated = new Decorator(component);
        System.out.println(decorated.operation());  // Should return "Base Component with Decorator"
    }
}
🔍 Your Task
Please identify and correct the issues present in each of the above Java code examples for the respective design patterns.
 Ensure that the implementations align with the characteristics and principles of their design patterns.





/*

Focusing on the 20 % of patterns that deliver approximately 80 % of common solutions, we recommend mastering the following five:

Singleton Pattern
Ensures a class has only one instance and provides a global access point.
• Use cases: configuration managers, logging, thread pools.

Factory Method Pattern
Defines an interface for creating objects but lets subclasses decide which class to instantiate.
• Use cases: decoupling object creation from implementation, plugin architectures.

Observer Pattern
Establishes a one-to-many dependency so that when one object changes state, all its dependents are notified and updated automatically.
• Use cases: event handling systems, UI frameworks, real-time data feeds.

Strategy Pattern
Defines a family of interchangeable algorithms and makes them interchangeable within that family.
• Use cases: sorting strategies, payment methods, validation rules.

Decorator Pattern
Attaches additional responsibilities to an object dynamically without altering its structure.
• Use cases: I/O stream extensions, GUI component enhancements, dynamic feature toggles.
*/









/*
java preparation --------------------------------------------------------------------------------------------------------------------------------------------------------------







💡 Key Java Concepts (80/20 Rule)
Focusing on a few core concepts can significantly enhance your interview performance. Here are crucial Java topics that often come up, comprising about 20% of the Java landscape but yielding 80% of interview questions.

1. Object-Oriented Programming (OOP) Principles

Encapsulation: Bundling data and methods that operate on that data within classes. Understand access modifiers (private, protected, public).
Inheritance: Concept of acquiring properties and behaviors from another class. Know how to use extends and method overriding.
Polymorphism: Ability to present the same interface for different data types. Understand method overloading (compile-time) and method overriding (runtime).
Abstraction: Hiding complex implementation details and exposing only necessary parts. Use of abstract classes and interfaces.

2. Java Collections Framework

Core Interfaces: Familiarize yourself with List, Set, and Map. Understand implementations like ArrayList, HashSet, and HashMap.
Common Methods: Be able to explain add(), remove(), contains(), size(), iterator(), and forEach().
Comparators and Comparables: Understand sorting and how to compare objects.

3. Exception Handling

Try-Catch-Finally: Grasp the structure of exception handling.
Checked vs. Unchecked Exceptions: Know when to use which and the implications of both.
Custom Exceptions: Be prepared to explain how to create and throw user-defined exceptions.

4. Java 8 Features

Lambda Expressions: Understand syntax and usage for functional interfaces.
Streams API: Familiarity with operations like filter(), map(), reduce(), and how they work with collections.
Optional Class: Know how to use Optional to avoid null pointer exceptions.

5. Multithreading and Concurrency

Thread Class vs. Runnable Interface: Understand how to create and manage threads.
Synchronization: The concept of controlling access to shared resources using synchronized blocks, methods, and the Lock interface.
Executors: Familiarity with the Executor framework for managing thread pools.

6. Design Patterns

Singleton Pattern: Understand how to implement it, its use cases, and multi-threading concerns.
Factory Pattern: Differentiate between Factory Method and Abstract Factory patterns.
Observer Pattern: Basics of event-driven programming through observers and subjects.

7. Garbage Collection and Memory Management

Heap vs. Stack Memory: Understand how Java handles memory allocation.
Garbage Collection Process: Familiarity with automatic memory management and how it works in the background.

8. Java Memory Model

JVM Structure: Basic understanding of the Java Virtual Machine, class loaders, and execution engine.
Memory Areas: Where static variables, instance variables, method areas, etc., are stored.


Prepare Examples
For each topic, prepare:

Definitions: Clear and concise explanations.
Code Samples: Short snippets to demonstrate your understanding.
Use Cases: Real-world scenarios where each concept is applicable.


Practice

Engage in mock interviews focusing on these core areas.
Review common interview questions related to these topics.


Conclusion
By mastering these essential concepts and practicing their application, you can confidently tackle a majority of questions in a Java-related interview. Good luck! 


*/
