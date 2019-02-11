package com.gcit.projects.weekoneproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {
	
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost/library?useSSL=true";
	public static final String username= "root";
	public static final String password = "SoccerFootballer18";

	public static void main(String[] args) {
//		String sql = "select * from tbl_author";
		
		try {
			//1. Register Drivers
			Class.forName(driver).newInstance();
			
			//2. Create connection
			Connection conn = DriverManager.getConnection(url, username, password);
			
			//3. Create a statement Object
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement("select * from tbl_author");
			
//			Scanner scan = new Scanner(System.in);
//			String name = scan.nextLine();
			
//			stmt.executeUpdate("insert into tbl_author (authorName) values ('"+name+"')");
			
			//4. Execute Query
//			ResultSet rs = stmt.executeQuery("select * from tbl_author where authorName  = '"+name+"'");
//			ResultSet rs = stmt.executeQuery("select * from tbl_author a inner join tbl_book_authors ba on a.authorId = ba.authorId inner join tbl_book b on b.bookId = ba.bookId where b.bookId = 40");
//			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				System.out.println("ID: "+rs.getInt("authorId"));
				System.out.println("Author Name: "+rs.getString("authorName"));
				System.out.println("---------------------------------------------");
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
