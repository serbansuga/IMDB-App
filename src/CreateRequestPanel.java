import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateRequestPanel extends JPanel implements ActionListener, ListSelectionListener {
    JLabel requestChooseLabel, requestDescriptionLabel;
    JComboBox requestChoose;
    DefaultListModel listModelActorRequest, listModelProdRequest;
    JList actorListRequests, prodListRequests;
    JScrollPane actorListRequestsPane, prodListRequestsPane;
    JButton submitRequestButton;
    JTextArea requestDescription;
    MenuPage menu;
    Production prod;
    Actor actor;
    public CreateRequestPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        requestChooseLabel = new JLabel("Choose the type of request:");
        requestChooseLabel.setBounds(40,55,400,25);
        requestChooseLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        String[] requestTypes = {"DELETE_ACCOUNT", "ACTOR_ISSUE", "MOVIE_ISSUE", "OTHERS"};
        requestChoose = new JComboBox<>(requestTypes);
        requestChoose.addActionListener(this);
        requestChoose.setBounds(40,80,150,25);

        listModelActorRequest = new DefaultListModel<>();
        for(Actor actor : IMDB.getInstance().actors){
            listModelActorRequest.addElement(actor);
        }

        actorListRequests = new JList<>(listModelActorRequest);
        actorListRequests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorListRequests.addListSelectionListener(this);
        //  actorListRequests.setVisible(false);
        actorListRequestsPane = new JScrollPane(actorListRequests);
        actorListRequestsPane.setBounds(40,120,350, 280);
        actorListRequestsPane.setVisible(false);

        listModelProdRequest = new DefaultListModel<>();
        for(Production production : IMDB.getInstance().productions){
            listModelProdRequest.addElement(production);
        }

        prodListRequests = new JList<>(listModelProdRequest);
        prodListRequests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodListRequests.addListSelectionListener(this);
        //  prodListRequests.setVisible(false);
        prodListRequestsPane = new JScrollPane(prodListRequests);
        prodListRequestsPane.setBounds(40,120,350, 280);
        prodListRequestsPane.setVisible(false);

        submitRequestButton = new JButton("Submit request");
        submitRequestButton.setBounds(530, 310,130,40);
        submitRequestButton.setFocusable(false);
        submitRequestButton.setBorder(BorderFactory.createEtchedBorder());
        submitRequestButton.addActionListener(this);

        requestDescriptionLabel = new JLabel("Description:");
        requestDescriptionLabel.setBounds(420,120,200,25);
        requestDescriptionLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        requestDescription = new JTextArea();

        requestDescription.setFont(new Font("Calibri", Font.BOLD, 16));
        requestDescription.setLineWrap(true);
        requestDescription.setEditable(true);
        requestDescription.setBounds(420, 145, 350, 150);

        this.add(requestChooseLabel);
        this.add(requestChoose);
        this.add(actorListRequestsPane);
        this.add(prodListRequestsPane);
        this.add(submitRequestButton);
        this.add(requestDescription);
        this.add(requestDescriptionLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == requestChoose) {
            String text = (String) requestChoose.getSelectedItem();
            if (text.equals("MOVIE_ISSUE")) {
                actorListRequestsPane.setVisible(false);
                prodListRequestsPane.setVisible(true);
                // createRequestPanel.add(prodListRequestsPane);
            } else if (text.equals("ACTOR_ISSUE")) {
                prodListRequestsPane.setVisible(false);
                actorListRequestsPane.setVisible(true);
                // createRequestPanel.add(actorListRequestsPane);
            }
        }

        if (e.getSource() == submitRequestButton) {
            Request.RequestTypes type = null;
            boolean error = false;
            String titleOrName = null;
            String description;
            if (requestChoose.getSelectedItem().equals("MOVIE_ISSUE")) {
                if (prodListRequests.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Select a production!", "Error", JOptionPane.ERROR_MESSAGE);
                    error = true;
                }else {
                    int indexList = prodListRequests.getSelectedIndex();
                    prod = IMDB.getInstance().productions.get(indexList);
                    titleOrName = prod.title;
                }

                type = Request.RequestTypes.MOVIE_ISSUE;
            }
            if (requestChoose.getSelectedItem().equals("ACTOR_ISSUE")) {
                if (actorListRequests.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Select an actor!", "Error", JOptionPane.ERROR_MESSAGE);
                    error = true;
                }else{
                    int indexList = actorListRequests.getSelectedIndex();
                    actor = IMDB.getInstance().actors.get(indexList);
                    titleOrName = actor.name;
                }

                type = Request.RequestTypes.ACTOR_ISSUE;

            }

            if (!error) {
                if (requestChoose.getSelectedItem().equals("DELETE_ACCOUNT")) {
                    type = Request.RequestTypes.DELETE_ACCOUNT;
                }
                if (requestChoose.getSelectedItem().equals("OTHERS")) {
                    type = Request.RequestTypes.OTHERS;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime nowDate = LocalDateTime.now();
                String date = nowDate.format(formatter);
                LocalDate finalDate = LocalDate.parse(date,formatter);
                boolean ok = true;

                description = requestDescription.getText();
                if(description.equals("") == false) {
                    Request new_request = new Request(type, finalDate, titleOrName, description, IMDB.getInstance().connectedUser.username);
                    if (IMDB.getInstance().connectedUser instanceof Regular) {
                        ((Regular) IMDB.getInstance().connectedUser).createRequest(new_request);
                    }
                    if (IMDB.getInstance().connectedUser instanceof Contributor<?>) {

                        if (new_request.requestType == Request.RequestTypes.MOVIE_ISSUE || new_request.requestType == Request.RequestTypes.ACTOR_ISSUE) {
                            Object item = null;
                            if (new_request.requestType == Request.RequestTypes.ACTOR_ISSUE) {
                                Actor SearchedActor = null;
                                for (Actor actor : IMDB.getInstance().actors) {
                                    if (new_request.ActorName.equals(actor.name)) {
                                        SearchedActor = actor;
                                        break;
                                    }
                                }
                                item = SearchedActor;
                            }

                            if (new_request.requestType == Request.RequestTypes.MOVIE_ISSUE) {
                                Production SearchedProd = null;
                                for (Production prod : IMDB.getInstance().productions) {
                                    if (new_request.MovieTtile.equals(prod.title)) {
                                        SearchedProd = prod;
                                        break;
                                    }
                                }
                                item = SearchedProd;
                            }
                            if(((Staff)IMDB.getInstance().connectedUser).addedItems.contains(item)){
                                ok = false;
                            }
                        }

                        if(!ok){
                            JOptionPane.showMessageDialog(null, "You cannot add a request for a production/actor you added.", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            ((Contributor) IMDB.getInstance().connectedUser).createRequest(new_request);
                        }
                    }
                    if(ok) {
                        JOptionPane.showMessageDialog(null, "Request added", "Info", JOptionPane.INFORMATION_MESSAGE);
                        menu.dispose();
                        new MenuPage("Menu");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Introduce request description!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
