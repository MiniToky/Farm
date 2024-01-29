package itu.Farm.controller;

import itu.Farm.bean.Culture;
import itu.Farm.bean.Parcelle;
import itu.Farm.bean.Utilisateur;
import itu.Farm.service.ParcelleServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parcelle")
public class ParcelleContr {
    @Autowired
    ParcelleServ serv;

    @GetMapping("/all")
    public List<Parcelle> getAll(){
        return serv.getAll();
    }

    @GetMapping("{id}")
    public Parcelle find(@PathVariable String id){
        return serv.find(id);
    }

    @GetMapping("{id}/cultures")
    public List<Culture> culturePossible(@PathVariable String id){return serv.getCulturePossible(id);}

    @GetMapping("/new/{idTerrain}/{largeur}/{longueur}")
    public Parcelle newParcelle(@PathVariable String idTerrain, @PathVariable double largeur, @PathVariable double longueur){return serv.create(idTerrain, largeur, longueur);}

    @GetMapping("/setcultures/{id}/{cult}")
    public void setCulture(@PathVariable String id, @PathVariable List<String> cult){ serv.setCulturePossible(id, cult);}

}
