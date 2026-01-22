import java.util.Vector;

abstract class Forme {
    private String name;
    private int color;
    private static int nbObj = 0;

    protected Forme(String name, int color) {
        this.name = name;
        this.color = color;
        nbObj++;
    }

    public String getName() { return name; }
    public int getColor() { return color; }
    public static int getNbObj() { return nbObj; }

    @Override
    public String toString() {
        return name;
    }

    public abstract float surface();
}

// new exception for negative dimensions
class NegativeDimensionException extends IllegalArgumentException {
    public NegativeDimensionException(String msg) { super(msg); }
}

class Rect extends Forme {
    private float L, l;
    public Rect(String name, int color, float L, float l) {
        super(name, color);
        if (L < 0 || l < 0) throw new NegativeDimensionException("Rect " + name + " has negative dimension(s): L=" + L + " l=" + l);
        this.L = L;
        this.l = l;
    }
    @Override
    public float surface() {
        return L * l;
    }
}

class Cercle extends Forme {
    private float r;
    public Cercle(String name, int color, float r) {
        super(name, color);
        if (r < 0) throw new NegativeDimensionException("Cercle " + name + " has negative radius: r=" + r);
        this.r = r;
    }
    @Override
    public float surface() {
        return (float)(Math.PI * r * r);
    }
}

class Triangle extends Forme {
    private float b, h;
    public Triangle(String name, int color, float b, float h) {
        super(name, color);
        if (b < 0 || h < 0) throw new NegativeDimensionException("Triangle " + name + " has negative dimension(s): b=" + b + " h=" + h);
        this.b = b;
        this.h = h;
    }
    @Override
    public float surface() {
        return (b * h) / 2f;
    }
}

// factory that catches negative-dimension exceptions and fixes them (using absolute values)
class FormFactory {
    public static Rect createRect(String name, int color, float L, float l) {
        try {
            return new Rect(name, color, L, l);
        } catch (NegativeDimensionException e) {
            System.err.println("Warning: " + e.getMessage() + " — using absolute values.");
            return new Rect(name, color, Math.abs(L), Math.abs(l));
        }
    }

    public static Cercle createCercle(String name, int color, float r) {
        try {
            return new Cercle(name, color, r);
        } catch (NegativeDimensionException e) {
            System.err.println("Warning: " + e.getMessage() + " — using absolute value.");
            return new Cercle(name, color, Math.abs(r));
        }
    }

    public static Triangle createTriangle(String name, int color, float b, float h) {
        try {
            return new Triangle(name, color, b, h);
        } catch (NegativeDimensionException e) {
            System.err.println("Warning: " + e.getMessage() + " — using absolute values.");
            return new Triangle(name, color, Math.abs(b), Math.abs(h));
        }
    }
}

class Main {
    public static void main(String[] args) {
        Vector<Forme> formes = new Vector<>();

        // use factory methods that handle negative values via try/catch
        formes.add(FormFactory.createRect("R1", 0xFF0000, 10f, 5f));    // 50.0
        formes.add(FormFactory.createCercle("C1", 0x00FF00, -7f));       // fixed to 7f
        formes.add(FormFactory.createTriangle("T1", 0x0000FF, -8f, 5f)); // fixed to 8f

        float total = 0f;
        for (Forme f : formes) {
            float s = f.surface();
            System.out.println(f + " -> Surface : " + s);
            total += s;
        }

        System.out.println("Nombre total d'objets : " + Forme.getNbObj());
        System.out.println("Surface cumulée : " + total);
    }
}