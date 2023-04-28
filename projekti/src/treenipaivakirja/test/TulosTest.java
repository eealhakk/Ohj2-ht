package treenipaivakirja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import treenipaivakirja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.28 10:12:32 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class TulosTest {


  // Generated by ComTest BEGIN
  /** testRekisteroi103 */
  @Test
  public void testRekisteroi103() {    // Tulos: 103
    Tulos tulos1 = new Tulos(); 
    assertEquals("From: Tulos line: 105", 0, tulos1.getTunnusNro()); 
    tulos1.rekisteroi(); 
    Tulos tulos2 = new Tulos(); 
    tulos2.rekisteroi(); 
    int n1 = tulos1.getTunnusNro(); 
    int n2 = tulos2.getTunnusNro(); 
    assertEquals("From: Tulos line: 111", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse250 */
  @Test
  public void testParse250() {    // Tulos: 250
    Tulos tulos = new Tulos(); 
    tulos.parse("   2  |  1  |  Penkki  |  5x5  |  100kg  |  vitutti"); 
    assertEquals("From: Tulos line: 253", 2, tulos.getTunnusNro()); 
    assertEquals("From: Tulos line: 254", 1, tulos.getPaivaNro()); 
    assertEquals("From: Tulos line: 255", "2 1 Penkki 5x5 100kg vitutti", tulos.tulosta(System.out)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnna325 */
  @Test
  public void testAnna325() {    // Tulos: 325
    Tulos tulos = new Tulos(); 
    tulos.parse("   2  |  1  |  Penkki  |  5x5  |  100kg  |  vitutti"); 
    assertEquals("From: Tulos line: 328", "2", tulos.anna(0)); 
    assertEquals("From: Tulos line: 329", "1", tulos.anna(1)); 
    assertEquals("From: Tulos line: 330", "Penkki", tulos.anna(2)); 
    assertEquals("From: Tulos line: 331", "5x5", tulos.anna(3)); 
    assertEquals("From: Tulos line: 332", "100kg", tulos.anna(4)); 
    assertEquals("From: Tulos line: 333", "vitutti", tulos.anna(5)); 
  } // Generated by ComTest END
}