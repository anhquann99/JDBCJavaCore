import java.awt.Taskbar.State;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainClass2 {
	Connection conn = null;
	SinhVien sv;

	public void connect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1434;databaseName=qlsv", "sa", "1234$");
			if (conn != null) {
				System.out.println("Kết nối CSDL thành công");
			} else {
				System.out.println("Kết nối CSDL không thành công");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private int showMenu() {
		System.out.println("=====MENU=====");
		System.out.println("1. Nhập thông tin sinh viên");
		System.out.println("2. Danh sách sinh viên");
		System.out.println("3. Sửa sinh viên");
		System.out.println("4. Xóa sinh viên");
		System.out.println("5. Tìm sinh viên");
		System.out.println("6. Thoát");
		System.out.println("Vui lòng chọn từ 1->6");
		Scanner nhap = new Scanner(System.in);
		return nhap.nextInt();
	}

	public void nhapThongTin() throws SQLException {
		conn.setAutoCommit(false);
		boolean isStop = false;
		int continues;
		String sql = "INSERT INTO tblsinhvien(rollnumber,name,address,phone,gender) VALUES(?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		do {
			SinhVien objS = new SinhVien();
			objS.nhapThongTin();
			ps.setString(1, objS.getRollNumber());
			ps.setString(2, objS.getName());
			ps.setString(3, objS.getAddress());
			ps.setString(4, objS.getPhoneNumber());
			ps.setBoolean(5, objS.isGender());
			System.out.println("Bạn muốn nhập tiếp không(1: Có | 2: Không");
			Scanner nhap = new Scanner(System.in);
			continues = nhap.nextInt();
			if (continues != 1) {
				isStop = true;
			}
		} while (!isStop);
		// int updateCount[] = ps.executeBatch();
		ps.executeUpdate();
		conn.setAutoCommit(true);
		System.out.println("Auto commit value is: " + conn.getAutoCommit());

	}

	private void suaThongTin() {
		System.out.println("Nhập thông tin sửa:");
		Scanner nhap = new Scanner(System.in);
		sv = new SinhVien();
		System.out.println("Nhập Rollnumber sinh viên cần sửa: ");
		sv.setRollNumber(nhap.nextLine());
		System.out.println("Họ và tên");
		sv.setName(nhap.nextLine());
		System.out.println("Địa chỉ");
		sv.setAddress(nhap.nextLine());
		System.out.println("SĐT");
		sv.setPhoneNumber(nhap.nextLine());
		System.out.println("Giới tính(Nam = 1 | Nữ = 0):");
		sv.setGender(Boolean.parseBoolean(nhap.nextLine()));

	}

	private void load() {
		try {
			String sql = "SELECT * FROM tblsinhvien";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String rollnumber = rs.getString("rollnumber");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String gender = "Nam";
				if (!rs.getBoolean("gender")) {
					gender = "Nữ";
				}
				System.out
						.println(id + "|" + rollnumber + "|" + name + "-" + address + "-" + phone + "-" + "-" + gender);

			}
			System.out.println("--------------------------------");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void update() throws SQLException {
		System.out.println("Cập nhật thông tin sinh viên");
		suaThongTin();
		String sql = "update tblsinhvien set name =?,address=?,phone=?,gender=? where rollnumber =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, sv.getName());
		pstmt.setString(2, sv.getAddress());
		pstmt.setString(3, sv.getPhoneNumber());
		if (sv.isGender()) {
			pstmt.setInt(4, 1);
		} else {
			pstmt.setInt(4, 0);
		}
		pstmt.setString(5, sv.getRollNumber());

		int i = pstmt.executeUpdate();
		if (i > 0) {
			System.out.println("Sửa thành công");

		} else {
			System.out.println("Thất bại, vui lòng kiểm tra lại dữ liệu");
		}
	}

	private void delete() throws SQLException {
		System.out.println("Nhập mã sinh viên muốn xóa");
		Scanner nhap = new Scanner(System.in);
		String rollNumber = nhap.nextLine();
		// String sql = "DELETE FROM tblsinhvien WHERE rollnumber = " + rollNumber+"";
		String sql = "DELETE FROM tblsinhvien WHERE rollnumber = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, rollNumber);
		int i = pstmt.executeUpdate();
		if (i > 0) {
			System.out.println("Xóa thông tin sinh viên thành công với rollnumber = " + rollNumber);
		} else {
			System.out.println("Xóa thông tin sinh viên không thành công với rollnumber = " + rollNumber);

		}

	}

	public void getStudentByID() throws SQLException {
		System.out.println("Nhập Rollnumber sinh viên muốn hiển thị");
		Scanner nhap = new Scanner(System.in);
		String roll = nhap.nextLine();

		String sql = "{CALL sp_getstudent(?)}";
		CallableStatement cs = conn.prepareCall(sql);
		cs.setString(1, roll);
		ResultSet rs = cs.executeQuery();

		if (rs.next()) {
			System.out.println("Tìm thấy sinh viên có roll " + roll);
			System.out.println("Rollnumber: " + rs.getString(2));
			System.out.println("Name: " + rs.getString(3));
			System.out.println("Address: " + rs.getString(4));
			System.out.println("Phone: " + rs.getString(5));
			System.out.println("Gender: " + (rs.getBoolean(6) ? "Nam" : "Nữ"));
		} else {
			System.out.println("Không tìm thấy sinh viên nào có roll:" + roll);
		}

	}

	public static void main(String[] args) {
		MainClass2 main = new MainClass2();
		main.connect();
		int choose = 0;
		do {
			choose = main.showMenu();
			switch (choose) {
			case 1: {
				try {
					main.nhapThongTin();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case 2: {

				main.load();
				break;
			}
			case 3: {
				try {
					main.update();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			case 4: {
				try {
					main.delete();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			case 5: {
				try {
					main.getStudentByID();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			}
		} while (choose > 0 && choose < 6);
		try {
			main.conn.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
