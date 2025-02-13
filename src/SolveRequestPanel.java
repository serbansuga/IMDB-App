import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolveRequestPanel extends JPanel implements ListSelectionListener, ActionListener {
    JLabel solveRequestLabel;
    DefaultListModel listModelSolveRequest;
    JList solveRequestList;
    JScrollPane solveRequestPane;
    JButton solveRequestButton, rejectRequestButton;
    MenuPage menu;
    public SolveRequestPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        solveRequestLabel = new JLabel("Choose request you want to solve:");
        solveRequestLabel.setBounds(40,40,400,25);
        solveRequestLabel.setFont(new Font("Calibri", Font.BOLD, 18));


        listModelSolveRequest = new DefaultListModel<>();
        IMDB.getInstance().requestsSolve.clear();
        if(IMDB.getInstance().connectedUser instanceof Contributor<?>) {
            for (Object request : ((Staff) IMDB.getInstance().connectedUser).requests) {
                listModelSolveRequest.addElement(request);
                IMDB.getInstance().requestsSolve.add((Request) request);
            }
        }
        if(IMDB.getInstance().connectedUser instanceof Admin){
            for (Object request : ((Staff) IMDB.getInstance().connectedUser).requests) {
                listModelSolveRequest.addElement(request);
                IMDB.getInstance().requestsSolve.add((Request) request);
            }

            for (Object request : RequestHolder.requestList) {
                listModelSolveRequest.addElement(request);
                IMDB.getInstance().requestsSolve.add((Request) request);
            }
        }

        solveRequestList = new JList<>(listModelSolveRequest);
        solveRequestList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        solveRequestList.addListSelectionListener(this);
        solveRequestPane = new JScrollPane(solveRequestList);
        solveRequestPane.setBounds(40,65,700, 280);

        solveRequestButton = new JButton("Solve request");
        solveRequestButton.setBounds(40, 365,130,40);
        solveRequestButton.setFocusable(false);
        solveRequestButton.setBorder(BorderFactory.createEtchedBorder());
        solveRequestButton.addActionListener(this);

        rejectRequestButton = new JButton("Reject request");
        rejectRequestButton.setBounds(200, 365,130,40);
        rejectRequestButton.setFocusable(false);
        rejectRequestButton.setBorder(BorderFactory.createEtchedBorder());
        rejectRequestButton.addActionListener(this);

        this.add(solveRequestLabel);
        this.add(solveRequestPane);
        this.add(solveRequestButton);
        this.add(rejectRequestButton);
    }

    public User getUserFromUsername(String username){
        for(User user : IMDB.getInstance().users){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == solveRequestButton){
            if(solveRequestList.isSelectionEmpty() == false){
                int indexList = solveRequestList.getSelectedIndex();
                Request request = IMDB.getInstance().requestsSolve.get(indexList);
                if(IMDB.getInstance().connectedUser instanceof Contributor<?>) {
                    ((Contributor)IMDB.getInstance().connectedUser).deleteRequest(request);
                }else  if(IMDB.getInstance().connectedUser instanceof Admin<?>) {
                    ((Admin)IMDB.getInstance().connectedUser).deleteRequest(request);
                }
                String notification = "Your request of type " + request.requestType + "(" + request.description + ") was solved!";
                Observer observer = getUserFromUsername(request.username);
                observer.update(notification);
                if(request.requestType == Request.RequestTypes.MOVIE_ISSUE || request.requestType == Request.RequestTypes.ACTOR_ISSUE) {

                    ((User)observer).strategy = new SolveRequestExperience();
                    ((User)observer).updateExperience();
                }

                JOptionPane.showMessageDialog(null, "Request solved", "Info", JOptionPane.INFORMATION_MESSAGE);
                menu.dispose();
                new MenuPage("Menu");
            }else{
                JOptionPane.showMessageDialog(null, "Select the request!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if(e.getSource() == rejectRequestButton){
            if(solveRequestList.isSelectionEmpty() == false){
                int indexList = solveRequestList.getSelectedIndex();
                Request request = IMDB.getInstance().requestsSolve.get(indexList);
                if(IMDB.getInstance().connectedUser instanceof Contributor<?>) {
                    ((Contributor)IMDB.getInstance().connectedUser).deleteRequest(request);
                }else  if(IMDB.getInstance().connectedUser instanceof Admin<?>) {
                    ((Admin)IMDB.getInstance().connectedUser).deleteRequest(request);
                }
                String notification = "Your request of type " + request.requestType + "(" + request.description + ") was rejected!";
                Observer observer = getUserFromUsername(request.username);
                observer.update(notification);
                JOptionPane.showMessageDialog(null, "You rejected the request.", "Info", JOptionPane.INFORMATION_MESSAGE);
                menu.dispose();
                new MenuPage("Menu");
            }else{
                JOptionPane.showMessageDialog(null, "Select the request!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
