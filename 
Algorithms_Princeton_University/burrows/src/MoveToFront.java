import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
  private static final byte[] ALPHABET = new byte[256];

  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode() {
    for (int i = 0; i < 256; i++) {
      ALPHABET[i] = (byte) i;
    }

    while (!BinaryStdIn.isEmpty()) {
      byte in = BinaryStdIn.readByte();
      byte index = shift(in);
      BinaryStdOut.write(index);
    }
    BinaryStdIn.close();
    BinaryStdOut.close();
  }

  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode() {
    for (int i = 0; i < 256; i++) {
      ALPHABET[i] = (byte) i;
    }

    while (!BinaryStdIn.isEmpty()) {
      int index = BinaryStdIn.readByte() & 0xFF;
      byte out = ALPHABET[index];
      System.arraycopy(ALPHABET, 0, ALPHABET, 1, index);
      ALPHABET[0] = out;
      BinaryStdOut.write(out);
    }
    BinaryStdIn.close();
    BinaryStdOut.close();
  }

  private static byte shift(byte in) {
    byte index = 0;
    for (int i = 0; i < ALPHABET.length; i++) {
      byte a = ALPHABET[i];
      if (a == in) {
        System.arraycopy(ALPHABET, 0, ALPHABET, 1, i);
        ALPHABET[0] = a;
        break;
      }
      index++;
    }
    return index;
  }

  // if args[0] is '-', apply move-to-front encoding
  // if args[0] is '+', apply move-to-front decoding
  public static void main(String[] args) {
    if (args == null || args.length == 0) {
      throw new IllegalArgumentException();
    }
    if ("-".equals(args[0])) {
      encode();
    } else if ("+".equals(args[0])) {
      decode();
    } else {
      throw new IllegalArgumentException();
    }
  }
}
