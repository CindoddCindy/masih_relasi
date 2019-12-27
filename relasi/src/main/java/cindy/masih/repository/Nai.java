package cindy.masih.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cindy.masih.model.NonAnggota;

public interface Nai {

    Long size();
    List<NonAnggota> findAll (int page, int limit);
    NonAnggota findById (@NotNull Long id);
    boolean save(@NotNull NonAnggota nonanggota);
    boolean update(@NotNull Long id, @NotBlank String name,@NotBlank String email, @NotBlank String password, @NotBlank String data); // @NotNull int grade);
    boolean destroy(@NotNull Long id);
}