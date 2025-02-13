import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelReviewPanel extends JPanel implements ActionListener {
    DefaultListModel listModel1,listModelreview1;
    JList prodList1, prodListReview1;
    JScrollPane scrollPane1, scrollPaneReview1;
    JButton viewReviewsButton, delReviewButton;
    Production prod;
    public DelReviewPanel(){
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        listModel1 = new DefaultListModel<>();
        for(Production prod : IMDB.getInstance().productions){
            listModel1.addElement(prod);
        }

        prodList1 = new JList<>(listModel1);
        prodList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane1 = new JScrollPane(prodList1);
        scrollPane1.setBounds(40,40,500, 180);

        viewReviewsButton = new JButton("View Reviews");
        viewReviewsButton.setBounds(570, 100,130,40);
        viewReviewsButton.setFocusable(false);
        viewReviewsButton.setBorder(BorderFactory.createEtchedBorder());
        viewReviewsButton.addActionListener(this);

        delReviewButton = new JButton("Delete Review");
        delReviewButton.setBounds(570, 300,130,40);
        delReviewButton.setFocusable(false);
        delReviewButton.setBorder(BorderFactory.createEtchedBorder());
        delReviewButton.addActionListener(this);

        listModelreview1 = new DefaultListModel<>();
        prodListReview1 = new JList<>(listModelreview1);
        prodListReview1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPaneReview1 = new JScrollPane(prodListReview1);
        scrollPaneReview1.setBounds(40,300,500, 140);

        this.add(scrollPane1);
        this.add(viewReviewsButton);
        this.add(delReviewButton);
        this.add(scrollPaneReview1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delReviewButton) {
            if (prodListReview1.isSelectionEmpty() == false) {
                int indexList = prodListReview1.getSelectedIndex();
                Rating rating = prod.ratings.get(indexList);
                if (rating.username.equals(IMDB.getInstance().connectedUser.username) == false) {
                    JOptionPane.showMessageDialog(null, "This review is not yours!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ((Regular) IMDB.getInstance().connectedUser).delRating(prod.title);
                    prod.averageRating = (double) 0;
                    for(Rating rating1 : prod.ratings){
                        prod.averageRating = prod.averageRating + rating1.rating;
                    }
                    prod.averageRating = prod.averageRating/prod.ratings.size();
                    Double avgRating = Math.round(prod.averageRating * 10.0)/10.0;
                    prod.averageRating = Double.valueOf(avgRating);
                    JOptionPane.showMessageDialog(null, "Review deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                    listModelreview1.removeElement(rating);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a review!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == viewReviewsButton) {
            if (prodList1.isSelectionEmpty() == false) {
                int indexList = prodList1.getSelectedIndex();
                prod = IMDB.getInstance().productions.get(indexList);

                listModelreview1.removeAllElements();
                for (Rating rating : prod.ratings) {
                    listModelreview1.addElement(rating);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a movie!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    }
}
