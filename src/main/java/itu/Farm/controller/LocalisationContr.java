package itu.Farm.controller;

import itu.Farm.bean.Localisation;
import itu.Farm.service.LocalisationServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loc")
public class LocalisationContr {
    @Autowired
    LocalisationServ serv;

    @GetMapping("/all")
    public List<Localisation> getAll(){
        return serv.getAll();
    }

    @GetMapping("{id}")
    public Localisation find(@PathVariable String id){
        return serv.find(id);
    }

}
