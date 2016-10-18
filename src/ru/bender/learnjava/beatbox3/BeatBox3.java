package ru.bender.learnjava.beatbox3;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static ru.bender.learnjava.beatbox.MiniMusicPlayer1.makeEvent;

/**
 * Created by bender on 11.10.2016.
 */
public class BeatBox3 {

    private static final String SERVER_IP = "127.0.0.1";

    private String myName = "Piter";
    private JFrame theFrame;
    private JPanel mainPanel;
    private ArrayList<JCheckBox> checkBoxList;
    private JTextField messageField;
    private DefaultListModel<BeatBoxMessage> incomingMessages;
    private JList jlistIncomingMessages;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private Socket socketToServer;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat",
            "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
            "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo",
            "Open Hi Conga"};
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {

        BeatBox3 beatBoxClient = new BeatBox3();
        beatBoxClient.buildGUI();
        beatBoxClient.connectToServer();
    }

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        checkBoxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);


        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Up tempo");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Down tempo");
        stop.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton sendIt = new JButton("Send It");
        sendIt.addActionListener(new MySendItListener());
        buttonBox.add(sendIt);

        BorderLayout chatBorderLayout = new BorderLayout();
        JPanel chatPanel = new JPanel(chatBorderLayout);
        buttonBox.add(chatPanel);
        messageField = new JTextField();
        chatPanel.add(messageField, BorderLayout.NORTH);

        incomingMessages = new DefaultListModel();
        jlistIncomingMessages = new JList(incomingMessages);
        jlistIncomingMessages.addListSelectionListener(new IncomingMessagesListener());
        chatPanel.add(jlistIncomingMessages, BorderLayout.CENTER);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < instrumentNames.length; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(BorderLayout.CENTER, background);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    public void buildTrackAndStart() {
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = (JCheckBox) checkBoxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 9, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    private void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }


    private void setCheckboxesStates(boolean[] checkBoxesStates) {
        for (int i = 0; i < checkBoxesStates.length; i++) {
            checkBoxList.get(i).setSelected(checkBoxesStates[i]);
        }
    }

    private boolean[] returnCheckboxesState() {
        boolean[] checkBoxesState = new boolean[256];
        for (int i = 0; i < checkBoxesState.length; i++) {
            if (checkBoxList.get(i).isSelected()) {
                checkBoxesState[i] = true;
            }
        }
        return checkBoxesState;
    }

    private BeatBoxMessage makeBeatBoxMessage() {
        BeatBoxMessage beatBoxMessage = new BeatBoxMessage();
        beatBoxMessage.text = messageField.getText();
        beatBoxMessage.checkBoxesStates = returnCheckboxesState();
        beatBoxMessage.sender = this.myName;
        return beatBoxMessage;
    }

    private void connectToServer() {
        try {
            socketToServer = new Socket(SERVER_IP, BeatBoxServer.SERVER_PORT);
            inputStream = Helper.getObjectInputStreamBySocket(socketToServer);
            outputStream = Helper.getObjectOutputStreamBySocket(socketToServer);
            Thread inputThread = new Thread(new InputFromServer());
            inputThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reciveInputMessage(BeatBoxMessage message){
        incomingMessages.addElement(message);
    }

    private void sendOutputMessage(){
        BeatBoxMessage message = makeBeatBoxMessage();
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseMessage(BeatBoxMessage message) {
        setCheckboxesStates(message.checkBoxesStates);
    }


    private class InputFromServer implements Runnable {
        @Override
        public void run() {
            BeatBoxMessage message;
            try {
                while ((message = (BeatBoxMessage) inputStream.readObject()) != null) {
                    reciveInputMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    class MyStartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }


    }
    class MyStopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }


    }
    class MyUpTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }


    }
    class MyDownTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * .97));
        }

    }
    private class MySendItListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            sendOutputMessage();
        }

    }

    private class IncomingMessagesListener implements javax.swing.event.ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            chooseMessage((BeatBoxMessage) jlistIncomingMessages.getSelectedValue());
        }
    }
}
