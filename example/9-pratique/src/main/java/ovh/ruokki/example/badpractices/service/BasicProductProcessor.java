package ovh.ruokki.example.badpractices.service;

public class BasicProductProcessor extends ProductProcessor {

    public BasicProductProcessor(boolean validation, boolean logging) {
        super("BASIC", validation, logging);
    }

    @Override
    public String process(String productType, double price, String category) {
        String processingLogic = "Basic processing of " + productType + " in category " + category + " with price " + price + " using " + processType;
        if ("ELECTRONICS".equals(category)) {
            processingLogic += " - Applying discount logic";
        } else if ("FOOD".equals(category)) {
            processingLogic += " - Checking expiration";
        } else {
            processingLogic += " - Standard processing";
        }
        return processingLogic;
    }

    @Override
    public boolean validateProduct(String productType, double price) {
        // Basic validation logic
        return price >= 0 && !productType.isEmpty();
    }

    @Override
    public void logProcessing(String message) {
        System.out.println("[BASIC] " + message);
    }
}
