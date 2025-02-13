import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActorUpdatePanel extends JPanel implements ActionListener, ListSelectionListener {
    static DefaultListModel listModelUpdateActor;
    JList actorUpdateList;
    JScrollPane actorUpdatePane;
    JLabel updateActorLabel, updateActorBioLabel;
    JTextArea actorBioUpdate;
    JButton updateActorButton;
    Actor actor;
    MenuPage menu;
    public ActorUpdatePanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().addedActors.clear();
        listModelUpdateActor = new DefaultListModel<>();
        if(IMDB.getInstance().connectedUser instanceof Regular == false) {
            for (Object obj : ((Staff) IMDB.getInstance().connectedUser).addedItems) {
                if (obj instanceof Actor) {
                    IMDB.getInstance().addedActors.add((Actor) obj);
                    listModelUpdateActor.addElement((Actor) obj);
                }
            }
        }

        actorUpdateList = new JList<>(listModelUpdateActor);
        actorUpdateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorUpdateList.addListSelectionListener(this);
        actorUpdatePane = new JScrollPane(actorUpdateList);
        actorUpdatePane.setBounds(40,80,260, 280);

        updateActorLabel = new JLabel("Choose actor you want to edit:");
        updateActorLabel.setBounds(40,55,400,25);
        updateActorLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        updateActorBioLabel = new JLabel("Change actor biography");
        updateActorBioLabel.setBounds(340,55,400,25);
        updateActorBioLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        actorBioUpdate = new JTextArea();
        //actorBioUpdate.setText(actor.biography);

        actorBioUpdate.setFont(new Font("Calibri", Font.BOLD, 16));
        actorBioUpdate.setLineWrap(true);
        actorBioUpdate.setEditable(true);
        actorBioUpdate.setBounds(340, 80, 340, 300);

        updateActorButton = new JButton("Update details");
        updateActorButton.setBounds(340, 410,140,40);
        updateActorButton.setFocusable(false);
        updateActorButton.setBorder(BorderFactory.createEtchedBorder());
        updateActorButton.addActionListener(this);

        this.add(updateActorButton);
        this.add(actorUpdatePane);
        this.add(updateActorLabel);
        this.add(actorBioUpdate);
        this.add(updateActorBioLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateActorButton) {
            if (actorUpdateList.isSelectionEmpty() == false) {
                String new_bio = actorBioUpdate.getText();
                if (((Staff) IMDB.getInstance().connectedUser).addedItems.contains(actor)) {
                    Actor new_actor = new Actor(actor.name, new_bio);
                    new_actor.performances = actor.performances;
                    ((Staff) IMDB.getInstance().connectedUser).updateActor(new_actor);
                    JOptionPane.showMessageDialog(null, "Actor updated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                } else {
                    JOptionPane.showMessageDialog(null, "Actor is not added by you!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select an actor!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(actorUpdateList.isSelectionEmpty()){
            return;
        }
        if(e.getValueIsAdjusting() == false) {
            int indexList1 = actorUpdateList.getSelectedIndex();
            actor = IMDB.getInstance().addedActors.get(indexList1);
            actorBioUpdate.setText(actor.biography);
        }
    }
}
