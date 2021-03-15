package com.meli.desafiospring.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter @Setter
public class BuyOrderDTO {

    private Integer id;
    private List<ProductTicketDTO> articles;
    private Integer total;
    private StatusCodeDTO statusCodeDTO;

    public BuyOrderDTO(Integer id, List<ProductTicketDTO> articles, Integer total, StatusCodeDTO statusCodeDTO) {
        this.id = id;
        this.articles = articles;
        this.total = total;
        this.statusCodeDTO = statusCodeDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyOrderDTO that = (BuyOrderDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(articles, that.articles) && Objects.equals(total, that.total) && Objects.equals(statusCodeDTO, that.statusCodeDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articles, total, statusCodeDTO);
    }

    @Override
    public String toString() {
        return "BuyOrderDTO{" +
                "id=" + id +
                ", articles=" + articles +
                ", total=" + total +
                ", statusCodeDTO=" + statusCodeDTO +
                '}';
    }
}
