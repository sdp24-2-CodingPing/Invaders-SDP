# scripts/parse_test_results.py

import sys
import os
import xml.etree.ElementTree as ET

# 최대 표시할 실패한 테스트 수
MAX_FAILED_TESTS = 10
# 에러 메시지 최대 길이
MAX_ERROR_MESSAGE_LENGTH = 100

def parse_test_results(test_results_dir):
    total_tests = 0
    total_failures = 0
    total_errors = 0
    total_skipped = 0
    failed_tests = []

    for root_dir, dirs, files in os.walk(test_results_dir):
        for file in files:
            if file.endswith('.xml'):
                tree = ET.parse(os.path.join(root_dir, file))
                root_element = tree.getroot()

                total_tests += int(root_element.attrib.get('tests', 0))
                total_failures += int(root_element.attrib.get('failures', 0))
                total_errors += int(root_element.attrib.get('errors', 0))
                total_skipped += int(root_element.attrib.get('skipped', 0))

                for testcase in root_element.findall('testcase'):
                    # 실패한 테스트 케이스 파싱
                    failures = testcase.findall('failure') + testcase.findall('error')
                    for failure in failures:
                        message = failure.attrib.get('message', '').strip()
                        if len(message) > MAX_ERROR_MESSAGE_LENGTH:
                            message = message[:MAX_ERROR_MESSAGE_LENGTH] + '...'
                        failed_tests.append({
                            'classname': testcase.attrib.get('classname', ''),
                            'name': testcase.attrib.get('name', ''),
                            'message': message
                        })

    passed_tests = total_tests - total_failures - total_errors - total_skipped

    return {
        'total': total_tests,
        'passed': passed_tests,
        'failures': total_failures,
        'errors': total_errors,
        'skipped': total_skipped,
        'failed_tests': failed_tests
    }

def generate_summary(result):
    status = '✅ **테스트 성공**' if result['failures'] == 0 and result['errors'] == 0 else '❌ **테스트 실패**'
    summary = f"{status}\n\n"
    summary += f"- 총 테스트: {result['total']}개\n"
    summary += f"- 성공: {result['passed']}개\n"
    summary += f"- 실패: {result['failures']}개\n"
    summary += f"- 에러: {result['errors']}개\n"
    summary += f"- 스킵됨: {result['skipped']}개\n\n"

    if result['failed_tests']:
        summary += "**실패한 테스트 상세 내역:**\n\n"
        summary += "| 번호 | 테스트 클래스 | 테스트 메서드 | 에러 메시지 |\n"
        summary += "|---|---|---|---|\n"

        for idx, test in enumerate(result['failed_tests'][:MAX_FAILED_TESTS], 1):
            summary += f"| {idx} | `{test['classname']}` | `{test['name']}` | `{test['message']}` |\n"

        remaining_failures = len(result['failed_tests']) - MAX_FAILED_TESTS
        if remaining_failures > 0:
            summary += f"\n...외 {remaining_failures}개의 실패한 테스트가 있습니다.\n"

        summary += "\n자세한 내용은 테스트 리포트를 확인해주세요."

    else:
        summary += "모든 테스트를 성공적으로 통과했습니다! 🎉"

    return summary

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: parse_test_results.py <test_results_dir>")
        sys.exit(1)

    test_results_dir = sys.argv[1]

    if not os.path.exists(test_results_dir):
        print("❌ 테스트 결과 디렉터리가 존재하지 않습니다.")
        sys.exit(0)

    result = parse_test_results(test_results_dir)
    summary = generate_summary(result)
    print(summary)
