package voip.audio_forwarder;

import com.biasedbit.efflux.session.SingleParticipantSession;
import voip.mixing.MixingPipedInputStream;

import java.io.IOException;

/**
 * Forward Audio data in a RTPSession mixing streams of other conversation participants.
 *
 * @author Micieli
 * @date 2015/04/28
 */
public class Forwarder {

    public static final int DATA_LENTH = 160;

    private MixingPipedInputStream audioInputStream;
    private SingleParticipantSession session;

    public Forwarder(MixingPipedInputStream audioInputStream, SingleParticipantSession session) {
        this.audioInputStream = audioInputStream;
        this.session = session;
    }

    public void forwardData() {
        byte[] data = new byte[DATA_LENTH];
        try {
            if (audioInputStream.read(data) > 0)
                session.sendData(data, System.currentTimeMillis(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
