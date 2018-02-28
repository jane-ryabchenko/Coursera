public class Outcast {
  private WordNet wordNet;

  public Outcast(WordNet wordnet) {
    this.wordNet = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns) {
    long maxDist = -1;
    String outcast = null;
    for (int i = 0; i < nouns.length; i++) {
      int currentDist = 0;
      for (int j = 0; j < nouns.length; j++) {
        if (i != j) {
          currentDist += wordNet.distance(nouns[i], nouns[j]);
        }
      }
      if (currentDist > maxDist) {
        maxDist = currentDist;
        outcast = nouns[i];
      }
    }
    return outcast;
  }
}
