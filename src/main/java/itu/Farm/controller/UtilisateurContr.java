package itu.Farm.controller;


import itu.Farm.bean.Utilisateur;
import itu.Farm.service.UtilisateurServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UtilisateurContr {
    @Autowired
    UtilisateurServ serv;

    @GetMapping("/all")
    public List<Utilisateur> getAll(){
        return serv.getAll();
    }

    @GetMapping("{email}/{pwd}")
    public Utilisateur find(@PathVariable String email, @PathVariable String pwd){
        return serv.find(email, pwd);
    }

    @GetMapping("{id}")
    public Utilisateur findById(@PathVariable String id){return serv.findById(id);}

    @GetMapping("/new/{nom}/{email}/{pass}")
    public Utilisateur newUser(@PathVariable String nom, @PathVariable String email, @PathVariable String pass){return serv.newUser(nom,email,pass);}
}
