import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;

public class MainClass {
	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// Thiết lập connection tới URL database đã tạo. Driver Manager chọn drive
			con = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=qlsv", "sa", "1234$");
			if (con != null) {
				System.out.println("Kết nối CSDL thành công");
			} else {
				System.out.println("Không kết nối vào csdl đc");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// Lay du lieu
		try {
			// Khởi tạo object cho việc gửi câu lệnh SQL tới database
			pstmt = con.prepareStatement("select * from tblsinhvien");
			// Thực thi câu lệnh sql và trả về resultset
			rs = pstmt.executeQuery();
			// duyệt từng row của ResultSet. Trả về false nếu hàng hiện tại không có
			while (rs.next()) {
				// Nhận dữ liệu từ cột của hàng hiện tại
				System.out.println("Student: ");
				System.out.println("\tId: " + rs.getInt(1));
				System.out.println("\tRollNumber: " + rs.getString(2));
				System.out.println("\tName: " + rs.getString(3));
				System.out.println("\tAddress: " + rs.getString(4));
				System.out.println("\tPhone: " + rs.getString(5));
				System.out.println("\tGender: " + (rs.getBoolean(6) ? "Nam" : "Nữ"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Cap nhat du lieu
		try {
			pstmt = con.prepareStatement("update tblsinhvien set name=?, address=?,phone=? where id=?");
			pstmt.setString(1, "Lưu Bị");
			pstmt.setString(2, "Thục quốc");
			pstmt.setString(3, "0123456789");
			pstmt.setInt(4, 3);
			// Thực thi câu lệnh SQL DML => Trả về 1 nếu có count row
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("Da cap nhat du lieu cho ban ghi có id=3");
			} else {
				System.out.println("Khong cap nhat duoc du lieu");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Them du lieu
		try {
			pstmt = con.prepareStatement("insert into tblsinhvien values(?,?,?,?,?)");
			pstmt.setString(1, "C1615M");
			pstmt.setString(2, "Tào tháo");
			pstmt.setString(3, "Ngụy Quốc");
			pstmt.setString(4, "19008888");
			pstmt.setBoolean(5, true);
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("Da them du lieu vao bang");
			} else {
				System.out.println("Khong them du lieu vao bang");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Xoa du lieu
		try {
			pstmt = con.prepareStatement("delete from tblsinhvien where id=3");
			int i = pstmt.executeUpdate();
			if (i > 0) {
				System.out.println("Da xoa du lieu");
			} else {
				System.out.println("Khong xoa duoc du lieu");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

	}
}
