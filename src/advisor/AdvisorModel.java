package advisor;

import java.util.ArrayList;
import java.util.List;

public class AdvisorModel {
    private static AdvisorModel advisorModel = new AdvisorModel();
    private List<List<String>> data = new ArrayList<>();
    private List<List<String>> currentData = new ArrayList<>();
    private int currentPage = 1;
    private int totalPages = 1;

    private AdvisorModel() {
    }

    public static AdvisorModel getInstance() {
        return advisorModel;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
        currentPage = 1;
        totalPages = data.size() / Config.getPageSize();
        setCurrentData();
    }

    public void next() {
        if (currentPage < totalPages) {
            currentPage++;
            setCurrentData();
        } else {
            System.out.println("No more pages.");
        }
    }

    public void prev() {
        if (currentPage > 1) {
            currentPage--;
            setCurrentData();
        } else {
            System.out.println("No more pages.");
        }
    }

    private void setCurrentData() {
        currentData = data.subList(
                (currentPage - 1) * Config.getPageSize(),
                Config.getPageSize() * currentPage
        );
        printData();
    }

    private void printData() {
        for (List<String> row : currentData) {
            for (String item : row) {
                System.out.println(item);
            }
            System.out.println();
        }
        System.out.printf("---PAGE %s OF %s---\n", currentPage, totalPages);
    }
}
