"""
SAMPLE WEBDRIVER MANAGER
"""

from seleniumwire import webdriver
from selenium.webdriver.firefox.options import Options
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager

def returnDriverFromManager(self, browserOption):
    if browserOption == "FIREFOX":
        options = isFirefox(False)
        return webdriver.Firefox(
            executable_path=GeckoDriverManager().install(), options=options)
    if browserOption == "CHROME":
        options = isChrome(False)
        return webdriver.Chrome(
            ChromeDriverManager().install(), 4040, options=options)
    if browserOption == "H-FIREFOX":
        options = isFirefox(True)
        return webdriver.Firefox(
            executable_path=GeckoDriverManager().install(), options=options)
    if browserOption == "H-CHROME":
        options = isChrome(True)
        return webdriver.Chrome(
            ChromeDriverManager().install(), 4040, options=options)
    else:
        # Chrome if none specified
        isChrome(False)


def isChrome(isHeadless):
    """Chrome driver"""
    print("DRIVER SELECTED: Using Chrome")

    chrome_options = webdriver.ChromeOptions()
    if (isHeadless):
        chrome_options.add_argument('--headless')

    chrome_options.add_argument("--kiosk")
    chrome_options.add_argument("window-size=1920,1080")

    return chrome_options


def isFirefox(isHeadless):
    """Firefox driver"""
    print("DRIVER SELECTED: Using Firefox")

    firefox_options = Options()
    if (isHeadless):
        firefox_options.add_argument('--headless')

    firefox_options.add_argument("--kiosk")

    return firefox_options



class ManageDriverClass:
    pass
