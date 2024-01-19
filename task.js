class ZennodeCart {
    static productAPrice = 20;
    static productBPrice = 40;
    static productCPrice = 50;

    static calculateTotal(price, quantity) {
        return price * quantity;
    }

    static displayProductDetails(productName, quantity, totalAmount) {
        console.log(`${productName}: ${quantity} units - $${totalAmount}`);
    }

    static applyDiscountRules(quantityA, quantityB, quantityC, subtotal) {
        let discountAmount = 0;
        let discountName = "No Discount";

        // Check "tiered_50_discount"
        if (quantityA + quantityB + quantityC > 30 && (quantityA > 15 || quantityB > 15 || quantityC > 15)) {
            let tieredDiscount = subtotal * 0.5;
            discountAmount = Math.max(discountAmount, tieredDiscount);
            discountName = "Tiered 50% Discount";
        } else if (quantityA > 10) {
            // Check "bulk_5_discount" for Product A
            let bulkDiscountA = this.calculateTotal(this.productAPrice, quantityA) * 0.05;
            discountAmount = Math.max(discountAmount, bulkDiscountA);
            discountName = "Bulk 5% Discount (Product A)";
        } else if (quantityB > 10) {
            // Check "bulk_5_discount" for Product B
            let bulkDiscountB = this.calculateTotal(this.productBPrice, quantityB) * 0.05;
            discountAmount = Math.max(discountAmount, bulkDiscountB);
            discountName = "Bulk 5% Discount (Product B)";
        } else if (quantityC > 10) {
            // Check "bulk_5_discount" for Product C
            let bulkDiscountC = this.calculateTotal(this.productCPrice, quantityC) * 0.05;
            discountAmount = Math.max(discountAmount, bulkDiscountC);
            discountName = "Bulk 5% Discount (Product C)";
        } else if (subtotal > 200) {
            // Check "flat_10_discount"
            discountAmount = 10;
            discountName = "Flat $10 Discount";
        } else if (quantityA + quantityB + quantityC > 20) {
            // Check "bulk_10_discount"
            let bulkDiscount = subtotal * 0.1;
            discountAmount = Math.max(discountAmount, bulkDiscount);
            discountName = "Bulk 10% Discount";
        }

        return { discountName, discountAmount };
    }

    static calculateGiftWrapFee(giftWrapA, giftWrapB, giftWrapC, quantityA, quantityB, quantityC) {
        let totalGiftWrapUnits = (giftWrapA ? quantityA : 0) + (giftWrapB ? quantityB : 0) + (giftWrapC ? quantityC : 0);
        return totalGiftWrapUnits * 1; // $1 per unit
    }

    static calculateShippingFee(quantityA, quantityB, quantityC) {
        let totalPackages = Math.ceil((quantityA + quantityB + quantityC) / 10);
        return totalPackages * 5; // $5 per package
    }

    static main() {
        const readline = require('readline-sync');

        // Quantity and gift wrap variables
        let quantityA, quantityB, quantityC;
        let giftWrapA, giftWrapB, giftWrapC;

        // Get user input for product quantities and gift wrapping
        quantityA = parseInt(readline.question("Enter quantity for Product A: "));
        giftWrapA = readline.keyInYNStrict("Is Product A a gift?");

        quantityB = parseInt(readline.question("Enter quantity for Product B: "));
        giftWrapB = readline.keyInYNStrict("Is Product B a gift?");

        quantityC = parseInt(readline.question("Enter quantity for Product C: "));
        giftWrapC = readline.keyInYNStrict("Is Product C a gift?");

        // Calculate total amounts for each product
        let totalA = this.calculateTotal(this.productAPrice, quantityA);
        let totalB = this.calculateTotal(this.productBPrice, quantityB);
        let totalC = this.calculateTotal(this.productCPrice, quantityC);

        // Calculate subtotal
        let subtotal = totalA + totalB + totalC;

        // Apply discount rules
        let discountResult = this.applyDiscountRules(quantityA, quantityB, quantityC, subtotal);

        // Calculate gift wrap fee
        let giftWrapFee = this.calculateGiftWrapFee(giftWrapA, giftWrapB, giftWrapC, quantityA, quantityB, quantityC);

        // Calculate shipping fee
        let shippingFee = this.calculateShippingFee(quantityA, quantityB, quantityC);

        // Calculate total
        let total = subtotal - discountResult.discountAmount + giftWrapFee + shippingFee;

        // Display the details
        console.log("\nProduct Details:");
        this.displayProductDetails("Product A", quantityA, totalA);
        this.displayProductDetails("Product B", quantityB, totalB);
        this.displayProductDetails("Product C", quantityC, totalC);

        console.log("\nSubtotal: $" + subtotal);
        console.log("Discount Applied: " + discountResult.discountName + " - $" + discountResult.discountAmount);
        console.log("Gift Wrap Fee: $" + giftWrapFee);
        console.log("Shipping Fee: $" + shippingFee);
        console.log("Total: $" + total);
    }
}

ZennodeCart.main();
