package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Document doc = Jsoup.parse(new URL("https://plk.pl/terminarz-i-wyniki.html"), 5000);

        //System.out.println(doc.html());
        List<Team> lista = new ArrayList<Team>();
       // List<Match> listamatch = new ArrayList<Match>();
        Elements tables = doc.getElementsByClass("global");

        for(Element table : tables)
        { Elements tableRows = table.getElementsByTag("tr");
            //System.out.println(tableRows);
             for(Element tableRow : tableRows)
        {
            if(lista.isEmpty()){
                lista.add(new Team(tableRow.select("span[itemprop=homeTeam]").text())); }
            else{
                Iterator<Team> teamIterator = lista.iterator();
                boolean check=false;
                while(teamIterator.hasNext()) {
                  if (teamIterator.next().getName().equals(tableRow.select("span[itemprop=homeTeam]").text())){
                        check=true;}
                }
                if(!check){
                    lista.add(new Team(tableRow.select("span[itemprop=homeTeam]").text()));
                }
                else{
                    check=false;
                 }
                }
            }
        }
        for(Element table : tables)
        { Elements tableRows = table.getElementsByTag("tr");
            for(Element tableRow : tableRows)
            {
                Match match = new Match();
                String wynik=tableRow.select("td.wynik").select("a").text();
                String[] parts = wynik.split(":", 2);
                String part1 = parts[0];
                String part2 = parts[1];
                if(!part2.equals("--")){
                    match.setGuestPoints(Integer.parseInt(part2));}
                if(!part1.equals("--")){
                    match.setHostPoints(Integer.parseInt(part1));}
                    String data =tableRow.select("div.below-sm").text();
                    String pattern = "dd.MM.yyyy HH:mm";
                if(data.length()==10){
                     pattern = "dd.MM.yyyy";
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date date = simpleDateFormat.parse(data);
                match.setLocalDateTime(date);
                Iterator<Team> teamIterator = lista.iterator();
                while(teamIterator.hasNext()) {
                   Team aktualny= teamIterator.next();
                    if (aktualny.getName().equals(tableRow.select("span[itemprop=awayTeam]").text())){
                        match.setGuest(aktualny);
                        match.getGuest().getAwayMatches().add(match);
                        }
                    else if (aktualny.getName().equals(tableRow.select("span[itemprop=homeTeam]").text())){
                        match.setHost(aktualny);
                        match.getHost().getHomeMatches().add(match);
                    }
                }
            }
        }
int z=1;
        for(Team list : lista) {
            System.out.println(z++ +"."+list.getName());

        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Wybierz zespoł (Podaj nr) :\n");
        int nr_zespołu=sc.nextInt();
        System.out.println(lista.get(nr_zespołu-1).getName());
        System.out.println("Mecze wyjazdowe- 1");
        System.out.println("Mecze domowe- 2");
        System.out.println("Wybierz lokalizacje :\n");
        int lokalizacja=sc.nextInt();

        if(lokalizacja==1){
             for (Match list2 : lista.get(nr_zespołu-1).getAwayMatches()) {
                System.out.println(list2.getHost().getName()+"-"+list2.getGuest().getName()+ "  "+list2.getLocalDateTime()+"  "+ list2.getHostPoints()+":"+list2.getGuestPoints());
            }
        }
        else if(lokalizacja==2){

            for (Match list2 : lista.get(nr_zespołu-1).getHomeMatches()) {
                System.out.println(list2.getHost().getName()+"-"+list2.getGuest().getName()+ "  "+list2.getLocalDateTime()+"  "+ list2.getHostPoints()+":"+list2.getGuestPoints());
            }
        }
        else{  System.out.println("błednę dane");}
}
}
