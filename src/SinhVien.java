import java.util.Scanner;

public class SinhVien {
	private int id;
	private String rollNumber;
	private String name;
	private String address;
	private String phoneNumber;
	private boolean gender;

	public SinhVien() {

	}

	public SinhVien(int id, String rollNumber, String name, String address, String phoneNumber, boolean gender) {
		super();
		this.id = id;
		this.rollNumber = rollNumber;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public void nhapThongTin() {
		System.out.println("Nhập thông tin sinh viên");
		Scanner nhap = new Scanner(System.in);
		System.out.println("Mã sinh viên: ");
		this.rollNumber = nhap.nextLine();
		System.out.println("Họ và Tên: ");
		this.name = nhap.nextLine();
		System.out.println("Địa chỉ: ");
		this.address = nhap.nextLine();
		System.out.println("SĐT: ");
		this.phoneNumber = nhap.nextLine();
		System.out.println("Giới tính(Nam = 1 | Nữ = 0");
		this.gender = Boolean.parseBoolean(nhap.nextLine());

	}

}
