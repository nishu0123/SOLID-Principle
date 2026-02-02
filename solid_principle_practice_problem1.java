//Below is a complex and deliberately incorrect Java code that violates multiple SOLID principles.

import java.util.*;

class Document {
    String content;
    String type;
    public Document(String content, String type) {
        this.content = content;
        this.type = type;
    }
}

class DocumentManager {
    private List<Document> documents = new ArrayList<>();
    
    public void saveDocument(Document doc) {
        documents.add(doc);
        System.out.println("Saved document: " + doc.content);
    }
    
    public void printDocument(Document doc) {
        if(doc.type.equals("PDF")) {
            System.out.println("Printing PDF: " + doc.content);
        } else if(doc.type.equals("WORD")) {
            System.out.println("Printing Word: " + doc.content);
        } else if(doc.type.equals("IMAGE")) {
            System.out.println("Printing Image: " + doc.content);
        } else {
            System.out.println("Unknown format");
        }
    }
    
    public void exportDocument(Document doc, String format) {
        if(format.equals("PDF")) {
            System.out.println("Exporting to PDF: " + doc.content);
        } else if(format.equals("WORD")) {
            System.out.println("Exporting to Word: " + doc.content);
        } else if(format.equals("TEXT")) {
            System.out.println("Exporting to Text: " + doc.content);
        }
    }
    
    public void sendDocumentToEmail(Document doc, String email) {
        System.out.println("Sending document to " + email + " : " + doc.content);
    }
    
    public void connectToDatabase() {
        System.out.println("Connecting to database...");
    }
    
    public void disconnectFromDatabase() {
        System.out.println("Disconnecting from database...");
    }
}

public class Main {
    public static void main(String[] args) {
        DocumentManager manager = new DocumentManager();
        Document doc1 = new Document("Steel Production Report", "PDF");
        Document doc2 = new Document("Machine Maintenance Schedule", "WORD");
        
        manager.connectToDatabase();
        manager.saveDocument(doc1);
        manager.printDocument(doc1);
        manager.exportDocument(doc1, "WORD");
        manager.sendDocumentToEmail(doc1, "operations@sms-group.com");
        
        manager.saveDocument(doc2);
        manager.printDocument(doc2);
        manager.disconnectFromDatabase();
    }
}


//correct it ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//1.single responsibilty principle 
//2.open-close principle 
//3.Liskov substitution principle 
//4.Interface sagrigation principle 
//5.Dependency inversion principle 


//lets first comment down the issues 
//1.there is comcrete class which is having more than one responsiblity - it violates SRP and Open-closed principle 
//2.there is no interface , so it violates interface segrigation principle 
//3.if interface segrigation is not there then there will be problem in the Dependency inversion principle , becasue of the concrete class 
//4.if it violated DIP then it will violated Liskov substitution principle .
import java.util.*;

//i dont think i need to create an interface for the payload 
//document is like a payload 
class Document {
    String content;
    String type;
    public Document(String content, String type) {
        this.content = content;
        this.type = type;
    }
}

//but document manager have lot of responsibities , now create separate class for each responsibility 
//listing out the different responsibilities 
//1.save document 
//2.print document  , send and export is the same type of responsiblities [Treat all these three as a separate responsibility] 
//3.database connection (connect , disconnect ) 

//Now separate the responsibilities

//at first create the interface for each responsibilities

public interface ISaveDocument
{
	void SaveDocument();
}

public interface SaveDocument
{
	void PrintDocument();
}

public interface ISendDocument
{
	void SendDocument();
}

public interface IExportDocument
{
	void ExportDocument();
	
}

public interface IDatabaseConnection
{
	void ConnectDatabase();
	void DisconnectDatabase();
	
}

//all the required interface have been defined above 
//now we can do concrete implementation by defining class  


public class saveDocument implements ISaveDocument
{
	private Document document;
	
	//add constructor 
	public saveDocument(Document doc)
	{
		this.document = doc;
	}
	
	void SaveDocument()
	{
		System.print.ln("saving document");
		//add the application logic here 
	}
}

public class PrintDocument implements IPrintDocument
{
	private Document document;
	
	//add constructor 
	public PrintDocument(Document doc)
	{
		this.document = doc;
	}
	
	void PrintDocument()
	{
		System.print.ln("printing document");
		//add the application logic here 
	}
}

public class SendViaEmail implements ISendDocument
{
	private Document document;
	
	//add constructor 
	public SendViaEmail(Document doc)
	{
		this.document = doc;
	}
	
	void SendDocument()
	{
		System.print.ln("sending document");
		//add the application logic 
	}
}

