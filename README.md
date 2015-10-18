Chungame Android Application
===========================================
Copyright (C) 2015 Ihub.

<h2>Dependencies</h2>

1. Eclipse for Android
1. Android SDK. Min sdk is 8
1. Android App Compat version 7
1. Validation Komensky
      https://github.com/inmite/android-validation-komensky

<h2>Running the project</h2>

1. Start Eclipse
1. Import the Google Play Services library project (available for download through the SDK manager):
    1. Click **File | Import | Android | Existing Android Code into Workspace**
    1. Select `RangerApp`
    1. Click **Finish**
1. Import `validation-komensky` **as a library**
    1. Download Validation Komensky
    1. Click **File | Import | Android | Existing Android Code into Workspace**
    1. Select the `validation-komensky` project
    1. Click **Finish**
    1. Right-click on `RangerApp`, then click **Properties**
    1. In the project properties window, click the **Android** section
    1. Add a reference to the `validation-komensky` project (click **Remove** to remove any broken references, then click **Add** to add the correct one)

Your project should now compile.

<h2>Structure</h2>

You will find the following under the **com.ihub.rangerapp** package

    * **adapter**. Contains utility functions used in the app.
    * **data/service**. Contains the CRUD and Data export functions.
    * **data/sqlite**. Contains the schema definition and function for connecting to the sqlite db.
    * **loader**. Contains functions that load data from the database.
    * **model**. Contains definitions of tables in the database.
    * **view**. Contains fragments used in the application.
    * **view/reports**. Contains fragments for the the report viewer.

For support, contact Vincent - vintuwei@gmail.com
