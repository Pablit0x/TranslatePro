//
//  iosTranslateProUITests.swift
//  iosTranslateProUITests
//
//  Created by Paweł Szymański on 05/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import XCTest

final class iosTranslateProUITests: XCTestCase {
    
    private var app: XCUIApplication!

    override func setUpWithError() throws {
        continueAfterFailure = false
        app = XCUIApplication()
        app.launchArguments = ["isUiTesting"]
        app.launch()
    }

    override func tearDownWithError() throws {}
    
    func testRecordAndTranslate(){
        app.buttons["Record audio"].tap()
        
        app.buttons["Voice recorder button"].tap()
        app.buttons["Voice recorder button"].tap()

        XCTAssert(app.staticTexts["test output"].waitForExistence(timeout: 3))
        
        app.buttons["Voice recorder button"].tap()
        
        XCTAssert(app.textViews["test output"].waitForExistence(timeout: 3))
        
        app.buttons["TRANSLATE"].tap()
        
        XCTAssert(app.staticTexts["test output"].waitForExistence(timeout: 3))
        XCTAssert(app.staticTexts["Fake Translation"].waitForExistence(timeout: 3))

    }
    
    func testFromLanguage(){
        app.buttons["from language dropdown"].tap()
        app.buttons["Arabic"].tap()
        XCTAssertEqual(app.buttons["from language dropdown"].label, "Arabic")

    }
    
    func testToLanguage(){
        app.buttons["to language dropdown"].tap()
        app.buttons["Arabic"].tap()
        
        XCTAssertEqual(app.buttons["to language dropdown"].label, "Arabic")

    }

    func testTextTranslation(){
        let idleTextField =  app.otherElements.textViews.firstMatch
        let pasteMenuItem = app.menuItems.firstMatch

        UIPasteboard.general.string = "test"
        
        idleTextField.tap()
        idleTextField.tap()
        _ = pasteMenuItem.waitForExistence(timeout: 5)
        pasteMenuItem.tap()
        
        app.buttons["TRANSLATE"].tap()
        
        XCTAssert(app.staticTexts["test"].waitForExistence(timeout: 3))
        XCTAssert(app.staticTexts["Fake Translation"].waitForExistence(timeout: 3))
    }
    
    func testSwapLanguages(){
        app.buttons["from language dropdown"].tap()
        app.buttons["Azerbaijani"].tap()
        
        XCTAssertEqual(app.buttons["from language dropdown"].label, "Azerbaijani")
        
        app.buttons["to language dropdown"].tap()
        app.buttons["Arabic"].tap()
        
        XCTAssertEqual(app.buttons["to language dropdown"].label, "Arabic")
        
        app.buttons["swap languages button"].tap()
        XCTAssertEqual(app.buttons["to language dropdown"].label, "Azerbaijani")
        XCTAssertEqual(app.buttons["from language dropdown"].label, "Arabic")
    }
}
