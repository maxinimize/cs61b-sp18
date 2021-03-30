public class NBody {
    public static double readRadius(String pfile) {
        In in = new In(pfile);
        in.readInt();
		double R = in.readDouble();
        return R;
    }

    public static Planet[] readPlanets(String pfile) {
        In in = new In(pfile);
        int N = in.readInt();
        in.readDouble();
        Planet[] allPlanets = new Planet[N];
        for (int i = 0; i < N; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            allPlanets[i] = new Planet(xP, yP, xV, yV, m, img);
        }
        return allPlanets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double R = readRadius(filename);
        Planet[] allPlanets = readPlanets(filename);
        

        StdDraw.enableDoubleBuffering();
        for (double i = 0; i <= T; i += dt) {
            double[] xForces = new double[allPlanets.length];
            double[] yForces = new double[allPlanets.length];
            for (int j = 0; j < allPlanets.length; j++) {
                xForces[j] = allPlanets[j].calcNetForceExertedByX(allPlanets);
                yForces[j] = allPlanets[j].calcNetForceExertedByY(allPlanets);
            }
            for (int j = 0; j < allPlanets.length; j++) {
                allPlanets[j].update(dt, xForces[j], yForces[j]);
            }
            StdDraw.setScale(-R, R);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : allPlanets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", allPlanets.length);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < allPlanets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
                allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);   
}
    }

}