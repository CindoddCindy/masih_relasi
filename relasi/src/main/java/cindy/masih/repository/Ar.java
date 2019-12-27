package cindy.masih.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import cindy.masih.model.Anggota;

@Singleton
public class Ar implements Ai {

    @PersistenceContext
    private EntityManager manager;

    public Ar(@CurrentSession EntityManager manager){
        this.manager = manager;
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        Long count = manager.createQuery("select count(*) from Anggota where deleted_at IS NULL", Long.class).getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public List<Anggota> findAll(int page, int limit) {
        TypedQuery<Anggota> query = manager
                .createQuery("from Anggota where deleted_at IS NULL", Anggota.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Anggota findById(@NotNull Long id) {
        Anggota query = manager.find(Anggota.class, id);
        return query;
    }

    @Override
    @Transactional
    public boolean save(@NotNull Anggota anggota) {
        try {
            manager.persist(anggota);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String name,String email, String password, String data) {
        try {

            Anggota c = manager.find(Insert.class, id);
            if (name.equals("-")==false) c.setName(name);
            if (email.equals("-")==false) c.setEmail(email);
            if (password.equals("-")==false) c.setPassword(password);
            if (data.equals("-")==false) c.setData(data);
            
          //  if (grade != 0) c.setGrade(grade);
            c.setUpdated_At(new Date());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean destroy(@NotNull Long id) {
    try {
        Anggota anggota = manager.find(Anggota.class, id); // diganti 
        if(insert != null){
            manager.remove(anggota);
        }
        anggota.setDeleted_At(new Date());
        return true;
        } catch (Exception e) {
            return false;
        }
    }

}