package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	//MySQL 접속 주소
		private static final String url = "jdbc:mysql://localhost:3306/javaboard?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
		
		//MySQL 계정
		private static final String user = "root";
		private static final String pass = "12345";
		
		//데이터베이스 연결 메소드
		public static Connection getConnection() {
			
			Connection conn = null;
			
			try {
				//DB 연결
				conn = DriverManager.getConnection(url,user,pass);
				
				System.out.println("MySQL 연결 성공!");
				
				
			} catch (Exception e) {
				System.out.println("MySQL 연결 실패!");
				e.printStackTrace();
			}
			
			return conn;
			
		}
	
}
