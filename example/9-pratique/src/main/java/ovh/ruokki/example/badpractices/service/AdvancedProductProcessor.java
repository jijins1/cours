package ovh.ruokki.example.badpractices.service;

public class AdvancedProductProcessor extends ProductProcessor {

    public AdvancedProductProcessor(boolean validation, boolean logging) {
        super("ADVANCED", validation, logging);
    }

    @Override
    public String process(String productType, double price, String category) {
        StringBuilder sb = new StringBuilder();
        sb.append("Advanced processing of ").append(productType).append(" ");
        sb.append("in ").append(category).append(" ");
        sb.append("priced at ").append(price).append(" ");
        sb.append("using ").append(processType).append(" mode");

        // More complex logic to show bad inheritance
        switch (category) {
            case "ELECTRONICS":
                sb.append(" - Warranty calculation");
                if (price > 1000) {
                    sb.append(" - Premium support");
                }
                break;
            case "FOOD":
                sb.append(" - Nutritional analysis");
                sb.append(" - Temperature control");
                break;
            case "BOOKS":
                sb.append(" - ISBN validation");
                sb.append(" - Author indexing");
                break;
            case "CLOTHING":
                sb.append(" - Size validation");
                sb.append(" - Material check");
                break;
            default:
                sb.append(" - Category specific processing");
        }

        if ("PREMIUM".equals(productType)) {
            sb.append(" - VIP handling");
        }

        for (int i = 0; i < 3; i++) {  // Adding unnecessary complexity
            sb.append(" - Step ").append(i + 1).append(" completed");
        }

        return sb.toString();
    }

    @Override
    public boolean validateProduct(String productType, double price) {
        boolean basicValidation = price >= 0 && !productType.isEmpty();
        if (!basicValidation) return false;

        // Advanced validation logic
        if ("PREMIUM".equals(productType)) {
            return price > 100;  // Premium products must be expensive
        } else if (price > 10000) {
            return false;  // Too expensive
        }

        return true;
    }

    @Override
    public void logProcessing(String message) {
        System.out.println("[ADVANCED] " + message);
        System.out.println("[ADVANCED] Additional logging details: " + System.currentTimeMillis());
    }
}
