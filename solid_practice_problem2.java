// Complex Incorrect Implementation (Another Use Case Scenario)

import java.util.*;

class User {
    String username;
    String password;
    String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

class UserService {
    private List<User> users = new ArrayList<>();

    public void registerUser(String username, String password, String role) {
        User user = new User(username, password, role);
        users.add(user);
        System.out.println("Registered user: " + username);
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("User " + username + " logged in successfully.");
                return true;
            }
        }
        return false;
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(oldPassword)) {
                user.password = newPassword;
                System.out.println("Password updated for user: " + username);
                return;
            }
        }
        System.out.println("Incorrect credentials for user: " + username);
    }

    public void assignRole(String username, String newRole) {
        for (User user : users) {
            if (user.username.equals(username)) {
                user.role = newRole;
                System.out.println("Role updated for user: " + username);
                return;
            }
        }
    }

    public void sendPasswordResetEmail(String username) {
        System.out.println("Sending password reset email to: " + username);
    }

    public void notifyUser(String username, String message) {
        System.out.println("Notifying user " + username + ": " + message);
    }

    public void generateUserReport() {
        System.out.println("Generating user report...");
        for (User user : users) {
            System.out.println("Username: " + user.username + ", Role: " + user.role);
        }
    }

    public void connectDatabase() {
        System.out.println("Connecting to user database...");
    }

    public void disconnectDatabase() {
        System.out.println("Disconnecting from user database...");
    }
}

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.connectDatabase();
        userService.registerUser("alice", "password123", "Admin");
        userService.registerUser("bob", "password456", "User");

        userService.login("alice", "password123");
        userService.changePassword("bob", "password456", "newpassword");
        userService.assignRole("bob", "Admin");
        userService.sendPasswordResetEmail("bob");
        userService.notifyUser("alice", "New feature available!");

        userService.generateUserReport();
        userService.disconnectDatabase();
    }
}

//correct it ------------------------------------------------------------------------------------------------------------------------------------------


//Listing out the problem 
//1. database connection 
//2. seding user report 
//3. sending email or notification  , send passord via mail it should be in the sending mail or notification 
//4. register / login 
//5. assign role 
//6. change password 
//above are the responsibility which are implemnented in the single class , so first create one class for one responsibility

//here use is the object on which operation will be performed 


//lets now create the interface and the class 

class User {
    String username;
    String password;
    String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}



//database connection related operation 
public interface IDatabaseConnection
{
	
	void ConnectDatabase();
	void DisconnectDatabase();
}

public class DatabaseConnection implements IDatabaseConnection
{
	private String connectionUrl ;
	
	public DatabaseConnection(String connectionString)
	{
		this.connectionUrl = connectionString;
	}
	
	void ConnectDatabase()
	{
		System.println.out("connection database");
	}
	void DisconnectDatabase()
	{
		System.println.out("Disconnection database");
		
	}
}

//sending report related responsibility
//it can be extended to generate report in any type of file .
public interface IGenerateReport
{
	
	void GenerateReport(String username);
}

public class GenerateUserReportInPdf implements IGenerateReport
{
	
	void GenerateReport(String username)
	{
		System.println.out("Generating Report in PDF for user having username  =  "+ username);
	}
	
	
}

//now implemnet for sending the email for password of normail mail or notification 

public interface ISend
{
	void send();
}

public class SendViaMail implements ISend
{
	public void Send()
	{
		System.println.out("Sending vial mail");
	}
}

public class Notify implements ISend
{
	public void Send()
	{
		System.println.out("Notifying the user");
	}
}

public class sendPasswordResetEmail implements ISend
{
	public void Send()
	{
		System.println.out("Sending mail for password reset");
	}
}

//now implement for register and login 

public interface IRegisterOrLogin 
{
	
	void Register();
	void Login();
}

public class RegisterOrLogin implements IRegisterOrLogin
{
	
	
	
	void Register(User user)
	{
		System.println.out("Registering user");
	}
	
	void Login(User user)
	{
		System.println.out("User Logged In");
	}
}

//now impmlementing Role assignment 
public interface IAssignRole
{
	
	void AssignRole();
}

public class AssignRole
{
	void AssignRole()
	{
			System.println.out("Assigning Role to ther user");
	}
}

//now implement for the change password 
public interface IChangePassword
{
	void ChangePassword();
}

public class ChangePassword
{
	private User user;
	void ChangePassword()
	{
		System.println.out("Changing Password");
	}
}


public class UserService
{
	//implement the userService in this class , this is not complete 
	//here we need the instance of all the other classes , so that we can 
	
	//here concrete class should not be there 
	
	private IChangePassword changePassword;
	private IAssignRole assignRole;
	private IRegisterOrLogin registerOrLogin;
	private ISend send;
	private IGenerateReport generateReport;
	private IDatabaseConnection disconnectDatabase;
	
	public UserService
	(
	IChangePassword changePassword ,
	IAssignRole assignRole, 
	IRegisterOrLogin registerOrLogin,
	ISend send, 
	IGenerateReport generateReport, 
	IDatabaseConnection disconnectDatabase
	)
	{
		this.changePassword = changePassword;
		this.assignRole = assignRole;
		this.registerOrLogin = registerOrLogin;
		this.send = send;
		this.generateReport = generateReport;
		this.disconnectDatabase = disconnectDatabase;	
	}
	
	//now call the each function using the corresponding object
	
	public void ChangePassword()
	{
		changePassword.ChangePassword();
	}
	
	public void AssignRole()
	{
		assignRole.AssignRole();
	}
	public void Register()
	{
		registerOrLogin.Register();
	}
	
	public void Login()
	{
		registerOrLogin.Login();
	}
	
	public void Send()
	{
		send.Send();
	}
	
	public void GenerateReport(String username)
	{
		generateReport.GenerateReport(username);
	}
}

public class Main {
    public static void main(String[] args) {
		//at first create object of all the interface and then pass it to the userService  , and then call the required function 
        //UserService userService = new UserService();
		
		/*
		uusername , password , role
		*/
		
		List<User> userList = new ArrayList<>();//create the list of all user and perform operation whichever you want with these users 
		//create all the required object
		userList.add(new User("nishant@10" , "dummyPass" , "student"));
		userList.add(new User("amir@87" , "passamr" , "Teacher"));
		userList.add(new User("bhanu@87" , "bh878@1" , "Admin"));
		
		
		//pass all the required object reference 
		IChangePassword changePassword = new ChangePassword();
		IAssignRole assignRole = new AssignRole(); 
		IRegisterOrLogin registerOrLogin = new RegisterOrLogin();
		ISend send = new Send(); 
		IGenerateReport generateReport = new GenerateReport(); 
		IDatabaseConnection disconnectDatabase = new DatabaseConnection();
		UserService = new UserService(
		 changePassword ,
		assignRole, 
		registerOrLogin,
		send, 
		generateReport, 
		disconnectDatabase
		);
		for(int i = 0; userList.length; i++)
		{
			//now do operation on any user any operation 
			userService.GenerateReport(userList.At(i).username);//for which user we want to generate report that should be known to generate so pass the parameter 
			
			//similarly we can perform any operation 
		}

        
    }
}


//------------------------------------------------------------------------------------------------------------------
