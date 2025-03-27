@ProgramModule
Feature: Program Module API Testing

  Background: 
    Given Admin logs in with valid credentials and Admin sets Authorization

  Scenario Outline: Add New Program with Valid and Invalid Data with Authorized User
    When User sends a POST request with data from "<sheetName>" row <Testcases>
    Then Response status code should be displayed with with status Message

    Examples: 
      | sheetName | Testcases |
      | Program   |         1 |
      | Program   |         2 |
      | Program   |         3 |
      | Program   |         4 |
      | Program   |         5 |
      | Program   |         6 |
      | Program   |         15 |
      | Program   |         16 |
      

  Scenario Outline: Get All Programs with Valid and Invalid Data
    When User sends a GET request to fetch all programs from "<sheetName>" row <Testcases>
    Then Response status code should be displayed with status Message

    Examples: 
      | sheetName | Testcases |
      | Program   |         7 |

  Scenario Outline: Get Program by ProgramId with Valid Data
    When User sends a GET request to fetch program by valid ProgramId from "<sheetName>" row <Testcases>
    Then Response status code should be displayed with Response body containing program details

    Examples: 
      | sheetName | Testcases |
      | Program   |        8 |

  Scenario Outline: Get All Programs with Valid and Invalid byusers
    When User sends a GET request to fetch all programs by users from "<sheetName>" row <Testcases>
    Then Response status code should be displayed with status Message

    Examples: 
      | sheetName | Testcases |
      | Program   |        9 |

  #updateByProgramID
  Scenario Outline: Validate updating a program by ID with various scenarios
    When User sends a PUT request with programid "<SheetName>" row <Testcases>
    Then Response status code should be displayed and programId should be saved for validuser.

    Examples: 
      | SheetName | Testcases |
      | Program   |        10 |

  #UpdateByProgramName
  Scenario Outline: Validate updating a program by Name with various scenarios
    When User sends a PUT request with programName "<SheetName>" row <Testcases>
    Then Response status code should be displayed and programName should be saved for validuser.

    Examples: 
      | SheetName | Testcases |
      | Program   |        11 |
      | Program   |        12 |

 # DeletebyProgName
  Scenario Outline: Delete program by Name with various scenarios
    When User sends a Delete request with programname "<SheetName>" row <Testcases>
    Then Response status code should be displayed and Data should be deleted.

    Examples: 
      | SheetName | Testcases |
      | Program   |        13 |

 #DeletebyProgId
  Scenario Outline: Delete program by ID with various scenarios
    When User sends a Delete request with programid "<SheetName>" row <Testcases>
    Then Response status code should be displayed and Data should be deleted.

    Examples: 
      | SheetName | Testcases |
      | Program   |       14 |
