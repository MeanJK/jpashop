package jpabook.jpashop.controller;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotBlank
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
