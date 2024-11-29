package screen;

import engine.Cooldown;
import engine.Core;
import engine.Sound;
import engine.SoundManager;
import engine.drawmanager.StopDrawManager;
import java.awt.event.KeyEvent;

public class StopScreen extends Screen {
  /** 선택 항목 간의 변경 대기 시간(밀리초). */
  private static final int SELECTION_TIME = 200;

  /** 선택 변경 대기 시간. */
  private Cooldown selectionCooldown;

  /** SoundManager의 싱글톤 인스턴스 */
  private final SoundManager soundManager = SoundManager.getInstance();

  /** 화면에 표시될 옵션 */
  private static final String[] options = {">Resume", ">Back to Main Menu", ">Quit Game"};

  /** 현재 선택된 옵션 */
  private int selectedOption = 0;

  /**
   * 생성자, 화면 속성을 설정합니다.
   *
   * @param width 화면 너비.
   * @param height 화면 높이.
   * @param fps 초당 프레임 수 (FPS).
   */
  public StopScreen(final int width, final int height, final int fps) {
    super(width, height, fps);
    this.returnCode = 0;
    this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
    this.selectionCooldown.reset();
  }

  /**
   * 화면을 실행합니다.
   *
   * @return 다음 화면 코드.
   */
  public final int run() {
    super.run();
    return this.returnCode;
  }

  /** 화면의 요소를 업데이트하고 이벤트를 확인합니다. */
  protected final void update() {
    super.update();

    draw();
    if (this.selectionCooldown.checkFinished()) {
      if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
        this.selectedOption = (this.selectedOption - 1 + options.length) % options.length;
        this.selectionCooldown.reset();
        soundManager.playSound(Sound.MENU_MOVE);
      } else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
        this.selectedOption = (this.selectedOption + 1) % options.length;
        this.selectionCooldown.reset();
        soundManager.playSound(Sound.MENU_MOVE);
      }

      if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
        switch (selectedOption) {
          case 0:
            // 게임 재개
            this.isRunning = false; // StopScreen 종료
            soundManager.playSound(Sound.MENU_CLICK);
            Core.getInputManager().flushKeys();
            break;

          case 1:
            this.returnCode = 1; // 메인 메뉴로 돌아가기 위한 returnCode 설정
            this.isRunning = false; // StopScreen 종료
            soundManager.playSound(Sound.MENU_CLICK);
            break;

          case 2:
            // 게임 종료
            System.exit(0); // 게임 완전 종료
            break;
        }
      }

      // ESC 키 입력 시 게임 재개
      if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
        this.isRunning = false; // StopScreen 종료
        soundManager.playSound(Sound.MENU_BACK);
      }
    }
  }

  /** 화면과 관련된 요소들을 그립니다. */
  private void draw() {
    drawManager.initDrawing(this);

    StopDrawManager.drawPauseOverlay(this, this.getHeight() / 4);

    for (int i = 0; i < options.length; i++) {
      boolean isSelected = (i == selectedOption);
      StopDrawManager.drawLeftAlignedRegularString(
          this, options[i], 200, this.getHeight() / 2 + i * 50, isSelected);
    }

    drawManager.completeDrawing(this);
  }
}
