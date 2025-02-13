public class AddRatingExperience implements ExperienceStrategy{
    // Experiența câștigată prin adăugarea unei recenzii este 1
    @Override
    public int calculateExperience() {
        return 1;
    }
}
