package javaRPGFX.animation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Animation {
    
    public SequentialTransition backgroundPattern1(Node img){
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(img);
        scale.setDuration(Duration.millis(10000));
        scale.setCycleCount(2);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.setByY(1.0);
        scale.setAutoReverse(true);
        ScaleTransition scale2 = new ScaleTransition();
        scale2.setNode(img);
        scale2.setDuration(Duration.millis(12000));
        scale2.setCycleCount(2);
        scale2.setInterpolator(Interpolator.EASE_BOTH);
        scale2.setByX(0.5);
        scale2.setAutoReverse(true);
        SequentialTransition animation = new SequentialTransition(scale,scale2,pause);
        animation.setCycleCount(SequentialTransition.INDEFINITE);
        return animation;
    }
    //WIP
    public TranslateTransition backgroundPattern2(Node back){
        //back.setOpacity(0.8);
        TranslateTransition trans1 = new TranslateTransition(Duration.seconds(15),back);
        trans1.setFromY(-256);
        trans1.setFromX(-256);
        trans1.setToY(0);
        trans1.setToX(0);
        trans1.setInterpolator(Interpolator.LINEAR);
        trans1.setCycleCount(-1);
        return trans1;
    }
    public TranslateTransition backgroundPattern3(Node back){
        TranslateTransition trans = new TranslateTransition(Duration.seconds(15),back);
        trans.setFromY(-256);
        trans.setFromX(0);
        trans.setToY(0);
        trans.setToX(-256);
        trans.setInterpolator(Interpolator.LINEAR);
        trans.setCycleCount(-1);
        return trans;
    }
    public FadeTransition healAnm(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        FadeTransition fade = new FadeTransition();
        gc.setFill(Color.LIMEGREEN);
        gc.setGlobalAlpha(0.4);
        gc.fillRect(0,0,600,400); 
        fade.setNode(canvas);
        fade.setDuration(Duration.millis(150));
        fade.setCycleCount(4);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(0.5);
        fade.setAutoReverse(true);
        return fade;
    }
    public void enemyDamagedAnm(ImageView img){
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-1.0);
        ColorAdjust whiteout = new ColorAdjust();
        whiteout.setBrightness(1.0);
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
            event ->{
                img.setEffect(blackout);
            }),
            new KeyFrame(Duration.millis(30),
            event ->{
                img.setEffect(whiteout);
            }),
            new KeyFrame(Duration.millis(60),
            event ->{
                img.setEffect(blackout);
            }),
            new KeyFrame(Duration.millis(90),
            event ->{
                img.setEffect(whiteout);
            }),
            new KeyFrame(Duration.millis(120),
            event ->{
                img.setEffect(null);
            })
        );
        timeline.play();
    }
    public void shakeAnm(Node node){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
                new KeyValue(node.translateYProperty(), -10,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(100),
                new KeyValue(node.translateYProperty(), 10,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(200),
                new KeyValue(node.translateYProperty(), -10,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(300),
                new KeyValue(node.translateYProperty(), 10,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(400),
                new KeyValue(node.translateYProperty(), -5,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(500),
                new KeyValue(node.translateYProperty(), 5,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(600),
                new KeyValue(node.translateYProperty(), 0,Interpolator.LINEAR)
            )
        );
        timeline.play();
    }
    public void startBattleAnm(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image img = new Image("/asset/encounterP.png");
        //ColorAdjust ca = new ColorAdjust();
        gc.setGlobalAlpha(0.5);
        gc.drawImage(img, 200, 100);
        
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(canvas);
        scale.setDuration(Duration.millis(1000));
        scale.setFromX(0.2);
        scale.setFromY(0.2);
        scale.setByY(9.0);
        scale.setByX(9.0);
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(canvas);
        rotate.setDuration(Duration.millis(800));
        rotate.setCycleCount(4);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.play();
        scale.play();
    }
    public SequentialTransition handleFadeInAnimation(Canvas canvas){
        PauseTransition pause = new PauseTransition(Duration.millis(400));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        FadeTransition fade = new FadeTransition();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 400);
        fade.setNode(canvas);
        fade.setDuration(Duration.millis(300));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        SequentialTransition animation = new SequentialTransition(fade,pause);
        return animation;
    }
    public SequentialTransition handleFadeOutAnimation(Canvas canvas){
        PauseTransition pause = new PauseTransition(Duration.millis(300));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        FadeTransition fade = new FadeTransition();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 600, 400);
        fade.setNode(canvas);
        fade.setDuration(Duration.millis(300));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        SequentialTransition animation = new SequentialTransition(pause,fade);
        return animation;
    }
    public Timeline handleTextAnimation(String txt,Text text){
        final IntegerProperty i = new SimpleIntegerProperty(0);
        i.set(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(70),
        event ->{
            text.setText(txt.substring(0,i.get()));
            i.set(i.get()+1);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(txt.length()+1);
        timeline.setOnFinished(finish ->{
            i.set(0);
        });
        return timeline;
    }
    public void enemyDeadAnm(ImageView img){
        PauseTransition pause = new PauseTransition(Duration.millis(200));
        FadeTransition fade = new FadeTransition();
        final DoubleProperty i = new SimpleDoubleProperty(0.0);
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(0);
        img.setEffect(blackout);
        img.setCache(true);
        img.setCacheHint(CacheHint.SPEED);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(20),
        event ->{
            i.set(i.get()-0.1);
            blackout.setBrightness(i.get());
            img.setEffect(blackout);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(10);
        
        fade.setNode(img);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(1);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);

        SequentialTransition animation = new SequentialTransition(timeline,pause,fade);
        animation.play();
    }
    public void setDeadStatus(ImageView img){
        ColorAdjust monochro = new ColorAdjust();
        monochro.setSaturation(-1.0);
        Blend blush = new Blend(
            BlendMode.MULTIPLY,
            monochro,
            new ColorInput(
                0,
                0,
                img.getImage().getWidth(),
                img.getImage().getHeight(),
                Color.RED
            )
        );
        img.effectProperty().set(blush);
        img.setCache(true);
        img.setCacheHint(CacheHint.SPEED);
    }
    public Timeline moveShapeToUpper(Node rect){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
                new KeyValue(rect.translateYProperty(),0,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(500),
                new KeyValue(rect.translateYProperty(),-rect.getBoundsInParent().getHeight(),Interpolator.LINEAR)
            )
        );
        return timeline;
    }
    public Timeline moveShapeToLower(Node rect){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
                new KeyValue(rect.translateYProperty(),0,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(500),
                new KeyValue(rect.translateYProperty(),rect.getBoundsInParent().getHeight(),Interpolator.LINEAR)
            )
        );
        return timeline;
    }
    public Timeline blinkingOpacity(Node node){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
                new KeyValue(node.opacityProperty(),1,Interpolator.LINEAR)
                ),
            new KeyFrame(Duration.millis(1500),
                new KeyValue(node.opacityProperty(),0,Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.millis(3000),
                new KeyValue(node.opacityProperty(),0,Interpolator.LINEAR)
            )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(-1);
        return timeline;
    }
    public Timeline blinkingOpacity_short(Node node){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
                new KeyValue(node.opacityProperty(),1,Interpolator.LINEAR)
                ),
            new KeyFrame(Duration.millis(500),
                new KeyValue(node.opacityProperty(),1,Interpolator.LINEAR)
                ),
            new KeyFrame(Duration.millis(1000),
                new KeyValue(node.opacityProperty(),0,Interpolator.LINEAR)
            )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(-1);
        return timeline;
    }
    public void bounceAnm(Node node){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(0),
                new KeyValue(node.translateYProperty(), 0,Interpolator.SPLINE(0.215, 0.610, 0.355, 1.000))
            ),
            new KeyFrame(Duration.millis(700),
                new KeyValue(node.translateYProperty(), 5,Interpolator.SPLINE(0.755, 0.050, 0.855, 0.060))
            )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(-1);
        timeline.play();
    }
}