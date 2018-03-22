package com.mycompany.unicafe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author haxrober
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    @Test
    public void rahaaOikeinAlussa() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void maukkaitaMyytyOikeinAlussa() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void edullisiaMyytyOikeinAlussa() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiToimiiKunKateistaRiittavasti() {
        int vaihto = kassa.syoEdullisesti(250);
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(10, vaihto);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiToimiiKunKateistaEiRiittavasti() {
        int vaihto = kassa.syoEdullisesti(230);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(230, vaihto);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiToimiiKunKateistaRiittavasti() {
        int vaihto = kassa.syoMaukkaasti(420);
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(20, vaihto);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiToimiiKunKateistaEiRiittavasti() {
        int vaihto = kassa.syoMaukkaasti(250);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(250, vaihto);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenOstaminenKortillaToimiiKunRahaaTarpeeksi() {
        Maksukortti m = new Maksukortti(240);
        boolean ret = kassa.syoEdullisesti(m);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertTrue(ret);
    }
    
    @Test
    public void edullisenOstaminenKortillaToimiiKunRahaaEiTarpeeksi() {
        Maksukortti m = new Maksukortti(230);
        boolean ret = kassa.syoEdullisesti(m);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertTrue(!ret);
    }
    
    
    @Test
    public void maukkaanOstaminenKortillaToimiiKunRahaaTarpeeksi() {
        Maksukortti m = new Maksukortti(400);
        boolean ret = kassa.syoMaukkaasti(m);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertTrue(ret);
    }
    
    @Test
    public void maukkaanOstaminenKortillaToimiiKunRahaaEiTarpeeksi() {
        Maksukortti m = new Maksukortti(230);
        boolean ret = kassa.syoMaukkaasti(m);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertTrue(!ret);
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaOstettaessaEdullinen() {
        Maksukortti m = new Maksukortti(240);
        boolean ret = kassa.syoEdullisesti(m);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaOstettaessaMaukas() {
        Maksukortti m = new Maksukortti(400);
        boolean ret = kassa.syoMaukkaasti(m);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleLataaminenToimiiKunLisattavaMaaraEpanegatiivinen() {
        Maksukortti m = new Maksukortti(0);
        kassa.lataaRahaaKortille(m, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
        assertEquals(1000, m.saldo());
    }
    
    @Test
    public void kortilleLataaminenToimiiKunLisattavaMaaraNegatiivinen() {
        Maksukortti m = new Maksukortti(0);
        kassa.lataaRahaaKortille(m, -1000);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, m.saldo());
    }
}