public class ExportDocument implements IExportDocument
{
	private Document document;
	
	//add constructor 
	public ExportDocument(Document doc)
	{
		this.document = doc;
	}
	
	
	void ExportDocument()
	{
		System.print.ln("Exporting document");
		//add the application logic here 
	}
}

public class DatabaseConnection implements IDatabaseConnection
{
	//private Document document;
	private String connectionUrl;
	
	//add constructor 
	public DatabaseConnection(String url)
	{
		this.connectionUrl = url;
	}
	
	void ConnectDatabase()
	{
		System.print.ln("Connecting Database");
		//add logic for the database connection 
		
	}
	
	void DisconnectDatabase()
	{
		System.print.ln("Disconnecting Database");
	}
}




//now implement the Document manager , it should contain all the instance of document related operation 

public class DocumentManager
{
	//here first of all we should have the document , on which we have to do the operation 
	// private Document document = new Document(); //in document class we can use constructor to initialize it -[i think this document should be passes from main ]
	
	private ISaveDocument saveDocument;
	private IPrintDocument printDocument;
	private ISendDocument sendDocument;
	private IExportDocument exportDodument;
	private IDatabaseConnection databaeConnection;
	
	//now create the constructor to intilize it 
	
	public DocumentManager(ISaveDocument saveDocument ,IPrintDocument printDocument , ISendDocument sendDocument, IExportDocument exportDodument , IDatabaseConnection databaseConnection )
	{
		this.saveDocument = saveDocument;
		this.printDocument = printDocument;
		this.sendDocument = sendDocument;
		this.exportDodument =exportDodument;
		this.databaeConnection = databaeConnection;
	}
	
	public void SaveDocument()
	{
		saveDocument.SaveDocument();
	}
	
	public void PrintDocument()
	{
		
		printDocument.PrintDocument();
	}
	
	public void SendDocument()
	{
		saveDocument.SaveDocument();
	}
	
	public void ExportDocument()
	{
		exportDocument.ExportDocument();
	}
	
	public void ConnectDatabase()
	{
		databaeConnection.ConnectDatabase();
	}
	
	public void DisconnectDatabase()
	{
		databaeConnection.DisconnectDatabase();
	}
	
}
/*
class DocumentManager {
    private List<Document> documents = new ArrayList<>();
    
    public void saveDocument(Document doc) {
        documents.add(doc);
        System.out.println("Saved document: " + doc.content);
    }
    
    public void printDocument(Document doc) {
        if(doc.type.equals("PDF")) {
            System.out.println("Printing PDF: " + doc.content);
        } else if(doc.type.equals("WORD")) {
            System.out.println("Printing Word: " + doc.content);
        } else if(doc.type.equals("IMAGE")) {
            System.out.println("Printing Image: " + doc.content);
        } else {
            System.out.println("Unknown format");
        }
    }
    
    public void exportDocument(Document doc, String format) {
        if(format.equals("PDF")) {
            System.out.println("Exporting to PDF: " + doc.content);
        } else if(format.equals("WORD")) {
            System.out.println("Exporting to Word: " + doc.content);
        } else if(format.equals("TEXT")) {
            System.out.println("Exporting to Text: " + doc.content);
        }
    }
    
    public void sendDocumentToEmail(Document doc, String email) {
        System.out.println("Sending document to " + email + " : " + doc.content);
    }
    
    public void connectToDatabase() {
        System.out.println("Connecting to database...");
    }
    
    public void disconnectFromDatabase() {
        System.out.println("Disconnecting from database...");
    }
}
*/
public class Main {
    public static void main(String[] args) {
		
		//now implementing the main function 
		//crate the object of all the five classes and pass it into this document manager  , at this time i am not passing all the required object to the constructor 
		//as architecture point of view it is complete 
		DocumentManager documentManager = new DocumentManager();
		
		//then we can do all operation using this document manager 
		documentManager.SaveDocument();//similiarly for all the operation we can do that ,
		
		/*
        DocumentManager manager = new DocumentManager();
        Document doc1 = new Document("Steel Production Report", "PDF");
        Document doc2 = new Document("Machine Maintenance Schedule", "WORD");
        
        manager.connectToDatabase();
        manager.saveDocument(doc1);
        manager.printDocument(doc1);
        manager.exportDocument(doc1, "WORD");
        manager.sendDocumentToEmail(doc1, "operations@sms-group.com");
        
        manager.saveDocument(doc2);
        manager.printDocument(doc2);
        manager.disconnectFromDatabase();
		*/
    }
}
