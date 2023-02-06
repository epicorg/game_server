package voip.mixing;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * Mixing stream for u-law audio byte.
 *
 * @author Micieli
 * @date 2015/04/28
 * @see PipedInputStream
 * @see <a href="http://en.wikipedia.org/wiki/%CE%9C-law_algorithm">μ-law</a>
 */
public class MixingPipedInputStream {

    private Collection<PipedInputStream> streams;

    /**
     * Generates a new MixingInputStream.
     *
     * @param streams a <code>Collection</code> of <code>PipedInputStream</code> containing data to mixing
     */
    public MixingPipedInputStream(Collection<PipedInputStream> streams) {
        super();
        this.streams = streams;
    }

    /**
     * Finds the minimum number of byte available for all the streams.
     *
     * @return the number of byte available
     */
    public synchronized int available() throws IOException {
        Iterator<PipedInputStream> iterator = streams.iterator();
        int available = iterator.next().available();
        while (iterator.hasNext()) {
            available = Math.max(available, iterator.next().available());
        }
        return available;
    }

    /**
     * Mixes the audio data from the <code>PipedInputStreams</code>.
     *
     * @param b byte
     */
    public int read(byte[] b) throws IOException {
        int byteToRead = b.length;
        int[] coded = new int[byteToRead];
        int found = 0;

        for (PipedInputStream stream : streams) {
            if (stream.available() > byteToRead) {
                found++;
                byte[] data = new byte[byteToRead];
                stream.read(data);
                for (int i = 0; i < data.length; i++) {
                    short linear = TConversionTool.ulaw2linear(data[i]);
                    coded[i] += linear;
                }
            }
        }

        if (found == 0)
            return 0;

        for (int i = 0; i < b.length; i++) {
            b[i] = TConversionTool.linear2ulaw(coded[i] / found);
        }

        return 1;
    }

}
