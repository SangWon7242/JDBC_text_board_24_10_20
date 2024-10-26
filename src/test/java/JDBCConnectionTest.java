import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionTest {
  // MySQL 데이터베이스 URL, 사용자명, 비밀번호를 설정합니다.
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // your_database를 데이터베이스 이름으로 변경하세요.
  private static final String USER = "sbsst"; // 사용자명을 입력하세요.
  private static final String PASSWORD = "sbs123414"; // 비밀번호를 입력하세요.

  public static void main(String[] args) {
    Connection conn = null;

    try {
      // JDBC 드라이버 로드 (MySQL의 경우 필요하지 않지만, 다른 드라이버에서는 필요할 수 있습니다.)
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("데이터베이스에 성공적으로 연결되었습니다.");

      // ----------
      // 쿼리 작성란!!
      // ----------

    } catch (ClassNotFoundException e) {
      System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터베이스 연결에 실패했습니다.");
      e.printStackTrace();
    } finally {
      // 연결 닫기
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
          System.out.println("데이터베이스 연결이 닫혔습니다.");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
