import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
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

      // SQL 삽입 쿼리
      String sql = "INSERT INTO article";
      sql += " SET regDate = NOW()";
      sql += ", updateDate = NOW()";
      sql += ", `subject` = CONCAT(\"제목\", RAND())";
      sql += ", content = CONCAT(\"내용\", RAND());";

      System.out.println(sql);

      pstat = conn.prepareStatement(sql);

      int affectedRows = pstat.executeUpdate();
      System.out.println("affectedRows : " + affectedRows);

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