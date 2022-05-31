import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class GoiThuTuc {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=qlsv", "sa", "1234$");
			if (conn != null) {
				System.out.println("Kết nối tới CSDL thành công");
			} else {
				System.out.println("Không kết nối vào CSDL được");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Gọi thủ tục
		CallableStatement cstmt;
		// Lưu trữ dữ liệu truy vấn từ server về local 
		ResultSet rs;

		try {
			cstmt = conn.prepareCall("{call GetAllSinhVien}");
			rs = cstmt.executeQuery();
			while (rs.next()) {
				System.out.println("Student:");
				System.out.println("\tId: " + rs.getInt(1));
				System.out.println("\tRollnumber: " + rs.getString(2));
				System.out.println("\tName: " + rs.getString(3));
				System.out.println("\tAddress: " + rs.getString(4));
				System.out.println("\tPhone no: " + rs.getString(5));
				System.out.println("\tGender: " + (rs.getBoolean(6) ? "Male" : "Female"));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
