package itu.Farm.controller;

import itu.Farm.bean.EtatParcelle;
import itu.Farm.service.EtatParcelleServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/etat")
public class EtatParcelleContr {
    @Autowired
    EtatParcelleServ serv;

    @GetMapping("/all")
    public List<EtatParcelle> getAll(){return serv.getAll();}

    @GetMapping("{idParcelle}")
    public EtatParcelle find(@PathVariable String idParcelle, @RequestParam String date){ return serv.find(idParcelle, date);}

    @GetMapping("/finished")
    public List<EtatParcelle> getFinished(){return serv.getByEtat(1);}

    @GetMapping("/loading")
    public List<EtatParcelle> getLoading(){return serv.getByEtat(0);}

    @GetMapping("{idParcelle}/histo")
    public List<EtatParcelle> getHistoParcelle(@PathVariable String idParcelle){return serv.histoParcelle(idParcelle);}

    @GetMapping("{idParcelle}/lastEtat")
    public EtatParcelle getLastEtatParcelle(@PathVariable String idParcelle){return serv.lastEtatParcelle(idParcelle);}

    @GetMapping("/details/{idParcelle}")
    public List<Double> getDetails(@PathVariable String idParcelle, @RequestParam String date){return serv.getDetails(idParcelle, date);}

    @GetMapping("/new/{idParcelle}/{idCulture}")
    public EtatParcelle newEtat(@PathVariable String idParcelle, @PathVariable String idCulture){ return serv.cultiver(idParcelle, idCulture);}
}
