import com.google.common.primitives.Bytes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import org.junit.Test;


import static com.google.common.truth.Truth.assertThat;

public class BurrowsWheelerTest {
  private final String ABRA_SOURCE = "ABRACADABRA!";
  private final byte[] ABRA_TRANSFORMED =
      new byte[] {0x00, 0x00, 0x00, 0x03, 0x41, 0x52, 0x44, 0x21, 0x52, 0x43, 0x41, 0x41, 0x41, 0x41, 0x42, 0x42};
  private final String STARS_SOURCE = "***";
  private final byte[] STARS_TRANSFORMED = new byte[] {0x00, 0x00, 0x00, 0x00, 0x2A, 0x2A, 0x2A};
  private final byte[] GIF_SOURCE = new byte[] {0x47, 0x49, 0x46, 0x38, 0x39, 0x61, (byte) 0x8e, 0x01};

  @Test
  public void testTransform() {
    System.setIn(new ByteArrayInputStream(ABRA_SOURCE.getBytes()));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    BurrowsWheeler.main(new String[] {"-"});
    assertThat(outputStream.toByteArray()).asList().containsExactlyElementsIn(Bytes.asList(ABRA_TRANSFORMED)).inOrder();
  }

  @Test
  public void testInverseTransform() {
    System.setIn(new ByteArrayInputStream(ABRA_TRANSFORMED));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    BurrowsWheeler.main(new String[] {"+"});
    assertThat(outputStream.toString()).isEqualTo(ABRA_SOURCE);
  }

  @Test
  public void testStarsTransform() {
    System.setIn(new ByteArrayInputStream(STARS_SOURCE.getBytes()));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    BurrowsWheeler.main(new String[] {"-"});
    assertThat(outputStream.toByteArray()).asList()
        .containsExactlyElementsIn(Bytes.asList(STARS_TRANSFORMED)).inOrder();
  }

  @Test
  public void testStarsInverseTransform() {
    System.setIn(new ByteArrayInputStream(STARS_TRANSFORMED));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    BurrowsWheeler.main(new String[] {"+"});
    assertThat(outputStream.toString()).isEqualTo(STARS_SOURCE);
  }

  @Test
  public void testGifTransform() throws FileNotFoundException {
    System.setIn(new FileInputStream(new File("us.gif")));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    BurrowsWheeler.main(new String[] {"-"});
//    List<Byte> result = Bytes.asList(outputStream.toByteArray());
//    assertThat(result.subList(0, 8)).containsExactlyElementsIn(Bytes.asList(GIF_SOURCE)).inOrder();
  }

  @Test
  public void testGifInverseTransform() throws FileNotFoundException {
    System.setIn(new FileInputStream(new File("us.gif.bwt")));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    BurrowsWheeler.main(new String[] {"+"});
    List<Byte> result = Bytes.asList(outputStream.toByteArray());
    assertThat(result.subList(0, 8)).containsExactlyElementsIn(Bytes.asList(GIF_SOURCE)).inOrder();
  }
}
