public class member {

    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private int registrationYear;
    private String membershipType;

    private static int totalMembers = 0;

    public member() {
        totalMembers++;
        this.memberId = String.format("MBR%03d", totalMembers);

        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.registrationYear = 0;

        this.membershipType = "Silver";
    }

    public member(String name, String email, String phoneNumber,
                  int registrationYear, String membershipType) {

        totalMembers++;
        this.memberId = String.format("MBR%03d", totalMembers);

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registrationYear = registrationYear;

        String type = membershipType.trim();
        if (type.equalsIgnoreCase("Silver") ||
                type.equalsIgnoreCase("Gold") ||
                type.equalsIgnoreCase("Platinum")) {

            this.membershipType =
                    type.substring(0, 1).toUpperCase() +
                            type.substring(1).toLowerCase();
        } else {
            this.membershipType = "Silver";
        }
    }

    public void displayInfo() {
        System.out.println("== Member Info ==");
        System.out.println("Member ID            : " + memberId);
        System.out.println("Name                 : " + name);
        System.out.println("Email                : " + email);
        System.out.println("Phone Number         : " + phoneNumber);
        System.out.println("Registration Year    : " + registrationYear);
        System.out.println("Membership Type      : " + membershipType);
        System.out.println("Membership Duration  : " + getMembershipDuration() + " tahun");
        System.out.println("Max Borrow Limit     : " + getMaxBorrowLimit() + " buku");
        System.out.println("Late Fee Discount    : " + (getMembershipDiscount() * 100) + "%");
    }

    public void upgradeMembership(String newType) {
        String type = newType.trim();
        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();

        if (!(type.equals("Silver") || type.equals("Gold") || type.equals("Platinum"))) {
            System.out.println("Tipe membership baru tidak valid.");
            return;
        }

        if (this.membershipType.equals("Silver") && type.equals("Gold")) {
            this.membershipType = "Gold";
            System.out.println("Upgrade: Silver → Gold berhasil.");
        } else if (this.membershipType.equals("Gold") && type.equals("Platinum")) {
            this.membershipType = "Platinum";
            System.out.println("Upgrade: Gold → Platinum berhasil.");
        } else if (this.membershipType.equals(type)) {
            System.out.println("Anda sudah berada pada level \"" + type + "\".");
        } else {
            System.out.println("Upgrade tidak sesuai urutan (Silver → Gold → Platinum).");
        }
    }

    public int getMaxBorrowLimit() {
        switch (membershipType) {
            case "Platinum":
                return 10;
            case "Gold":
                return 7;
            default:
                return 5;
        }
    }

    public int getMembershipDuration() {
        int yearNow = java.time.Year.now().getValue();
        return yearNow - registrationYear;
    }

    public double getMembershipDiscount() {
        switch (membershipType) {
            case "Platinum":
                return 0.50;
            case "Gold":
                return 0.25;
            default:
                return 0.10;
        }
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getRegistrationYear() { return registrationYear; }
    public String getMembershipType() { return membershipType; }
    public static int getTotalMembers() { return totalMembers; }
}
