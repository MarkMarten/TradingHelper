import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public ChromeOptions options = getOptions();
    public WebDriver driver = new ChromeDriver(options);
    public JavascriptExecutor executor = (JavascriptExecutor) driver;

    @FXML
    private Label oscillatorSell;

    @FXML
    private Label oscillatorNeutral;

    @FXML
    private Label oscillatorBuy;

    @FXML
    private Label summarySell;

    @FXML
    private Label summaryNeutral;

    @FXML
    private Label summaryBuy;

    @FXML
    private Label MAsell;

    @FXML
    private Label MAneutral;

    @FXML
    private Label MAbuy;

    @FXML
    private Label oscDecision;

    @FXML
    private Label sumDecision;

    @FXML
    private Label MAdecision;

    @FXML
    private TextField cryptoField;

    @FXML
    private ChoiceBox<String> timeBox;

    @FXML
    public void otsi() throws InterruptedException {
        String crypto = cryptoField.getText();
        String timeValitud=timeBox.getValue().toString();
        String valik="";
        if(timeValitud.equals("1 H")){
            valik="h1";
        }else if(timeValitud.equals("4 H")){
            valik="h4";
        } else if(timeValitud.equals("1 D")){
            valik="d1";
        } else {
            System.out.println("Vali aeg");
        }

        if(!valik.equals("")){
            String veebileht = "https://www.tradingview.com/symbols/"+crypto.toUpperCase()+"/technicals/";
            System.out.println(veebileht);
            driver.get(veebileht);

            try {
                WebElement kuniSummaryTitleVisible = new WebDriverWait(driver, 10)
                        .until(ExpectedConditions.presenceOfElementLocated(By.className("speedometerTitle-30PXfiU1")));
            } catch (Exception e) {
                WebElement techincalsButton = driver.findElement(By.xpath("/html/body/div[2]/div[5]/div/header/div/div[5]/div/div[1]/div/a[2]"));
                executor.executeScript("arguments[0].click();", techincalsButton);
                WebElement kuniSummaryTitleVisible = new WebDriverWait(driver, 10)
                        .until(ExpectedConditions.presenceOfElementLocated(By.className("speedometerTitle-30PXfiU1")));
            }

        }


        List<WebElement> times = driver.findElements(By.className("tab-B2mArR2X"));
        if(valik.equals("h1")){
            for(int i=0;i<times.size();i++){
                if(times.get(i).getText().equals("1 hour")){
                    executor.executeScript("arguments[0].click();", times.get(i));
                }
            }
        } else if(valik.equals("h4")){
            for(int i=0;i<times.size();i++){
                if(times.get(i).getText().equals("4 hours")){
                    executor.executeScript("arguments[0].click();", times.get(i));
                }
            }
        } else {
            for(int i=0;i<times.size();i++){
                if(times.get(i).getText().equals("1 day")){
                    executor.executeScript("arguments[0].click();", times.get(i));
                }
            }
        }
        Thread.sleep(1500);

        getData();
    }

    public void getData(){
        List<WebElement> numbriList = driver.findElements(By.className("counterNumber-3l14ys0C"));

        for(int i=0;i<numbriList.size();i++){
            String number = numbriList.get(i).getText();

            switch(i){
                case 0:
                    oscillatorSell.setTextFill(Color.RED);
                    oscillatorSell.setText(number);
                    break;
                case 1:
                    oscillatorNeutral.setTextFill(Color.SLATEGREY);
                    oscillatorNeutral.setText(number);
                    break;
                case 2:
                    oscillatorSell.setTextFill(Color.MEDIUMSLATEBLUE);
                    oscillatorBuy.setText(number);
                    break;
                case 3:
                    summarySell.setTextFill(Color.RED);
                    summarySell.setText(number);
                    break;
                case 4:
                    summaryNeutral.setTextFill(Color.SLATEGREY);
                    summaryNeutral.setText(number);
                    break;
                case 5:
                    summaryBuy.setTextFill(Color.MEDIUMSLATEBLUE);
                    summaryBuy.setText(number);
                    break;
                case 6:
                    MAsell.setTextFill(Color.RED);
                    MAsell.setText(number);
                    break;
                case 7:
                    MAneutral.setTextFill(Color.SLATEGREY);
                    MAneutral.setText(number);
                    break;
                case 8:
                    MAbuy.setTextFill(Color.MEDIUMSLATEBLUE);
                    MAbuy.setText(number);
                    break;
            }
        }

        List<WebElement> decisionList = driver.findElements(By.className("speedometerSignal-pyzN--tL"));

        for(int j=0;j<decisionList.size();j++){
            String decision = decisionList.get(j).getText();

            switch(j){
                case 0:
                    if(decision.equals("SELL")){
                        oscDecision.setTextFill(Color.RED);
                    }else if(decision.equals("STRONG SELL")){
                        oscDecision.setTextFill(Color.DARKRED);
                    }else if(decision.equals("NEUTRAL")){
                        oscDecision.setTextFill(Color.DARKSLATEGREY);
                    }else if(decision.equals("BUY")){
                        oscDecision.setTextFill(Color.MEDIUMSLATEBLUE);
                    }else if(decision.equals("STRONG BUY")){
                        oscDecision.setTextFill(Color.BLUE);
                    }
                    oscDecision.setText(decision);
                    break;
                case 1:
                    if(decision.equals("SELL")){
                        sumDecision.setTextFill(Color.RED);
                    }else if(decision.equals("STRONG SELL")){
                        sumDecision.setTextFill(Color.DARKRED);
                    }else if(decision.equals("NEUTRAL")){
                        sumDecision.setTextFill(Color.DARKSLATEGREY);
                    }else if(decision.equals("BUY")){
                        sumDecision.setTextFill(Color.MEDIUMSLATEBLUE);
                    }else if(decision.equals("STRONG BUY")){
                        sumDecision.setTextFill(Color.BLUE);
                    }
                    sumDecision.setText(decision);
                    break;
                case 2:
                    if(decision.equals("SELL")){
                        MAdecision.setTextFill(Color.RED);
                    }else if(decision.equals("STRONG SELL")){
                        MAdecision.setTextFill(Color.DARKRED);
                    }else if(decision.equals("NEUTRAL")){
                        MAdecision.setTextFill(Color.DARKSLATEGREY);
                    }else if(decision.equals("BUY")){
                        MAdecision.setTextFill(Color.MEDIUMSLATEBLUE);
                    }else if(decision.equals("STRONG BUY")){
                        MAdecision.setTextFill(Color.BLUE);
                    }
                    MAdecision.setText(decision);
                    break;
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeBox.getItems().add("1 H");
        timeBox.getItems().add("4 H");
        timeBox.getItems().add("1 D");
    }

    public ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return options;
    }
}
