import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddReviewPanel extends JPanel implements ActionListener {
    DefaultListModel listModelreview;
    JList prodListReview;
    JScrollPane scrollPaneReview;
    JLabel ratingReviewLabel, commentReviewLabel;
    JTextField userRatingReview, commentReview;
    JButton addReviewButton;
    Production prod;
    MenuPage menu;
    public AddReviewPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        listModelreview = new DefaultListModel<>();
        for(Production prod : IMDB.getInstance().productions){
            listModelreview.addElement(prod);
        }

        prodListReview = new JList<>(listModelreview);
        prodListReview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneReview = new JScrollPane(prodListReview);
        scrollPaneReview.setBounds(40,40,500, 280);


        ratingReviewLabel = new JLabel("Introduce rating:");
        ratingReviewLabel.setBounds(40,335,400,25);
        ratingReviewLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        userRatingReview = new JTextField();
        userRatingReview.setBounds(30,360,300,25);
        userRatingReview.setFont(new Font("Calibri", Font.PLAIN, 17));

        commentReviewLabel = new JLabel("Introduce comment:");
        commentReviewLabel.setBounds(40,395,400,25);
        commentReviewLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        commentReview = new JTextField();
        commentReview.setBounds(30,420,300,25);
        commentReview.setFont(new Font("Calibri", Font.PLAIN, 17));

        addReviewButton = new JButton("Add Review");
        addReviewButton.setBounds(40, 455,130,40);
        addReviewButton.setFocusable(false);
        addReviewButton.setBorder(BorderFactory.createEtchedBorder());
        addReviewButton.addActionListener(this);

        this.add(scrollPaneReview);
        this.add(userRatingReview);
        this.add(commentReview);
        this.add(addReviewButton);
        this.add(ratingReviewLabel);
        this.add(commentReviewLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addReviewButton) {
            if (prodListReview.isSelectionEmpty() == false) {
                int indexList = prodListReview.getSelectedIndex();
                prod = IMDB.getInstance().productions.get(indexList);
                if (IMDB.getInstance().connectedUser instanceof Regular<?>) {
                    try{
                        Integer rating = Integer.parseInt(userRatingReview.getText());
                        if(rating >= 1 && rating <= 10) {
                            ((Regular<?>) IMDB.getInstance().connectedUser).addRating(prod.title, rating, commentReview.getText());
                            prod.averageRating = (double) 0;
                            for(Rating rating1 : prod.ratings){
                                prod.averageRating = prod.averageRating + rating1.rating;
                            }
                            prod.averageRating = prod.averageRating/prod.ratings.size();
                            Double avgRating = Math.round(prod.averageRating * 10.0)/10.0;
                            prod.averageRating = Double.valueOf(avgRating);
                            JOptionPane.showMessageDialog(null, "Review added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                            menu.dispose();
                            new MenuPage("Menu");
                        }else{
                            JOptionPane.showMessageDialog(null, "Rating should be between 1 and 10!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Enter a valid number for rating!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a movie!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
