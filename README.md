# Chaos Game
Sovelluksella voi piirtää [Chaos Game](https://en.wikipedia.org/wiki/Chaos_game) fraktaaleja graafisen generaattorin avulla.
## Dokumentaatio
[Vaatimusmäärittely](https://github.com/haxrober/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/haxrober/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/haxrober/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/haxrober/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[Testausdokumentti](https://github.com/haxrober/otm-harjoitustyo/blob/master/dokumentointi/testausdokumentti.md)

## Versiot

[Release 1](https://github.com/haxrober/otm-harjoitustyo/releases/tag/viikko5)

[Release 2](https://github.com/haxrober/otm-harjoitustyo/releases/tag/viikko6)

[Lopullinen palautus](https://github.com/haxrober/otm-harjoitustyo/releases/tag/1.0)

## Komentoriviohjeet

- Testaus: `mvn test`
- Testikattavuusraportin generointi: `mvn test jacoco:report`
- .jar -tiedoston generointi: `mvn package`
- Checkstyle: `mvn jxr:jxr checkstyle:checkstyle`
- Javadocin generointi: `mvn javadoc:javadoc`
