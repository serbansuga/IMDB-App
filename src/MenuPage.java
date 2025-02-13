import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MenuPage extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu addMenu, deleteMenu, viewMenu, backHome, logoutMenu, updateMenu;
    JMenuItem viewProd, viewActors, viewNotifications, addActorFav, addProdFav, delActorFav, delProdFav,
                addActorSys, addProdSys, delActorSys, delProdSys, addReview,delReview, addUser, delUser, addRequest,
            delRequest, back, logout, updateProd, updateActor,solveRequest;
    JPanel viewProdPanel, notificationsPanel, addProdSysPanel,delProdSysPanel,addReviewPanel,delReviewPanel,
            addProdFavPanel, delProdFavPanel, viewActorsPanel,  addActorSysPanel, delActorSysPanel, addActorFavPanel,
            delActorFavPanel, actorUpdatePanel, updateProdPanel, createRequestPanel, deleteRequestPanel, delUserPanel,
            addUserPanel, solveRequestPanel;
    public MenuPage(String name){
        super(name);

        menuBar = new JMenuBar();

        addMenu = new JMenu("Add");
        viewMenu = new JMenu("View");
        deleteMenu = new JMenu("Delete");
        backHome = new JMenu("Home");
        logoutMenu = new JMenu("LogOut");
        updateMenu = new JMenu("Edit");

        back = new JMenuItem("Back Home");
        back.addActionListener(this);
        backHome.add(back);

        logout = new JMenuItem("Logout");
        logout.addActionListener(this);
        logoutMenu.add(logout);

        solveRequest = new JMenuItem("Requests");
        solveRequest.addActionListener(this);

        viewProd = new JMenuItem("Productions");
        viewActors = new JMenuItem("Actors");
        viewNotifications = new JMenuItem("Notifications");
        viewProd.addActionListener(this);
        viewActors.addActionListener(this);
        viewNotifications.addActionListener(this);

        viewMenu.add(viewProd);
        viewMenu.add(viewActors);
        viewMenu.add(solveRequest);
        viewMenu.add(viewNotifications);

        addActorFav = new JMenuItem("Actor to favorites");
        addProdFav = new JMenuItem("Production to favorites");
        addActorFav.addActionListener(this);
        addProdFav.addActionListener(this);
        addMenu.add(addActorFav);
        addMenu.add(addProdFav);

        if(!(IMDB.getInstance().connectedUser instanceof Admin<?>)){
            addRequest = new JMenuItem("Request");
            addMenu.add(addRequest);
            addRequest.addActionListener(this);
        }
        if(!(IMDB.getInstance().connectedUser instanceof Regular<?>)){
            addActorSys = new JMenuItem("Actor to system");
            addProdSys = new JMenuItem("Production to system");
            addActorSys.addActionListener(this);
            addProdSys.addActionListener(this);
            addMenu.add(addActorSys);
            addMenu.add(addProdSys);

            updateProd = new JMenuItem("Production");
            updateActor = new JMenuItem("Actor");
            updateProd.addActionListener(this);
            updateActor.addActionListener(this);
            updateMenu.add(updateProd);
            updateMenu.add(updateActor);
        }

        if(IMDB.getInstance().connectedUser instanceof Regular<?>){
            addReview = new JMenuItem("Review");
            addReview.addActionListener(this);
            addMenu.add(addReview);
        }

        if(IMDB.getInstance().connectedUser instanceof Admin<?>){
            addUser = new JMenuItem("User");
            addUser.addActionListener(this);
            addMenu.add(addUser);
        }


        delActorFav = new JMenuItem("Actor from favorites");
        delProdFav = new JMenuItem("Production from favorites");
        delProdFav.addActionListener(this);
        delActorFav.addActionListener(this);
        deleteMenu.add(delActorFav);
        deleteMenu.add(delProdFav);

        if(!(IMDB.getInstance().connectedUser instanceof Admin<?>)){
            delRequest = new JMenuItem("Request");
            delRequest.addActionListener(this);
            deleteMenu.add(delRequest);
        }

        if(!(IMDB.getInstance().connectedUser instanceof Regular<?>)){
            delActorSys = new JMenuItem("Actor from system");
            delProdSys = new JMenuItem("Production from system");
            delActorSys.addActionListener(this);
            delProdSys.addActionListener(this);
            deleteMenu.add(delActorSys);
            deleteMenu.add(delProdSys);
        }

        if(IMDB.getInstance().connectedUser instanceof Regular<?>){
            delReview = new JMenuItem("Review");
            delReview.addActionListener(this);
            deleteMenu.add(delReview);
        }

        if(IMDB.getInstance().connectedUser instanceof Admin<?>){
            delUser = new JMenuItem("User");
            delUser.addActionListener(this);
            deleteMenu.add(delUser);
        }
        menuBar.add(viewMenu);
        menuBar.add(addMenu);
        menuBar.add(deleteMenu);
        menuBar.add(updateMenu);
        menuBar.add(backHome);
        menuBar.add(logoutMenu);

        viewProdPanel = new ViewProd();
        notificationsPanel = new ViewNotificationsPanel();
        addProdSysPanel = new AddProdSysPanel(this);
        delProdSysPanel = new DelProdSysPanel(this);
        addReviewPanel = new AddReviewPanel(this);
        delReviewPanel = new DelReviewPanel();
        addProdFavPanel= new AddProdFavPanel(this);
        delProdFavPanel= new DelProdFavPanel();
        updateProdPanel = new UpdateProdPanel(this);
        viewActorsPanel = new ViewActorsPanel();
        addActorSysPanel = new AddActorSysPanel(this);
        delActorSysPanel = new DelActorSystemPanel(this);
        actorUpdatePanel = new ActorUpdatePanel(this);
        addActorFavPanel= new AddActorFavPanel(this);
        delActorFavPanel= new DelActorFavPanel();
        createRequestPanel= new CreateRequestPanel(this);
        deleteRequestPanel= new DeleteRequestPanel();
        solveRequestPanel = new SolveRequestPanel(this);
        delUserPanel = new DelUserPanel(this);
        addUserPanel = new AddUserPanel(this);



        menuBar.setFont(new Font("Calibri", Font.PLAIN, 25));
        menuBar.setPreferredSize(new Dimension(400,30));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setJMenuBar(menuBar);
        this.add(viewProdPanel);
        this.add(notificationsPanel);
        this.add(addProdSysPanel);
        this.add(addReviewPanel);
        this.add(delReviewPanel);
        this.add(delProdSysPanel);
        this.add(addProdFavPanel);
        this.add(delProdFavPanel);
        this.add(viewActorsPanel);
        this.add(addActorSysPanel);
        this.add(delActorSysPanel);
        this.add(addActorFavPanel);
        this.add(delActorFavPanel);
        this.add(actorUpdatePanel);
        this.add(updateProdPanel);
        this.add(createRequestPanel);
        this.add(deleteRequestPanel);
        this.add(delUserPanel);
        this.add(solveRequestPanel);
        this.add(addUserPanel);

        ImageIcon ImdbIcon = new ImageIcon("IMDBLogo.png");
        this.setIconImage(ImdbIcon.getImage());
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
            new MainPage("Main");
        }
        if (e.getSource() instanceof JMenuItem) {
            delReviewPanel.setVisible(false);
            notificationsPanel.setVisible(false);
            addActorSysPanel.setVisible(false);
            viewProdPanel.setVisible(false);
            addProdSysPanel.setVisible(false);
            addReviewPanel.setVisible(false);
            delProdSysPanel.setVisible(false);
            addProdFavPanel.setVisible(false);
            delProdFavPanel.setVisible(false);
            viewActorsPanel.setVisible(false);
            delActorSysPanel.setVisible(false);
            addActorFavPanel.setVisible(false);
            delActorFavPanel.setVisible(false);
            actorUpdatePanel.setVisible(false);
            updateProdPanel.setVisible(false);
            createRequestPanel.setVisible(false);
            deleteRequestPanel.setVisible(false);
            delUserPanel.setVisible(false);
            solveRequestPanel.setVisible(false);
            addUserPanel.setVisible(false);
        }
        if (e.getSource() == viewProd) {
            viewProdPanel.setVisible(true);
        }
        if (e.getSource() == viewNotifications) {
            notificationsPanel.setVisible(true);
        }
        if (e.getSource() == addActorSys) {
            addActorSysPanel.setVisible(true);
        }
        if (e.getSource() == addProdSys) {
            addProdSysPanel.setVisible(true);
        }
        if (e.getSource() == addReview) {
            addReviewPanel.setVisible(true);
        }
        if (e.getSource() == delReview) {
            delReviewPanel.setVisible(true);
        }
        if (e.getSource() == delProdSys) {
            delProdSysPanel.setVisible(true);
        }
        if (e.getSource() == addProdFav) {
            addProdFavPanel.setVisible(true);
        }
        if (e.getSource() == delProdFav) {
            delProdFavPanel.setVisible(true);
        }
        if (e.getSource() == viewActors) {
            viewActorsPanel.setVisible(true);
        }
        if (e.getSource() == delActorSys) {
            delActorSysPanel.setVisible(true);
        }
        if (e.getSource() == addActorFav) {
            addActorFavPanel.setVisible(true);
        }
        if (e.getSource() == delActorFav) {
            delActorFavPanel.setVisible(true);
        }
        if (e.getSource() == logout) {
            this.dispose();
            new LoginPage("Login");
        }
        if (e.getSource() == updateActor) {
            actorUpdatePanel.setVisible(true);
        }
        if (e.getSource() == updateProd) {
            updateProdPanel.setVisible(true);
        }
        if (e.getSource() == addRequest) {
            createRequestPanel.setVisible(true);
        }
        if(e.getSource() == delRequest){
            deleteRequestPanel.setVisible(true);
        }
        if(e.getSource() == delUser){
            delUserPanel.setVisible(true);
        }
        if(e.getSource() == solveRequest){
            solveRequestPanel.setVisible(true);
        }
        if(e.getSource() == addUser){
            addUserPanel.setVisible(true);
        }
    }

}
