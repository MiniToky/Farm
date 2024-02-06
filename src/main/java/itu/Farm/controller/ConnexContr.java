package itu.Farm.controller;

import itu.Farm.conn.Connexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@RestController
@RequestMapping("/conn")
public class ConnexContr {
    @Autowired
    Connexion co;

    @GetMapping("/get")
    public Connection getConnection(){ return co.connect();}
}
