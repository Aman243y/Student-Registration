package conn.sms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.AliasRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.tomcat.jni.User;

import com.mysql.cj.Session;

import conn.sms.entity.Student;
@WebServlet("/StudentController")
public class StudentController extends HttpServlet {
	@Resource(name="sms")
	DataSource ds;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   
		
		
		
		String wtd = req.getParameter("cmd");
		if (wtd == null) {
			wtd = "LIST";
		}

		switch (wtd) {
		case "LIST":
			studentList(req, resp);
			break;
		case "ADD":
			addStudent(req, resp);
			break;
		case "LOAD":
			loadStudent(req, resp);
			break;
		case "UPDATE":
			updateStudent(req, resp);
			break;
		case "DELETE":
			try {
				deleteStudent(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "AUTH":
			authStudent(req, resp);
			break;
		}
		
		
		
		
	}//doGet ends here


	private void authStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("authenticate student");
		
		String user =req.getParameter("user");
		String pass =req.getParameter("pass");
		System.out.println(user +" "+ pass);
		
		String q="Select * from users where username=? and pass=?";
		
		try {
			PreparedStatement ps=ds.getConnection().prepareStatement(q);
			ps.setString(1, user);
			ps.setString(2, pass);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				//display student list page
				studentList(req, resp);
				
			}
			else {
				System.out.println("galat!");

				RequestDispatcher rd=req.getRequestDispatcher("error.html");
				rd.forward(req, resp);
				
			}

		} catch (SQLException e) {
			//show error page
			e.printStackTrace();
		}

		
	}

    //delete student
	private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		String sid = req.getParameter("sid");
		System.out.println("Deleting Student where sid is " +sid);
		
		String q = "DELETE from students where sid = ?";
		try {
			PreparedStatement ps = ds.getConnection().prepareStatement(q);
			ps.setInt(1, Integer.parseInt(sid));
			ps.execute();
			
			System.out.println("Student with id : " +sid +" is deleted");
			
			studentList(req, resp);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
}

    //update student
	private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Update a student record");
		
		int sid = Integer.parseInt(req.getParameter("sid"));
		String f =req.getParameter("Fn");
		String l=req.getParameter("Ln");
		String e=req.getParameter("Email");
		
		String vada="UPDATE students set first_name=?,last_name=?,email=? where sid=?";
		try {
			PreparedStatement ps=ds.getConnection().prepareStatement(vada);
			ps.setString(1, f);
			ps.setString(2, l);
			ps.setString(3, e);
			ps.setInt(4, sid);
			
			ps.execute();
			//show updated data
			studentList(req, resp);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}

   //
	//load student
	private void loadStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Loading a student list");
		
		String q="SELECT * from students where sid=?";
		try {
			PreparedStatement ps=ds.getConnection().prepareStatement(q);
			ps.setInt(1, Integer.parseInt(req.getParameter("sid")));
			
			ResultSet rs=ps.executeQuery();
			Student theStudent = null;
			if(rs.next()) {
				theStudent=new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
			}
			req.setAttribute("THE_STUDENT", theStudent);
			
			RequestDispatcher rd= req.getRequestDispatcher("/update-form.jsp");
			rd.forward(req, resp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    //add student
	private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String f =req.getParameter("Fn");
		String l=req.getParameter("Ln");
		String e=req.getParameter("Email");
		
		System.out.println(f + " "+ l + " "+ e);
		
		try {
			Connection conn = ds.getConnection();
			String q="Insert into Students(first_name,last_name,email) values(?,?,?)";
			PreparedStatement ps= conn.prepareStatement(q);
			ps.setString(1, f);
			ps.setString(2, l);
			ps.setString(3, e);
			ps.execute();
			
			System.out.println("data saved");
			//show update data
			studentList(req,resp);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	 
		
	}

   //display list of student
	private void studentList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("the cmd param contains null values");
		//read data from db
		
		try {
			Connection conn=ds.getConnection();
			String q="SELECT * from Students";
			Statement stmt=conn.createStatement();
			
			ResultSet rs=stmt.executeQuery(q);
			List<Student> students=new ArrayList<Student>();
			
			while(rs.next()) {
				Student theStudent=new Student(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));
				students.add(theStudent);
			}
			req.setAttribute("vadas", students);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		//calling jsp or html file from servlet (java class)
		RequestDispatcher rd=req.getRequestDispatcher("/student_list.jsp");
		rd.forward(req, resp);
		
	}
	

}

