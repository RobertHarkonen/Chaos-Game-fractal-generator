# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovelluksella voi piirtää fraktaaleja [Chaos Game](https://en.wikipedia.org/wiki/Chaos_game)- sääntöjen mukaan. Käyttäjä saa itse sijoittaa ankkuripisteitä graafisen käyttöliittymän piirtotasolle ja muokata generaattorin asetuksia piirtääkseen monia erilaisia kuvioita.

## Perusversion toiminnallisuuksia
### Alussa
- Käyttäjä aloittaa tyhjästa pohjasta, mutta voi halutessaan ladata aiemman fraktaalin asetukset
- Fraktaalien asetukset ovat tallennettuja paikalliseen tietokantaan

### Uusi fraktaali
- Vasemmassa laidassa on asetuspalkki, jossa voidaan muuttaa generaattorin asetuksia, tyhjentää piirokset tai aloittaa generointi.
- Piirtotasolle voidaan sijoittaa *ankkuripisteitä*, ts. pisteet, joista jokaisessa iteraatiossa valitaan satunnainen ja liikutaan kohti.
- Kuinka paljon tämänhetkisestä pisteestä valittuun pisteeseen liikutaan kutsumme fraktaalin *suhteeksi* (ratio). Tämä on jokin luku välillä 0 - 1.5. Suhde on oletusarvoisesti 0.5, mutta voidaan muuttaa asetuspalkista.
- Voidaan myös asettaa niin sanottu *toistumissääntö* (repeat rule), jonka mukaan samaa ankkuripistettä ei voida valita kaksi kertaa peräkkäin. Tällä on huomattava vaikutus fraktaalin ulkonäköön.
- Fraktaalin ulkonäköön voidaan vielä vaikuttaa muuttamalla piirroksen hienoutta (grain size) ja väriä.
- Piirtonopeus voidaan säätää asetuspalkista asettamalla haluttu määrä iteraatioita jokaisessa AnimationTimer- syklissä (iterations per cycle).

### Fraktaalin tallennus
- Fraktaalin generoivat asetukset voidaan tallentaa tietokantaan oikeasta laidasta löytyvästä valikosta.
- Samasta valikosta voidaan myös avata aiempia tallennettuja fraktaaliasetuksia.

## Jatkokehitysideoita
**Heplommin toteutettavat:**
- Kokonaisten valmiiksi piirrettyjen fraktaalikuvien tallennus (tässä vaiheessa voi ottaa screenshotteja)
- Lisää väriominaisuuksia, esim. piirtovärin muuttuminen ajan kuluessa tai riippuen koordinaateista.
- Monimutkaisempia sääntöja, esim. jokaisessa iteraatiossa valitaan satunnaisen ankkuripisteen sijaan satunnainen jono ankkuripisteitä. Mahdolliset jonot voidaan määritellä etukäteen.

**Enemmän vaivaa vaativat ideat, todennäköisesti ei toteuteta**
- Online- tietokanta johon voidaan tallentaa omia fraktaaleja ja josta voidaan ladata muiden käyttäjien fraktaaleja.
- Vaihtoehtoinen fraktaalilogiikka, esim. Koch Snowflake
