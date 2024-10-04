package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.Core;
import engine.FileManager;

public class CreditScreen extends Screen{

    private List<String> creditlist;

    public CreditScreen(final int width, final int height, final int fps){
        super(width, height, fps);

        this.returnCode = 4;//셋팅 스크린 나중에 수정

        try{
            this.creditlist = Core.getFileManager().loadCreditList();
            logger.info(""+this.creditlist);
        }  catch (NumberFormatException | IOException e) {
            logger.warning("Couldn't load credit list!");
        }



    }
    public int run(){
        super.run();

        return this.returnCode;
    }

//    private final ArrayList loadcredit(){
//
//
//
//    }

    protected final void update() {
        super.update();

        draw();
        if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
                && this.inputDelay.checkFinished())
            this.isRunning = false;
    }

    private void draw(){
        drawManager.initDrawing(this);
        //drawManager.drawEndingCredit(this,this.creditlist);
        drawManager.completeDrawing(this);
    }

}
