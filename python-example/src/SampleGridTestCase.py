"""
SAMPLE CASE FOR INVENTORY SYSTEM (GRID)
"""

import unittest
import sys

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.common.exceptions import TimeoutException


class SampleTestCases(unittest.TestCase):

    def setUp(self):
        SAUCE_USERNAME = ""
        SAUCE_ACCESS_KEY = ""

        options = ChromeOptions()
        options.browser_version = 'latest'
        options.platform_name = 'Windows 10'

        for arg in sys.argv:
            if arg.startswith('SauceUsername'):
                envCustom = arg
                varReceived = envCustom.split("_")
                SAUCE_USERNAME = varReceived[1]

            if arg.startswith('AccessKey'):
                envCustom = arg
                varReceived = envCustom.split("_")
                SAUCE_ACCESS_KEY = varReceived[1]

        sauce_options = {'username': SAUCE_USERNAME,
                         'accessKey': SAUCE_ACCESS_KEY,
                         'name': "PRUEBA EN SESSION 8"}

        options.set_capability('sauce:options', sauce_options)
        sauce_url = "https://ondemand.us-west-1.saucelabs.com/wd/hub"

        self.driver = webdriver.Remote(
            command_executor=sauce_url, options=options)
        self.baseUrl = 'https://corecode-qa-bootcamp-site.herokuapp.com/login.php'

    def tearDown(self):
        self.driver.quit()

    def get_element_by_name_wait(self, element_id, timeout=3):
        try:
            return WebDriverWait(self.driver, timeout).until(
                EC.presence_of_element_located((By.NAME, element_id)))
        except TimeoutException:
            err = 'Element with id {} could not be found!'
            raise Exception(err.format(element_id))

    def get_element_by_xpath_wait(self, element_id, timeout=3):
        try:
            return WebDriverWait(self.driver, timeout).until(
                EC.presence_of_element_located((By.XPATH, element_id)))
        except TimeoutException:
            err = 'Element with id {} could not be found!'
            raise Exception(err.format(element_id))

    def test_login_success(self):
        self.driver.get(self.baseUrl)

        # get elements
        email = self.get_element_by_name_wait('user_name')
        password = self.get_element_by_name_wait('user_password')

        # set values
        email.send_keys('corecodeqa')
        password.send_keys('Corecode2022!')
        email.send_keys(Keys.RETURN)

        # verify that page was loaded
        searchProductsBar = self.get_element_by_xpath_wait('.//*[@id="q"]')
        assert searchProductsBar.get_attribute(
            'placeholder') == u'Código o nombre del producto'

    def test_login_failed(self):
        self.driver.get(self.baseUrl)

        # get elements
        email = self.get_element_by_name_wait('user_name')
        password = self.get_element_by_name_wait('user_password')

        # set values
        email.send_keys('corecodeqa')
        password.send_keys('Corecode2020!')
        email.send_keys(Keys.RETURN)

        # verify that alert message is shown
        try:
            cont = WebDriverWait(self.driver, 3).until(
                EC.presence_of_element_located(
                    (By.CSS_SELECTOR, '.alert-dismissible')))
        except TimeoutException:
            err = 'Element with id {} could not be found!'
            raise Exception(err.format(''))
        else:
            if 'Usuario y/o contraseña no coinciden o no tiene permisos para sistema de inventario.' in cont.text:
                print('Incorrect login as expected')


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(SampleTestCases)
    unittest.TextTestRunner(verbosity=2).run(suite)
