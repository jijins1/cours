package ovh.ruokki.example.badpractices.service;

public abstract class ProductProcessor {

    protected String processType;
    protected boolean enabledValidation;
    protected boolean enabledLogging;

    public ProductProcessor(String type, boolean validation, boolean logging) {
        this.processType = type;
        this.enabledValidation = validation;
        this.enabledLogging = logging;
    }

    public abstract String process(String productType, double price, String category);

    public abstract boolean validateProduct(String productType, double price);

    public abstract void logProcessing(String message);

    public final String executeProcessing(String productType, double price, String category) {
        if (enabledValidation) {
            if (price < 0 || productType == null || productType.isEmpty()) {
                System.out.println("Common validation failed for " + productType);
                return "Validation failed";
            }
        }
        String result = process(productType, price, category);
        if (enabledLogging) {
            System.out.println("Common processing: " + result);
        }
        return result;
    }
}
