"""
SUITE OF TESTS
"""

import datetime
import unittest

import HtmlTestRunner

from SampleTestCase import SampleTestCases

sample_test_cases = unittest.TestLoader().loadTestsFromTestCase(SampleTestCases)

test_suite = unittest.TestSuite([
    sample_test_cases
])

# configure HTMLTestRunner options

# getting current year, month and day

d = datetime.datetime.today()
currentYear = d.year
currentMonth = d.month
currentDay = d.day
currentHour = d.hour
currentMinute = d.minute
currentDate = str(currentYear) + '-' + str(currentMonth) + '-' \
    + str(currentDay) + '-'
reportFolderName = 'reports/' + currentDate + str(currentHour) + '-' \
                   + str(currentMinute) + '-' + 'result-local-suite'

runner = HtmlTestRunner.HTMLTestRunner(output=reportFolderName)

# run the suite using HTMLTestRunner then upload and notify results if needed

runner.run(test_suite)
