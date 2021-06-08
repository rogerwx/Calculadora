package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import java.util.Optional;

import static javafx.scene.paint.Color.RED;

public class Controller
{

    ///////// FXML //////////////
    public Button BTOff;
    @FXML
    private TextField LBResultat;
    @FXML
    private Button BT4;
    @FXML
    private Button BT5;
    @FXML
    private Button BT6;
    @FXML
    private Button BT7;
    @FXML
    private Button BTArrel;
    @FXML
    private Button BTPotencia;
    @FXML
    private Button BT8;
    @FXML
    private Button BT9;
    @FXML
    private Button BT1;
    @FXML
    private Button BT2;
    @FXML
    private Button BT3;
    @FXML
    private Button BTDividir;
    @FXML
    private Button BTMulti;
    @FXML
    private Button BTResta;
    @FXML
    private Button BTSuma;
    @FXML
    private Button BT0;
    @FXML
    private Button BTPunt;
    @FXML
    private Button BTClear;
    @FXML
    private Button BTIgual;
    @FXML
    private Button BTPosNeg;



    private boolean on = false;
    private double resultat = 0;
    private double operador1 = 0;
    private char operacio = '.';
    private String numeroStr = "";
    private String numeroStrTmp = "";
    private boolean bloquejar = false;


    public void initialize(){

        botonsOp(true, true);
        botonsNum(true);

        LBResultat.setEditable(false);

        LBResultat.focusedProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(() -> {
                    int carretPosition = LBResultat.getCaretPosition();
                    if (LBResultat.getAnchor() != carretPosition) {
                        LBResultat.selectRange(carretPosition, carretPosition);
                    }
                });
            }
        });



    }

    // FUNCIONS TECLAT

    @FXML
    private void handleKeyPressed(KeyEvent ke){

        Button [] botonsNumerics = {BT0,BT1,BT2,BT3,BT4,BT5,BT6,BT7,BT8,BT9};
        String tecla = String.valueOf(ke.getCode());

        try {
            int n = Integer.parseInt(String.valueOf(tecla.charAt(tecla.length()-1)));
            botonsNumerics[n].fire();
        } catch (NumberFormatException e) {

            switch (ke.getCode()) {
                case ADD      :  case CLOSE_BRACKET :  BTSuma.fire();    break;
                case SUBTRACT :  case SLASH         :  BTResta.fire();   break;
                case MULTIPLY :  case M             :  BTMulti.fire();   break;
                case DIVIDE   :  case D             :  BTDividir.fire(); break;
                case C        :  BTClear.fire();        break;
                case PERIOD   :  case COMMA         :  BTPunt.fire();    break;
            }
        }
    }

    // FUNCIONS DE HABILITAR/DESHABILITAR

    public void botonsOp(boolean b, boolean r) {
        BTArrel.setDisable(b);
        BTSuma.setDisable(b);
        BTResta.setDisable(b);
        BTMulti.setDisable(b);
        BTDividir.setDisable(b);
        BTIgual.setDisable(r);
        BTPosNeg.setDisable(b);
        BTPotencia.setDisable(b);
        BTClear.setDisable(b);
    }

    public void botonsNum(boolean b) {
        BT0.setDisable(b);
        BT1.setDisable(b);
        BT2.setDisable(b);
        BT3.setDisable(b);
        BT4.setDisable(b);
        BT5.setDisable(b);
        BT6.setDisable(b);
        BT7.setDisable(b);
        BT8.setDisable(b);
        BT9.setDisable(b);
        BTPunt.setDisable(b);
    }

    // FUNCIONS DELS BOTONS

    public void posarNum(ActionEvent e) {

        char n = String.valueOf(e.getSource()).split("'")[1].charAt(0);
        BT0.setDisable(false);

        botonsOp(bloquejar, !bloquejar);

        if (n == '.') {
            BTPunt.setDisable(true);
            if (numeroStr.equals(""))
                numeroStr = "0";
        }

        // LIMIT DE DIGITS
       if (numeroStr.length()>9)
            botonsNum(true);

        numeroStr += n;
        LBResultat.setText(numeroStr);
    }

    public void operacioSimple(ActionEvent e) {
        bloquejar = true;
        botonsOp(true, true);
        botonsNum(false);
        BT0.setDisable(true);
        operador1 = Double.parseDouble(numeroStr);
        numeroStr = "";
        LBResultat.setText(numeroStr);
        operacio = String.valueOf(e.getSource()).split("'")[1].charAt(0);

    }

    public void operacioComplexa(ActionEvent e) {


       try  {
            botonsNum(false);
            BT0.setDisable(true);
            operador1 = Double.parseDouble(numeroStr);
            operacio = String.valueOf(e.getSource()).split("'")[1].charAt(0);

            switch (operacio) {
                // SQR
                case 'S':
                    resultat = Math.sqrt(operador1);
                    break;
                // POTENCIA
                case 'X':
                    resultat = Math.pow(operador1, 2);
                    break;
                // CAMBIAR SIMBOL
                case '+':
                    resultat = operador1 * (-1);
                    break;
            }

            numeroStr = String.valueOf(resultat);
            calcular();

        } catch (ArrayIndexOutOfBoundsException ex) {

          LBResultat.setText("Error de cálcul");
          LBResultat.setStyle("-fx-text-fill: red");
          botonsOp(true,true);
          botonsNum(true);
          BTClear.setDisable(false);
      }
    }

    public void resultat() {

        botonsOp(false, true);
        botonsNum(false);
        bloquejar = false;

       try {
           double operador2 = Double.parseDouble(numeroStr);

            switch (operacio) {
                case '+':
                    resultat = operador1 + operador2;
                    break;
                case '-':
                    resultat = operador1 - operador2;
                    break;
                case '*':
                    resultat = operador1 * operador2;
                    break;
                case '/':
                    resultat = operador1 / operador2;
                    break;
            }

           numeroStr = String.valueOf(resultat);

            calcular();

        } catch (ArrayIndexOutOfBoundsException e) {

           LBResultat.setText("Error de cálcul");
           LBResultat.setStyle("-fx-text-fill: red");
           botonsOp(true,true);
           botonsNum(true);
           BTClear.setDisable(false);

       }

    }

    public void calcular() {

        boolean tmp = false;

        // SI EL RESULTAT ÉS MASSA GRAN
        for (int i=0; i<numeroStr.length(); i++)
            if (numeroStr.charAt(i) == 'E') {
                tmp = true;
                break;
            }

        if (tmp)
            numeroStrTmp = String.format("%6.3e",Double.parseDouble(numeroStr)).replace(',','.');

        else {

            String numeroIntegral = numeroStr.split("\\.")[0];
            String numeroDecimals = numeroStr.split("\\.")[1];

            // COMPROBAR SI TE DECIMALS
            if (resultat % 1 == 0)
                numeroStr = numeroIntegral;

            else if (numeroDecimals.length() > 5)
                numeroStr = numeroIntegral + "." + numeroDecimals.substring(0, 5);

            if (resultat % 1 != 0)
                BTPunt.setDisable(true);
        }
        if (!numeroStrTmp.equals(""))
            LBResultat.setText(numeroStrTmp);
        else
            LBResultat.setText(numeroStr);

        numeroStrTmp = "";
    }

    public void esborrar() {
        botonsOp(true, true);
        botonsNum(false);
        BT0.setDisable(true);
        BTPunt.setDisable(false);
        numeroStr = "";
        LBResultat.setText(numeroStr);
        LBResultat.setStyle("-fx-text-fill: black");
    }

    public void sortir() {

        if (on) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Sortir");
            alert.setHeaderText("Estàs a punt de tancar la calculadora");
            alert.getDialogPane().setPrefSize(400, 200);
            String s = "És finalitzara l'execució del programa, segur que vols continuar? ";
            alert.setContentText(s);

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && (result.get() == ButtonType.OK))
                System.exit(0);
        }

        else {
            on = true;
            botonsNum(false);
            BT0.setDisable(true);
            BTOff.setText("OFF");
            BTOff.setTextFill(RED);
        }



    }


}

