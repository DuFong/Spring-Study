package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter // @Setter 안쓴다! 값 타입은 변경이 불가하도록 만들어야 함!
public class Address {

    private String city;

    private String street;

    private String zipcode;

    protected Address()  // JPA 스펙상 @Entity or @Embeddable은 디폴트생성자가 필요 (protected~private)
    {

    }

    public Address(String city, String street, String zipcode)
    {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
