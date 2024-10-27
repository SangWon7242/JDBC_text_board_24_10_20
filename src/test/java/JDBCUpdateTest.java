import java.sql.*;

public class JDBCUpdateTest {
  // MySQL 데이터베이스 URL, 사용자명, 비밀번호를 설정합니다.
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // your_database를 데이터베이스 이름으로 변경하세요.
  private static final String USER = "sbsst"; // 사용자명을 입력하세요.
  private static final String PASSWORD = "sbs123414"; // 비밀번호를 입력하세요.

  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement pstat = null;

    try {
      // JDBC 드라이버 로드
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("데이터베이스에 성공적으로 연결되었습니다.");

      int id = 2;

      // SQL 수정 쿼리
      String sql = "UPDATE article";
      sql += " SET updateDate = NOW()";
      sql += ", `subject` = \"%s\"".formatted("제목 수정 1");
      sql += ", content = \"%s\"".formatted("내용 수정 1");
      sql += " WHERE id = %d;".formatted(id);

      System.out.println("게시물이 수정되었습니다.");

      pstat = conn.prepareStatement(sql);
      pstat.executeUpdate();

    } catch (ClassNotFoundException e) {
      System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
      e.printStackTrace();
    } finally {
      // 자원 해제
      try {
        if (pstat != null && !pstat.isClosed()) pstat.close();
        if (conn != null && !conn.isClosed()) conn.close();
        System.out.println("데이터베이스 연결이 닫혔습니다.");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
