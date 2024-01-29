package itu.Farm.controller;

import itu.Farm.bean.Parcelle;
import itu.Farm.bean.Terrain;
import itu.Farm.bean.Utilisateur;
import itu.Farm.service.TerrainServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/terrain")
public class TerrainContr {
    @Autowired
    TerrainServ serv;

    @GetMapping("/all")
    public List<Terrain> getAll(){
        return serv.getAll();
    }

    @GetMapping("{id}")
    public Terrain find(@PathVariable String id){
        return serv.find(id);
    }

    @GetMapping("{id}/photos")
    public List<String> getPhotos(@PathVariable String id){ return serv.getPhotos(id);}

    @GetMapping("{id}/parcelles")
    public List<Parcelle> getParcelles(@PathVariable String id){ return serv.getParcelles(id);}

    @GetMapping("/create/{loc}/{desc}/{idU}/{nbP}")
    public Terrain create(@PathVariable String loc, @PathVariable String desc, @PathVariable String idU, @PathVariable int nbP){return serv.create(loc,desc,idU,nbP);}

    @GetMapping("/valider/{id}")
    public Terrain valider(@PathVariable String id){return serv.valider(id);}

    @GetMapping("{id}/addPhoto/{path}")
    public List<String> addPhotoo(@PathVariable String id, @PathVariable List<String> path){return serv.setPhotos(id, path);}

    @GetMapping("/all/{id}")
    public List<Terrain> terrainProprio(@PathVariable String id){ return serv.terrainProprio(id);}

}
