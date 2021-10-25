package com.company;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Team {
    String name;
    List<Match> awayMatches= new ArrayList<Match>();
    List<Match> homeMatches=new ArrayList<Match>();
    public Team (String name){
        this.name=name;
    }
}
