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

public class MoveToFrontTest {
  private final String ABRA_SOURCE = "ABRACADABRA!";
  private final byte[] ABRA_ENCODED =
      new byte[] {0x41, 0x42, 0x52, 0x02, 0x44, 0x01, 0x45, 0x01, 0x04, 0x04, 0x02, 0x26};

  @Test
  public void testEncode() throws FileNotFoundException {
    System.setIn(new ByteArrayInputStream(ABRA_SOURCE.getBytes()));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    MoveToFront.main(new String[] {"-"});
    assertThat(outputStream.toByteArray()).asList().containsExactlyElementsIn(Bytes.asList(ABRA_ENCODED)).inOrder();
  }

  @Test
  public void testDecode() throws FileNotFoundException {
    System.setIn(new ByteArrayInputStream(ABRA_ENCODED));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    MoveToFront.main(new String[] {"+"});
    assertThat(outputStream.toString()).isEqualTo(ABRA_SOURCE);
  }

  @Test
  public void testEncodeAesop() throws FileNotFoundException {
    System.setIn(new FileInputStream(new File("aesop.txt")));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    MoveToFront.main(new String[] {"-"});
    List<Byte> result = Bytes.asList(outputStream.toByteArray());
    assertThat(result.subList(0, 9))
        .containsExactlyElementsIn(
            Bytes.asList(new byte[] {0x41, 0x65, 0x73, 0x70, 0x71, 0x2c, 0x03, 0x26, 0x4a})).inOrder();
  }

  @Test
  public void testEncodeGif() throws FileNotFoundException {
    System.setIn(new FileInputStream(new File("us.gif")));
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    MoveToFront.main(new String[] {"-"});
    List<Byte> result = Bytes.asList(outputStream.toByteArray());
    assertThat(result.subList(0, 4))
        .containsExactlyElementsIn(
            Bytes.asList(new byte[] {0x47, 0x49, 0x48, 0x3B})).inOrder();
  }
}
