# Testausdokumentti
Sovelluksella on jokaiselle logiikkaluokalle oma testiluokkansa, joissa on runsaasti yksikkötestejä. Integraatiotestaus on implisiittisesti rakennettu näihin, etenkin Fractal- luokkaan, joka riippuu vahvasti muista luokista. Käyttöliittymän toiminnallisuutta on testattu manuaalisesti.

### Sovelluslogiikka
Integraatiotestaus (pääasiallisesti FractalTest- luokassa) simuloi tilanteita, joita esiintyy käyttäjän piirtäessä fraktaaleja ja muuttaessa asetuksia. Koska toimivuus riippu ensisijaisesti siitä, mitä näkyy käyttöliittymän piirtoalustalla, testien täytyy tilapäisesti ottaa muistiin, missä koordinaateissa ollaan käyty. Jotkut ulkonäköominaisuudet, kuten väri ja piirtonopeus, eivät näy logiikkatasolla, joten näitä ei testata automaattisesti.

Yksikkötestausta on jokaisen luokan melkein kaikille metodeille (lukuunottamatta Fractal-luokan tietokantametodit, koska nämä viittaavat suoraan DAO-luokan metodeihin). Poikkeustilanteet yritetään simuloida mahdollisimman hyvin.

### SettingsDAOTest
Tietokantatoiminnallisuus testataan erillisellä "TestDatabase"- tietokannalla. Metodien testit ovat sovellettuja simuloimaan käyttäjän mahdollisia syötteitä. Haarautumakatavuus on täsä heikompi, koska catch- haarat saavutetaan vain aiheuttamalla SQLException. Tälle en löytänyt simulointimahdollisuuksia aiheuttamatta ongelmia sovelluslogiikassa.

### Testauskattavuus
Testaamatta jäi yllä mainitut tilanteet.
