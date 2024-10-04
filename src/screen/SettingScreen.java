package screen;

import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;

public class SettingScreen extends Screen {

    // 상수값 설정 (UI 간격, 속도 등)
    private static final int VOLUME_BAR_WIDTH = 200; // 볼륨바 너비
    private static final int VOLUME_ADJUST_STEP = 10; // 볼륨 조정 단위 (2%)
    private static final int MENU_ITEM_GAP = 120; // 메뉴 항목 간격 (Ending Credit을 더 밑으로 조정)
    private static final int VOLUME_BAR_GAP = 20; // 사운드바와 텍스트 사이 간격 (Sound와 가까워짐)
    private static final int VOLUME_PERCENTAGE_GAP = 40; // 사운드바와 음량 숫자 사이 간격
    private static final int INITIAL_VOLUME = 50; // 초기 볼륨 값
    private static final int COOLDOWN_TIME = 200; // 쿨다운 시간(ms)

    // 메뉴 항목들
    private String[] menuItems = { "Sound", "Ending Credit" }; // 메뉴 항목
    private int selectedItem = 0; // 기본 선택 항목은 'Sound'
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

        if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
            this.isRunning = false; // ESC 키를 누르면 화면 종료
            this.returnCode = 1;    // 이전 화면으로 돌아가기 위한 반환 코드 설정
            return; // ESC 키가 눌렸을 때 다른 업데이트 로직을 실행하지 않도록 리턴
        }

        if (this.selectionCooldown.checkFinished()) {

            // 'Sound'가 기본 선택 상태에서 좌우 화살표로 바로 볼륨 조절 가능
            if (selectedItem == 0) {
                if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                    volumeLevel = Math.max(0, volumeLevel - VOLUME_ADJUST_STEP); // 최소 0%
                    this.selectionCooldown.reset();
                } else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
                    volumeLevel = Math.min(100, volumeLevel + VOLUME_ADJUST_STEP); // 최대 100%
                    this.selectionCooldown.reset();
                }
            }

            // 위아래 화살표로 메뉴 항목 선택
            if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
                selectedItem = (selectedItem - 1 + menuItems.length) % menuItems.length;
                this.selectionCooldown.reset();
            } else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
                selectedItem = (selectedItem + 1) % menuItems.length;
                this.selectionCooldown.reset();
            }

            // 'Ending Credit' 선택 시 다른 화면으로 전환
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && selectedItem == 1) {
                this.returnCode = 7;
                this.isRunning = false; // 화면 전환용 종료 플래그
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
            boolean isSelected = (i == selectedItem); // 메뉴가 선택되었는지 여부
            drawManager.drawCenteredRegularString(this, menuItems[i], this.getHeight() / 3 + i * MENU_ITEM_GAP, isSelected);
        }

        // 'Sound' 텍스트 바로 아래에 볼륨 바를 배치
        int filledWidth = (volumeLevel * VOLUME_BAR_WIDTH) / 100;
        boolean isVolumeSelected = (selectedItem == 0); // 'Sound'가 선택되었는지 여부

        // Sound 텍스트 아래 볼륨 바와 퍼센트 값 그리기 (간격 조정됨)
        drawManager.drawVolumeBar(this, this.getWidth() / 2 - VOLUME_BAR_WIDTH / 2, this.getHeight() / 3 + VOLUME_BAR_GAP, VOLUME_BAR_WIDTH, filledWidth, isVolumeSelected);

        // 사운드바 밑에 퍼센트 값 더 떨어지게 표시
        drawManager.drawVolumePercentage(this, this.getWidth() / 2, this.getHeight() / 3 + VOLUME_BAR_GAP + VOLUME_PERCENTAGE_GAP, volumeLevel, isVolumeSelected);

        drawManager.completeDrawing(this);
    }
}
