import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteRequestPanel extends JPanel implements ActionListener, ListSelectionListener {
    JLabel deleteRequestLabel;
    DefaultListModel listModelDelRequest;
    JList deleteRequestList;
    JScrollPane deleteRequestPane;
    JButton delRequestButton;
    public DeleteRequestPanel(){
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        deleteRequestLabel = new JLabel("Choose the request to delete");
        deleteRequestLabel.setBounds(40,95,400,25);
        deleteRequestLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        IMDB.getInstance().requestsDelete.clear();
        listModelDelRequest = new DefaultListModel<>();
        listModelDelRequest.removeAllElements();
        for(Request request : IMDB.getInstance().requests){
            if(request.username.equals(IMDB.getInstance().connectedUser.username)) {
                listModelDelRequest.addElement(request);
                IMDB.getInstance().requestsDelete.add(request);
            }
        }

        deleteRequestList = new JList<>(listModelDelRequest);
        deleteRequestList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deleteRequestList.addListSelectionListener(this);
        //  actorListRequests.setVisible(false);
        deleteRequestPane = new JScrollPane(deleteRequestList);
        deleteRequestPane.setBounds(40,120,350, 280);

        delRequestButton = new JButton("Delete request");
        delRequestButton.setBounds(40, 420,130,40);
        delRequestButton.setFocusable(false);
        delRequestButton.setBorder(BorderFactory.createEtchedBorder());
        delRequestButton.addActionListener(this);


        this.add(deleteRequestLabel);
        this.add(deleteRequestPane);
        this.add(delRequestButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == delRequestButton){
            if(deleteRequestList.isSelectionEmpty() == false) {
                int indexList = deleteRequestList.getSelectedIndex();
                Request request = IMDB.getInstance().requestsDelete.get(indexList);
               // System.out.println(request.username);
                if(IMDB.getInstance().connectedUser instanceof Regular<?>){
                    ((Regular)IMDB.getInstance().connectedUser).deleteRequest(request);
                    listModelDelRequest.remove(indexList);
                }else if(IMDB.getInstance().connectedUser instanceof Contributor<?>){
                    ((Contributor)IMDB.getInstance().connectedUser).deleteRequest(request);
                    listModelDelRequest.remove(indexList);
                }
                JOptionPane.showMessageDialog(null, "Request deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
//                this.dispose();
//                new MenuPage("Menu");
            }
            else{
                JOptionPane.showMessageDialog(null, "Select a request!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
