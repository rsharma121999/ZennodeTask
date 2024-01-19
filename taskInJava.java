import java.util.Scanner;

public class ZennodeCart {
    static double productAPrice = 20;
    static double productBPrice = 40;
    static double productCPrice = 50;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        // Quantity and gift wrap variables
        int quantityA, quantityB, quantityC;
        boolean giftWrapA, giftWrapB, giftWrapC;

        // Get user input for product quantities and gift wrapping
        System.out.print("Enter quantity for Product A: ");
        quantityA = scanner.nextInt();
        System.out.print("Is Product A a gift? (true/false): ");
        giftWrapA = scanner.nextBoolean();

        System.out.print("Enter quantity for Product B: ");
        quantityB = scanner.nextInt();
        System.out.print("Is Product B a gift? (true/false): ");
        giftWrapB = scanner.nextBoolean();

        System.out.print("Enter quantity for Product C: ");
        quantityC = scanner.nextInt();
        System.out.print("Is Product C a gift? (true/false): ");
        giftWrapC = scanner.nextBoolean();

        // Calculate total amounts for each product
        double totalA = calculateTotal(productAPrice, quantityA);
        double totalB = calculateTotal(productBPrice, quantityB);
        double totalC = calculateTotal(productCPrice, quantityC);

        // Calculate subtotal
        double subtotal = totalA + totalB + totalC;

        // Apply discount rules
        DiscountResult discountResult = applyDiscountRules(quantityA, quantityB, quantityC, subtotal);

        // Calculate gift wrap fee
        double giftWrapFee = calculateGiftWrapFee(giftWrapA, giftWrapB, giftWrapC, quantityA, quantityB, quantityC);

        // Calculate shipping fee
        double shippingFee = calculateShippingFee(quantityA, quantityB, quantityC);

        // Calculate total
        double total = subtotal - discountResult.getDiscountAmount() + giftWrapFee + shippingFee;

        // Display the details
        System.out.println("\nProduct Details:");
        displayProductDetails("Product A", quantityA, totalA);
        displayProductDetails("Product B", quantityB, totalB);
        displayProductDetails("Product C", quantityC, totalC);

        System.out.println("\nSubtotal: $" + subtotal);
        System.out.println("Discount Applied: " + discountResult.getDiscountName() + " - $" + discountResult.getDiscountAmount());
        System.out.println("Gift Wrap Fee: $" + giftWrapFee);
        System.out.println("Shipping Fee: $" + shippingFee);
        System.out.println("Total: $" + total);
    }

    // Helper method to calculate the total amount for a product
    private static double calculateTotal(double price, int quantity) {
        return price * quantity;
    }

    // Helper method to display product details
    private static void displayProductDetails(String productName, int quantity, double totalAmount) {
        System.out.println(productName + ": " + quantity + " units - $" + totalAmount);
    }

    // Helper method to apply discount rules
    private static DiscountResult applyDiscountRules(int quantityA, int quantityB, int quantityC, double subtotal) {
        double discountAmount = 0;
        String discountName = "No Discount";

        // Check "tiered_50_discount"
        if (quantityA + quantityB + quantityC > 30 && (quantityA > 15 || quantityB > 15 || quantityC > 15)) {
            double tieredDiscount = subtotal * 0.5;
            discountAmount = Math.max(discountAmount, tieredDiscount);
            discountName = "Tiered 50% Discount";
        } else if (quantityA > 10) {
            // Check "bulk_5_discount" for Product A
            double bulkDiscountA = calculateTotal(productAPrice, quantityA) * 0.05;
            discountAmount = Math.max(discountAmount, bulkDiscountA);
            discountName = "Bulk 5% Discount (Product A)";
        } else if (quantityB > 10) {
            // Check "bulk_5_discount" for Product B
            double bulkDiscountB = calculateTotal(productBPrice, quantityB) * 0.05;
            discountAmount = Math.max(discountAmount, bulkDiscountB);
            discountName = "Bulk 5% Discount (Product B)";
        } else if (quantityC > 10) {
            // Check "bulk_5_discount" for Product C
            double bulkDiscountC = calculateTotal(productCPrice, quantityC) * 0.05;
            discountAmount = Math.max(discountAmount, bulkDiscountC);
            discountName = "Bulk 5% Discount (Product C)";
        } else if (subtotal > 200) {
            // Check "flat_10_discount"
            discountAmount = 10;
            discountName = "Flat $10 Discount";
        } else if (quantityA + quantityB + quantityC > 20) {
            // Check "bulk_10_discount"
            double bulkDiscount = subtotal * 0.1;
            discountAmount = Math.max(discountAmount, bulkDiscount);
            discountName = "Bulk 10% Discount";
        }

        return new DiscountResult(discountName, discountAmount);
    }

    // Helper method to calculate gift wrap fee
    private static double calculateGiftWrapFee(boolean giftWrapA, boolean giftWrapB, boolean giftWrapC,
                                               int quantityA, int quantityB, int quantityC) {
        int totalGiftWrapUnits = (giftWrapA ? quantityA : 0) + (giftWrapB ? quantityB : 0) + (giftWrapC ? quantityC : 0);
        return totalGiftWrapUnits * 1; // $1 per unit
    }

    // Helper method to calculate shipping fee
    private static double calculateShippingFee(int quantityA, int quantityB, int quantityC) {
        int totalPackages = (int) Math.ceil((double) (quantityA + quantityB + quantityC) / 10);
        return totalPackages * 5; // $5 per package
    }
}

// Class to store discount result
class DiscountResult {
    private final String discountName;
    private final double discountAmount;

    public DiscountResult(String discountName, double discountAmount) {
        this.discountName = discountName;
        this.discountAmount = discountAmount;
    }

    public String getDiscountName() {
        return discountName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
