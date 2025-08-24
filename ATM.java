package datastructure;
import java.util.*;

// Interface for ATM operations
interface ATMOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void showMiniStatement();
}

// Transaction class to log deposits and withdrawals
class Transaction {
    String type;
    double amount;
    Date timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return timestamp + " | " + type + " | ₹" + amount;
    }
}

// User class implementing ATMOperations
class User implements ATMOperations {
    String userId;
    String pin;
    double balance;
    List<Transaction> transactions;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
        System.out.println("✅ ₹" + amount + " deposited successfully.");
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("❌ Insufficient balance.");
        } else {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
            System.out.println("✅ ₹" + amount + " withdrawn successfully.");
        }
    }

    @Override
    public void showMiniStatement() {
        System.out.println("\n📄 Mini Statement:");
        int count = 0;
        for (int i = transactions.size() - 1; i >= 0 && count < 5; i--, count++) {
            System.out.println(transactions.get(i));
        }
    }
}

// Main ATM class
public class ATM {
    static Map<String, User> users = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        users.put("123456", new User("123456", "4321", 10000));

        System.out.println("🔐 Welcome to Java ATM");
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();

        User currentUser = users.get(userId);
        if (currentUser == null) {
            System.out.println("❌ User not found.");
            return;
        }

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        if (!currentUser.authenticate(pin)) {
            System.out.println("❌ Incorrect PIN.");
            return;
        }

        ATMOperations atmUser = currentUser; // Interface reference

        int choice;
        do {
            System.out.println("\n💳 ATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("💰 Current Balance: ₹" + currentUser.balance);
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ₹");
                    double depositAmt = sc.nextDouble();
                    atmUser.deposit(depositAmt);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmt = sc.nextDouble();
                    atmUser.withdraw(withdrawAmt);
                    break;
                case 4:
                    atmUser.showMiniStatement();
                    break;
                case 5:
                    System.out.println("👋 Thank you for using Java ATM.");
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        } while (choice != 5);
    }
}