package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import dbconnection.DBConnection;

public class MainClass {
	
	static Scanner sc = new Scanner(System.in);
	
	//전역변수로 로그인한 유저의 아이디를 저장
	static String loginId = null;
	
	// 회원가입 메소드
	public static void join() {
		
		System.out.print("아이디 입력 : ");
		String userid = sc.nextLine();
		userid = userid.trim(); // 양쪽 공백 삭제
		
		System.out.print("비밀번호 입력 : ");
		String password = sc.nextLine();
		password = password.trim();
		
		System.out.print("이름 입력 : ");
		String username = sc.nextLine();
		username = username.trim();
		
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
			
			conn.close();
			
		} catch(Exception e) {
			System.out.println("회원 가입 실패!");
			e.printStackTrace(); //에러 원인 출력
		}
		
	}
	
	// 회원탈퇴 메소드
	public static void memberDelete() {
		
		if (loginId == null) { //참이면 로그인하지 않은 상태
			System.out.println("로그인한 유저만 회원 탈퇴가 가능합니다. 로그인해주세요");
			login(); //로그인 메소드 호출 -> 로그인 화면으로 이동			
		}
		
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
			
			conn.close();
			
		} catch(Exception e) {
			System.out.println("회원 탈퇴 실패!");
			e.printStackTrace();
		}
	}
	
	// 회원정보 수정 메소드
	public static void memberUpdate() {
		
//		System.out.print("회원 정보를 변경할 아이디 입력 : ");
//		String userid = sc.nextLine();
		
		if (loginId == null) { //참이면 로그인하지 않은 상태
			System.out.println("로그인한 유저만 정보 수정이 가능합니다. 로그인해주세요");
			login(); //로그인 메소드 호출 -> 로그인 화면으로 이동
		}
		
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
			pstmt.setString(3, loginId);
			
			int result = pstmt.executeUpdate(); //반환값이 1이면 탈퇴 성공
			
			if (result == 1) {
				System.out.println("회원 정보 수정 성공!");
			} else {
				System.out.println("회원 정보 수정 실패!");
			}
			
			conn.close();
			
		} catch(Exception e) {
			System.out.println("회원 정보 수정 실패!");
			e.printStackTrace();
		}
	}
	
	//로그인한 유저의 회원 정보 출력 메소드(내 회원 정보 보기)
	public static void memberInfo() {
		
		if (loginId == null) { //참이면 로그인하지 않은 상태
			System.out.println("로그인한 유저만 회원 정보 조회가 가능합니다. 로그인해주세요");
			login(); //로그인 메소드 호출 -> 로그인 화면으로 이동
		}
		
//		System.out.print("회원 정보를 출력할 아이디 입력 : ");
//		String userid = sc.nextLine();
		
		String sql = "SELECT * FROM member WHERE userid = ?";
		
		try {
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, loginId);
			
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
				System.out.println("로그인 한 유저 정보가 존재하지 않습니다!");
			}
			
			rs.close(); // rs 닫기
			conn.close(); //connection 닫기
			
		} catch(Exception e) {
			System.out.println("회원 정보 조회 실패!");
			e.printStackTrace();
		}
	}
	
	// 모든 회원 정보를 조회하는 메소드
	public static void allMemberInfo() {		
		//관리자 아이디:admin, 비밀번호:8888 로 지정	
		
		
		if (loginId == null) { //참->로그인 안된 상태
			System.out.println("로그인한 유저만 사용할 수 있는 메뉴입니다. 로그인해주세요.");
			login(); //로그인 메소드 호출 -> 로그인 화면으로 이동
		} 
		
		if (!loginId.equals("amdin")) { //참->admin 아이디로 로그인 하지 않음
			System.out.println("모든 회원 정보는 관리자만 조회가능합니다. 관리자로 로그인해주세요");
			login(); //로그인 메소드 호출 -> 로그인 화면으로 이동
		}			
		
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
			rs.close(); // rs 닫기
			conn.close(); //connection 닫기
			
		} catch(Exception e) {
			System.out.println("회원 정보 조회 실패!");
			e.printStackTrace();
		}
	}
	
	// 로그인 메소드
	public static void login() {
		
		System.out.print("로그인 아이디 입력 : ");
		String userid = sc.nextLine();
		
		System.out.print("로그인 비밀번호 입력 : ");
		String password = sc.nextLine();
		
		String sql = "SELECT * FROM member WHERE userid = ? AND password = ?";
		
		try {	
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery(); //레코드가 1개 오거나 아니면 0개 오거나
			
			// rs 내에 DB로 부터 select문에 의해서 받은 레코드의 존재여부를 확인
			if (rs.next()) { // 참이면 로그인 성공
				
				System.out.println("로그인 성공!");				
				
				String mid = rs.getString("userid"); //회원 아이디				
				String mname = rs.getString("username"); //회원 이름
				
				loginId = mid; //로그인아이디에 로그인 성공한 유저의 아이디를 저장
				
				System.out.println(mname + "님 환영합니다."); //이름으로 환영인사 출력				
				
			} else {
				System.out.println("아이디 또는 비밀번호가 잘못 입력되었습니다. 다시 확인해주세요.");
				login();
			}
			
			rs.close(); // rs 닫기
			conn.close(); //connection 닫기
			
		} catch(Exception e) {
			System.out.println("회원 정보 조회 실패!");
			e.printStackTrace();
		}
		
	}
	
	//로그아웃 메소드
	public static void logout() {
		loginId = null; //로그 아웃
	}
	
	//게시판 글쓰기
	public static void boardWrite() {
		
		if(loginId == null) {
			System.out.println("게시판은 로그인한 유저만 작성가능합니다. 로그인 해주세요.");
			login();
		}
		
		System.out.print("게시글 제목 : ");
		String title = sc.nextLine();
		
		System.out.print("게시글 내용 : ");
		String content = sc.nextLine();
		
		String sql = "INSERT INTO board(title, content, writer) VALUES (?,?,?)";
		
		try {
					
			Connection conn = DBConnection.getConnection();			
			PreparedStatement pstmt = conn.prepareStatement(sql);			

			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, loginId);			

			int result = pstmt.executeUpdate(); //SQL문 실행 성공하면 정수 1이 반환됨
			
			if(result == 1) {
				System.out.println("글작성 성공!");
			} else {
				System.out.println("글작성 실패!");
			}
			
			conn.close();
			
		} catch(Exception e) {
			System.out.println("게시글 작성 실패!");
			e.printStackTrace(); //에러 원인 출력
		}
		
	}
	
	//게시판 목록 조회 메소드
	public static void boardList() {
		
		// 모든 유저가 게시판 목록을 조회할 수 있음(로그인하지 않은 유저도 조회 가능)		
		
		String sql = "SELECT * FROM board";
		
		try {
			
			Connection conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			// rs 내에 DB로 부터 select문에 의해서 받은 레코드의 존재여부를 확인
			while (rs.next()) {
				int bnum = rs.getInt("bno"); //게시판 글번호
				String btitle = rs.getString("title"); //게시판 글제목
				String bcontent = rs.getString("content"); //게시판 글내용
				String bwriter = rs.getString("writer"); //글 작성자 아이디
				String bdate = rs.getString("regdate"); //게시글 등록일시				
				
				System.out.println("------------------------------------------");
				System.out.println("번호: " + bnum);
				System.out.println("제목: " + btitle);
				System.out.println("작성자: " + bwriter);
				System.out.println("작성일: " + bdate);
								
			} 
			System.out.println("------------------------------------------");
			
			rs.close(); // rs 닫기
			conn.close(); //connection 닫기
			
		} catch(Exception e) {
			System.out.println("게시글 목록 조회 실패!");
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		
//		join(); //회원가입 메소드 호출
//		memberDelete(); //회원탈퇴 메소드 호출
//		memberUpdate(); //회원수정 메소드 호출
//		memberInfo(); //로그인한 유저의 내 정보 조회 메소드 호출
//		allMemberInfo(); //모든 회원정보 보회 메소드 호출(관리자만 호출 가능)
//		login(); //로그인 메소드 호출
//		boardWrite(); //게시판 글쓰기 메소드 호출
		boardList(); //게시판 목록 메소드 호출

	}

}
