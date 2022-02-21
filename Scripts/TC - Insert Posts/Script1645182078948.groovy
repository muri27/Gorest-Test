import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper
import static org.assertj.core.api.Assertions.*

response = WS.sendRequest(findTestObject('Posts/Insert Posts', [('Title') : Title, ('Body') : Body, ('UserId') : UserId]))

if (WS.verifyResponseStatusCode(response, 201, FailureHandling.OPTIONAL)) {
    response2 = WS.sendRequest(findTestObject('Posts/Get Posts', [('UserId') : UserId]))

    def jsonSlurper = new JsonSlurper()

    def jsonResponse = jsonSlurper.parseText(response2.getResponseText())

    assertThat(jsonResponse.user_id.toString()).contains(UserId)

    assertThat(jsonResponse.title.toString()).contains(Title)

    assertThat(jsonResponse.body.toString()).contains(Body)
}

if (WS.verifyResponseStatusCode(response, 422, FailureHandling.OPTIONAL)) {
    print('Title/Body Cannot Be Empty !')
} else if (WS.verifyResponseStatusCode(response, 401, FailureHandling.OPTIONAL)) {
    print('Authorization Failed')
} else if (WS.verifyResponseStatusCode(response, 404, FailureHandling.OPTIONAL)) {
    print('Check Your Endpoint')
}

