@class2 
Feature: Class feature

  Background: Admin sets Authorization to Bearer Token.

  @ClassModule_StaffID1
  Scenario Outline: Check if admin able to retrieve all Classes with valid StaffId (allclasses by staffId)
    Given Admin creates GET Request for CLass module(allclasses by staffId)
    When Admin sends HTTPS Request in ClassModule_StaffID with "<scenarioType>"
    Then Admin receives Status in ClassModule_StaffID with response <statusCode>

    Examples: 
      | scenarioType     | statusCode |
      | valid staffId    |        200 |
      | invalid staffId  |        404 |
      | invalid endpoint |        404 |
      | invalid method   |        405 |
      | no authorization |        401 |

  @ClassModule_recording
  Scenario Outline: Check if admin able to retrieve all class recording
    Given Admin creates GET Request for ClassModule_recording
    When Admin sends HTTPS Request in ClassModule_recording with "<scenarioType>"
    Then Admin receives Status in ClassModule_recording with response <statusCode>

    Examples: 
      | scenarioType     | statusCode |
      | valid            |        200 |
      | invalid endpoint |        404 |
      | invalid method   |        405 |
      | no authorization |        401 |

  @ClassModule_recordingbyCLassID
  Scenario Outline: Check if admin able to retrieve all class recording by classID
    Given Admin creates GET Request for ClassModule_recordingbyCLassID
    When Admin sends HTTPS Request in ClassModule_recordingbyCLassID with "<scenarioType>"
    Then Admin receives Status in ClassModule_recordingbyCLassID with response <statusCode>

    Examples: 
      | scenarioType     | statusCode |
      | valid csId       |        200 |
      | invalid csId     |        404 |
      | invalid endpoint |        404 |
      | invalid method   |        405 |
      | no authorization |        401 |

  @ClassModule_UpdateNewClass
  Scenario Outline: Check if admin able to update a Class with valid classID and mandatory fields in request body ClassModule_UpdateNewClass
    Given Admin creates PUT Request for ClassModule_UpdateNewClass
    When Admin sends HTTPS Request in ClassModule_UpdateNewClass with "<scenarioType>"
    Then Admin receives Status in ClassModule_UpdateNewClass with response <statusCode>

    Examples: 
      | scenarioType    | statusCode |
      | validclassID    |        200 |
      | invalidclassID  |        404 |
      | invaliddata     |        400 |
      | missingData     |        400 |
      | invalidendpoint |        404 |
      | invalidmethod   |        405 |
      | noauthorization |        401 |

  @ClassModule_UpdateClassRecordingpath
  Scenario Outline: Check if admin able to update a Class with valid classID and mandatory fields in request body ClassModule_UpdateClassRecordingpath
    Given Admin creates PUT Request for ClassModule_UpdateClassRecordingpath
    When Admin sends HTTPS Request in ClassModule_UpdateClassRecordingpath with "<scenarioType>"
    Then Admin receives Status in ClassModule_UpdateClassRecordingpath with response <statusCode>

    Examples: 
      | scenarioType    | statusCode |
      | validclassID    |        200 |
      | invalidclassID  |        404 |
      | invaliddata     |        400 |
      | missingData     |        400 |
      | invalidendpoint |        404 |
      | invalidmethod   |        405 |
      | noauthorization |        401 |

  @DeleteClass_CSID
  Scenario Outline: Check if admin able to delete a class with valid class ID DeleteClass_CSID
    Given Admin creates DELETE Request with valid classId DeleteClass_CSID
    When Admin sends HTTPS Request  with endpoint DeleteClass_CSID "<scenarioType>"
    Then Admin receives Status in DeleteClass_CSID with response <statusCode>

    Examples: 
      | scenarioType    | statusCode |
      | validclassID    |        200 |
      | invalidendpoint |        404 |
      | invalidclassID  |        404 |
      | noauthorization |        401 |
