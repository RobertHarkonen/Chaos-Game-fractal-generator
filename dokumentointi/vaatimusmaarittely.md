# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksella voi piirtää fraktaaleja [Chaos Game](https://en.wikipedia.org/wiki/Chaos_game)- sääntöjen mukaan. Käyttäjä saa itse sijoittaa ankkuripisteitä graafisen käyttöliittymän piirtotasolle ja muokata generaattorin asetuksia piirtääkseen monia erilaisia kuvioita.

## Perusversion toiminnallisuuksia
### Alussa
- Käyttäjä voi joko aloittaa tyhjästä pohjasta tai avata aiemman fraktaalin asetukset
- Fraktaalien asetukset ovat tallennettuja paikalliseen tietokantaan

### Uusi fraktaali
- Vasemmassa laidassa on asetuspalkki, jossa voidaan muuttaa generaattorin asetuksia, tyhjentää piirokset tai aloittaa generointi.
- Piirtotasolle voidaan sijoittaa *ankkuripisteitä*, ts. pisteet, joista jokaisessa iteraatiossa valitaan satunnainen ja liikutaan kohti.
- Kuinka paljon tämänhetkisestä pisteestä valittuun pisteeseen liikutaan kutsumme fraktaalin *suhteeksi*. Tämä on jokin nollaa suurempi luku, ja voidaan muuttaa asetuspalkista.
- Voidaan myös määritellä muutamia erityisiä sääntöja jotka vaikuttavat fraktaalin muotoon, esim. samaa ankkuripistettä ei voida valita kaksi kertaa peräkkäin.
- Fraktaalin ulkonäköön voidaan vielä vaikuttaa muuttamalla taustan ja pisteiden värejä sekä pisteiden kokoa.

### Fraktaalin tallennus
- Fraktaalin generoivat asetukset voidaan tallentaa tietokantaan ylälaidasta löytyvästä valikosta.
- Samasta valikosta voidaan myös avata aiempia fraktaaleja.

## Lisäominaisuuksia ja jatkokehitysideoita
**Jos aika sallii, allaolevan listan toiminnallisuuksia voidaan toteuttaa:**
- Kokonaisten valmiiksi piirrettyjen fraktaalikuvien tallennus
- Lisää väriominaisuuksia, esim. piirtovärin muuttuminen ajan kuluessa tai riippuen koordinaateista.
- Monimutkaisempia sääntöja, esim. jokaisessa iteraatiossa valitaan satunnaisen ankkuripisteen sijaan satunnainen jono ankkuripisteitä. Mahdolliset jonot voidaan määritellä etukäteen.

**Enemmän vaivaa vaativat ideat, todennäköisesti ei toteuteta**
- Online- tietokanta johon voidaan tallentaa omia fraktaaleja ja josta voidaan ladata muiden käyttäjien fraktaaleja.
- Vaihtoehtoinen fraktaalilogiikka, esim. Koch Snowflake
