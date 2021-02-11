//import javax.swing.*;
import java.awt.*;

public class TstMultiChart {
    public static void main(String[] args) {
        String fileName = "E:\\Java\\IdeaProjectsRosan\\TstJFreeChart\\data\\data.txt";

        EventQueue.invokeLater(() -> {

            ChartComponent ex = new ChartComponent("EKP personalization", fileName);
            if (ex.createDataset("2018") < 0)
                return;
            if (ex.createDataset("2019") < 0)
                return;
            if (ex.createDataset("2020") < 0)
                return;
            if (ex.createDataset("2021") < 0)
                return;
            ex.initUI();
        });
    }
}
