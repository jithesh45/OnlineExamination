import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExam 
{
	private static String Login_username;
	private static String Login_password;
	
    public static void main(String[] args) 
    {    	
    	Scanner sc = new Scanner(System.in);
    	 
		while(true) 
		{
			System.out.println("       Main Menu      ");
			System.out.println("1. Login");
			System.out.println("2. Update Profile and Password");
			System.out.println("3. Select Answers for MCQs");
			System.out.println("4. Timer and Auto Submit");
			System.out.println("5. Close Session and Logout\n");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			System.out.println();
		
			switch (choice) 
			{
				case 1:
					login(sc);
					break;
				case 2:
					updateProfileandpassword(sc);
					break;
				case 3:
					selectAnswers(sc);
					break;
				case 4:
					timerAndAutoSubmit();
					break;
				case 5:
					logout();
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
    }
	public static void login(Scanner sc) 
    {
		try
		{
			System.out.println("Welcome to Login...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineexamination","root","jithesh@1998");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from Userinfo");
			while (rs.next())
			{
				int check=0,check1=0;
				do
				{
					check=0;
					System.out.println("Please enter Your Username : ");
					Login_username=sc.next();
					if(Login_username.equals(rs.getString(2)))
					{
						do
						{
							check1=0;
							System.out.println("Please enter Your Password : ");
							Login_password=sc.next();
							if(Login_password.equals(rs.getString(6)))
							{
								System.out.println("Login Succesfully...Welcome "+ Login_username);
							}
							else
							{
								System.out.println("Wrong Password...");
								String passwordwrong="yes";
								while(passwordwrong.equals("yes"))
								{
									System.out.println("Do you want to retype the password(y/n) : ");
									passwordwrong=sc.next();
									passwordwrong=passwordwrong.toLowerCase();
									if(passwordwrong.equals("y")||passwordwrong.equals("yes"))
									{
										check1=1;
									}
								}
							}
						}while(check1==1);	
					}
					else
					{
						System.out.println("Wrong Username...");
						String usernamewrong="yes";
						while(usernamewrong.equals("yes"))
						{
							System.out.println("Do you want to retype the username(y/n) : ");
							usernamewrong=sc.next();
							usernamewrong=usernamewrong.toLowerCase();
							if(usernamewrong.equals("y")||usernamewrong.equals("yes"))
							{
								check=1;
							}
						}
					}
				}while(check==1);
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
    }

    public static void updateProfileandpassword(Scanner sc) 
    {
    	int Update_check,result;
    	try
    	{
    		System.out.println("Update profile and Password\n");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineexamination","root","jithesh@1998");
			System.out.println("Enter the UserID for Update : ");
			int UserID = sc.nextInt();
			do
			{
				Update_check=0;
				System.out.println("Which one you want to update? \n1.UserProfile\n2.UserPassword\n");
				int Update_choice=sc.nextInt();
				if(Update_choice==1)
				{
					System.out.println("Login UserProfile for Update : \n");
					System.out.println("Enter the UserDOB for Update : ");
					String UserDOB = sc.next();
					System.out.println("Enter the EMailID for Update : ");
					String EmailID = sc.next();
					PreparedStatement ps=con.prepareStatement("update Userinfo set UserDOB=?, EmailID=? where UserID=?");
					ps.setString(1,UserDOB);
					ps.setString(2,EmailID);
					ps.setInt(3,UserID);
					result=ps.executeUpdate();
					if(result!=0)
					{
						System.out.println("Data Update successfully...");
					}
					else
					{
						System.out.println("Invalid UserID...");
					}
					
				}
				else if(Update_choice==2)
				{
					System.out.println("Login UserPassword for Update : \n");
					System.out.println("Enter the UserPassword for Update : ");
					String UserPassword = sc.next();
					PreparedStatement ps=con.prepareStatement("update Userinfo set UserPassword=? where UserID=?");
					ps.setString(1,UserPassword);
					ps.setInt(2,UserID);
					result=ps.executeUpdate();
					if(result!=0)
					{
						System.out.println("Data Update successfully...");
					}
					else
					{
						System.out.println("Invalid UserID...");
					}
				}
				else
				{
					String cont="yes";
					while(cont.equals("yes"))
					{
						System.out.println("Your choice is Wrong...\nDo you want to Update profile and Password Again(y/n) : ");
						cont=sc.next();
						cont=cont.toLowerCase();
						if(cont.equals("y")||cont.equals("yes"))
						{
							Update_check=1;
						}
					}
				}
			}
			while(Update_check==1);
			con.close();
    	}
    	catch(Exception e)
		{
			System.out.println(e);
		}    	             
    }

    public static void selectAnswers(Scanner sc) 
    {
    	try
    	{
    		System.out.println("Select Answers for Mcqs Question\n");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineexamination","root","jithesh@1998");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from mcqs");
			while (rs.next()) 
			{
                int id = rs.getInt(1);
                String question = rs.getString(2);
                String optionA = rs.getString(3);
                String optionB = rs.getString(4);
                String optionC = rs.getString(5);
                String optionD = rs.getString(6);

                System.out.println("Question " + id + ": " + question);
                System.out.println("A. " + optionA);
                System.out.println("B. " + optionB);
                System.out.println("C. " + optionC);
                System.out.println("D. " + optionD);
                System.out.println();
                
                System.out.print("Your answer (A/B/C/D): ");
                String answer = sc.next().toUpperCase();
                System.out.println("\nSelected answer for question " + id + ": " + answer);
                if(answer.equals(rs.getString(7)))
                {
                	System.out.println("\nThe Selected Answer is correct : "+rs.getString(7));
                }
                else
                {
                	System.out.println("\nWrong Answer...\nThe Correct Answer is : "+rs.getString(7));
                }
                System.out.println();
                
            }
			con.close();
    	}
    	catch(Exception e)
		{
			System.out.println(e);
		}    	
    }

    public static void timerAndAutoSubmit() 
    {
    	try
    	{
    	int examDurationMinutes = 60; 
        int autoSubmitDelaySeconds = 5; 

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() 
        {
            int remainingTime = examDurationMinutes * 60;

            public void run() 
            {
                if (remainingTime > 0) 
                {
                    System.out.println("Time Left: " + remainingTime / 60 + " minutes " + remainingTime % 60 + " seconds");
                    remainingTime--;
                } 
                else 
                {
                    System.out.println("Time's up! Auto-submitting the exam...");
                    
                    timer.cancel(); 
                    logout(); 
                }
            }
        }, 0, 1000);

        
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Auto-submitting the exam...");
                
                timer.cancel(); 
                logout(); 
            }
        }, autoSubmitDelaySeconds * 1000);
    	}
    	catch(Exception e)
		{
			System.out.println(e);
		}            
    }

    public static void logout() 
    {
		System.out.println("Closing session and logging out. Goodbye, " + Login_username + "!");
        System.exit(0);
    }
}
    



