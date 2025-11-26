package tiagoribeine.com.github.data.dto;

//PARA ENTRADA: CREATE/UPDATE

import java.util.Objects;

public class ProductRequestDTO {

    private String name; //Só o que o Client PODE enviar - Nunca ID< Status, timestamps etc
    private Integer quantity;
    private Double price;
    private String status;

    public ProductRequestDTO(){}

    //NÃO TEM ID (gerado pelo sistema)
    //Construtor vazio + Getters/Setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductRequestDTO that = (ProductRequestDTO) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuantity(), getPrice(), getStatus());
    }
}
