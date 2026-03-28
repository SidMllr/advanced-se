@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF)
@echo off

set MAVEN_PROJECTBASEDIR=%~dp0

if "%JAVA_HOME%"=="" (
    echo Error: JAVA_HOME not set.
    exit /B 1
)

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

set DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

if not exist %WRAPPER_JAR% (
    echo Downloading Maven Wrapper...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('%DOWNLOAD_URL%', %WRAPPER_JAR%)"
)

"%JAVA_HOME%\bin\java.exe" -classpath %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
