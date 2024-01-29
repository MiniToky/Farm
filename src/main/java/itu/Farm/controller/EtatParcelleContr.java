package itu.Farm.controller;

import itu.Farm.bean.EtatParcelle;
import itu.Farm.service.EtatParcelleServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/etqt")
public class EtatParcelleContr {
    @Autowired
    EtatParcelleServ serv;

    @GetMapping("/all")
    public List<EtatParcelle> getAll(){return serv.getAll();}

    @GetMapping("/finished")
    public List<EtatParcelle> getFinished(){return serv.getFinished();}

    @GetMapping("/loading")
    public List<EtatParcelle> getLoading(){return serv.getEnCours();}

    @GetMapping("/details/{idParcelle}/{plantation}")
    public List<Double> getDetails(@PathVariable String idParcelle, @PathVariable Timestamp plantation){return serv.getDetails(idParcelle, plantation);}

    @GetMapping("/new/{idParcelle}/{idCulture}")
    public EtatParcelle newEtat(@PathVariable String idParcelle, @PathVariable String idCulture){ return serv.cultiver(idParcelle, idCulture);}
}
