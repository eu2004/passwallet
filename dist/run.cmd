set JAVA_HOME=jre1.8.0_144

rem %JAVA_HOME%\bin\java -classpath ".;classes" ro.eu.passwallet.client.flexui.ApplicationLauncher
D:\openjdk-15.0.2\bin\java --module-path="." --add-modules=javafx.controls,javafx.fxml -jar passwallet-fx-client.jar