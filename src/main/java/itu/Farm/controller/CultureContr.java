package itu.Farm.controller;

import itu.Farm.bean.Culture;
import itu.Farm.service.CultureServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/culture")
public class CultureContr {
    @Autowired
    CultureServ serv;

    @GetMapping("/all")
    public List<Culture> getAll(){
        return serv.getAll();
    }

    @GetMapping("{id}")
    public Culture find(@PathVariable String id){
        return serv.find(id);
    }

    @GetMapping("/new/{nom}/{rend}/{prix}/{duree}")
    public Culture create(@PathVariable String nom, @PathVariable double rend, @PathVariable double prix, @PathVariable double duree){return serv.newCulture(nom,rend,prix,duree);}

}
