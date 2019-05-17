package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	public boolean update(Long no, String title, String contents) {
		
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = getConnection();
			
			
			// 3.statement 객체 생성
			String sql = " update board " + 
						"	  set title = ? ," + 
						"         contents = ? " + 
						"   where no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1,title);
			pstmt.setString(2,contents);
			pstmt.setLong(3, no);
			// 4. SQL문 실행
			result = (1 == pstmt.executeUpdate());
			
			// 5. 결과 가져오기
			
		} catch (SQLException e) {
			System.out.println("SqlException : " + e );
		} finally {
			try {
				if( conn != null ) {
					conn.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	public BoardVo get(Long no) {
		BoardVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = getConnection();
			
			
			// 3.statement 객체 생성
			String sql = " select no, title , contents " + 
							"from board " + 
							"where no = ? " ; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			// 4. SQL문 실행
			rs = pstmt.executeQuery();
			
			
			// 5. 결과 가져오기
			if ( rs.next() ) {
				String title = rs.getString(2);
				String contents = rs.getString(3);
				
				
				
				result = new BoardVo();
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				
			}
		} catch (SQLException e) {
			System.out.println("SqlException : " + e );
		} finally {
			try {
				if( conn != null ) {
					conn.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( rs != null ) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	public Boolean insert(BoardVo vo) {
		Boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = getConnection();

			
			// 3.statement 객체 생성
			String sql = "insert into board values(null, ?, ?, ?, 0, now()) ";
			pstmt = conn.prepareStatement(sql);
			
			
			//3-1. 데이터 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContents());
			
			// 4. SQL문 실행
			int count = pstmt.executeUpdate();
			result = (count == 1);
			
			
		} catch (SQLException e) {
			System.out.println("SqlException : " + e );
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return result;
	}
	
	
	public List<BoardVo> getList() {
		List<BoardVo> result = new ArrayList<BoardVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = getConnection();
			
			
			// 3.statement 객체 생성
			String sql = "select no, title, writer, contents, count, reg_date " + 
					" from board " + 
					" order by reg_date desc  ";
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL문 실행
			
			rs = pstmt.executeQuery();
			
			
			// 5. 결과 가져오기
			while( rs.next() ) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String writer = rs.getString(3);
				String contents = rs.getString(4);
				int count = rs.getInt(5);
				String regDate = rs.getString(6);
				
				
				BoardVo vo = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setWriter(writer);
				vo.setContents(contents);
				vo.setCount(count);
				vo.setRegDate(regDate);
				
				result.add(vo);
				 
			}
			
			
			
		} catch (SQLException e) {
			System.out.println("SqlException : " + e );
		} finally {
			try {
				if( conn != null ) {
					conn.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( rs != null ) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	private Connection getConnection() throws SQLException {
		
		Connection conn = null;
		
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.1.190:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return conn;
	}
}
