package cindy.masih.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cindy.masih.model.Adna;

public interface Anai {

    Long size();
    List<Adna> findAll (int page, int limit);
    Adna findById (@NotNull Long id);
    boolean save(@NotNull Adna adna);
    boolean update(@NotNull Long id, @NotBlank String name,@NotBlank String email, @NotBlank String password, @NotBlank String data); // @NotNull int grade);
    boolean destroy(@NotNull Long id);
}