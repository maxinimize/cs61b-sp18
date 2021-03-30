public class Planet {
    public double xxPos; //Its current x position
    public double yyPos; //Its current y position
    public double xxVel; //Its current velocity in the x direction
    public double yyVel; //Its current velocity in the y direction
    public double mass; //Its mass
    public String imgFileName; //The name of the file that corresponds to the image that depicts the planet (for example, jupiter.gif)
    private static double G = 6.67e-11; //Gravitational constant as a ‘static final’ variable

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double r = Math.sqrt((p.xxPos - this.xxPos) * (p.xxPos - this.xxPos) + (p.yyPos - this.yyPos) * (p.yyPos - this.yyPos));
        return r;
    }

    public double calcForceExertedBy(Planet p) {
        double F = (Planet.G * this.mass * p.mass) / (calcDistance(p) * calcDistance(p));
        return F;
    }

    public double calcForceExertedByX(Planet p) {
        double xxF = (calcForceExertedBy(p) * (p.xxPos - this.xxPos)) / calcDistance(p);
        return xxF;
    }

    public double calcForceExertedByY(Planet p) {
        double yyF = (calcForceExertedBy(p) * (p.yyPos - this.yyPos)) / calcDistance(p);
        return yyF;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double txxF = 0;
        for (Planet p : allPlanets) {
            if (this.equals(p)) {
                continue;
            }
            txxF += calcForceExertedByX(p);
        }
        return txxF;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double tyyF = 0;
        for (Planet p : allPlanets) {
            if (this.equals(p)) {
                continue;
            }
            tyyF += calcForceExertedByY(p);
        }
        return tyyF;
    }

    public void update(double dt, double xF, double yF) {
        double xxa = xF / this.mass;
        double yya = yF / this.mass;
        this.xxVel += dt * xxa;
        this.yyVel += dt * yya;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
}