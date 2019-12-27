package cindy.masih.controller;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import cindy.masih.model.Adna;
import cindy.masih.repository.Anai;

@Controller("/adna")
public class Anac{

    private Anai anai;
    
    Anac(Anai anai){
        this.anai = anai;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public String index(@QueryValue int page, @QueryValue int limit) {
        List<Adna> adna = anai.findAll(page, limit);
        HashMap<String, Object> data = new HashMap<>();
        data.put("page", Math.ceil(anai.size() / limit));
        data.put("data", adna);
        return (new Gson()).toJson(data);
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String show(@PathVariable Long id) {
        return (new Gson()).toJson(anai.findById(id));
    }

    @Post(consumes=MediaType.APPLICATION_JSON)//APPLICATION_FORM_URLENCODED 
    public String save(@Body Adna adna) {
        HashMap<String, Object> data = new HashMap<>();
        if (anai.save(adna)) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson()).toJson(data);
    }

    @Put(consumes=MediaType.APPLICATION_JSON)//APPLICATION_FORM_URLENCODED
    public String update(@Body Adna adna) {
        HashMap<String, Object> data = new HashMap<>();
        if (anai.update(c.getId(), c.getName(), c.getEmail(), c.getPassword(), c.getData())) {
        // if (repository.updateCode(c.getId(), c.getCode())) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson()).toJson(data);
    }

    @Delete("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String destroy(@PathVariable Long id) {
        HashMap<String, Object> data = new HashMap<>();
        if (repository.destroy(id)) {
            data.put("status", "ok");
        } else {
            data.put("status", "fail");
        }
        return (new Gson()).toJson(data);
        // return String.valueOf(id);
    }
}