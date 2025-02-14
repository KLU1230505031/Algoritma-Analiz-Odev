import java.util.*;

public class Main {
    public static void main(String[] args) {

        Map<String, double[]> neighborhoods = new HashMap<>();
        neighborhoods.put("Mahalle A", new double[]{80, 60, 50, 40, 90});
        neighborhoods.put("Mahalle B", new double[]{60, 80, 70, 60, 85});
        neighborhoods.put("Mahalle C", new double[]{75, 50, 60, 50, 80});

        double[] weights = softmax(neighborhoods);

        System.out.println("Mahalle Ağırlıkları (Softmax Sonucu):");
        int index = 0;
        for (String neigh : neighborhoods.keySet()) {
            System.out.printf("%s: %.4f\n", neigh, weights[index++]);
        }

        String topRoute = getTopRoute(neighborhoods, weights);
        System.out.printf("\nEn uygun toplu taşıma güzergahı: %s\n", topRoute);

        System.out.println("\nMaliyet-Fayda Analizi:");
        for (String neigh : neighborhoods.keySet()) {
            double ratio = neighborhoods.get(neigh)[4] / neighborhoods.get(neigh)[2];
            System.out.printf("%s: %.2f\n", neigh, ratio);
        }
    }

    public static double[] softmax(Map<String, double[]> neighborhoods) {
        double[] scores = new double[neighborhoods.size()];
        int index = 0;
        for (double[] values : neighborhoods.values()) {
            scores[index++] = Arrays.stream(values).sum();
        }

        double maxScore = Arrays.stream(scores).max().getAsDouble();
        double[] expScores = Arrays.stream(scores).map(x -> Math.exp(x - maxScore)).toArray();
        double sumExpScores = Arrays.stream(expScores).sum();

        return Arrays.stream(expScores).map(x -> x / sumExpScores).toArray();
    }

    public static String getTopRoute(Map<String, double[]> neighborhoods, double[] weights) {
        Iterator<String> iterator = neighborhoods.keySet().iterator();
        double maxWeight = Double.NEGATIVE_INFINITY;
        String bestNeighborhood = "";
        int index = 0;
        while (iterator.hasNext()) {
            String neighborhood = iterator.next();
            if (weights[index] > maxWeight) {
                maxWeight = weights[index];
                bestNeighborhood = neighborhood;
            }
            index++;
        }
        return bestNeighborhood;
    }
}
