/*
 * SIP Communicator, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.sip.communicator.impl.gui.main.call;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;

import net.java.sip.communicator.impl.gui.*;
import net.java.sip.communicator.impl.gui.utils.*;
import net.java.sip.communicator.service.audionotifier.*;
import net.java.sip.communicator.service.protocol.*;
import net.java.sip.communicator.service.resources.*;
import net.java.sip.communicator.util.*;
import net.java.sip.communicator.util.swing.*;

/**
 * The <tt>DialPanel</tt> is the panel that contains the buttons to dial a
 * phone number.
 *
 * @author Yana Stamcheva
 */
public class DialPanel
    extends JPanel
    implements MouseListener
{
    /**
     * Our class logger
     */
    private final Logger logger = Logger.getLogger(DialPanel.class);

    /**
     * The dial panel.
     */
    private final JPanel dialPadPanel =
        new JPanel(new GridLayout(4, 3,
            GuiActivator.getResources()
                .getSettingsInt("impl.gui.DIAL_PAD_HORIZONTAL_GAP"),
            GuiActivator.getResources()
                .getSettingsInt("impl.gui.DIAL_PAD_VERTICAL_GAP")));

    /**
     * Current call peers.
     */
    private final java.util.List<CallPeer> callPeersList =
        new LinkedList<CallPeer>();

    /**
     * Creates an instance of <tt>DialPanel</tt> for a specific call, by
     * specifying the parent <tt>CallManager</tt> and the
     * <tt>CallPeer</tt>.
     *
     * @param callPeers the <tt>CallPeer</tt>s, for which the
     * dialpad will be opened.
     */
    public DialPanel(Iterator<? extends CallPeer> callPeers)
    {
        // We need to send DTMF tones to all peers each time the user
        // presses a dial button, so we put the iterator into a list.
        while (callPeers.hasNext())
        {
            this.callPeersList.add(callPeers.next());
        }

        this.init();
    }

    /**
     * Initializes this panel by adding all dial buttons to it.
     */
    public void init()
    {
        this.dialPadPanel.setOpaque(false);

        this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        int width = GuiActivator.getResources()
            .getSettingsInt("impl.gui.DIAL_PAD_WIDTH");

        int height = GuiActivator.getResources()
            .getSettingsInt("impl.gui.DIAL_PAD_HEIGHT");

        this.dialPadPanel.setPreferredSize(new Dimension(width, height));

        ImageID[] images =
            new ImageID[]
            { ImageLoader.ONE_DIAL_BUTTON, ImageLoader.TWO_DIAL_BUTTON,
                ImageLoader.THREE_DIAL_BUTTON, ImageLoader.FOUR_DIAL_BUTTON,
                ImageLoader.FIVE_DIAL_BUTTON, ImageLoader.SIX_DIAL_BUTTON,
                ImageLoader.SEVEN_DIAL_BUTTON, ImageLoader.EIGHT_DIAL_BUTTON,
                ImageLoader.NINE_DIAL_BUTTON, ImageLoader.STAR_DIAL_BUTTON,
                ImageLoader.ZERO_DIAL_BUTTON, ImageLoader.DIEZ_DIAL_BUTTON };
        String[] names =
            new String[]
            { "one", "two", "three", "four", "five", "six", "seven", "eight",
                "nine", "star", "zero", "diez" };
        final int buttonCount = images.length;
        if (buttonCount != names.length)
            throw new IllegalStateException("names");
        Image bgImage = ImageLoader.getImage(ImageLoader.DIAL_BUTTON_BG);

        for (int buttonIndex = 0; buttonIndex < buttonCount; buttonIndex++)
            dialPadPanel.add(createDialButton(bgImage, images[buttonIndex],
                names[buttonIndex]));

        this.add(dialPadPanel, BorderLayout.CENTER);
    }

    /**
     * Creates DTMF button.
     * @param bgImage
     * @param iconImage
     * @param name
     * @return
     */
    private JButton createDialButton(Image bgImage, ImageID iconImage,
        String name)
    {
        JButton button =
            new SIPCommButton(bgImage, ImageLoader.getImage(iconImage));

        button.setAlignmentY(JButton.LEFT_ALIGNMENT);
        button.setName(name);
        button.setOpaque(false);
        button.addMouseListener(this);
        return button;
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    /**
     * Handles the <tt>MouseEvent</tt> triggered when user presses one of the
     * dial buttons.
     * @param e the event
     */
    public void mousePressed(MouseEvent e)
    {
        JButton button = (JButton) e.getSource();
        String buttonName = button.getName();

        AudioNotifierService audioNotifier = GuiActivator.getAudioNotifier();
        DTMFTone tone = null;

        if (buttonName.equals("one"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_ONE).play();
            tone = DTMFTone.DTMF_1;
        }
        else if (buttonName.equals("two"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_TWO).play();
            tone = DTMFTone.DTMF_2;
        }
        else if (buttonName.equals("three"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_THREE).play();
            tone = DTMFTone.DTMF_3;
        }
        else if (buttonName.equals("four"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_FOUR).play();
            tone = DTMFTone.DTMF_4;
        }
        else if (buttonName.equals("five"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_FIVE).play();
            tone = DTMFTone.DTMF_5;
        }
        else if (buttonName.equals("six"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_SIX).play();
            tone = DTMFTone.DTMF_6;
        }
        else if (buttonName.equals("seven"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_SEVEN).play();
            tone = DTMFTone.DTMF_7;
        }
        else if (buttonName.equals("eight"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_EIGHT).play();
            tone = DTMFTone.DTMF_8;
        }
        else if (buttonName.equals("nine"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_NINE).play();
            tone = DTMFTone.DTMF_9;
        }
        else if (buttonName.equals("zero"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_ZERO).play();
            tone = DTMFTone.DTMF_0;
        }
        else if (buttonName.equals("diez"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_DIEZ).play();
            tone = DTMFTone.DTMF_SHARP;
        }
        else if (buttonName.equals("star"))
        {
            audioNotifier.createAudio(SoundProperties.DIAL_STAR).play();
            tone = DTMFTone.DTMF_STAR;
        }

        if(tone != null)
            this.startSendingDtmfTone(tone);
    }

    /**
     * Handles the <tt>MouseEvent</tt> triggered when user releases one of the
     * dial buttons.
     * @param e the event
     */
    public void mouseReleased(MouseEvent e)
    {
        this.stopSendingDtmfTone();
    }

    /**
     * Paints the main background image to the background of this dial panel.
     */
    public void paintComponent(Graphics g)
    {
     // do the superclass behavior first
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        boolean isTextureBackground = Boolean.parseBoolean(GuiActivator.getResources()
            .getSettingsString("impl.gui.IS_CONTACT_LIST_TEXTURE_BG_ENABLED"));

        BufferedImage bgImage
            = ImageLoader.getImage(ImageLoader.MAIN_WINDOW_BACKGROUND);

        // paint the image
        if (bgImage != null)
        {
            if (isTextureBackground)
            {
                Rectangle rect
                    = new Rectangle(0, 0,
                            bgImage.getWidth(null),
                            bgImage.getHeight(null));

                TexturePaint texture = new TexturePaint(bgImage, rect);

                g2.setPaint(texture);

                g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            else
            {
                g.setColor(new Color(
                    GuiActivator.getResources()
                        .getColor("contactListBackground")));

                // paint the background with the choosen color
                g.fillRect(0, 0, getWidth(), getHeight());

                g2.drawImage(bgImage,
                        this.getWidth() - bgImage.getWidth(),
                        this.getHeight() - bgImage.getHeight(),
                        this);
            }
        }
    }

    /**
     * Sends a DTMF tone to the current DTMF operation set.
     *
     * @param dtmfTone The DTMF tone to send.
     */
    private void startSendingDtmfTone(DTMFTone dtmfTone)
    {
        Iterator<? extends CallPeer> callPeers = this.callPeersList.iterator();

        try
        {
            while (callPeers.hasNext())
            {
                CallPeer peer = callPeers.next();
                OperationSetDTMF dtmfOpSet
                    = peer
                        .getProtocolProvider()
                            .getOperationSet(OperationSetDTMF.class);

                if (dtmfOpSet != null)
                    dtmfOpSet.startSendingDTMF(peer, dtmfTone);
            }
        }
        catch (Throwable e1)
        {
            logger.error("Failed to send a DTMF tone.", e1);
        }
    }

    /**
     * Stop sending DTMF tone.
     */
    private void stopSendingDtmfTone()
    {
        Iterator<? extends CallPeer> callPeers = this.callPeersList.iterator();

        try
        {
            while (callPeers.hasNext())
            {
                CallPeer peer = callPeers.next();
                OperationSetDTMF dtmfOpSet
                    = peer
                        .getProtocolProvider()
                            .getOperationSet(OperationSetDTMF.class);

                if (dtmfOpSet != null)
                    dtmfOpSet.stopSendingDTMF(peer);
            }
        }
        catch (Throwable e1)
        {
            logger.error("Failed to send a DTMF tone.", e1);
        }
    }
}
