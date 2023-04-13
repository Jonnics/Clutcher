package clutcher;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.Color;

public class Clutcher extends Robot {

    private boolean peek; 
    private double moveAmount;


    public void run() {
        setBodyColor(Color.MAGENTA);
        setGunColor(Color.BLACK);
        setRadarColor(Color.MAGENTA);
        setBulletColor(Color.MAGENTA);
        setScanColor(Color.MAGENTA);

        // No início de cada rodada, o robô se move o mais rápido possível para a borda do tabuleiro, ignorando qualquer combate.
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

        // vira para a esquerda para ficar de frente para a parede.
        // getHeading() % 90 significa o resto da divisão de getHeading() por 90.
        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        // Gira a arma para virar à direita em 90 graus.
        peek = true;
        turnGunRight(90);
        turnRight(90);

while (true) {
            // Olha antes de virar quando ahead() termina.
            peek = true;
            // Move-se pela parede
            ahead(moveAmount);
            // Não olhe agora
            peek = false;
            // Vira para a próxima parede
            turnRight(90);
        }

    }

 
    public void onHitRobot(HitRobotEvent e) {

        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);
        } 
        else {
            ahead(100);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (getOthers() == 1) {  // Só atira se estiver no 1x1
            fire(2);
        }
        if (peek) {
            scan();
        }
    }
}