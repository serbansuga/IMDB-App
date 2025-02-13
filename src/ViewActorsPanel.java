import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;

public class ViewActorsPanel extends JPanel implements ItemListener, ListSelectionListener, ActionListener {
    DefaultListModel listModelActors;
    JList actorList;
    JScrollPane actorPane;
    JButton viewDetailsActors;
    JLabel actorsLabel;
    JRadioButton filterActors;
    Actor actor;
    public ViewActorsPanel(){
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        listModelActors = new DefaultListModel<>();
        for(Actor actor : IMDB.getInstance().actors){
            listModelActors.addElement(actor);
            //IMDB.getInstance().filtered.add(prod);
        }

        actorList = new JList<>(listModelActors);
        actorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorList.addListSelectionListener(this);
        actorPane = new JScrollPane(actorList);
        actorPane.setBounds(40,60,500, 280);

        viewDetailsActors = new JButton("View details");
        viewDetailsActors.setBounds(40, 360,130,40);
        viewDetailsActors.setFocusable(false);
        viewDetailsActors.setBorder(BorderFactory.createEtchedBorder());
        viewDetailsActors.addActionListener(this);

        actorsLabel = new JLabel();
        actorsLabel.setText("Actors:");
        actorsLabel.setBounds(40,35,210,25);
        actorsLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        filterActors = new JRadioButton("Sort by name");
        filterActors.setFocusable(false);
        filterActors.setBounds(200,365,200,30);
        filterActors.setFont(new Font(null, Font.PLAIN, 19));
        filterActors.addItemListener(this);
        filterActors.setBackground(new Color(221,221,221));

        this.add(actorPane);
        this.add(viewDetailsActors);
        this.add(filterActors);
        this.add(actorsLabel);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(filterActors.isSelected()){
            Collections.sort(IMDB.getInstance().actors);
            listModelActors.removeAllElements();
            for (Actor actor : IMDB.getInstance().actors) {
                listModelActors.addElement(actor);
            }

            ActorUpdatePanel.listModelUpdateActor.removeAllElements();
            for (Actor actor : IMDB.getInstance().actors) {
                ActorUpdatePanel.listModelUpdateActor.addElement(actor);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewDetailsActors) {
            if (actorList.isSelectionEmpty() == false) {
                int indexList1 = actorList.getSelectedIndex();
                actor = IMDB.getInstance().actors.get(indexList1);
                ActorDetailsPageMenu actorPage = new ActorDetailsPageMenu("Actor Details", actor);
            } else {
                JOptionPane.showMessageDialog(null, "Select an actor!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
