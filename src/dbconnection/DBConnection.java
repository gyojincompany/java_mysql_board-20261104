package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	//Oracle 접속용 주소
	private static final String url = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
	
	//Oracle 계정
	private static final String user = "JAVABOARD";
	private static final String pass = "12345";
	
	//데이터베이스 연결 메소드
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			//DB 연결
			conn = DriverManager.getConnection(url,user,pass);
			
//				System.out.println("MySQL 연결 성공!");
			
			
		} catch (Exception e) {
			System.out.println("MySQL 연결 실패!");
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
}
