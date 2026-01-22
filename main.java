import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Registre reg = new Registre();

        reg.ajouter(new Etudiant("6754778", "Amina", 20, 12.5));
        reg.ajouter(new Etudiant("2324722", "Ahmed", 21, 15.5));
        reg.ajouter(new Etudiant("9088986", "Ahlem", 20, 17.5));
        reg.ajouter(new Prof("5433478", "M. Achref", 45, 2500.0));
        reg.ajouter(new Prof("2234221", "Mme. Olfa", 45, 2800.0));

        reg.listerTout();

        System.out.println("Moyenne d'age : " + reg.moyenneAgeFormatted());
        System.out.println("Moyenne des notes : " + reg.moyenneMGFormatted());
    }
}

class Citoyen {
    protected String cin;
    protected String nom;
    protected int age;
    static int nbreCitoyens = 0;

    Citoyen(String cin, String nom, int age) {
        this.cin = cin;
        this.nom = nom;
        this.age = age;
        nbreCitoyens++;
    }

    @Override
    public String toString() {
        return cin + "\t" + nom + "\t" + age;
    }
}

class Etudiant extends Citoyen {
    private double mg;

    Etudiant(String cin, String nom, int age, double mg) {
        super(cin, nom, age);
        this.mg = mg;
    }

    public double getMg() { return mg; }

    @Override
    public String toString() {
        return "ETUDIANT : " + super.toString() + "\t MG: " + mg;
    }
}

class Prof extends Citoyen {
    private double sal;

    Prof(String cin, String nom, int age, double sal) {
        super(cin, nom, age);
        this.sal = sal;
    }

    public double getSal() { return sal; }

    @Override
    public String toString() {
        return "PROF : " + super.toString() + "\t SALAIRE: " + sal;
    }
}

class Registre {
    private List<Citoyen> liste = new ArrayList<>();

    public void ajouter(Citoyen c) { liste.add(c); }

    public void listerTout() {
        for (Citoyen c : liste) {
            System.out.println(c.toString());
        }
    }

    public void listerEtudiants() {
        for (Citoyen c : liste) {
            if (c instanceof Etudiant) System.out.println(c.toString());
        }
    }

    public double moyenneAge() {
        if (liste.isEmpty()) return 0;
        double sum = 0;
        for (Citoyen c : liste) sum += c.age;
        return sum / liste.size();
    }

    public String moyenneAgeFormatted() {
        double avg = moyenneAge();
        // truncate to 1 decimal to match output example
        double trunc = Math.floor(avg * 10) / 10.0;
        return String.format("%.1f", trunc);
    }

    public double moyenneMG() {
        double sum = 0;
        int count = 0;
        for (Citoyen c : liste) {
            if (c instanceof Etudiant) {
                sum += ((Etudiant) c).getMg();
                count++;
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    public String moyenneMGFormatted() {
        double avg = moyenneMG();
        // truncate to 2 decimals to match example (15.16)
        double trunc = Math.floor(avg * 100) / 100.0;
        return String.format("%.2f", trunc);
    }
}