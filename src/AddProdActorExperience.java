public class AddProdActorExperience implements ExperienceStrategy{
    // Experiența câștigată prin adăugarea unei productii/actor este 2
    @Override
    public int calculateExperience() {
        return 2;
    }
}
