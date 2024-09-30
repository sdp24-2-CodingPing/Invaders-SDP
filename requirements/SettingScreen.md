/* SettingScreen 코드

package screen;

import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;

public class SettingScreen extends Screen {

    // 상수값 설정 (UI 간격, 속도 등)
    private static final int VOLUME_BAR_WIDTH = 200; // 볼륨바 너비
    private static final int VOLUME_ADJUST_STEP = 2; // 볼륨 조정 단위 (2%)
    private static final int MENU_ITEM_GAP = 150; // 메뉴 항목 간격
    private static final int INITIAL_VOLUME = 50; // 초기 볼륨 값
    private static final int COOLDOWN_TIME = 200; // 쿨다운 시간(ms)

    // 메뉴 항목들
    private String[] menuItems = { "Sound", "Ending Credit" }; // 메뉴 항목
    private int selectedItem = 0; // 기본 선택 항목은 'Sound'
    private boolean soundSelected = false; // 'Sound'가 선택된 상태인지 확인하는 플래그
    private int volumeLevel = INITIAL_VOLUME; // 볼륨 기본 값 (0-100 범위)
    private Cooldown selectionCooldown; // 메뉴 선택 및 볼륨 조절에서 빠른 입력 방지용 쿨다운

    public SettingScreen(int width, int height, int fps) {
        super(width, height, fps);
        this.returnCode = 1; // 기본값 설정
        this.selectionCooldown = Core.getCooldown(COOLDOWN_TIME); // 200ms 딜레이
    }

    @Override
    protected void update() {
        super.update();

        if (this.selectionCooldown.checkFinished()) {
            if (soundSelected) {
                // 'Sound'가 선택된 상태에서는 좌우 화살표로 볼륨 조절
                if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                    volumeLevel = Math.max(0, volumeLevel - VOLUME_ADJUST_STEP); // 최소 0%
                    this.selectionCooldown.reset();
                } else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
                    volumeLevel = Math.min(100, volumeLevel + VOLUME_ADJUST_STEP); // 최대 100%
                    this.selectionCooldown.reset();
                }

                if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                    // 스페이스바를 다시 누르면 'Sound' 선택 해제
                    soundSelected = false;
                    this.selectionCooldown.reset();
                }
            } else {
                // 위아래 화살표로 메뉴 항목 선택
                if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
                    selectedItem = (selectedItem - 1 + menuItems.length) % menuItems.length;
                    this.selectionCooldown.reset();
                } else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
                    selectedItem = (selectedItem + 1) % menuItems.length;
                    this.selectionCooldown.reset();
                }

                if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                    if (selectedItem == 0) {
                        // 'Sound' 선택 시 볼륨 조정 모드로 진입
                        soundSelected = true;
                    } else if (selectedItem == 1) {
                        // 'Ending Credit' 버튼을 선택하면 다른 화면으로 전환 가능
                        System.out.println("Ending Credit selected");
                        this.isRunning = false; // 화면 전환용 종료 플래그
                    }
                    this.selectionCooldown.reset();
                }
            }
        }

        draw(); // 화면 그리기
    }

    @Override
    public int run() {
        super.run();
        return this.returnCode;
    }

    /**
     * 설정 화면을 그리는 메서드. 타이틀, 메뉴 항목, 볼륨 바를 그린다.
     */
    private void draw() {
        drawManager.initDrawing(this);

        // 'Settings' 타이틀 그리기
        drawManager.drawSettingsScreen(this);

        // 메뉴 항목 그리기 (선택 여부에 따라 색상 변경)
        for (int i = 0; i < menuItems.length; i++) {
            boolean isSelected = (i == selectedItem && !soundSelected); // 메뉴가 선택되었는지 여부
            drawManager.drawCenteredRegularString(this, menuItems[i], this.getHeight() / 3 + i * MENU_ITEM_GAP, isSelected);
        }

        // 볼륨 바 및 'Sound' 텍스트 그리기 (항상 표시되며 선택 여부에 따라 색상 변경)
        int filledWidth = (volumeLevel * VOLUME_BAR_WIDTH) / 100;
        boolean isVolumeSelected = (selectedItem == 0); // 'Sound'가 선택되었는지 여부

        // 'Sound'와 볼륨 바의 y 위치 계산
        int soundYPosition = this.getHeight() / 3; // 'Sound' 텍스트의 y 위치
        int volumeBarYPosition = soundYPosition + 20; // 음량 조절 바의 y 위치
        int volumePercentYPosition = volumeBarYPosition + 30; // 퍼센트 텍스트의 y 위치

        // 'Sound' 텍스트와 볼륨 바 그리기
        drawManager.drawVolumeBar(this, this.getWidth() / 2 - VOLUME_BAR_WIDTH / 2, volumeBarYPosition, VOLUME_BAR_WIDTH, filledWidth, isVolumeSelected);
        drawManager.drawVolumePercentage(this, this.getWidth() / 2, volumePercentYPosition, volumeLevel, isVolumeSelected);

        drawManager.completeDrawing(this);
    }

}
