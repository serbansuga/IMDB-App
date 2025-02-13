import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelActorSystemPanel extends JPanel implements ActionListener, ListSelectionListener {
    DefaultListModel listModelActors;
    JList actorList;
    JScrollPane actorPane;
    JLabel delActorSysLabel;
    JButton delActorSysButton;
    Actor actor;
    MenuPage menu;
    public DelActorSystemPanel(MenuPage menu) {
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().addedActors.clear();
        listModelActors = new DefaultListModel<>();
        listModelActors.removeAllElements();
        if(IMDB.getInstance().connectedUser instanceof Regular == false) {
            for (Object obj : ((Staff) IMDB.getInstance().connectedUser).addedItems) {
                if (obj instanceof Actor) {
                    IMDB.getInstance().addedActors.add((Actor) obj);
                    listModelActors.addElement((Actor) obj);
                }
            }
        }

        actorList = new JList<>(listModelActors);
        actorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorList.addListSelectionListener(this);
        actorPane = new JScrollPane(actorList);
        actorPane.setBounds(40,80,500, 280);

        delActorSysLabel = new JLabel("Choose actor you want to delete:");
        delActorSysLabel.setBounds(40,55,400,25);
        delActorSysLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        delActorSysButton = new JButton("Delete Actor");
        delActorSysButton.setBounds(40, 380,140,40);
        delActorSysButton.setFocusable(false);
        delActorSysButton.setBorder(BorderFactory.createEtchedBorder());
        delActorSysButton.addActionListener(this);

        this.add(actorPane);
        this.add(delActorSysButton);
        this.add(delActorSysLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delActorSysButton) {
            if (actorList.isSelectionEmpty() == false) {
                int indexList = actorList.getSelectedIndex();
                actor = IMDB.getInstance().addedActors.get(indexList);
                if (IMDB.getInstance().connectedUser instanceof Admin<?> == false) {
                    if (((Staff) IMDB.getInstance().connectedUser).addedItems.contains(actor) == false) {
                        JOptionPane.showMessageDialog(null, "Actor is not added by you!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ((Contributor) IMDB.getInstance().connectedUser).removeActorSystem(actor.name);
                        IMDB.getInstance().addedActors.remove(actor);
                        JOptionPane.showMessageDialog(null, "Actor deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                        menu.dispose();
                        new MenuPage("Menu");
                    }
                } else {

                    ((Admin) IMDB.getInstance().connectedUser).removeActorSystem(actor.name);
                    JOptionPane.showMessageDialog(null, "Actor deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select an actor to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
