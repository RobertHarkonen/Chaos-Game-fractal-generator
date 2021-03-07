# Testausdokumentti
Sovelluksella on jokaiselle logiikkaluokalle oma testiluokkansa, joissa on runsaasti yksikkötestejä. Integraatiotestaus on implisiittisesti rakennettu näihin, etenkin Fractal- luokkaan, joka riippuu vahvasti muista luokista. Käyttöliittymän toiminnallisuutta on testattu manuaalisesti.

### Sovelluslogiikka
Integraatiotestaus (pääasiallisesti FractalTest- luokassa) simuloi tilanteita, joita esiintyy käyttäjän piirtäessä fraktaaleja ja muuttaessa asetuksia. Koska toimivuus riippu ensisijaisesti siitä, mitä näkyy käyttöliittymän piirtoalustalla, testien täytyy tilapäisesti ottaa muistiin, missä koordinaateissa ollaan käyty. Jotkut ulkonäköominaisuudet, kuten väri ja piirtonopeus, eivät näy logiikkatasolla, joten näitä ei testata automaattisesti.

Yksikkötestausta on jokaisen luokan melkein kaikille metodeille (lukuunottamatta Fractal-luokan tietokantametodit, koska nämä viittaavat suoraan DAO-luokan metodeihin). Poikkeustilanteet yritetään simuloida mahdollisimman hyvin.

### SettingsDAOTest
Tietokantatoiminnallisuus testataan erillisellä "TestDatabase"- tietokannalla. Metodien testit ovat sovellettuja simuloimaan käyttäjän mahdollisia syötteitä. Haarautumakatavuus on täsä heikompi, koska catch- haarat saavutetaan vain aiheuttamalla SQLException. Tälle en löytänyt simulointimahdollisuuksia aiheuttamatta ongelmia sovelluslogiikassa.

### Testauskattavuus
Testaamatta jäi yllä mainitut tilanteet.
Rivi- ja haarautumakattavuus näkyy allaolevasta kuvasta:
![testit](https://github.com/RobertHarkonen/Chaos-Game-fractal-generator/blob/master/Documentation/testit.png?raw=true)
Testatut osat näkyy tarkemmin generoimalla jacoco raportti (katso ohjeet).

### Käyttöliittymä
Käyttöliittymän testaus suoritettiin manuaalisesti. Kävin läpi kaikki käyttöohjeessa mainitut ominaisuudet, muodostin näistä mahdollisen monta tilannetta ja yritin kaataa ohjelman. Ei onnistunut.

### Ongelmia
Klikkaamalla tallennuspainiketta monta kertaa erittäin nopeasti voi aiheutua tallennusongelmia, esim kaikki ankkuripisteet eivät esiintyneet kun yritin ladata asetukset. Tämän korjaamiseen ei ollut riittävästi aikaa, mutta se ei olekaan tilanne, johon törmätään sovelluksen tavallisessa käytössä.
