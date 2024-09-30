/* DrqwManager에 추가할 코드

	public void drawSettingsScreen(final Screen screen) {
			String settingsTitle = "Settings"; // 타이틀

			// 타이틀을 초록색으로 중앙에 그리기
			backBufferGraphics.setColor(Color.GREEN);
			drawCenteredBigString(screen, settingsTitle, screen.getHeight() / 8);
		}

		/** 볼륨 바를 그리는 메서드 */
		public void drawVolumeBar(Screen screen, int x, int y, int totalWidth, int filledWidth, boolean isSelected) {
			// 선택된 경우 초록색, 그렇지 않으면 흰색으로 표시
			backBufferGraphics.setColor(isSelected ? Color.GREEN : Color.WHITE);
			backBufferGraphics.fillRect(x, y, filledWidth, 10); // 채워진 부분

			// 나머지 부분은 회색으로 표시
			backBufferGraphics.setColor(Color.GRAY);
			backBufferGraphics.fillRect(x + filledWidth, y, totalWidth - filledWidth, 10); // 바의 나머지 부분
		}

		/** 퍼센트 값을 그리는 메서드 */
		public void drawVolumePercentage(Screen screen, int x, int y, int volume, boolean isSelected) {
			String volumeText = volume + "%";
			// 선택된 경우 초록색, 그렇지 않으면 흰색으로 표시
			backBufferGraphics.setColor(isSelected ? Color.GREEN : Color.WHITE);
			drawCenteredRegularString(screen, volumeText, y); // 퍼센트 값을 중앙에 표시
		}

		public void drawCenteredRegularString(final Screen screen,
											  final String string, final int height, boolean isSelected) {
			backBufferGraphics.setFont(fontRegular);
			// 선택된 경우 초록색, 그렇지 않으면 흰색으로 표시
			backBufferGraphics.setColor(isSelected ? Color.GREEN : Color.WHITE);
			backBufferGraphics.drawString(string, screen.getWidth() / 2
					- fontRegularMetrics.stringWidth(string) / 2, height);
		}


		/
