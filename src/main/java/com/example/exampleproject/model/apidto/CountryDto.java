package com.example.exampleproject.model.apidto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class CountryDto implements ICountry {
    private Long id;
    private String name;
    private String code;
    private String flag;

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String code() {
        return code;
    }

    public String flag() {
        return flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CountryDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.code, that.code) &&
                Objects.equals(this.flag, that.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, flag);
    }

    @Override
    public String toString() {
        return "CountryDto[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "code=" + code + ", " +
                "flag=" + flag + ']';
    }

}
