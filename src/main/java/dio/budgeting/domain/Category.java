package dio.budgeting.domain;

public enum Category {
    GROCERIES("Bebidas e alimentos"),
    PHARMA("Medicamentos e outros items de farmácia"),
    AUTO("Veiculos e outros tipos relacionados");

    private String description;

    private Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 
