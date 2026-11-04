package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import dbconnection.DBConnection;

public class MainClass {
	
	static Scanner sc = new Scanner(System.in);
	
	// 회원가입 메소드
	public static void join() {
		
		System.out.print("아이디 입력 : ");
		String userid = sc.nextLine();
		
		System.out.print("비밀번호 입력 : ");
		String password = sc.nextLine();
		
		System.out.print("이름 입력 : ");
		String username = sc.nextLine();
		
		// SQL문 작성
//		String sql = "INSERT INTO member(userid, password, username) VALUES ('"+userid+"','"+password+"','"+username+")";
		String sql = "INSERT INTO member(userid, password, username) VALUES (?,?,?)";
		//? -> 나중에 입력할 values 값의 위치와 갯수 만 잡아 놓은 것 
		
		try {
			
			//DB와 JAVA 간의 연결 커넥션 생성
			Connection conn = DBConnection.getConnection();
			//PreparedStatement -> SQL문장을 안전하게 실행하기 위한 객체 생성 클래스
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//? 자리에 사용자로부터 입력받은 값이 저장된 변수를 대입
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			pstmt.setString(3, username);
			
			//SQL문 실행
			//insert, delete, update -> executeUpdate()
			//select -> executeQuery()
			int result = pstmt.executeUpdate(); //SQL문 실행 성공하면 정수 1이 반환됨
			
			if(result == 1) {
				System.out.println("회원 가입 성공!");
			} else {
				System.out.println("회원 가입 실패!");
			}
			
		} catch(Exception e) {
			System.out.println("회원 가입 실패!");
			e.printStackTrace(); //에러 원인 출력
		}
		
	}
	
	// 회원탈퇴 메소드
	public static void memberDelete() {
		System.out.print("탈퇴할 아이디 입력 : ");
		String userid = sc.nextLine();
		
		String sql = "DELETE FROM member WHERE userid = ?";
		
		try {
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userid);
			
			int result = pstmt.executeUpdate(); //반환값이 1이면 탈퇴 성공
			
			if (result == 1) {
				System.out.println("회원 탈퇴 성공!");
			} else {
				System.out.println("회원 탈퇴 실패!");
			}
			
		} catch(Exception e) {
			System.out.println("회원 탈퇴 실패!");
			e.printStackTrace();
		}
	}
	
	// 회원정보 수정 메소드
	public static void memberUpdate() {
		System.out.print("회원 정보를 변경할 아이디 입력 : ");
		String userid = sc.nextLine();
		
		System.out.print("변경할 비밀번호 입력 : ");
		String password = sc.nextLine();
		
		System.out.print("변경할 이름 입력 : ");
		String username = sc.nextLine();
		
		String sql = "UPDATE member SET password = ?, username = ? WHERE userid = ?";
		
		try {
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);			
			
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			pstmt.setString(3, userid);
			
			int result = pstmt.executeUpdate(); //반환값이 1이면 탈퇴 성공
			
			if (result == 1) {
				System.out.println("회원 정보 수정 성공!");
			} else {
				System.out.println("회원 정보 수정 실패!");
			}
			
		} catch(Exception e) {
			System.out.println("회원 정보 수정 실패!");
			e.printStackTrace();
		}
	}
	
	//특정 회원 정보 출력 메소드
	public static void memberInfo() {
		
		System.out.print("회원 정보를 출력할 아이디 입력 : ");
		String userid = sc.nextLine();
		
		String sql = "SELECT * FROM member WHERE userid = ?";
		
		try {
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userid);
			
			ResultSet rs = pstmt.executeQuery();
			
			// rs 내에 DB로 부터 select문에 의해서 받은 레코드의 존재여부를 확인
			if (rs.next()) {
//				System.out.println(userid + " 회원은 존재하는 회원입니다!");
				String mid = rs.getString("userid"); //회원 아이디
				String mpw = rs.getString("password"); //회원 비밀번호
				String mname = rs.getString("username"); //회원 이름
				String mdate = rs.getString("regdate"); //회원 가입일시
				
				System.out.println("아이디 / 비밀번호 / 이름 / 가입일시");
				System.out.println("---------------------------");
				System.out.println(mid + " / " + mpw + " / " + mname + " / " + mdate);
				
			} else {
				System.out.println(userid + " 회원은 존재하지 않는 회원입니다!");
			}
			
		} catch(Exception e) {
			System.out.println("회원 정보 조회 실패!");
			e.printStackTrace();
		}
	}
	
	// 모든 회원 정보를 조회하는 메소드
	public static void allMemberInfo() {		
				
		String sql = "SELECT * FROM member";
		
		try {
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			// rs 내에 DB로 부터 select문에 의해서 받은 레코드의 존재여부를 확인
			while (rs.next()) {
//				System.out.println(userid + " 회원은 존재하는 회원입니다!");
				String mid = rs.getString("userid"); //회원 아이디
				String mpw = rs.getString("password"); //회원 비밀번호
				String mname = rs.getString("username"); //회원 이름
				String mdate = rs.getString("regdate"); //회원 가입일시
				
				System.out.println("아이디 / 비밀번호 / 이름 / 가입일시");
				System.out.println("---------------------------");
				System.out.println(mid + " / " + mpw + " / " + mname + " / " + mdate);
				System.out.println("---------------------------");
				
			} 
			
		} catch(Exception e) {
			System.out.println("회원 정보 조회 실패!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
//		join(); //회원가입 메소드 호출
//		memberDelete(); //회원탈퇴 메소드 호출
//		memberUpdate(); //회원수정 메소드 호출
//		memberInfo(); //회원정보 조회 메소드 호출
		allMemberInfo(); //모든 회원정보 보회 메소드 호출 

	}

}
